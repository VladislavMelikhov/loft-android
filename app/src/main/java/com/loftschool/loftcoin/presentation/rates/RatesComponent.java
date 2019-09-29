package com.loftschool.loftcoin.presentation.rates;

import androidx.fragment.app.Fragment;

import com.loftschool.loftcoin.domain.FormattersModule;
import com.loftschool.loftcoin.domain.LoadersModule;
import com.loftschool.loftcoin.vm.ViewModelModule;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules ={
	RatesModule.class,
	ViewModelModule.class,
	FormattersModule.class,
	LoadersModule.class
})
public interface RatesComponent {

	void inject(ExchangeRatesFragment fragment);

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder fragment(Fragment fragment);

		RatesComponent build();
	}
}
