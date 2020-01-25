package com.loftschool.loftcoin.common;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loftschool.loftcoin.AppComponent;
import com.loftschool.loftcoin.data.Currencies;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.util.Locale;
import java.util.Objects;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public interface FragmentModule {

	@Provides
	@Reusable
	static AppComponent appComponent(@NonNull final Fragment fmt) {
		Objects.requireNonNull(fmt);
		return AppComponent.from(fmt.requireContext());
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
}
