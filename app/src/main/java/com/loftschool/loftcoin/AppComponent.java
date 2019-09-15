package com.loftschool.loftcoin;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Locale;

import javax.inject.Provider;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
	AppModule.class
})
public interface AppComponent {

	@NonNull
	static AppComponent from(@NonNull final Context context) {
		final Context applicationContext = context.getApplicationContext();
		if (applicationContext instanceof LoftApp) {
			return ((LoftApp) applicationContext).getAppComponent();
		}
		throw new IllegalStateException("Application should be an instance of LoftApp");
	}

	Provider<Locale> locale();

	@Component.Factory
	interface Factory {
		AppComponent create(@BindsInstance Application application);
	}
}
