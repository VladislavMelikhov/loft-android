package com.loftschool.loftcoin.log;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import timber.log.Timber;

public final class DebugTree extends Timber.DebugTree {

	@Override
	protected void log(final int priority,
	                   final String tag,
	                   final @NotNull String message,
	                   final Throwable throwable) {

		final StackTraceElement ste = new Throwable().fillInStackTrace().getStackTrace()[5];
		super.log(
			priority,
			tag,
			String.format(
				Locale.getDefault(),
				"[%s] %s(%s:%d): %s",
				Thread.currentThread().getName(),
				ste.getMethodName(),
				ste.getFileName(),
				ste.getLineNumber(),
				message),
			throwable
		);
	}
}
