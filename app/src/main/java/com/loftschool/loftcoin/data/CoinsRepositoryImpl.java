package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.loftschool.loftcoin.data.dto.Coin;
import com.loftschool.loftcoin.data.dto.Listings;
import com.loftschool.loftcoin.data.dto.Quote;
import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.db.CoinsDao;
import com.loftschool.loftcoin.db.LoftDb;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public final class CoinsRepositoryImpl implements CoinsRepository {

	private final CoinMarketCapApi coinMarketCapApi;
	private final LoftDb db;
	private final RxSchedulers schedulers;

	@Inject
	public CoinsRepositoryImpl(@NonNull final CoinMarketCapApi coinMarketCapApi,
	                           @NonNull final LoftDb db,
	                           @NonNull final RxSchedulers schedulers) {
		this.coinMarketCapApi = Objects.requireNonNull(coinMarketCapApi);
		this.db = Objects.requireNonNull(db);
		this.schedulers = Objects.requireNonNull(schedulers);
	}

	@NonNull
	@Override
	public Observable<List<CoinEntity>> listings(@NonNull final String convert) {
		final CoinsDao coinsDao = db.coins();
		return Observable.merge(
			coinsDao.fetchAllCoins(),
			coinMarketCapApi
				.listings(convert)
				.map(this::fromListings)
				.doOnNext(coinsDao::insertAll)
				.skip(1)
				.subscribeOn(schedulers.io())
		);
	}

	private List<CoinEntity> fromListings(@Nullable final Listings listings) {
		if (listings != null && listings.getData() != null) {
			final List<CoinEntity> entities = new ArrayList<>();
			for (final Coin coin : listings.getData()) {
				double price = 0d;
				double change24 = 0d;
				final Iterator<Quote> it = coin.getQuotes().values().iterator();
				if (it.hasNext()) {
					final Quote quote = it.next();
					if (quote != null) {
						price = quote.getPrice();
						change24 = quote.getChange24h();
					}
				}
				entities.add(CoinEntity.create(
					coin.getId(),
					coin.getSymbol(),
					price,
					change24
				));
			}
			return Collections.unmodifiableList(entities);
		}
		return Collections.emptyList();
	}
}
