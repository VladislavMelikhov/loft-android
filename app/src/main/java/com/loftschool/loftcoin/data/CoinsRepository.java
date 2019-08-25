package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.util.Consumer;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinsRepository {

	final CoinMarketCapApi coinMarketCapApi;

	public CoinsRepository(@NonNull final CoinMarketCapApi coinMarketCapApi) {
		this.coinMarketCapApi = Objects.requireNonNull(coinMarketCapApi);
	}

	public void listings(@NonNull final String convert,
	                     @NonNull final Consumer<Void> onSucces,
	                     @NonNull final Consumer<Throwable> onError) {
		coinMarketCapApi.listings(convert).enqueue(new Callback<Void>() {
			@Override
			public void onResponse(Call<Void> call, Response<Void> response) {

			}

			@Override
			public void onFailure(Call<Void> call, Throwable t) {

			}
		});
	}
}
