package com.loftschool.loftcoin;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.data.CoinsRepository;
import com.loftschool.loftcoin.data.DataModule;

import java.util.Locale;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
	AppModule.class,
	DataModule.class
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

	CoinsRepository coinsRepository();

	@Component.Factory
	interface Factory {
		AppComponent create(@BindsInstance Application application);
	}
}
