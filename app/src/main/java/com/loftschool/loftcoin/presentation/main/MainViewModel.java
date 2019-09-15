package com.loftschool.loftcoin.presentation.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loftschool.loftcoin.R;

import javax.inject.Inject;

public final class MainViewModel extends ViewModel {

	private final MutableLiveData<Integer> titleId =
		new MutableLiveData<>();

	@Inject
	public MainViewModel() {
		titleId.setValue(R.string.wallets);
	}

	@NonNull
	public LiveData<Integer> getTitleId() {
		return titleId;
	}
}
