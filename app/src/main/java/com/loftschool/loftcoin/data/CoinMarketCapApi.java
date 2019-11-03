package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.data.dto.Listings;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinMarketCapApi {

	@GET("cryptocurrency/listings/latest")
	Observable<Listings> listings(@NonNull @Query("convert") String convert);
}
