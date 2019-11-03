package com.loftschool.loftcoin.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface CoinsDao {

	@Query("SELECT * FROM coins ORDER BY price DESC")
	Observable<List<CoinEntity>> fetchAllCoins();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<CoinEntity> coins);
}
