package com.loftschool.loftcoin.domain;


import androidx.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CoinRate {

	@NonNull
	public static CoinRate.Builder builder() {
		return new AutoValue_CoinRate.Builder();
	}

	public abstract int id();

	@NonNull
	public abstract String symbol();

	@NonNull
	public abstract String price();

	@NonNull
	public abstract String change24();

	@NonNull
	public abstract String imageUrl();

	public abstract boolean isChange24Negative();

	@AutoValue.Builder
	public abstract static class Builder {

		public abstract Builder id(int value);

		public abstract Builder symbol(String value);

		public abstract Builder price(String value);

		public abstract Builder change24(String value);

		public abstract Builder imageUrl(String value);

		public abstract Builder isChange24Negative(boolean value);

		public abstract CoinRate build();
	}
}
