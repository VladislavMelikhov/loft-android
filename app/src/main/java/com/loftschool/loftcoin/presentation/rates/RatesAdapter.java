package com.loftschool.loftcoin.presentation.rates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.domain.ChangeFormatter;
import com.loftschool.loftcoin.domain.ImageUrlFormatter;
import com.loftschool.loftcoin.domain.PriceFormatter;
import com.loftschool.loftcoin.domain.ImageLoader;

import java.util.Objects;

import javax.inject.Inject;

public final class RatesAdapter extends ListAdapter<CoinEntity, RatesAdapter.ViewHolder> {

	private LayoutInflater layoutInflater;
	private final ImageLoader imageLoader;
	private final PriceFormatter priceFormatter;
	private final ChangeFormatter changeFormatter;
	private final ImageUrlFormatter imageUrlFormatter;

	@Inject
	public RatesAdapter(@NonNull final ImageLoader imageLoader,
	                    @NonNull final PriceFormatter priceFormatter,
	                    @NonNull final ChangeFormatter changeFormatter,
	                    @NonNull final ImageUrlFormatter imageUrlFormatter) {
		super(new DiffUtil.ItemCallback<CoinEntity>() {

			@Override
			public boolean areItemsTheSame(@NonNull final CoinEntity oldItem,
			                               @NonNull final CoinEntity newItem) {
				return oldItem.id() == newItem.id();
			}

			@Override
			public boolean areContentsTheSame(@NonNull final CoinEntity oldItem,
			                                  @NonNull final CoinEntity newItem) {
				return Objects.equals(oldItem, newItem);
			}
		});
		this.imageLoader = Objects.requireNonNull(imageLoader);
		this.priceFormatter = Objects.requireNonNull(priceFormatter);
		this.changeFormatter = Objects.requireNonNull(changeFormatter);
		this.imageUrlFormatter = Objects.requireNonNull(imageUrlFormatter);
		setHasStableIds(true);
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		this.layoutInflater = LayoutInflater.from(recyclerView.getContext());
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
			),
			imageLoader,
			priceFormatter,
			changeFormatter,
			imageUrlFormatter
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

		private final ImageLoader imageLoader;
		private final PriceFormatter priceFormatter;
		private final ChangeFormatter changeFormatter;
		private final ImageUrlFormatter imageUrlFormatter;

		ViewHolder(@NonNull final View itemView,
		           @NonNull final ImageLoader imageLoader,
		           @NonNull final PriceFormatter priceFormatter,
		           @NonNull final ChangeFormatter changeFormatter,
		           @NonNull final ImageUrlFormatter imageUrlFormatter) {

			super(Objects.requireNonNull(itemView));

			this.imageLoader = Objects.requireNonNull(imageLoader);
			this.priceFormatter = Objects.requireNonNull(priceFormatter);
			this.changeFormatter = Objects.requireNonNull(changeFormatter);
			this.imageUrlFormatter = Objects.requireNonNull(imageUrlFormatter);

			iv_logo = itemView.findViewById(R.id.iv_logo);
			tv_symbol = itemView.findViewById(R.id.tv_symbol);
			tv_price = itemView.findViewById(R.id.tv_price);
			tv_change = itemView.findViewById(R.id.tv_change);
		}

		private void bind(@NonNull final CoinEntity rate,
		                  @NonNull final ImageLoader imageLoader,
		                  final boolean isEven) {
			Objects.requireNonNull(rate);
			Objects.requireNonNull(imageLoader);

			tv_symbol.setText(rate.symbol());
			tv_price.setText(priceFormatter.format(rate.price()));
			tv_change.setText(changeFormatter.format(rate.change24()));

			tv_change.setTextColor(
				ContextCompat.getColor(
					itemView.getContext(),
					getChangeColor(rate))
			);

			itemView.setBackgroundResource(
				getBackgroundColorId(isEven)
			);

			imageLoader.loadImage(
				imageUrlFormatter.format(rate.id()),
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
		private int getChangeColor(@NonNull final CoinEntity coinRate) {
			Objects.requireNonNull(coinRate);

			if (coinRate.change24() < 0) {
				return R.color.colorNegative;
			} else {
				return R.color.colorPositive;
			}
		}
	}
}
