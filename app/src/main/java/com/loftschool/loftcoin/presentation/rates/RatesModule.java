package com.loftschool.loftcoin.presentation.rates;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.AppComponent;
import com.loftschool.loftcoin.data.CoinsRepository;
import com.loftschool.loftcoin.data.Currencies;
import com.loftschool.loftcoin.data.dto.Coin;
import com.loftschool.loftcoin.domain.CoinRate;
import com.loftschool.loftcoin.util.Function;

import java.util.List;
import java.util.Objects;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
public interface RatesModule {

	@Provides
	@Reusable
	static AppComponent appComponent(@NonNull final Fragment fragment) {
		Objects.requireNonNull(fragment);
		return AppComponent.from(fragment.requireContext());
	}

	@Provides
	static CoinsRepository coinsRepository(@NonNull final AppComponent appComponent) {
		Objects.requireNonNull(appComponent);
		return appComponent.coinsRepository();
	}

	@Provides
	static Currencies currencies(@NonNull final AppComponent appComponent) {
		Objects.requireNonNull(appComponent);
		return appComponent.currencies();
	}

	@Binds
	Function<List<Coin>, List<CoinRate>> ratesMapper(RatesMapper impl);

	@Binds
	@IntoMap
	@ClassKey(RatesViewModel.class)
	ViewModel ratesViewModel(RatesViewModel impl);
}
