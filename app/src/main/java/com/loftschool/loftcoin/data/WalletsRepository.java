package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.db.Wallet;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface WalletsRepository {

	@NonNull
	Observable<List<Wallet.View>> wallets();

	@NonNull
	Single<CoinEntity> findNextCoin();

	@NonNull
	Single<Long> saveWallet(@NonNull Wallet wallet);
}
