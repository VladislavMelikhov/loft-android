package com.loftschool.loftcoin.presentation.main;

import androidx.annotation.IdRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.util.Supplier;

import java.util.Objects;

import javax.inject.Inject;

public final class MainRouter {

	private final FragmentActivity activity;
	private final SparseArrayCompat<Supplier<Fragment>> fragments;

	@Inject
	public MainRouter(@NonNull final FragmentActivity activity,
	                  @NonNull final SparseArrayCompat<Supplier<Fragment>> fragments) {

		this.activity = Objects.requireNonNull(activity);
		this.fragments = Objects.requireNonNull(fragments);
	}

	public void navigateTo(@IdRes int id) {

		final Supplier<Fragment> factory = fragments.get(id);

		if (factory != null) {
			replaceFragment(
				R.id.fragment_container,
				factory.get(),
				activity.getSupportFragmentManager()
			);
		}
	}

	private void replaceFragment(@IdRes final int containerViewId,
	                             @NonNull final Fragment fragment,
	                             @NonNull final FragmentManager fragmentManager) {

		Objects.requireNonNull(fragment);
		Objects.requireNonNull(fragmentManager);

		if (!fragmentManager.isStateSaved()) {
			fragmentManager
				.beginTransaction()
				.replace(containerViewId, fragment)
				.commit();
		}
	}
}
