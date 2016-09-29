/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.headlines;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author satosh.dhanyamraju
 */
public interface HeadlinesApi {
  @GET("api/v1/headlines/user/group/852362467?edition=india&langCode=en,te,ur&returnTickers=false")
  Observable<HeadlinesResponse> headlines();

  @GET
  Observable<HeadlinesResponse> nextpage(@Url String path);
}
