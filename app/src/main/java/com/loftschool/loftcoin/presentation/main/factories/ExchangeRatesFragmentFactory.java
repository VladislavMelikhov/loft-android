package com.loftschool.loftcoin.presentation.main.factories;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.presentation.ExchangeRatesFragment;

public final class ExchangeRatesFragmentFactory implements MainFragmentFactory {

	@Override
	public int getTitle() {
		return R.string.exchange_rate;
	}

	@NonNull
	@Override
	public Fragment getFragment() {
		return new ExchangeRatesFragment();
	}
}
