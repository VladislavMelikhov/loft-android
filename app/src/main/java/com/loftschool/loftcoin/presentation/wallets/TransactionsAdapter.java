package com.loftschool.loftcoin.presentation.wallets;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.adapter.StableIdDiff;
import com.loftschool.loftcoin.db.Transaction;
import com.loftschool.loftcoin.domain.PriceFormatter;

import java.util.Objects;

public final class TransactionsAdapter extends ListAdapter<Transaction, TransactionsAdapter.ViewHolder> {

	private final LayoutInflater layoutInflater;
	private final PriceFormatter priceFormatter;

	public TransactionsAdapter(@NonNull final LayoutInflater layoutInflater,
	                           @NonNull final PriceFormatter priceFormatter) {
		super(new StableIdDiff<>());
		this.layoutInflater = Objects.requireNonNull(layoutInflater);
		this.priceFormatter = Objects.requireNonNull(priceFormatter);
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
	                                     final int viewType) {
		return new ViewHolder(
			layoutInflater.inflate(
				R.layout.li_transaction,
				parent,
				false
			),
			priceFormatter
		);
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder,
	                             final int position) {
		holder.bind(getItem(position));
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		private final TextView tv_amount1;
		private final TextView tv_amount2;
		private final TextView tv_timestamp;

		private final PriceFormatter priceFormatter;


		ViewHolder(@NonNull View itemView,
		           @NonNull final PriceFormatter priceFormatter) {
			super(itemView);

			tv_amount1 = itemView.findViewById(R.id.tv_amount1);
			tv_amount2 = itemView.findViewById(R.id.tv_amount2);
			tv_timestamp = itemView.findViewById(R.id.tv_timestamp);

			this.priceFormatter = Objects.requireNonNull(priceFormatter);
		}

		void bind(@NonNull final Transaction transaction) {
			Objects.requireNonNull(transaction);

			tv_amount1.setText(priceFormatter.format(transaction.amount1(), transaction.wallet().coin().symbol()));
			tv_amount2.setText(priceFormatter.format(transaction.amount2()));

			final Context context = itemView.getContext();

			tv_amount2.setTextColor(
				ContextCompat.getColor(
					context,
					getChangeColor(transaction))
			);

			tv_timestamp.setText(DateUtils.formatDateTime(
				context,
				transaction.timestamp().getTime(),
				DateUtils.FORMAT_SHOW_YEAR
			));
		}

		@ColorRes
		private int getChangeColor(@NonNull final Transaction transaction) {
			Objects.requireNonNull(transaction);

			if (transaction.amount2() < 0) {
				return R.color.colorNegative;
			} else {
				return R.color.colorPositive;
			}
		}
	}
}
