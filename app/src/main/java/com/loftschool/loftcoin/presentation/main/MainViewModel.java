package com.loftschool.loftcoin.presentation.main;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.presentation.main.factories.ConverterFragmentFactory;
import com.loftschool.loftcoin.presentation.main.factories.ExchangeRatesFragmentFactory;
import com.loftschool.loftcoin.presentation.main.factories.MainFragmentFactory;
import com.loftschool.loftcoin.presentation.main.factories.WalletFragmentFactory;

import java.util.Objects;

public final class MainViewModel extends ViewModel {

	private final MutableLiveData<Integer> titleId =
		new MutableLiveData<>();

	private final SparseArrayCompat<MainFragmentFactory> fragmentFactories =
		new SparseArrayCompat<>();

	@NonNull
	public LiveData<Integer> getTitleId() {
		return titleId;
	}

	public MainViewModel() {
		fragmentFactories.put(R.id.wallets, new WalletFragmentFactory());
		fragmentFactories.put(R.id.exchange_rate, new ExchangeRatesFragmentFactory());
		fragmentFactories.put(R.id.converter, new ConverterFragmentFactory());
	}

	public void onTabSelect(@IdRes int tabId,
	                        @NonNull final FragmentManager fragmentManager) {

		final MainFragmentFactory fragmentFactory = fragmentFactories.get(tabId);

		titleId.setValue(
			Objects.requireNonNull(fragmentFactory).getTitle()
		);
		replaceFragment(
			fragmentFactory.getFragment(),
			fragmentManager
		);
	}

	private void replaceFragment(@NonNull final Fragment fragment,
	                             @NonNull final FragmentManager fragmentManager) {

		if (!fragmentManager.isStateSaved()) {
			fragmentManager
				.beginTransaction()
				.replace(R.id.fragment_container, fragment)
				.commit();
		}
 	}
}
