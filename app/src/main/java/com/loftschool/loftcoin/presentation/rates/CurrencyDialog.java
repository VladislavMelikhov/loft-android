package com.loftschool.loftcoin.presentation.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loftschool.loftcoin.R;

public final class CurrencyDialog extends DialogFragment {

	public static final String TAG = "CurrencyDialog";

	@Nullable
	@Override
	public View onCreateView(@NonNull final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dialog_currency, container, false);
	}

	@Override
	public void onViewCreated(@NonNull final View view,
	                          @Nullable final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final RatesViewModel ratesViewModel = ViewModelProviders
			.of(requireParentFragment(), new RatesViewModel.Factory(requireContext()))
			.get(RatesViewModel.class);

		final RecyclerView rv_currencies = view.findViewById(R.id.rv_currencies);
		rv_currencies.setHasFixedSize(true);
		rv_currencies.setLayoutManager(
			new LinearLayoutManager(requireContext())
		);
		rv_currencies.setAdapter(
			new CurrenciesAdapter(
				getLayoutInflater(),
				Currency.values(),
				currency -> {
					ratesViewModel.setCurrency(currency);
					dismiss();
				}
			)
		);
	}
}
