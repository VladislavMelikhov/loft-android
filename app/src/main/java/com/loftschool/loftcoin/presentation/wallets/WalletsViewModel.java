package com.loftschool.loftcoin.presentation.wallets;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.data.WalletsRepository;
import com.loftschool.loftcoin.db.Transaction;
import com.loftschool.loftcoin.db.Wallet;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public final class WalletsViewModel extends ViewModel {

	private static final Random PRNG = new SecureRandom();

	private final WalletsRepository walletsRepository;
	private final RxSchedulers schedulers;

	private final Observable<List<Wallet>> wallets;

	private final Subject<Integer> walletPosition;

	private final Observable<List<Transaction>> transations;

	@Inject
	public WalletsViewModel(@NonNull final WalletsRepository walletsRepository,
	                        @NonNull final RxSchedulers schedulers) {
		this.walletsRepository = Objects.requireNonNull(walletsRepository);
		this.schedulers = Objects.requireNonNull(schedulers);

		wallets = walletsRepository
			.wallets()
			.replay(1)
			.autoConnect()
			.subscribeOn(schedulers.io());

		walletPosition = BehaviorSubject.createDefault(0);

		transations = wallets
			.filter(wallets -> !wallets.isEmpty())
			.switchMap(wallets -> walletPosition
				.observeOn(schedulers.io())
				.map(position -> Math.max(0, position))
				.map(position -> Math.min(position, wallets.size() - 1))
				.map(wallets::get)
			)
			.distinctUntilChanged(Wallet::id)
			.switchMap(walletsRepository::transactions)
			.subscribeOn(schedulers.io());
	}

	@NonNull
	Observable<List<Wallet>> wallets() {
		return wallets.observeOn(schedulers.main());
	}

	@NonNull
	Completable createNextWallet() {
		return wallets
			.firstElement()
			.flatMapSingle(wallets -> Observable
				.fromIterable(wallets)
				.map(wallet -> wallet.coin().id())
				.toList()
			)
			.flatMap(walletsRepository::findNextCoin)
			.map(coin -> Wallet.create(
				UUID.randomUUID().toString(),
				PRNG.nextDouble() * (1 + PRNG.nextInt(100)),
				coin
			))
			.flatMapCompletable(walletsRepository::saveWallet)
			.subscribeOn(schedulers.io())
			.observeOn(schedulers.main());
	}

	@NonNull
	Observable<List<Transaction>> transactions() {
		return transations.observeOn(schedulers.main());
	}

	void submitWalletPosition(final int position) {
		walletPosition.onNext(position);
	}
}
