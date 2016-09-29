package me.samen.mesure.headlines;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.samen.mesure.databinding.ListItemErrorBinding;

class ProgressViewHolder extends RecyclerView.ViewHolder implements
    HeadlinesAdapter.UpdatableViewHolder {
  private ListItemErrorBinding errorBinding;

  ProgressViewHolder(View itemView) {
    super(itemView);
    errorBinding = DataBindingUtil.bind(itemView);
  }

  @Override
  public void update(HeadlinesResponse.Story story) {
    errorBinding.setError(story.title);
  }
}