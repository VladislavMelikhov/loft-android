package com.loftschool.loftcoin.presentation.main;

import androidx.fragment.app.FragmentActivity;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
	MainModule.class
})
public interface MainComponent {

	void inject(MainActivity activity);

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder activity(FragmentActivity activity);

		MainComponent build();
	}
}
