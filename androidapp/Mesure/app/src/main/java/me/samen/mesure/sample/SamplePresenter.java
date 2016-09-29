/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.sample;

import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author satosh.dhanyamraju
 */
public class SamplePresenter {
  private StackexchangeApi api;
  private static final String TAG = "SamplePresenter";

  public SamplePresenter(StackexchangeApi api) {
    this.api = api;
  }

  Observable<String> getTags() {
    return api.getTags()
        .map(apiResponse -> {
          Log.i(TAG, "getTags: remaining "+apiResponse.quotaRemaining);
          return apiResponse.items;
        })
        .flatMap(new
                     Func1<List<APIResponse.Items>, Observable<APIResponse.Items>>() {
                       @Override
                       public Observable<APIResponse.Items> call(List<APIResponse.Items> itemses) {
                         return Observable.from(itemses);
                       }
                     })
        .reduce((items, items2) ->  new APIResponse.Items(items.name + "\n" + items2.name))
        .map(items -> items.name);
  }
}
