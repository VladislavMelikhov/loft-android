package com.loftschool.loftcoin.presentation.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.data.CoinsRepository;
import com.loftschool.loftcoin.data.Currencies;
import com.loftschool.loftcoin.data.Currency;
import com.loftschool.loftcoin.db.CoinEntity;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public final class RatesViewModel extends ViewModel {


	private final CoinsRepository coinsRepository;

	private final LiveData<List<CoinEntity>> coinRates;
	private final MutableLiveData<Boolean> loadingState = new MutableLiveData<>();
	private final MutableLiveData<Throwable> errorState = new MutableLiveData<>();
	private final Currencies currencies;

	@Inject
	public RatesViewModel(@NonNull final CoinsRepository coinsRepository,
	                      @NonNull final Currencies currencies) {
		this.coinsRepository = Objects.requireNonNull(coinsRepository);
		this.currencies = Objects.requireNonNull(currencies);
		this.coinRates = this.coinsRepository.listings();
		refresh();
	}

	@NonNull
	public LiveData<List<CoinEntity>> getCoinRates() {
		return coinRates;
	}

	@NonNull
	public LiveData<Boolean> getLoadingState() {
		return loadingState;
	}

	@NonNull
	public LiveData<Throwable> getErrorState() {
		return errorState;
	}

	void refresh() {
		loadingState.postValue(true);
		final Currency currency = currencies.getCurrent();

		coinsRepository.refresh(currency.getCode(),
			() -> {
				loadingState.postValue(false);
			},
			error -> {
				errorState.postValue(error);
				loadingState.postValue(false);
			});
	}

	void updateCurrency(@NonNull final Currency currency) {
		Objects.requireNonNull(currency);
		currencies.setCurrent(currency);
		refresh();
	}
}
