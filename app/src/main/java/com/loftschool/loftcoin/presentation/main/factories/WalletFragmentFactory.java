package com.loftschool.loftcoin.presentation.main.factories;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.presentation.wallets.WalletsFragment;

public final class WalletFragmentFactory implements MainFragmentFactory {

	@Override
	public int getTitle() {
		return R.string.wallets;
	}

	@NonNull
	@Override
	public Fragment getFragment() {
		return new WalletsFragment();
	}
}
