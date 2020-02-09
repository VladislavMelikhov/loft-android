package com.loftschool.loftcoin.presentation.wallets;

import com.loftschool.loftcoin.TestSchedulers;
import com.loftschool.loftcoin.data.WalletsRepository;
import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.db.Wallet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public final class WalletsViewModelTest {

	private WalletsViewModel viewModel;
	private List<Wallet> wallets;
	private Subject<List<Wallet>> walletsRelay;

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
		final WalletsRepository repository = BDDMockito.mock(WalletsRepository.class);
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
}