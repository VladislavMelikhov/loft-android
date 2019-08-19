package com.loftschool.loftcoin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public final class SplashActivity extends AppCompatActivity {

	private static final int SPLASH_DELAY = 2000;
	public static final String SHOW_WELCOME_SCREEN = "SHOW_WELCOME_SCREEN";

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		final Settings settings = new Settings(this);

		new Handler().postDelayed(() -> {
			if (settings.shouldShowWelcomeScreen()) {
				startActivity(new Intent(this, WelcomeActivity.class));
			} else {
				startActivity(new Intent(this, MainActivity.class));
			}

		}, SPLASH_DELAY);
	}
}
