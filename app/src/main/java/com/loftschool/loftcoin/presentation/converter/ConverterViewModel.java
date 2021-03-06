package com.loftschool.loftcoin.presentation.converter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.data.CoinsRepository;
import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.domain.PriceFormatter;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public final class ConverterViewModel extends ViewModel {

	private final Subject<Integer> from = BehaviorSubject.createDefault(0);
	private final Subject<Integer> to = BehaviorSubject.createDefault(1);

	private final Subject<String> fromValue = BehaviorSubject.create();
	private final Subject<String> toValue = BehaviorSubject.create();

	private final Observable<List<CoinEntity>> topCoins;

	private final Observable<CoinEntity> fromCoin;
	private final Observable<CoinEntity> toCoin;

	private final Observable<Double> factor;

	private final RxSchedulers schedulers;
	private final PriceFormatter priceFormatter;


	@Inject
	ConverterViewModel(@NonNull final CoinsRepository coinsRepository,
	                   @NonNull final RxSchedulers rxSchedulers,
	                   @NonNull final PriceFormatter priceFormatter) {

		this.schedulers = Objects.requireNonNull(rxSchedulers);
		this.priceFormatter = Objects.requireNonNull(priceFormatter);

		topCoins = Objects.requireNonNull(coinsRepository)
			.top(5)
			.replay(1)
			.autoConnect()
			.subscribeOn(schedulers.io());

		fromCoin = topCoins
			.switchMap(coins ->
				from.map(coins::get)
			)
			.replay(1)
			.autoConnect()
			.subscribeOn(schedulers.io());

		toCoin = topCoins
			.switchMap(coins ->
				to.map(coins::get)
			)
			.replay(1)
			.autoConnect()
			.subscribeOn(schedulers.io());

		factor = fromCoin
			.switchMap(f ->
				toCoin.map(t ->
					f.price() / t.price()
				)
			)
			.replay(1)
			.autoConnect()
			.subscribeOn(schedulers.io());
	}

	@NonNull
	Observable<List<CoinEntity>> topCoins() {
		return topCoins.observeOn(schedulers.main());
	}

	@NonNull
	Observable<CoinEntity> fromCoin() {
		return fromCoin.observeOn(schedulers.main());
	}

	@NonNull
	Observable<CoinEntity> toCoin() {
		return toCoin.observeOn(schedulers.main());
	}

	void changeFromCoin(int position) {
		from.onNext(position);
	}

	void changeToCoin(int position) {
		to.onNext(position);
	}

	void changeFromValue(String text) {
		fromValue.onNext(text);
	}

	void changeToValue(String text) {
		toValue.onNext(text);
	}

	@NonNull
	Observable<String> toValue() {
		return fromValue
			.compose(parseValue())
			.switchMap(value ->
				factor.map(factor ->
					value * factor
				)
			)
			.compose(formatValue())
			.subscribeOn(schedulers.io())
			.observeOn(schedulers.main());
	}

	@NonNull
	Observable<String> fromValue() {
		return toValue
			.compose(parseValue())
			.switchMap(value ->
				factor.map(factor ->
					value / factor
				)
			)
			.compose(formatValue())
			.subscribeOn(schedulers.io())
			.observeOn(schedulers.main());
	}

	@NonNull
	private ObservableTransformer<String, Double> parseValue() {
		return upstream -> upstream
			.distinctUntilChanged()
			.map(value -> value.isEmpty() ? "0" : value)
			.map(value -> value.trim().replace(",", ""))
			.map(value -> value.replaceAll("\\s+", ""))
			.map(Double::parseDouble);
	}

	@NonNull
	private ObservableTransformer<Double, String> formatValue() {
		return upstream -> upstream.map(value -> {
			if (value > 0) {
				return priceFormatter.format(value, "");
			} else {
				return "";
			}
		});
	}
}
