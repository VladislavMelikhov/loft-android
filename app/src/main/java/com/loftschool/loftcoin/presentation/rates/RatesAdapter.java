package com.loftschool.loftcoin.presentation.rates;

import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.domain.CoinRate;
import com.loftschool.loftcoin.util.ImageLoader;

import java.util.Objects;

public final class RatesAdapter extends ListAdapter<CoinRate, RatesAdapter.ViewHolder> {

	private final LayoutInflater layoutInflater;
	private final ImageLoader imageLoader;

	public RatesAdapter(@NonNull final LayoutInflater layoutInflater,
	                    @NonNull final ImageLoader imageLoader) {
		super(new DiffUtil.ItemCallback<CoinRate>() {

			@Override
			public boolean areItemsTheSame(@NonNull final CoinRate oldItem,
			                               @NonNull final CoinRate newItem) {
				return oldItem.id() == newItem.id();
			}

			@Override
			public boolean areContentsTheSame(@NonNull final CoinRate oldItem,
			                                  @NonNull final CoinRate newItem) {
				return Objects.equals(oldItem, newItem);
			}
		});
		this.layoutInflater = Objects.requireNonNull(layoutInflater);
		this.imageLoader = Objects.requireNonNull(imageLoader);
		setHasStableIds(true);
	}

	@Override
	public long getItemId(final int position) {
		return getItem(position).id();
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
	                                     final int viewType) {
		return new ViewHolder(
			layoutInflater.inflate(
				R.layout.list_item_rate,
				parent,
				false
			)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder,
	                             final int position) {
		holder.bind(
			getItem(position),
			imageLoader,
			position % 2 == 0
		);
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		private final AppCompatImageView iv_logo;
		private final AppCompatTextView tv_symbol;
		private final AppCompatTextView tv_price;
		private final AppCompatTextView tv_change;

		ViewHolder(@NonNull final View itemView) {
			super(Objects.requireNonNull(itemView));

			iv_logo = itemView.findViewById(R.id.iv_logo);
			tv_symbol = itemView.findViewById(R.id.tv_symbol);
			tv_price = itemView.findViewById(R.id.tv_price);
			tv_change = itemView.findViewById(R.id.tv_change);
		}

		private void bind(@NonNull final CoinRate rate,
		                  @NonNull final ImageLoader imageLoader,
		                  final boolean isEven) {
			Objects.requireNonNull(rate);

			tv_symbol.setText(rate.symbol());
			tv_price.setText(rate.price());
			tv_change.setText(rate.change24());

			tv_change.setTextColor(
				ContextCompat.getColor(
					itemView.getContext(),
					getChangeColor(rate))
			);

			itemView.setBackgroundResource(
				getBackgroundColorId(isEven)
			);

			imageLoader.loadImage(
				rate.imageUrl(),
				iv_logo
			);
		}

		@ColorRes
		private int getBackgroundColorId(final boolean isEven) {
			if (isEven) {
				return R.color.dark_two;
			} else {
				return R.color.dark_three;
			}
		}

		@ColorRes
		private int getChangeColor(@NonNull final CoinRate coinRate) {
			Objects.requireNonNull(coinRate);

			if (coinRate.isChange24Negative()) {
				return R.color.colorNegative;
			} else {
				return R.color.colorPositive;
			}
		}
	}
}
