package com.loftschool.loftcoin.presentation.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loftschool.loftcoin.R;

import java.util.Objects;

public final class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final MainViewModel mainViewModel = ViewModelProviders
			.of(this)
			.get(MainViewModel.class);

		setSupportActionBar(findViewById(R.id.toolbar_main));

		mainViewModel
			.getTitleId()
			.observe(
				this,
				titleId -> {
					Objects
						.requireNonNull(getSupportActionBar())
						.setTitle(titleId);
				}
			);

		this.<BottomNavigationView>findViewById(R.id.bottom_navigation)
			.setOnNavigationItemSelectedListener(menuItem -> {
				mainViewModel.onTabSelect(
					menuItem.getItemId(),
					getSupportFragmentManager()
				);
				return true;
			});

		if (savedInstanceState == null) {
			mainViewModel.onTabSelect(
				R.id.wallets,
				getSupportFragmentManager()
			);
		}
	}
}
