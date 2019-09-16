package com.loftschool.loftcoin.presentation.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.data.CoinsRepository;
import com.loftschool.loftcoin.data.dto.Coin;
import com.loftschool.loftcoin.domain.CoinRate;
import com.loftschool.loftcoin.util.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public final class RatesViewModel extends ViewModel {


	private final CoinsRepository coinsRepository;
	private final Function<List<Coin>, List<CoinRate>> ratesMapper;

	private final MutableLiveData<List<CoinRate>> coinRates = new MutableLiveData<>();
	private final MutableLiveData<Boolean> loadingState = new MutableLiveData<>();
	private final MutableLiveData<Throwable> errorState = new MutableLiveData<>();

	private Currency currency = Currency.USD;

	@Inject
	public RatesViewModel(@NonNull final CoinsRepository coinsRepository,
	                      @NonNull final Function<List<Coin>, List<CoinRate>> ratesMapper) {
		this.coinsRepository = Objects.requireNonNull(coinsRepository);
		this.ratesMapper = Objects.requireNonNull(ratesMapper);
		refresh();
	}

	@NonNull
	public LiveData<List<CoinRate>> getCoinRates() {
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

	public void setCurrency(@NonNull final Currency value) {
		Objects.requireNonNull(value);
		if (currency != value) {
			currency = value;
		}
		refresh();
	}

	void refresh() {
		loadingState.postValue(true);
		final String convert = currency.name();
		coinsRepository.listings(convert,
			coins -> {
				coinRates.postValue(ratesMapper.apply(coins));
				loadingState.postValue(false);
			},
			error -> {
				errorState.postValue(error);
				loadingState.postValue(false);
			});
	}
}
