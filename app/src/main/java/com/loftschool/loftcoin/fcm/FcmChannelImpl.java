package com.loftschool.loftcoin.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.util.Objects;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public final class FcmChannelImpl implements FcmChannel {

	private final Context context;
	private final RxSchedulers schedulers;
	private final Executor ioExecutor;
	private final NotificationManager notificationManager;

	@Inject
	public FcmChannelImpl(@NonNull final Context context,
	                      @NonNull final RxSchedulers schedulers) {
		this.context = Objects.requireNonNull(context);
		this.schedulers = Objects.requireNonNull(schedulers);
		ioExecutor = schedulers.io()::scheduleDirect;
		notificationManager = ContextCompat.getSystemService(context, NotificationManager.class);
	}

	@NonNull
	@Override
	public Single<String> token() {
		return Single.create(emitter -> {
			FirebaseInstanceId.getInstance().getInstanceId()
				.addOnSuccessListener(ioExecutor, result -> {
					if (!emitter.isDisposed()) {
						emitter.onSuccess(result.getToken());
					}
				})
				.addOnFailureListener(ioExecutor, emitter::tryOnError);
		});
	}

	@NonNull
	@Override
	public Completable createDefaultChannel() {
		return Completable.fromAction(() -> {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				notificationManager.createNotificationChannel(new NotificationChannel(
					context.getString(R.string.fcm_default_channel_id),
					context.getString(R.string.fcm_default_channel_name),
					NotificationManager.IMPORTANCE_LOW
				));
			}
		}).subscribeOn(schedulers.main());
	}

	@NonNull
	@Override
	public Completable notify(@NonNull final String title,
	                          @NonNull final String message,
	                          @NonNull final Class<?> receiver) {
		return Completable
			.fromAction(() -> {
				final String channelId = context.getString(R.string.fcm_default_channel_id);
				final Notification notification = new NotificationCompat.Builder(context, channelId)
					.setSmallIcon(R.mipmap.ic_launcher)
					.setContentTitle(title)
					.setContentText(message)
					.setAutoCancel(true)
					.setContentIntent(PendingIntent.getActivity(
						context,
						0,
						new Intent(context, receiver).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
						PendingIntent.FLAG_ONE_SHOT
					))
					.build();
				notificationManager.notify(1, notification);
			})
			.startWith(createDefaultChannel())
			.subscribeOn(schedulers.main());

	}
}
