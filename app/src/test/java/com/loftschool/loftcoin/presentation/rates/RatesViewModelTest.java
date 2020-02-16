package com.loftschool.loftcoin.presentation.rates;

import com.loftschool.loftcoin.TestSchedulers;
import com.loftschool.loftcoin.data.CoinsRepository;
import com.loftschool.loftcoin.data.Currencies;
import com.loftschool.loftcoin.data.Currency;
import com.loftschool.loftcoin.db.CoinEntity;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
public class RatesViewModelTest {

	private Currencies currencies;
	private RatesViewModel viewModel;
	private List<CoinEntity> usdCoins;
	private List<CoinEntity> eurCoins;

	@Before
	public void setUp() throws Exception {
		final CoinsRepository coinsRepository = mock(CoinsRepository.class);

		usdCoins = new ArrayList<>();
		usdCoins.add(CoinEntity.create(1, "Bitcoin", "BTC", 12345, +5.43));
		usdCoins.add(CoinEntity.create(1, "Etherium", "ETH", 1234, -1.54));
		given(coinsRepository.listings("USD")).willReturn(Observable.just(usdCoins));

		eurCoins = new ArrayList<>();
		eurCoins.add(CoinEntity.create(1, "Bitcoin", "BTC", 12323, +5.43));
		eurCoins.add(CoinEntity.create(1, "Etherium", "ETH", 1233, -1.54));
		given(coinsRepository.listings("EUR")).willReturn(Observable.just(eurCoins));

		given(coinsRepository.listings("RUB")).willReturn(Observable.error(new AssertionError("RUB")));

		currencies = mock(Currencies.class);

		given(currencies.current()).willReturn(Observable.just(Currency.USD));

		viewModel = new RatesViewModel(coinsRepository, currencies, new TestSchedulers());
	}

	@Test
	public void uiState_when_everything_is_ok() {
		final TestObserver<RatesUiState> observer = viewModel.uiState().test();

		observer.awaitCount(2);
		viewModel.refresh();
		observer.awaitCount(4);
		observer.assertValues(
			RatesUiState.loading(),
			RatesUiState.success(usdCoins),
			RatesUiState.loading(),
			RatesUiState.success(usdCoins)
		);
	}

	@Test
	public void uiState_when_repo_throws_error() {
		given(currencies.current()).willReturn(Observable.just(Currency.RUB));
		final TestObserver<RatesUiState> observer = viewModel.uiState().test();

		observer.awaitCount(2);
		observer.assertValues(
			RatesUiState.loading(),
			RatesUiState.failure(new AssertionError("RUB"))
		);
	}

	@Test
	public void uiState_on_currency_change() {
		final Subject<Currency> subject = BehaviorSubject.createDefault(Currency.USD);
		given(currencies.current()).willReturn(subject);

		final TestObserver<RatesUiState> observer = viewModel.uiState().test();

		observer.awaitCount(2);
		subject.onNext(Currency.EUR);
		observer.awaitCount(4);
		observer.assertValues(
			RatesUiState.loading(),
			RatesUiState.success(usdCoins),
			RatesUiState.loading(),
			RatesUiState.success(eurCoins)
		);
	}
}