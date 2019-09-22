package com.loftschool.loftcoin.data;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.data.dto.Coin;
import com.loftschool.loftcoin.util.Consumer;

import java.util.List;

public interface CoinsRepository {

	void listings(@NonNull String convert,
	              @NonNull Consumer<List<Coin>> onSuccess,
	              @NonNull Consumer<Throwable> onError);
}