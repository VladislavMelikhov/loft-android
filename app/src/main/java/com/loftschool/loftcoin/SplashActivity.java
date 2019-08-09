package com.loftschool.loftcoin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public final class SplashActivity extends AppCompatActivity {

	private static final int SPLASH_DELAY = 2000;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Handler().postDelayed(() -> {
			startActivity(new Intent(this, MainActivity.class));
		}, SPLASH_DELAY);
	}
}
