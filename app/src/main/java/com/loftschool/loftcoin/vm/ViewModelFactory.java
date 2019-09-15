package com.loftschool.loftcoin.vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

public final class ViewModelFactory implements ViewModelProvider.Factory {

	private final Map<Class<?>, Provider<ViewModel>> providers;

	@Inject
	public ViewModelFactory(@NonNull final Map<Class<?>, Provider<ViewModel>> providers) {
		this.providers = Objects.requireNonNull(providers);
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
		final Provider<ViewModel> provider = providers.get(modelClass);
		if (provider != null) {
			return (T) provider.get();
		}
		for (final Map.Entry<Class<?>, Provider<ViewModel>> entry : providers.entrySet()) {
			if (modelClass.isAssignableFrom(entry.getKey())) {
				return (T) entry.getValue().get();
			}
		}
		throw new IllegalArgumentException("No such provider for " + modelClass);
	}
}
