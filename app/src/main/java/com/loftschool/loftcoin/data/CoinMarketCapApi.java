package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinMarketCapApi {

	@GET("cryptocurrency/listings/latest")
	Call<Void> listings(@NonNull @Query("convert") String convert);
}
