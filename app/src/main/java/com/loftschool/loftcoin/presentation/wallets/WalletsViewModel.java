package com.loftschool.loftcoin.presentation.wallets;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.data.WalletsRepository;
import com.loftschool.loftcoin.db.Wallet;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;

public final class WalletsViewModel extends ViewModel {

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
}
