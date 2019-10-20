package com.loftschool.loftcoin.presentation.wallets;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.data.WalletsRepository;
import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.db.Transaction;
import com.loftschool.loftcoin.db.Wallet;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public final class WalletsViewModel extends ViewModel {

	private static final Random PRNG = new SecureRandom();

	private final WalletsRepository walletsRepository;
	private final RxSchedulers schedulers;

	@Inject
	public WalletsViewModel(@NonNull final WalletsRepository walletsRepository,
	                        @NonNull final RxSchedulers schedulers) {
		this.walletsRepository = Objects.requireNonNull(walletsRepository);
		this.schedulers = Objects.requireNonNull(schedulers);
	}

	@NonNull
	Observable<List<Wallet.View>> wallets() {
		return walletsRepository
			.wallets()
			.subscribeOn(schedulers.io())
			.observeOn(schedulers.main());
	}

	@NonNull
	Completable createNewWallet() {
		return walletsRepository
			.findNextCoin()
			.map(this::createFakeWallet)
			.flatMap(walletsRepository::saveWallet)
			.map(this::generateFakeTransactions)
			.flatMapCompletable(walletsRepository::saveTransactions)
			.subscribeOn(schedulers.io())
			.observeOn(schedulers.main());
	}

	@NonNull
	Observable<List<Transaction.View>> transactions() {
		return walletsRepository
			.transactions(1)
			.subscribeOn(schedulers.io())
			.observeOn(schedulers.main());
	}

	@NonNull
	private Wallet createFakeWallet(@NonNull final CoinEntity coin) {
		Objects.requireNonNull(coin);
		return Wallet.create(
			0,
			PRNG.nextDouble() * (1 + PRNG.nextInt(100)),
			coin.id()
		);
	}

	@NonNull
	private List<Transaction> generateFakeTransactions(final long walletId) {
		final int count = 1 + PRNG.nextInt(20);
		final List<Transaction> transactions = new ArrayList<>(count);
		final long now = System.currentTimeMillis();
		for (int i = 0; i < count; ++i) {
			transactions.add(
				Transaction.create(
					0,
					now - TimeUnit.HOURS.toMillis(12 + PRNG.nextInt(120)),
					PRNG.nextDouble() * (PRNG.nextInt(100) - 50),
					walletId
				)
			);
		}
		return transactions;
	}
}
