package com.loftschool.loftcoin.presentation.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.db.CoinEntity;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public final class ConverterFragment extends Fragment {

	private final CompositeDisposable compositeDisposable = new CompositeDisposable();

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

	@Override
	public void onViewCreated(@NonNull final View view,
	                          @Nullable final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final TextView tv_from_coin = view.findViewById(R.id.tv_from_coin);
		final TextView tv_to_coin = view.findViewById(R.id.tv_to_coin);

		compositeDisposable.add(
			converterViewModel
				.fromCoin()
				.map(CoinEntity::symbol)
				.subscribe(tv_from_coin::setText)
		);

		compositeDisposable.add(
			converterViewModel
				.toCoin()
				.map(CoinEntity::symbol)
				.subscribe(tv_to_coin::setText)
		);

		final EditText et_from = view.findViewById(R.id.et_from);
		final EditText et_to = view.findViewById(R.id.et_to);

		compositeDisposable.add(
			converterViewModel
				.fromValue()
				.filter(value -> !et_from.hasFocus())
				.subscribe(et_from::setText)
		);

		compositeDisposable.add(
			converterViewModel
				.toValue()
				.filter(value -> !et_to.hasFocus())
				.subscribe(et_to::setText)
		);

		compositeDisposable.add(
			RxTextView
				.textChanges(et_from)
				.map(CharSequence::toString)
				.subscribe(converterViewModel::changeFromValue)
		);

		compositeDisposable.add(
			RxTextView
				.textChanges(et_to)
				.map(CharSequence::toString)
				.subscribe(converterViewModel::changeToValue)
		);
	}

	@Override
	public void onDestroyView() {
		compositeDisposable.clear();
		super.onDestroyView();
	}
}
