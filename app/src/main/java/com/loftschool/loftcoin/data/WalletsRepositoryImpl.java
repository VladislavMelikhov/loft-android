package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.loftschool.loftcoin.db.CoinEntity;
import com.loftschool.loftcoin.db.LoftDb;
import com.loftschool.loftcoin.db.Transaction;
import com.loftschool.loftcoin.db.Wallet;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;

public final class WalletsRepositoryImpl implements WalletsRepository {

	private final RxSchedulers schedulers;
	private final LoftDb loftDb;
	private final FirebaseFirestore firestore;
	private final Executor ioExecutor;

	@Inject
	public WalletsRepositoryImpl(@NonNull final RxSchedulers schedulers,
	                             @NonNull final LoftDb loftDb) {
		this.schedulers = Objects.requireNonNull(schedulers);
		this.loftDb = Objects.requireNonNull(loftDb);
		this.firestore = FirebaseFirestore.getInstance();
		this.ioExecutor = schedulers.io()::scheduleDirect;
	}

	@NonNull
	@Override
	public Observable<List<Wallet>> wallets() {
		return Observable
			.create(new QueryOnSubscribe(ioExecutor, firestore
				.collection("wallets")
				.orderBy("created", Query.Direction.ASCENDING)
			))
			.flatMapSingle(documents -> Observable
				.fromIterable(documents)
				.flatMapSingle(document ->
					loftDb.coins()
						.fetchCoin(document.getLong("coinId"))
						.map(coin -> Wallet.create(
							document.getId(),
							document.getDouble("balance"),
							coin
						))
				)
				.toList()
			);
	}

	@NonNull
	@Override
	public Observable<List<Transaction>> transactions(@NonNull final Wallet wallet) {
		return Observable
			.create(new QueryOnSubscribe(ioExecutor, firestore
				.collection("wallets")
				.document(wallet.id())
				.collection("transactions")
				.orderBy("timestamp", Query.Direction.DESCENDING)
			))
			.flatMapSingle(documents -> Observable
				.fromIterable(documents)
				.map(document -> Transaction.create(
					document.getId(),
					document.getDouble("amount"),
					document.getDate("timestamp"),
					wallet
				))
				.toList()
			);
	}

	@NonNull
	@Override
	public Single<CoinEntity> findNextCoin(@NonNull final List<Long> exclude) {
		return loftDb.coins().findNextCoin(exclude);
	}

	@NonNull
	@Override
	public Completable saveWallet(@NonNull final Wallet wallet) {
		return Completable.create(emitter -> {
			final Map<String, Object> data = new HashMap<>();
			final CoinEntity coin = wallet.coin();
			data.put("balance", wallet.balance1());
			data.put("coinId", coin.id());
			data.put("symbol", coin.symbol());
			data.put("created", FieldValue.serverTimestamp());
			firestore.collection("wallets").add(data)
				.addOnSuccessListener(ioExecutor, documentReference -> {
					if (!emitter.isDisposed()) {
						emitter.onComplete();
					}
				})
				.addOnFailureListener(ioExecutor, emitter::tryOnError);
		});
	}
}
