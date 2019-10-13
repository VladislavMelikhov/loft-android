package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.db.LoftDb;
import com.loftschool.loftcoin.db.Wallet;
import com.loftschool.loftcoin.db.WalletsDao;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public final class WalletsRepositoryImpl implements WalletsRepository {

	private final WalletsDao wallets;

	@Inject
	public WalletsRepositoryImpl(@NonNull final LoftDb db) {
		this.wallets = Objects.requireNonNull(db).wallets();
	}

	@NonNull
	@Override
	public Observable<List<Wallet.View>> wallets() {
		//TODO react on 'coins' changes
		return wallets.fetchAllWallets();
	}

	@NonNull
	@Override
	public Single<CoinEntity> findNextCoin() {
		return wallets.findNextCoin();
	}

	@NonNull
	@Override
	public Single<Long> saveWallet(@NonNull Wallet wallet) {
		Objects.requireNonNull(wallet);
		return wallets.insertWallet(wallet);
	}
}
