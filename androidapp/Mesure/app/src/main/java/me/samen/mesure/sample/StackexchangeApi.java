/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.sample;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author satosh.dhanyamraju
 */
public interface StackexchangeApi {

  @GET("2.2/tags?order=desc&sort=popular&site=stackoverflow")
  Observable<APIResponse> getTags();
}
