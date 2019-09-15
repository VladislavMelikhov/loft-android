package com.loftschool.loftcoin.presentation.main;

import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.presentation.ConverterFragment;
import com.loftschool.loftcoin.presentation.WalletFragment;
import com.loftschool.loftcoin.presentation.rates.ExchangeRatesFragment;
import com.loftschool.loftcoin.util.Supplier;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
public interface MainModule {

	@Provides
	static SparseArrayCompat<Supplier<Fragment>> fragments() {
		final SparseArrayCompat<Supplier<Fragment>> fragments = new SparseArrayCompat<>();
		fragments.put(R.id.wallets, WalletFragment::new);
		fragments.put(R.id.exchange_rate, ExchangeRatesFragment::new);
		fragments.put(R.id.converter, ConverterFragment::new);
		return fragments;
	}

	@Binds
	@IntoMap
	@ClassKey(MainViewModel.class)
	ViewModel mainViewModel(MainViewModel impl);
}
