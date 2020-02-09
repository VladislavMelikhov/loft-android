package com.loftschool.loftcoin.presentation.converter;

import androidx.fragment.app.Fragment;

import com.loftschool.loftcoin.common.FragmentModule;
import com.loftschool.loftcoin.domain.FormattersModule;
import com.loftschool.loftcoin.vm.ViewModelModule;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
	FragmentModule.class,
	ViewModelModule.class,
	ConverterModule.class,
	FormattersModule.class
})
public interface ConverterComponent {

	void inject(ConverterFragment fmt);

	void inject(CoinsSheetDialog fmt);

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder fragment(Fragment fmt);

		ConverterComponent build();
	}
}
