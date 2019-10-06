package com.loftschool.loftcoin.presentation.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.data.CoinsRepository;
import com.loftschool.loftcoin.data.Currencies;
import com.loftschool.loftcoin.data.Currency;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public final class RatesViewModel extends ViewModel {

	private final CoinsRepository coinsRepository;
	private final Currencies currencies;
	private final RxSchedulers schedulers;

	private final Subject<Boolean> sourceOfTruth;
	private final Observable<RatesUiState> uiState;

	@Inject
	public RatesViewModel(@NonNull final CoinsRepository coinsRepository,
	                      @NonNull final Currencies currencies,
	                      @NonNull final RxSchedulers schedulers) {
		this.coinsRepository = Objects.requireNonNull(coinsRepository);
		this.currencies = Objects.requireNonNull(currencies);
		this.schedulers = Objects.requireNonNull(schedulers);

		this.sourceOfTruth = BehaviorSubject.createDefault(true);

		this.uiState = sourceOfTruth
			.observeOn(schedulers.io())
			.map(refresh -> currencies.getCurrent().getCode())
			.flatMap(currencyCode -> coinsRepository
				.listings(currencyCode)
				.map(RatesUiState::success)
				.onErrorReturn(RatesUiState::failure)
				.startWith(RatesUiState.loading()))
			.subscribeOn(schedulers.io());
	}

	void refresh() {
		sourceOfTruth.onNext(true);
	}

	@NonNull
	public Observable<RatesUiState> uiState() {
		return uiState.observeOn(schedulers.main());
	}

	void updateCurrency(@NonNull final Currency currency) {
		Objects.requireNonNull(currency);
		currencies.setCurrent(currency);
		refresh();
	}
}
