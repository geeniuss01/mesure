/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.headlines;

import android.view.View;

import org.jetbrains.annotations.Nullable;

import me.samen.mesure.R;

/**
 * @author satosh.dhanyamraju
 */
public class TypeFactoryImpl implements TypeFactory {

  @Override
  public int type(HeadlinesResponse.Story s) {
    return R.layout.list_item_news;
  }

  @Override
  public int type(ProgressData s) {
    return R.layout.list_item_error;
  }


  @Nullable
  public VisitableViewHolder viewHolder(View view, int type) {
    switch (type) {
      case R.layout.list_item_news:
        return new NewsItemViewHolder(view);
      case R.layout.list_item_error:
        return new ProgressViewHolder(view);
    }
    return null;
  }
}
