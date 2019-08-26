package com.loftschool.loftcoin.data.dto;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.Map;

public final class Coin {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("symbol")
	private String symbol;

	@SerializedName("quote")
	private Map<String, Quote> quotes;

	public int getId() {
		return id;
	}

	@NonNull
	public String getName() {
		return name == null ? "" : name;
	}

	@NonNull
	public String getSymbol() {
		return symbol == null ? "" : symbol;
	}

	@NonNull
	public Map<String, Quote> getQuotes() {
		return quotes == null ? Collections.emptyMap() : Collections.unmodifiableMap(quotes);
	}
}
