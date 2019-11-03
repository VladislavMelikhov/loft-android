package com.loftschool.loftcoin.rx;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class RxSchedulersImpl implements RxSchedulers {

	@Inject
	RxSchedulersImpl() {

	}

	@NonNull
	@Override
	public Scheduler io() {
		return Schedulers.io();
	}

	@NonNull
	@Override
	public Scheduler main() {
		return AndroidSchedulers.mainThread();
	}
}
