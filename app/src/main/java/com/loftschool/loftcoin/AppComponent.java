package com.loftschool.loftcoin;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.data.CoinsRepository;
import com.loftschool.loftcoin.data.Currencies;
import com.loftschool.loftcoin.data.DataModule;
import com.loftschool.loftcoin.data.WalletsRepository;
import com.loftschool.loftcoin.fcm.FcmChannel;
import com.loftschool.loftcoin.fcm.FcmModule;
import com.loftschool.loftcoin.fcm.FcmService;
import com.loftschool.loftcoin.rx.RxModule;
import com.loftschool.loftcoin.rx.RxSchedulers;

import java.util.Locale;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
	AppModule.class,
	DataModule.class,
	RxModule.class,
	FcmModule.class
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

	void inject(FcmService service);

	Locale locale();

	CoinsRepository coinsRepository();

	WalletsRepository walletsRepository();

	Currencies currencies();

	RxSchedulers schedulers();

	FcmChannel fcmChannel();

	@Component.Factory
	interface Factory {
		AppComponent create(@BindsInstance Application application);
	}
}
