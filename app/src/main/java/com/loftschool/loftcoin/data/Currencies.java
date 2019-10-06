package com.loftschool.loftcoin.data;


import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;

public interface Currencies {

	@NonNull
	List<Currency> getAvailableCurrencies();

	@NonNull
	Currency getCurrent();

	void setCurrent(@NonNull Currency currency);

	@NonNull
	Observable<Currency> current();
}
