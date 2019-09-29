package com.loftschool.loftcoin.presentation.rates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.data.Currencies;
import com.loftschool.loftcoin.data.Currency;
import com.loftschool.loftcoin.util.Consumer;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.ViewHolder> {

	private final LayoutInflater layoutInflater;
	private final List<Currency> currencies;
	private final Consumer<Currency> onCLick;

	public CurrenciesAdapter(@NonNull final LayoutInflater layoutInflater,
	                         @NonNull final Currencies currencies,
	                         @NonNull final Consumer<Currency> onCLick) {
		this.layoutInflater = Objects.requireNonNull(layoutInflater);
		this.currencies = Objects.requireNonNull(currencies).getAvailableCurrencies();
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
			),
			onCLick
		);
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder,
	                             final int position) {
		holder.bind(currencies.get(position));
	}

	@Override
	public int getItemCount() {
		return currencies.size();
	}

	static final class ViewHolder extends RecyclerView.ViewHolder {

		private final Consumer<Currency> onClick;

		private final AppCompatTextView tv_currency_symbol;
		private final AppCompatTextView tv_currency_name;

		private ViewHolder(@NonNull final View itemView,
		                   @NonNull final Consumer<Currency> onCLick) {
			super(Objects.requireNonNull(itemView));

			this.onClick = Objects.requireNonNull(onCLick);

			tv_currency_symbol = itemView.findViewById(R.id.tv_currency_symbol);
			tv_currency_name = itemView.findViewById(R.id.tv_currency_name);
		}

		private void bind(@NonNull final Currency currency) {
			Objects.requireNonNull(currency);
			Objects.requireNonNull(onClick);

			final Context context = itemView.getContext();
			tv_currency_name.setText(String.format(Locale.US, "%s | %s",
				currency.getCode(),
				context.getString(currency.getNameResId())
			));
			tv_currency_symbol.setText(currency.getSign());

			itemView.setOnClickListener(view ->
				onClick.apply(currency)
			);
		}
	}
}
