package com.loftschool.loftcoin.presentation.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.rx.RxRecyclerView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public final class CoinsSheetDialog extends BottomSheetDialogFragment {

	private static final String KEY_MODE = "mode";
	private static final int MODE_FROM = 1;
	private static final int MODE_TO = 2;

	private final CompositeDisposable compositeDisposable = new CompositeDisposable();

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	private ConverterViewModel converterViewModel;
	private int mode = MODE_FROM;


	static void chooseFromCoin(@NonNull final FragmentManager fm) {
		final CoinsSheetDialog dialog = new CoinsSheetDialog();
		final Bundle arguments = new Bundle();
		arguments.putInt(KEY_MODE, MODE_FROM);
		dialog.setArguments(arguments);
		dialog.show(fm, CoinsSheetDialog.class.getSimpleName());
	}

	static void chooseToCoin(@NonNull final FragmentManager fm) {
		final CoinsSheetDialog dialog = new CoinsSheetDialog();
		final Bundle arguments = new Bundle();
		arguments.putInt(KEY_MODE, MODE_TO);
		dialog.setArguments(arguments);
		dialog.show(fm, CoinsSheetDialog.class.getSimpleName());
	}

	@Override
	public int getTheme() {
		return R.style.AppTheme_BottomSheet_Dialog;
	}

	@Override
	public void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DaggerConverterComponent.builder()
			.fragment(requireParentFragment())
			.build()
			.inject(this);

		converterViewModel = ViewModelProviders
			.of(requireParentFragment(), viewModelFactory)
			.get(ConverterViewModel.class);

		mode = requireArguments().getInt(KEY_MODE, MODE_FROM);
	}

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
		final RecyclerView rv_currencies = view.findViewById(R.id.rv_currencies);
		rv_currencies.setLayoutManager(new LinearLayoutManager(view.getContext()));

		final CoinsSheetAdapter coinsSheetAdapter = new CoinsSheetAdapter(getLayoutInflater());
		rv_currencies.swapAdapter(coinsSheetAdapter, false);

		compositeDisposable.add(
			converterViewModel
				.topCoins()
				.subscribe(coinsSheetAdapter::submitList)
		);

		compositeDisposable.add(
			RxRecyclerView
				.onItemClick(rv_currencies)
				.map(rv_currencies::getChildAdapterPosition)
				.doOnNext(position -> {
					if (MODE_FROM == mode) {
						converterViewModel.changeFromCoin(position);
					} else {
						converterViewModel.changeToCoin(position);
					}
				})
				.doOnNext(position -> dismissAllowingStateLoss())
				.subscribe()
		);
	}

	@Override
	public void onDestroyView() {
		compositeDisposable.clear();
		super.onDestroyView();
	}
}
