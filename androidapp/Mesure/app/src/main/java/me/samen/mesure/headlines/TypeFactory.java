/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.headlines;

import android.view.View;

/**
 * @author satosh.dhanyamraju
 */
interface TypeFactory {
  int type(HeadlinesResponse.Story s);

  int type(ProgressData s);


  VisitableViewHolder viewHolder(View view, int type);

}
