package com.loftschool.loftcoin.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public final class PagerDecoration extends RecyclerView.ItemDecoration {

	private final int dividerWidth;

	public PagerDecoration(@NonNull final Context context,
	                       final float dip) {
		Objects.requireNonNull(context);
		final DisplayMetrics dm = context.getResources().getDisplayMetrics();
		dividerWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm));
	}

	@Override
	public void getItemOffsets(@NonNull final Rect outRect,
	                           @NonNull final View view,
	                           @NonNull final RecyclerView parent,
	                           @NonNull final RecyclerView.State state) {
		final int position = parent.getChildAdapterPosition(view);
		if (position == 0) {
			outRect.set(dividerWidth, 0, dividerWidth, 0);
		} else {
			outRect.set(0, 0, dividerWidth, 0);
		}
	}
}
