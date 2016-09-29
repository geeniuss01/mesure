package me.samen.mesure.headlines;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.picasso.Picasso;

import me.samen.mesure.R;
import me.samen.mesure.databinding.ListItemNewsBinding;

class NewsItemViewHolder extends RecyclerView.ViewHolder implements
    HeadlinesAdapter.UpdatableViewHolder {
    private ListItemNewsBinding binding;

    NewsItemViewHolder(View itemView) {
      super(itemView);
      binding = DataBindingUtil.bind(itemView);
    }

    public void update(HeadlinesResponse.Story story) {
      binding.setStory(story);
      if (story.contentImage != null && story.contentImage.url != null) {
        binding.newsIV.setVisibility(View.VISIBLE);
        int size =
            binding.newsIV.getResources().getDimensionPixelOffset(R.dimen.news_item_image_width);
        Picasso.with(binding.newsIV.getContext())
            .load(story.contentImage.url)
            .resize(size, size)
            .centerCrop()
            .into(binding.newsIV);
      } else {
        binding.newsIV.setVisibility(View.GONE);
      }
    }
  }
