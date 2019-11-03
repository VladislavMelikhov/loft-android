package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.db.CoinEntity;

import java.util.List;

import io.reactivex.Observable;

public interface CoinsRepository {

	@NonNull
	Observable<List<CoinEntity>> listings(@NonNull String convert);
}