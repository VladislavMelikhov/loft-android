package com.loftschool.loftcoin.fcm;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.loftschool.loftcoin.AppComponent;
import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.presentation.main.MainActivity;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public final class FcmService extends FirebaseMessagingService {

	private final CompositeDisposable compositeDisposable = new CompositeDisposable();

	@Inject
	FcmChannel fcmChannel = null;

	@Override
	public void onCreate() {
		super.onCreate();
		AppComponent.from(getApplicationContext()).inject(this);
	}

	@Override
	public void onMessageReceived(@NonNull final RemoteMessage remoteMessage) {
		final RemoteMessage.Notification notification = remoteMessage.getNotification();
		if (notification != null) {
			compositeDisposable.add(
				fcmChannel.notify(
					Objects.toString(notification.getTitle(), getString(R.string.app_name)),
					Objects.toString(notification.getBody(), "wtf"),
					MainActivity.class
				).subscribe()
			);
		}
	}

	@Override
	public void onDestroy() {
		compositeDisposable.dispose();
		super.onDestroy();
	}
}
