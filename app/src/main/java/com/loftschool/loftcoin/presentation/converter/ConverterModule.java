package com.loftschool.loftcoin.presentation.converter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.AppComponent;
import com.loftschool.loftcoin.data.CoinsRepository;

import java.util.Objects;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
public interface ConverterModule {

	@Provides
	static CoinsRepository coinsRepository(@NonNull final AppComponent appComponent) {
		return Objects.requireNonNull(appComponent).coinsRepository();
	}

	@Binds
	@IntoMap
	@ClassKey(ConverterViewModel.class)
	ViewModel converterViewModel(ConverterViewModel impl);
}
