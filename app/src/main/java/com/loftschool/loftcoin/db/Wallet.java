package com.loftschool.loftcoin.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.auto.value.AutoValue;

import java.util.Objects;

@AutoValue
@Entity(tableName = "wallets")
public abstract class Wallet implements StableId {

	@NonNull
	public static Wallet create(final long id,
	                            final double balance,
	                            final long coinId) {
		return  new AutoValue_Wallet(id, balance, coinId);
	}

	@PrimaryKey(autoGenerate = true)
	@AutoValue.CopyAnnotations
	@Override
	public abstract long id();

	public abstract double balance();

	@ColumnInfo(name = "coin_id")
	@AutoValue.CopyAnnotations
	public abstract long coinId();


	@AutoValue
	@DatabaseView(
		viewName = "wallets_view",
		value = "SELECT w.id, c.symbol," +
				"w.balance AS balance1," +
				"w.balance * c.price AS balance2," +
				"w.coin_id AS coinId" +
				"FROM wallets AS w INNER JOIN coins AS c ON w.coin_id = c.id"
	)
	public static abstract class View implements StableId {

		@NonNull
		public static View create(final long id,
		                          @NonNull final String symbol,
		                          final double balance1,
		                          final double balance2,
		                          final long coinId) {
			Objects.requireNonNull(symbol);
			return new AutoValue_Wallet_View(id, symbol, balance1, balance2, coinId);
		}

		@Override
		public abstract long id();

		public abstract String symbol();

		public abstract double balance1();

		public abstract double balance2();

		public abstract long coinId();
	}
}
