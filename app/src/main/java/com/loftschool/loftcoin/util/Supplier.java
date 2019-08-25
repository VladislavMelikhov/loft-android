package com.loftschool.loftcoin.util;

import androidx.annotation.NonNull;

public interface Supplier<T> {
	@NonNull
	T get();
}
