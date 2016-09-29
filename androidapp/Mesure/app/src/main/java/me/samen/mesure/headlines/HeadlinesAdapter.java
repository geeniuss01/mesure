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

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author satosh.dhanyamraju
 */
public class HeadlinesAdapter extends RecyclerView.Adapter<VisitableViewHolder>{
  private static final String TAG = "HeadlinesAdapter";
  private HeadlinesPresenter presenter;
  private List<Visitable> stories;
  private ProgressData progressData;
  private Subscription subscription;
  private TypeFactory typeFactory;

  public HeadlinesAdapter(HeadlinesPresenter presenter, TypeFactory typeFactory) {
    this.presenter = presenter;
    this.typeFactory = typeFactory;
    stories = new ArrayList<>();
    progressData = new ProgressData(null);
    stories.add(progressData); // for footer
  }

  @Override
  public int getItemViewType(int position) {
    return stories.get(position).type(typeFactory);
  }

  @Override
  public VisitableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflate =
        LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
    return typeFactory.viewHolder(inflate, viewType);
  }

  @Override
  public void onBindViewHolder(VisitableViewHolder holder, int position) {
    holder.update(stories.get(position));
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
    progressData.title = throwable.getMessage();
    notifyItemChanged(stories.size()-1);
  }

  @Override
  public int getItemCount() {
    return stories.size();
  }

  void addAll(List<HeadlinesResponse.Story> items) {
    int size = stories.size();
    stories.addAll(size-1, items);
    progressData.title = null;
    notifyItemRangeChanged(size, items.size());
  }

  void unsubscribe() {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }

}
