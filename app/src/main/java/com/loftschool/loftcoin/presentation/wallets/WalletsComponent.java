package com.loftschool.loftcoin.presentation.wallets;

import androidx.fragment.app.Fragment;

import com.loftschool.loftcoin.domain.FormattersModule;
import com.loftschool.loftcoin.domain.LoadersModule;
import com.loftschool.loftcoin.vm.ViewModelModule;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
	WalletsModule.class,
	ViewModelModule.class,
	FormattersModule.class,
	LoadersModule.class
})
public interface WalletsComponent {

	void inject(WalletsFragment fmt);

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder fragment(Fragment fmt);

		WalletsComponent build();
	}
}
