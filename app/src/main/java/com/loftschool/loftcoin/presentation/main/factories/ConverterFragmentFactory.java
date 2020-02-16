package com.loftschool.loftcoin.presentation.main.factories;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.presentation.converter.ConverterFragment;

public final class ConverterFragmentFactory implements MainFragmentFactory {

	@Override
	public int getTitle() {
		return R.string.converter;
	}

	@NonNull
	@Override
	public Fragment getFragment() {
		return new ConverterFragment();
	}
}
