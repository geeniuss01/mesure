/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.sample;

import java.util.List;

import rx.Observable;

/**
 * @author satosh.dhanyamraju
 */
public class SamplePresenter {
  private StackexchangeApi api;

  public SamplePresenter(StackexchangeApi api) {
    this.api = api;
  }

  public Observable<List<APIResponse.Items>> getTags() {
    return api.getTags().map(apiResponse -> apiResponse.items);
  }
}
