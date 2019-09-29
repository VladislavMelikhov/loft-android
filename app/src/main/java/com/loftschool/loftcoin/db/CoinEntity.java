package com.loftschool.loftcoin.db;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.auto.value.AutoValue;

import java.util.Objects;

@AutoValue
@Entity(tableName = "coins")
public abstract class CoinEntity {

	@PrimaryKey
	@AutoValue.CopyAnnotations
	public abstract long id();

	public abstract String symbol();

	public abstract double price();

	public abstract double change24();

	public static CoinEntity create(final long id,
	                                @NonNull final String symbol,
	                                final double price,
	                                final double change24) {
		Objects.requireNonNull(symbol);
		return new AutoValue_CoinEntity(id, symbol, price, change24);
	}
}
