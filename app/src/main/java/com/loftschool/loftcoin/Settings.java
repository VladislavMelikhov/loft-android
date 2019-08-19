package com.loftschool.loftcoin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import java.util.Objects;

public final class Settings {

	private static final String KEY_SHOW_WELCOME_SCREEN = "SHOW_WELCOME_SCREEN";

	private final SharedPreferences sharedPreferences;

	public Settings(@NonNull final Context context) {
		Objects.requireNonNull(context);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public boolean shouldShowWelcomeScreen() {
		return sharedPreferences.getBoolean(KEY_SHOW_WELCOME_SCREEN, true);
	}

	public void doNotShowWelcomeScreenNextTime() {
		sharedPreferences
			.edit()
			.putBoolean(KEY_SHOW_WELCOME_SCREEN, false)
			.apply();
	}
}
