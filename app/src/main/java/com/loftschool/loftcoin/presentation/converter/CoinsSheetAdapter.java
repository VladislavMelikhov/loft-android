package com.loftschool.loftcoin.presentation.converter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.adapter.StableIdDiff;
import com.loftschool.loftcoin.db.CoinEntity;

import java.util.Locale;
import java.util.Objects;

public final class CoinsSheetAdapter extends ListAdapter<CoinEntity, CoinsSheetAdapter.ViewHolder> {

	private final LayoutInflater layoutInflater;

	public CoinsSheetAdapter(@NonNull final LayoutInflater layoutInflater) {
		super(new StableIdDiff<>());
		this.layoutInflater = Objects.requireNonNull(layoutInflater);
	}

	@Override
	public long getItemId(int position) {
		return Objects.requireNonNull(getItem(position)).id();
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
		return new ViewHolder(layoutInflater.inflate(R.layout.list_item_currency, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
		holder.bind(Objects.requireNonNull(getItem(position)));
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		private final TextView tv_symbol;
		private final TextView tv_name;

		public ViewHolder(@NonNull final View itemView) {
			super(itemView);
			tv_symbol = itemView.findViewById(R.id.tv_currency_symbol);
			tv_name = itemView.findViewById(R.id.tv_currency_name);
		}

		void bind(@NonNull final CoinEntity coin) {
			tv_symbol.setText(String.valueOf(coin.symbol().charAt(0)));
			tv_name.setText(String.format(Locale.US, "%s | %s", coin.name(), coin.symbol()));
		}
	}
}
