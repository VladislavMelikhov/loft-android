package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.data.dto.Listings;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinMarketCapApi {

	@GET("cryptocurrency/listings/latest")
	Call<Listings> listings(@NonNull @Query("convert") String convert);
}
