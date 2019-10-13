package com.loftschool.loftcoin.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.loftschool.loftcoin.db.StableId;

import java.util.Objects;

public final class StableIdDiff<T extends StableId> extends DiffUtil.ItemCallback<T> {

	@Override
	public boolean areItemsTheSame(@NonNull final T oldItem,
	                               @NonNull final T newItem) {
		return oldItem.id() == newItem.id();
	}

	@Override
	public boolean areContentsTheSame(@NonNull final T oldItem,
	                                  @NonNull final T newItem) {
		return Objects.equals(oldItem, newItem);
	}
}
