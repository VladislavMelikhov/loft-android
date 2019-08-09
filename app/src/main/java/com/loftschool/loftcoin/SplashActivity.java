package com.loftschool.loftcoin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Handler().postDelayed(() -> {
			final Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}, 2000);
	}
}
