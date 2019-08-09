package com.loftschool.loftcoin;

import android.app.Application;
import android.os.StrictMode;

import com.loftschool.loftcoin.log.DebugTree;

import timber.log.Timber;

public final class LoftApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		if (BuildConfig.DEBUG) {
			StrictMode.enableDefaults();
			Timber.plant(new DebugTree());
		}
		Timber.d("%s", this);
	}
}
