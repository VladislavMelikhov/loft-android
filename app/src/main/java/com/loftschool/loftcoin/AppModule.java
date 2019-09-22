package com.loftschool.loftcoin;

import android.app.Application;
import android.content.Context;

import androidx.core.os.ConfigurationCompat;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

import dagger.Module;
import dagger.Provides;

@Module
public interface AppModule {

	@Provides
	static Context context(@NotNull final Application application) {
		Objects.requireNonNull(application);

		return application.getApplicationContext();
	}

	@Provides
	static Locale locale(@NotNull final Context context) {
		Objects.requireNonNull(context);

		return ConfigurationCompat
			.getLocales(context.getResources().getConfiguration())
			.get(0);
	}
}
