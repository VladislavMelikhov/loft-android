package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiFactory {

	private final Retrofit retrofit;

	public ApiFactory() {
		retrofit = new Retrofit.Builder()
			.client(createOkHttpClient())
			.baseUrl(BuildConfig.CMC_API_ENDPOINT)
			.addConverterFactory(GsonConverterFactory.create())
			.build();
	}

	@NonNull
	public CoinMarketCapApi createCoinMarketCapApi() {
		return retrofit.create(CoinMarketCapApi.class);
	}

	@NonNull
	private OkHttpClient createOkHttpClient() {
		final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
		addAddKeyInterceptor(okHttpClientBuilder);
		addHttpLoggingInterceptor(okHttpClientBuilder);
		return okHttpClientBuilder.build();
	}

	private void addAddKeyInterceptor(@NonNull final OkHttpClient.Builder okHttpClientBuilder) {
		okHttpClientBuilder.addInterceptor(new AddKeyInterceptor());
	}

	private void addHttpLoggingInterceptor(@NonNull final OkHttpClient.Builder okHttpClientBuilder) {
		if (BuildConfig.DEBUG) {
			final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
			httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.HEADERS);
			httpLoggingInterceptor.redactHeader(AddKeyInterceptor.KEY_HEADER);
			okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
		}
	}
}
