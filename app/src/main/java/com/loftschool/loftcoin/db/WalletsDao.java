package com.loftschool.loftcoin.db;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface WalletsDao {

	@Query("SELECT * FROM wallets_view")
	Observable<List<Wallet.View>> fetchAllWallets();

	@Query("SELECT * FROM coins" +
		"WHERE id NOT IN(SELECT coin_id FROM wallets)" +
		"ORDER BY price DESC" +
		"LIMIT 1")
	Single<CoinEntity> findNextCoin();

	@Insert
	Single<Long> insertWallet(Wallet wallet);
}
