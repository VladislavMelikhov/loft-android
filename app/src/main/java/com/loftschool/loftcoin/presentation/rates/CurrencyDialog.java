package com.loftschool.loftcoin.presentation.rates;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.data.Currencies;

import javax.inject.Inject;

public final class CurrencyDialog extends DialogFragment {

	public static final String TAG = "CurrencyDialog";

	@Inject
	Currencies currencies;

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	@Nullable
	@Override
	public View onCreateView(@NonNull final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dialog_currency, container, false);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
		final AppCompatDialog dialog = new AppCompatDialog(requireContext());
		dialog.setTitle(R.string.currency_chooser);
		return dialog;
	}

	@Override
	public void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Fragment parentFragment = requireParentFragment();

		DaggerRatesComponent.builder()
			.fragment(parentFragment)
			.build()
			.inject(this);
	}

	@Override
	public void onViewCreated(@NonNull final View view,
	                          @Nullable final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final RecyclerView rv_currencies = view.findViewById(R.id.rv_currencies);
		rv_currencies.setHasFixedSize(true);
		rv_currencies.setLayoutManager(
			new LinearLayoutManager(requireContext())
		);
		rv_currencies.setAdapter(
			new CurrenciesAdapter(
				getLayoutInflater(),
				currencies,
				currency -> {
					currencies.setCurrent(currency);
					dismissAllowingStateLoss();
				}
			)
		);
	}
}
