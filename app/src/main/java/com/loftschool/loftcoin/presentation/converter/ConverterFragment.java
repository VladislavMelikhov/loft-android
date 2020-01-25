package com.loftschool.loftcoin.presentation.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.loftschool.loftcoin.R;

import javax.inject.Inject;

public final class ConverterFragment extends Fragment {

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	private ConverterViewModel converterViewModel;

	@Nullable
	@Override
	public View onCreateView(@NonNull final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {
		return inflater.inflate(
			R.layout.fragment_converter,
			container,
			false
		);
	}

	@Override
	public void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DaggerConverterComponent
			.builder()
			.fragment(this)
			.build()
			.inject(this);

		converterViewModel = ViewModelProviders
			.of(this, viewModelFactory)
			.get(ConverterViewModel.class);
	}
}
