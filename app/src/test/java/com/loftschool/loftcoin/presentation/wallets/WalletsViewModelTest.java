package com.loftschool.loftcoin.presentation.wallets;

import com.loftschool.loftcoin.TestSchedulers;
import com.loftschool.loftcoin.data.WalletsRepository;
import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.db.Transaction;
import com.loftschool.loftcoin.db.Wallet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public final class WalletsViewModelTest {

	private WalletsViewModel viewModel;
	private List<Wallet> wallets;
	private Subject<List<Wallet>> walletsRelay;
	private WalletsRepository repository;

	@Before
	public void setUp() throws Exception {

		wallets = new ArrayList<>();

		wallets.add(
			Wallet.create(
				UUID.randomUUID().toString(),
				1.23,
				CoinEntity.create(
					1,
					"Bitcoin",
					"BTC",
					11234,
					+5.78
				)
			)
		);

		wallets.add(
			Wallet.create(
				UUID.randomUUID().toString(),
				6.77,
				CoinEntity.create(
					2,
					"Etherium",
					"ETH",
					174,
					-2.23
				)
			)
		);
		repository = BDDMockito.mock(WalletsRepository.class);
		walletsRelay = BehaviorSubject.create();
		BDDMockito
			.given(repository.wallets())
			.willReturn(walletsRelay);

		viewModel = new WalletsViewModel(repository, new TestSchedulers());
	}

	@Test
	public void wallets_when_everything_is_ok() {
		final TestObserver<List<Wallet>> observer = viewModel.wallets().test();

		walletsRelay.onNext(wallets);

		observer.awaitCount(1);
		observer.assertNoErrors();
		observer.assertValue(wallets);
	}

	@Test
	public void wallets_when_repo_throws_error() {
		final TestObserver<List<Wallet>> observer = viewModel.wallets().test();

		final Exception error = new Exception("test error");
		walletsRelay.onError(error);

		observer.awaitTerminalEvent();
		observer.assertError(error);
	}

	@Test
	public void transactions_when_wallets_is_empty() throws Exception {
		final TestObserver<List<Transaction>> observer = viewModel.transactions().test();

		walletsRelay.onNext(Collections.emptyList());

		observer.await(100, TimeUnit.MILLISECONDS);
		observer.assertEmpty();
	}

	@Test
	public void transactions() {

		final Wallet wallet0 = wallets.get(0);
		final List<Transaction> transactions0 = Collections.singletonList(Transaction.create(
			UUID.randomUUID().toString(),
			34.56,
			new Date(),
			wallet0
		));

		final Wallet wallet1 = wallets.get(1);
		final List<Transaction> transactions1 = Collections.singletonList(Transaction.create(
			UUID.randomUUID().toString(),
			56.78,
			new Date(),
			wallet1
		));

		BDDMockito
			.given(repository.transactions(wallet0))
			.willReturn(Observable.just(transactions0));

		BDDMockito
			.given(repository.transactions(wallet1))
			.willReturn(Observable.just(transactions1));

		walletsRelay.onNext(wallets);

		final TestObserver<List<Transaction>> observer = viewModel.transactions().test();


		observer.awaitCount(1);
		viewModel.submitWalletPosition(1);

		observer.awaitCount(2);
		observer.assertNoErrors();
		observer.assertValues(
			transactions0,
			transactions1
		);
	}

	@Test
	public void transaction_when_position_out_of_bounds() {
		final List<Transaction> transactions = Collections.emptyList();

		BDDMockito
			.given(repository.transactions(wallets.get(0)))
			.willReturn(Observable.just(transactions));

		BDDMockito
			.given(repository.transactions(wallets.get(1)))
			.willReturn(Observable.just(transactions));

		walletsRelay.onNext(wallets);
		final TestObserver<List<Transaction>> observer = viewModel.transactions().test();

		observer.awaitCount(1);
		viewModel.submitWalletPosition(-1);
		observer.awaitCount(2);
		viewModel.submitWalletPosition(wallets.size() + 1);
		observer.awaitCount(3);
		observer.assertNoErrors();
	}

	@Test
	public void createNextWallet() {

		final CoinEntity coin = CoinEntity.create(
			2,
			"Bitcoin Cash",
			"BTH",
			345,
			-2.23
		);

		walletsRelay.onNext(wallets);

		BDDMockito
			.given(repository.findNextCoin(BDDMockito.any()))
			.willReturn(Single.just(coin));

		BDDMockito
			.given(repository.saveWallet(BDDMockito.any()))
			.willReturn(Completable.complete());

		final TestObserver<Void> observer = viewModel.createNextWallet().test();

		observer.awaitTerminalEvent();
		observer.assertComplete();
	}

	@Test
	public void createNextWallet_when_there_is_no_more_coins() {
		final Exception error = new Exception("no more coins");

		walletsRelay.onNext(wallets);


		BDDMockito
			.given(repository.findNextCoin(BDDMockito.any()))
			.willReturn(Single.error(error));

		BDDMockito
			.given(repository.saveWallet(BDDMockito.any()))
			.willReturn(Completable.complete());

		final TestObserver<Void> observer = viewModel.createNextWallet().test();

		observer.awaitTerminalEvent();
		observer.assertError(error);
	}

	@Test
	public void createNextWallet_when_saveWallet_throws_error() {
		final Exception error = new Exception("could not save wallet");
		final CoinEntity coin = CoinEntity.create(
			2,
			"Bitcoin Cash",
			"BTH",
			345,
			-2.23
		);

		walletsRelay.onNext(wallets);


		BDDMockito
			.given(repository.findNextCoin(BDDMockito.any()))
			.willReturn(Single.just(coin));

		BDDMockito
			.given(repository.saveWallet(BDDMockito.any()))
			.willReturn(Completable.error(error));

		final TestObserver<Void> observer = viewModel.createNextWallet().test();

		observer.awaitTerminalEvent();
		observer.assertError(error);
	}
}