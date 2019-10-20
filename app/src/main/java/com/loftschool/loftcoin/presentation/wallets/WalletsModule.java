package com.loftschool.loftcoin.presentation.wallets;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.AppComponent;
import com.loftschool.loftcoin.data.Currencies;
import com.loftschool.loftcoin.data.WalletsRepository;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.util.Locale;
import java.util.Objects;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
interface WalletsModule {

	@Provides
	@Reusable
	static AppComponent appComponent(@NonNull final Fragment fmt) {
		Objects.requireNonNull(fmt);
		return AppComponent.from(fmt.requireContext());
	}

	@Provides
	static WalletsRepository walletsRepository(@NonNull final AppComponent appComponent) {
		Objects.requireNonNull(appComponent);
		return appComponent.walletsRepository();
	}

	@Provides
	static Currencies currencies(@NonNull final AppComponent appComponent) {
		Objects.requireNonNull(appComponent);
		return appComponent.currencies();
	}

	@Provides
	static Locale locale(@NonNull final AppComponent appComponent) {
		Objects.requireNonNull(appComponent);
		return appComponent.locale();
	}

	@Provides
	static RxSchedulers schedulers(@NonNull final AppComponent appComponent) {
		Objects.requireNonNull(appComponent);
		return appComponent.schedulers();
	}

	@Binds
	@IntoMap
	@ClassKey(WalletsViewModel.class)
	ViewModel walletsViewModel(WalletsViewModel impl);
}
