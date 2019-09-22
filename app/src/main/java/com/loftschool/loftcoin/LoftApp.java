package com.loftschool.loftcoin;

import android.app.Application;
import android.os.StrictMode;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.log.DebugTree;

import timber.log.Timber;

public final class LoftApp extends Application {

	private AppComponent appComponent = null;

	@Override
	public void onCreate() {
		super.onCreate();
		if (BuildConfig.DEBUG) {
			StrictMode.enableDefaults();
			Timber.plant(new DebugTree());
		}
		Timber.d("%s", this);

		appComponent = DaggerAppComponent.factory().create(this);
	}

	@NonNull
	public AppComponent getAppComponent() {
		return appComponent;
	}
}
