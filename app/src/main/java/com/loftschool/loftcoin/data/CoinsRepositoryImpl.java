package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.data.dto.Coin;
import com.loftschool.loftcoin.data.dto.Listings;
import com.loftschool.loftcoin.util.Consumer;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CoinsRepositoryImpl implements CoinsRepository {

	final CoinMarketCapApi coinMarketCapApi;

	@Inject
	public CoinsRepositoryImpl(@NonNull final CoinMarketCapApi coinMarketCapApi) {
		this.coinMarketCapApi = Objects.requireNonNull(coinMarketCapApi);
	}

	@Override
	public void listings(@NonNull final String convert,
	                     @NonNull final Consumer<List<Coin>> onSuccess,
	                     @NonNull final Consumer<Throwable> onError) {
		coinMarketCapApi
			.listings(convert)
			.enqueue(new Callback<Listings>() {
				@Override
				public void onResponse(final Call<Listings> call,
				                       final Response<Listings> response) {
					final Listings listings = response.body();
					if (listings != null) {
						onSuccess.apply(listings.getData());
					} else {
						onSuccess.apply(Collections.emptyList());
					}
				}

				@Override
				public void onFailure(final Call<Listings> call,
				                      final Throwable throwable) {
					onError.apply(throwable);
				}
			});
	}
}