package com.loftschool.loftcoin.presentation.wallets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.domain.ImageLoader;
import com.loftschool.loftcoin.domain.ImageUrlFormatter;
import com.loftschool.loftcoin.domain.PriceFormatter;
import com.loftschool.loftcoin.view.PagerDecoration;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public final class WalletsFragment extends Fragment {

	private final CompositeDisposable compositeDisposable = new CompositeDisposable();

	private RecyclerView rv_wallets;

	private RecyclerView rv_transactions;

	private SnapHelper snapHelper;

	private RecyclerView.OnScrollListener onWalletsScroll;

	private WalletsViewModel walletsViewModel;

	@Inject
	ImageLoader imageLoader;

	@Inject
	PriceFormatter priceFormatter;

	@Inject
	ImageUrlFormatter imageUrlFormatter;

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	@Nullable
	@Override
	public View onCreateView(@NonNull final LayoutInflater inflater,
	                         @Nullable final ViewGroup container,
	                         @Nullable final Bundle savedInstanceState) {
		return inflater.inflate(
			R.layout.fragment_wallets,
			container,
			false
		);
	}

	@Override
	public void onViewCreated(@NonNull final View view,
	                          @Nullable final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		rv_wallets = view.findViewById(R.id.rv_wallets);
		rv_wallets.setLayoutManager(new LinearLayoutManager(
			view.getContext(),
			LinearLayoutManager.HORIZONTAL,
			false
		));
		rv_wallets.addItemDecoration(new PagerDecoration(view.getContext(), 16));

		snapHelper = new PagerSnapHelper();
		snapHelper.attachToRecyclerView(rv_wallets);

		onWalletsScroll = new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				if (RecyclerView.SCROLL_STATE_IDLE == newState) {
					final View snapView = snapHelper.findSnapView(recyclerView.getLayoutManager());
					if (snapView != null) {
						walletsViewModel.submitWalletPosition(recyclerView.getChildAdapterPosition(snapView));
					}
				}
			}
		};
		rv_wallets.addOnScrollListener(onWalletsScroll);

		final WalletsAdapter walletsAdapter = new WalletsAdapter(
			getLayoutInflater(),
			imageLoader,
			priceFormatter,
			imageUrlFormatter
		);

		rv_wallets.swapAdapter(walletsAdapter, false);

		final View iv_wallet_card = view.findViewById(R.id.iv_wallet_card);
		compositeDisposable.add(
			walletsViewModel
				.wallets()
				.subscribe(wallets -> {
					iv_wallet_card.setVisibility(wallets.isEmpty() ? View.VISIBLE : View.GONE);
					walletsAdapter.submitList(wallets);
					rv_wallets.invalidateItemDecorations();
				})
		);

		rv_transactions = view.findViewById(R.id.rv_transactions);
		rv_transactions.setLayoutManager(new LinearLayoutManager(view.getContext()));
		rv_transactions.setHasFixedSize(true);

		final TransactionsAdapter transactionsAdapter = new TransactionsAdapter(
			getLayoutInflater(),
			priceFormatter
		);

		rv_transactions.swapAdapter(transactionsAdapter, false);

		compositeDisposable.add(
			walletsViewModel
				.transactions()
				.subscribe(transactionsAdapter::submitList)
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
		inflater.inflate(R.menu.menu_wallet, menu);
	}

	@Override
	public void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DaggerWalletsComponent
			.builder()
			.fragment(this)
			.build()
			.inject(this);

		walletsViewModel = ViewModelProviders
			.of(this, viewModelFactory)
			.get(WalletsViewModel.class);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
		Objects.requireNonNull(item);

		if (R.id.add_wallet == item.getItemId()) {
			compositeDisposable.add(
				walletsViewModel
					.createNextWallet()
					.subscribe(
						() -> Toast.makeText(requireContext(), R.string.wallet_created, Toast.LENGTH_SHORT).show(),
						e -> Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show()
					)
			);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroyView() {
		snapHelper.attachToRecyclerView(null);
		rv_wallets.swapAdapter(null, false);
		rv_wallets.removeOnScrollListener(onWalletsScroll);
		rv_transactions.swapAdapter(null, false);
		compositeDisposable.dispose();
		super.onDestroyView();
	}
}
