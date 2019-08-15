package com.loftschool.loftcoin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public final class SplashActivity extends AppCompatActivity {

	private static final int SPLASH_DELAY = 2000;
	public static final String SHOW_WELCOME_SCREEN = "SHOW_WELCOME_SCREEN";

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		new Handler().postDelayed(() -> {
			if (sharedPreferences.getBoolean(SHOW_WELCOME_SCREEN, true)) {
				startActivity(new Intent(this, WelcomeActivity.class));
			} else {
				startActivity(new Intent(this, MainActivity.class));
			}

		}, SPLASH_DELAY);
	}
}
