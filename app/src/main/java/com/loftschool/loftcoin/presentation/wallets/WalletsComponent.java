package com.loftschool.loftcoin.presentation.wallets;

import androidx.fragment.app.Fragment;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {

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
