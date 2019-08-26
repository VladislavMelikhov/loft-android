package com.loftschool.loftcoin.data.dto;

import com.google.gson.annotations.SerializedName;

public final class Quote {

	@SerializedName("price")
	private double price;

	@SerializedName("percent_change_24h")
	private double change24h;

	public double getPrice() {
		return price;
	}

	public double getChange24h() {
		return change24h;
	}
}
