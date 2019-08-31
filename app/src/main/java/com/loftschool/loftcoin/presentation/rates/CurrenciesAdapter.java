package com.loftschool.loftcoin.presentation.rates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.util.Consumer;

import java.util.Objects;

public final class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.ViewHolder> {

	private final LayoutInflater layoutInflater;
	private final Currency[] currencies;
	private final Consumer<Currency> onCLick;

	public CurrenciesAdapter(@NonNull final LayoutInflater layoutInflater,
	                         @NonNull final Currency[] currencies,
	                         @NonNull final Consumer<Currency> onCLick) {
		this.layoutInflater = Objects.requireNonNull(layoutInflater);
		this.currencies = Objects.requireNonNull(currencies);
		this.onCLick = Objects.requireNonNull(onCLick);
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
	                                     final int viewType) {
		return new ViewHolder(
			layoutInflater.inflate(
				R.layout.list_item_currency,
				parent,
				false
			)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder,
	                             final int position) {
		holder.bind(currencies[position], onCLick);
	}

	@Override
	public int getItemCount() {
		return currencies.length;
	}

	static final class ViewHolder extends RecyclerView.ViewHolder {

		private final AppCompatTextView tv_currency_symbol;
		private final AppCompatTextView tv_currency_name;

		private ViewHolder(@NonNull final View itemView) {
			super(Objects.requireNonNull(itemView));

			tv_currency_symbol = itemView.findViewById(R.id.tv_currency_symbol);
			tv_currency_name = itemView.findViewById(R.id.tv_currency_name);
		}

		private void bind(@NonNull final Currency currency,
		                  @NonNull final Consumer<Currency> onClick) {
			Objects.requireNonNull(currency);
			Objects.requireNonNull(onClick);

			final Context context = itemView.getContext();
			tv_currency_name.setText(currency.getName(context));
			tv_currency_symbol.setText(currency.getSymbol(context));

			itemView.setOnClickListener(view ->
				onClick.apply(currency)
			);
		}
	}
}
