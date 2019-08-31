package com.loftschool.loftcoin.data.dto;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public final class Listings {

	@SerializedName("data")
	private List<Coin> data;

	@NonNull
	public List<Coin> getData() {
		return data == null ? Collections.emptyList() : Collections.unmodifiableList(data);
	}
}
