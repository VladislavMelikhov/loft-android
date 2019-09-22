package com.loftschool.loftcoin.presentation.main;

import androidx.fragment.app.FragmentActivity;

import com.loftschool.loftcoin.vm.ViewModelModule;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
	MainModule.class,
	ViewModelModule.class
})
public interface MainComponent {

	void inject(MainActivity activity);

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder activity(FragmentActivity activity);

		@BindsInstance
		Builder args(MainVMArgs args);

		MainComponent build();
	}
}
