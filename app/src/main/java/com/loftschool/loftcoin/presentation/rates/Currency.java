package com.loftschool.loftcoin.presentation.rates;

import android.content.Context;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;

import com.loftschool.loftcoin.R;

import java.util.Objects;

public enum Currency {

	USD,
	EUR,
	RUB;

	@NonNull
	public String getName(@NonNull final Context context) {
		Objects.requireNonNull(context);
		return getElementOfArray(context, R.array.currency_names);
	}

	@NonNull
	public String getSymbol(@NonNull final Context context) {
		Objects.requireNonNull(context);
		return getElementOfArray(context, R.array.currency_symbols);
	}

	@NonNull
	private String getElementOfArray(@NonNull final Context context,
	                                 @ArrayRes final int arrayId) {
		Objects.requireNonNull(context);
		return context
			.getResources()
			.getStringArray(arrayId)[ordinal()];
	}
}
