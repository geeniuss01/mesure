/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.sample.di;

import dagger.Module;
import dagger.Provides;
import me.samen.mesure.common.di.PerActivity;
import me.samen.mesure.sample.SamplePresenter;
import me.samen.mesure.sample.StackexchangeApi;
import retrofit2.Retrofit;

/**
 * @author satosh.dhanyamraju
 */
@Module
public class StackModule {
  @Provides
  @PerActivity
  StackexchangeApi api(Retrofit retrofit) {
    return retrofit.create(StackexchangeApi.class);
  }

  @PerActivity @Provides
  SamplePresenter presenter(StackexchangeApi api) {
    return new SamplePresenter(api);
  }
}
