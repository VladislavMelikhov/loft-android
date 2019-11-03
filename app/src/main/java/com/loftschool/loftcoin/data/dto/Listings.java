package com.loftschool.loftcoin.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Listings {

	@SerializedName("data")
	private List<Coin> data;

	@Nullable
	public List<Coin> getData() {
		return data;
	}
}
