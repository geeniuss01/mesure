/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.sample;

import rx.Observable;

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
        .map(apiResponse -> apiResponse.items)
        .flatMap(Observable::from)
        .reduce((items, items2) ->  new APIResponse.Items(items.name + "\n" + items2.name))
        .map(items -> items.name);
  }
}
