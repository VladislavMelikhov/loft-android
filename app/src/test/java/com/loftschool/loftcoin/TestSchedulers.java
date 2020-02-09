package com.loftschool.loftcoin;

import androidx.annotation.NonNull;

import com.loftschool.loftcoin.rx.RxSchedulers;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public final class TestSchedulers implements RxSchedulers {

	@NonNull
	@Override
	public Scheduler io() {
		return Schedulers.io();
	}

	@NonNull
	@Override
	public Scheduler main() {
		return Schedulers.single();
	}
}
