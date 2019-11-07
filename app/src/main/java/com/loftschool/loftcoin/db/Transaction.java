package com.loftschool.loftcoin.db;

import androidx.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.Date;
import java.util.Objects;

@AutoValue
public abstract class Transaction implements StableId<String> {

	@NonNull
	public static Transaction create(@NonNull final String id,
	                                 @NonNull final Double amount,
	                                 @NonNull final Date timestamp,
	                                 @NonNull final Wallet wallet) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(amount);
		Objects.requireNonNull(timestamp);
		Objects.requireNonNull(wallet);
		return new AutoValue_Transaction(id, amount, timestamp, wallet);
	}


	public abstract double amount1();

	public abstract Date timestamp();

	public abstract Wallet wallet();

	public double amount2() {
		return amount1() * wallet().coin().price();
	}
}
