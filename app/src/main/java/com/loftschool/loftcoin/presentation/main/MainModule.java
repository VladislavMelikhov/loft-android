package com.loftschool.loftcoin.presentation.main;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.AppComponent;
import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.fcm.FcmChannel;
import com.loftschool.loftcoin.presentation.converter.ConverterFragment;
import com.loftschool.loftcoin.presentation.wallets.WalletsFragment;
import com.loftschool.loftcoin.presentation.rates.ExchangeRatesFragment;
import com.loftschool.loftcoin.util.Supplier;

import java.util.Objects;

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
		fragments.put(R.id.wallets, WalletsFragment::new);
		fragments.put(R.id.exchange_rate, ExchangeRatesFragment::new);
		fragments.put(R.id.converter, ConverterFragment::new);
		return fragments;
	}

	@Provides
	static FcmChannel fcmChannel(@NonNull final FragmentActivity activity) {
		Objects.requireNonNull(activity);
		return AppComponent.from(activity.getApplicationContext()).fcmChannel();
	}

	@Binds
	@IntoMap
	@ClassKey(MainViewModel.class)
	ViewModel mainViewModel(MainViewModel impl);
}
