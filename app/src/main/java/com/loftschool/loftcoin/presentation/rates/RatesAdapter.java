package com.loftschool.loftcoin.presentation.rates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.domain.CoinRate;

import java.util.Objects;

public final class RatesAdapter extends ListAdapter<CoinRate, RatesAdapter.ViewHolder> {

	private final LayoutInflater layoutInflater;

	public RatesAdapter(@NonNull final LayoutInflater layoutInflater) {
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
		holder.bind(getItem(position));
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

		private void bind(@NonNull final CoinRate rate) {
			Objects.requireNonNull(rate);
			tv_symbol.setText(rate.symbol());
			tv_price.setText(rate.price());
			tv_change.setText(rate.change24());
		}
	}
}
