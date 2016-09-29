/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.headlines;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.samen.mesure.R;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author satosh.dhanyamraju
 */
public class HeadlinesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
  private static final String TAG = "HeadlinesAdapter";
  private HeadlinesPresenter presenter;
  private List<HeadlinesResponse.Story> stories;
  private Subscription subscription;


  public HeadlinesAdapter(HeadlinesPresenter presenter) {
    this.presenter = presenter;
    stories = new ArrayList<>();
    stories.add(new HeadlinesResponse.Story()); // for footer
  }

  @Override
  public int getItemViewType(int position) {
    return position == stories.size() - 1 ? 1 : 0;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == 1) {
      View inflate =
          LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_error, parent, false);
      return new ProgressViewHolder(inflate);
    }
    View inflate =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
    return new NewsItemViewHolder(inflate);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((UpdatableViewHolder)holder).update(stories.get(position));
    if (position == stories.size() - 2) {
      if (subscription != null && !subscription.isUnsubscribed()) {
        // no duplicate requests
        return;
      }
      subscription =
          presenter.nextPage().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers
              .mainThread()).subscribe(this::addAll,this::onError);
    }
  }

  void onError(Throwable throwable) {
    Log.e(TAG, "onError: "+throwable.getMessage(),throwable);
    stories.get(stories.size() - 1).title = throwable.getMessage();
    notifyItemChanged(stories.size()-1);
  }

  @Override
  public int getItemCount() {
    return stories.size();
  }

  void addAll(List<HeadlinesResponse.Story> items) {
    int size = stories.size();
    stories.addAll(size-1, items);
    stories.get(stories.size() - 1).title = null;
    notifyItemRangeChanged(size, items.size());
  }

  void unsubscribe() {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }


  interface UpdatableViewHolder {
    void update(HeadlinesResponse.Story story);
  }
}
