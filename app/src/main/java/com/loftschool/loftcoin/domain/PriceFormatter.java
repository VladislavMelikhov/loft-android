package com.loftschool.loftcoin.domain;

import androidx.annotation.NonNull;

public interface PriceFormatter {

	String format(double value);

	String format(double value, @NonNull String sign);
}
