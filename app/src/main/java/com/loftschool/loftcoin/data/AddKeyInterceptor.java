package com.loftschool.loftcoin.data;

import com.loftschool.loftcoin.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public final class AddKeyInterceptor implements Interceptor {

	public static final String KEY_HEADER = "X-CMC_PRO_API_KEY";

	@NotNull
	@Override
	public Response intercept(@NotNull final Chain chain) throws IOException {
		return chain.proceed(
				chain
					.request()
					.newBuilder()
					.addHeader(KEY_HEADER, BuildConfig.CMC_API_KEY)
					.build()
		);
	}
}
