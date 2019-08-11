package com.loftschool.loftcoin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

public final class WelcomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		this.<ViewPager>findViewById(R.id.vp_welcome_pager)
			.setAdapter(new WelcomePagesAdapter(getLayoutInflater()));

		this.<AppCompatButton>findViewById(R.id.btn_start_working)
			.setOnClickListener(view -> {
				final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
				sharedPreferences.edit().putBoolean("show_welcome_screen", false).apply();

				final Intent intent = new Intent(this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
			});
	}
}
