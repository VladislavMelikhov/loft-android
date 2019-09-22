package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.BuildConfig;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public interface DataModule {

	@Provides
	@Singleton
	static OkHttpClient httpClient() {
		final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
		okHttpClientBuilder.addInterceptor(new AddKeyInterceptor());
		if (BuildConfig.DEBUG) {
			final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
			httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.HEADERS);
			httpLoggingInterceptor.redactHeader(AddKeyInterceptor.KEY_HEADER);
			okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
		}
		return okHttpClientBuilder.build();
	}

	@Provides
	@Singleton
	static CoinMarketCapApi coinMarketCapApi(@NonNull final OkHttpClient okHttpClient) {
		return new Retrofit.Builder()
			.client(okHttpClient)
			.baseUrl(BuildConfig.CMC_API_ENDPOINT)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(CoinMarketCapApi.class);
	}

	@Binds
	CoinsRepository coinsRepository(CoinsRepositoryImpl impl);

	@Binds
	Currencies currencies(CurrenciesImpl impl);
}
