/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.headlines;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author satosh.dhanyamraju
 */
abstract class VisitableViewHolder<T extends Visitable> extends RecyclerView.ViewHolder {
  VisitableViewHolder(View itemView) {
    super(itemView);
  }
  public abstract void  update(T t);
}
