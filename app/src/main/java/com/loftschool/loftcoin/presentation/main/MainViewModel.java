package com.loftschool.loftcoin.presentation.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public final class MainViewModel extends ViewModel {

	private final MutableLiveData<Integer> titleId =
		new MutableLiveData<>();

	public MainViewModel() {
	}

	@NonNull
	public LiveData<Integer> getTitleId() {
		return titleId;
	}
}
