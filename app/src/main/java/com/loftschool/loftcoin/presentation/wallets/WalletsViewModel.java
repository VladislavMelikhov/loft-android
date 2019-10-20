package com.loftschool.loftcoin.presentation.wallets;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.data.WalletsRepository;
import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.db.Wallet;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

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
	Single<Long> createNewWallet() {
		return walletsRepository
			.findNextCoin()
			.map(this::createFakeWallet)
			.flatMap(walletsRepository::saveWallet)
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
}
