package com.loftschool.loftcoin.db;

import androidx.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.Objects;

@AutoValue
public abstract class Wallet implements StableId<String> {

	@NonNull
	public static Wallet create(@NonNull final String id,
	                            @NonNull final Double balance,
	                            @NonNull final CoinEntity coin) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(balance);
		Objects.requireNonNull(coin);
		return new AutoValue_Wallet(id, balance, coin);
	}

	public abstract double balance1();

	public abstract CoinEntity coin();

	public final double balance2() {
		return balance1() * coin().price();
	}
}
