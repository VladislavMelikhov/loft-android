package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.loftschool.loftcoin.R;

import java.util.Objects;

public enum Currency {

	USD(R.string.usd, "$"),
	EUR(R.string.eur, "€"),
	RUB(R.string.rub, "₽");

	@StringRes
	private final int nameResId;

	private final String sign;

	Currency(@StringRes final int nameResId,
	         @NonNull final String sign) {
		this.nameResId = nameResId;
		this.sign = Objects.requireNonNull(sign);
	}

	public int getNameResId() {
		return nameResId;
	}

	@NonNull
	public String getSign() {
		return sign;
	}

	@NonNull
	public String getCode() {
		return name();
	}
}
