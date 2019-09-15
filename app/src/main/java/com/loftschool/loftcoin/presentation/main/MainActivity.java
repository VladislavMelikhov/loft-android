package com.loftschool.loftcoin.presentation.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loftschool.loftcoin.R;
import com.loftschool.loftcoin.vm.ViewModelFactory;

import java.util.Objects;

import javax.inject.Inject;

public final class MainActivity extends AppCompatActivity {

	@Inject MainRouter mainRouter = null;

	@Inject
	ViewModelProvider.Factory viewModelFactory = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DaggerMainComponent
			.builder()
			.activity(this)
			.build()
			.inject(this);

		setContentView(R.layout.activity_main);

		final MainViewModel mainViewModel = ViewModelProviders
			.of(this, viewModelFactory)
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
				mainRouter.navigateTo(menuItem.getItemId());
				return true;
			});

		if (savedInstanceState == null) {
			mainRouter.navigateTo(R.id.wallets);
		}
	}
}
