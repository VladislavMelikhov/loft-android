package com.loftschool.loftcoin.presentation.rates;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.loftschool.loftcoin.db.CoinEntity;

import java.util.Collections;
import java.util.List;

@AutoValue
abstract class RatesUiState {

	abstract List<CoinEntity> rates();

	@Nullable
	abstract String error();

	abstract boolean isRefreshing();

	static RatesUiState loading() {
		return new AutoValue_RatesUiState(Collections.emptyList(), null, true);
	}

	static RatesUiState success(List<CoinEntity> rates) {
		return new AutoValue_RatesUiState(rates, null, false);
	}

	static RatesUiState failure(Throwable e) {
		return new AutoValue_RatesUiState(Collections.emptyList(), e.getMessage(), false);
	}
}
