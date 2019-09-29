package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.loftschool.loftcoin.data.dto.Coin;
import com.loftschool.loftcoin.data.dto.Listings;
import com.loftschool.loftcoin.data.dto.Quote;
import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.db.LoftDb;
import com.loftschool.loftcoin.util.Consumer;

import java.util.ArrayList;
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

	private final CoinMarketCapApi coinMarketCapApi;
	private final LoftDb db;

	@Inject
	public CoinsRepositoryImpl(@NonNull final CoinMarketCapApi coinMarketCapApi,
	                           @NonNull final LoftDb db) {
		this.coinMarketCapApi = Objects.requireNonNull(coinMarketCapApi);
		this.db = Objects.requireNonNull(db);
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
					new Thread(() -> {
						if (listings != null) {
							onSuccess.apply(Collections.unmodifiableList(listings.getData()));
						} else {
							onSuccess.apply(Collections.emptyList());
						}
					}).start();
				}

				@Override
				public void onFailure(final Call<Listings> call,
				                      final Throwable throwable) {
					onError.apply(throwable);
				}
			});
	}

	@Override
	public LiveData<List<CoinEntity>> listings() {
		return db.coins().fetchAll();
	}

	@Override
	public void refresh(@NonNull final String convert,
	                    @NonNull final Runnable onSuccess,
	                    @NonNull final Consumer<Throwable> onError) {

		Objects.requireNonNull(convert);
		Objects.requireNonNull(onSuccess);
		Objects.requireNonNull(onError);

		listings(convert, coins -> {
			final List<CoinEntity> entities = new ArrayList<>();
			for (final Coin coin : coins) {
				double price = 0d;
				double change24 = 0d;
				final Quote quote = coin.getQuotes().get(convert);
				if (quote != null) {
					price = quote.getPrice();
					change24 = quote.getChange24h();
				}
				entities.add(CoinEntity.create(
					coin.getId(),
					coin.getSymbol(),
					price,
					change24
				));

				db.coins().insertAll(Collections.unmodifiableList(entities));
				onSuccess.run();
			}
		}, onError);
	}
}
