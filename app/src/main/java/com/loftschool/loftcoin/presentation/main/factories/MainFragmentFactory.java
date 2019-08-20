package com.loftschool.loftcoin.presentation.main.factories;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public interface MainFragmentFactory {

	@StringRes
	int getTitle();

	@NonNull
	Fragment getFragment();
}
