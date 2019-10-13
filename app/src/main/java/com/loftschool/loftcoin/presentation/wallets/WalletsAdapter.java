package com.loftschool.loftcoin.presentation.wallets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.adapter.StableIdDiff;
import com.loftschool.loftcoin.db.Wallet;
import com.loftschool.loftcoin.domain.ImageLoader;
import com.loftschool.loftcoin.domain.ImageUrlFormatter;
import com.loftschool.loftcoin.domain.PriceFormatter;

import java.util.Objects;

public final class WalletsAdapter extends ListAdapter<Wallet.View, WalletsAdapter.ViewHolder> {

	private final LayoutInflater layoutInflater;
	private final ImageLoader imageLoader;
	private final PriceFormatter priceFormatter;
	private final ImageUrlFormatter imageUrlFormatter;

	public WalletsAdapter(@NonNull final LayoutInflater layoutInflater,
	                      @NonNull final ImageLoader imageLoader,
	                      @NonNull final PriceFormatter priceFormatter,
	                      @NonNull final ImageUrlFormatter imageUrlFormatter) {
		super(new StableIdDiff<>());
		this.layoutInflater = Objects.requireNonNull(layoutInflater);
		this.imageLoader = Objects.requireNonNull(imageLoader);
		this.priceFormatter = Objects.requireNonNull(priceFormatter);
		this.imageUrlFormatter = Objects.requireNonNull(imageUrlFormatter);

	}

	@Override
	public long getItemId(final int position) {
		return Objects.requireNonNull(getItem(position)).id();
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
	                                     final int viewType) {
		Objects.requireNonNull(parent);
		return new ViewHolder(
			layoutInflater.inflate(R.layout.list_item_wallet, parent, false),
			imageLoader,
			priceFormatter,
			imageUrlFormatter
		);
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder,
	                             final int position) {
		Objects.requireNonNull(holder);
		holder.bind(getItem(position));
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		private final ImageView iv_logo;
		private final TextView tv_symbol;
		private final TextView tv_balance1;
		private final TextView tv_balance2;

		private final ImageLoader imageLoader;
		private final PriceFormatter priceFormatter;
		private final ImageUrlFormatter imageUrlFormatter;

		ViewHolder(@NonNull final View itemView,
		           @NonNull final ImageLoader imageLoader,
		           @NonNull final PriceFormatter priceFormatter,
		           @NonNull final ImageUrlFormatter imageUrlFormatter) {
			super(Objects.requireNonNull(itemView));

			this.imageLoader = Objects.requireNonNull(imageLoader);
			this.priceFormatter = Objects.requireNonNull(priceFormatter);
			this.imageUrlFormatter = Objects.requireNonNull(imageUrlFormatter);

			iv_logo = itemView.findViewById(R.id.iv_logo);
			tv_symbol = itemView.findViewById(R.id.tv_symbol);
			tv_balance1 = itemView.findViewById(R.id.tv_balance1);
			tv_balance2 = itemView.findViewById(R.id.tv_balance2);
		}

		private void bind(@NonNull final Wallet.View wallet) {
			Objects.requireNonNull(wallet);

			imageLoader.loadImage(
				imageUrlFormatter.format(wallet.coinId()),
				iv_logo
			);

			tv_symbol.setText(wallet.symbol());
			tv_balance1.setText(priceFormatter.format(wallet.balance1()));
			tv_balance2.setText(priceFormatter.format(wallet.balance2()));
		}
	}
}
