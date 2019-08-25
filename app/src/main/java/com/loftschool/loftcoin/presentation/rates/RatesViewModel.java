package com.loftschool.loftcoin.presentation.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.loftschool.loftcoin.data.ApiFactory;
import com.loftschool.loftcoin.data.CoinsRepository;

import java.util.Objects;

public final class RatesViewModel extends ViewModel {

	static final class Factory implements ViewModelProvider.Factory {

		@NonNull
		@Override
		public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
			return (T) new RatesViewModel(new CoinsRepository(new ApiFactory().createCoinMarketCapApi()));
		}
	}

	private final CoinsRepository coinsRepository;

	public RatesViewModel(@NonNull final CoinsRepository coinsRepository) {
		this.coinsRepository = Objects.requireNonNull(coinsRepository);
		refresh();
	}

	void refresh() {
		coinsRepository.listings("USD",
			coins -> {

			},
			error -> {

			});
	}
}
