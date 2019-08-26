package com.loftschool.loftcoin.domain;


import androidx.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CoinRate {

	@NonNull
	static CoinRate.Builder builder() {
		return new AutoValue_CoinRate.Builder();
	}

	abstract int id();

	@NonNull
	abstract String symbol();

	@NonNull
	abstract String price();

	@NonNull
	abstract String change24();

	@NonNull
	abstract String imageUrl();

	abstract boolean isChange24Negative();

	@AutoValue.Builder
	abstract static class Builder {

		abstract Builder id(int value);

		abstract Builder symbol(String value);

		abstract Builder price(String value);

		abstract Builder change24(String value);

		abstract Builder imageUrl(String value);

		abstract Builder isChange24Negative(boolean value);

		abstract CoinRate build();
	}
}
