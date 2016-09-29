package me.samen.mesure.headlines;

import android.databinding.DataBindingUtil;
import android.view.View;

import me.samen.mesure.databinding.ListItemErrorBinding;

class ProgressViewHolder extends VisitableViewHolder<ProgressData> {
  private ListItemErrorBinding errorBinding;

  ProgressViewHolder(View itemView) {
    super(itemView);
    errorBinding = DataBindingUtil.bind(itemView);
  }

  @Override
  public void update(ProgressData progressData) {
    errorBinding.setError(progressData.title);
  }
}