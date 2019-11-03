package com.loftschool.loftcoin.presentation.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.db.CoinEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public final class ExchangeRatesFragment extends Fragment {

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	@Inject
	RatesAdapter ratesAdapter;

	private final CompositeDisposable compositeDisposable = new CompositeDisposable();

	@Nullable
	@Override
	public View onCreateView(@NonNull final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {
		return inflater.inflate(
			R.layout.fragment_exchange_rates,
			container,
			false
		);
	}

	@Override
	public void onViewCreated(@NonNull final View view,
	                          @Nullable final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		DaggerRatesComponent
			.builder()
			.fragment(this)
			.build()
			.inject(this);

		final RatesViewModel ratesViewModel = ViewModelProviders
			.of(this, viewModelFactory)
			.get(RatesViewModel.class);

		final RecyclerView rv_rates = view.findViewById(R.id.rv_rates);
		final SwipeRefreshLayout rl_rates = view.findViewById(R.id.rl_rates);

		rv_rates.setHasFixedSize(true);
		rv_rates.setLayoutManager(new LinearLayoutManager(view.getContext()));
		rv_rates.swapAdapter(ratesAdapter, false);

		rl_rates.setOnRefreshListener(ratesViewModel::refresh);

		compositeDisposable.add(
			ratesViewModel
				.uiState()
				.subscribe(state -> {
					rl_rates.setRefreshing(state.isRefreshing());
					final List<CoinEntity> rates = state.rates();
					if (!rates.isEmpty()) {
						ratesAdapter.submitList(rates);
					}
					final String errorMessage = state.error();
					if (errorMessage != null) {
						Snackbar.make(view, errorMessage, Snackbar.LENGTH_SHORT).show();
					}
				})
		);
	}

	@Override
	public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(@NonNull final Menu menu,
	                                @NonNull final MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_rates, menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
		if (R.id.currency == item.getItemId()) {
			new CurrencyDialog()
				.show(getChildFragmentManager(), CurrencyDialog.TAG);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		compositeDisposable.clear();
	}
}
