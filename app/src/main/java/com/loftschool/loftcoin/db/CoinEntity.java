package com.loftschool.loftcoin.db;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.auto.value.AutoValue;

import java.util.Objects;

@AutoValue
@Entity(tableName = "coins")
public abstract class CoinEntity implements StableId<Long> {

	@PrimaryKey
	@AutoValue.CopyAnnotations
	@Override
	public abstract Long id();

	public abstract String name();

	public abstract String symbol();

	public abstract double price();

	public abstract double change24();

	public static CoinEntity create(final long id,
	                                @NonNull final String name,
	                                @NonNull final String symbol,
	                                final double price,
	                                final double change24) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(symbol);
		return new AutoValue_CoinEntity(id, name, symbol, price, change24);
	}
}
