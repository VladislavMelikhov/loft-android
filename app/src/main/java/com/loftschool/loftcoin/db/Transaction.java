package com.loftschool.loftcoin.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.auto.value.AutoValue;

import java.util.Objects;

@AutoValue
@Entity(tableName = "transactions")
public abstract class Transaction implements StableId {

	@NonNull
	public static Transaction create(final long id,
	                                 final long timestamp,
	                                 final double amount,
	                                 final long walletId) {
		return new AutoValue_Transaction(id, timestamp, amount, walletId);
	}

	@PrimaryKey(autoGenerate = true)
	@AutoValue.CopyAnnotations
	@Override
	public abstract long id();

	public abstract long timestamp();

	public abstract double amount();

	@ColumnInfo(name = "wallet_id")
	@AutoValue.CopyAnnotations
	public abstract long walletId();

	@AutoValue
	@DatabaseView(
		viewName = "transactions_view",
		value = "SELECT t.id, c.symbol, t.timestamp, t.wallet_id, " +
			"t.amount AS amount1, " +
			"(t.amount * c.price) AS amount2 " +
			"FROM transactions AS t " +
			"INNER JOIN wallets AS w ON t.wallet_id = w.id " +
			"INNER JOIN coins AS c ON w.coin_id = c.id"
	)
	public static abstract class View implements StableId {

		@NonNull
		public static Transaction.View create(final long id,
		                                      @NonNull final String symbol,
		                                      final long timestamp,
		                                      final double amount1,
		                                      final double amount2) {
			Objects.requireNonNull(symbol);
			return new AutoValue_Transaction_View(id, symbol, timestamp, amount1, amount2);
		}

		@Override
		public abstract long id();

		public abstract String symbol();

		public abstract long timestamp();

		public abstract double amount1();

		public abstract double amount2();
	}
}
