package com.loftschool.loftcoin.data;


import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public final class QueryOnSubscribe implements ObservableOnSubscribe<List<DocumentSnapshot>> {

	private final Executor executor;
	private final Query query;

	public QueryOnSubscribe(@NonNull final Executor executor,
	                        @NonNull final Query query) {
		this.executor = Objects.requireNonNull(executor);
		this.query = Objects.requireNonNull(query);
	}

	@Override
	public void subscribe(final ObservableEmitter<List<DocumentSnapshot>> emitter) throws Exception {
		final ListenerRegistration registration = query
			.addSnapshotListener(executor, (snapshots, e) -> {
				if (snapshots != null) {
					if (!emitter.isDisposed()) {
						emitter.onNext(snapshots.getDocuments());
					}
				} else if (e != null) {
					emitter.tryOnError(e);
				}
			});
		emitter.setCancellable(registration::remove);
	}
}