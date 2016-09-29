/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.headlines.di;

import dagger.Module;
import dagger.Provides;
import me.samen.mesure.di.PerActivity;
import me.samen.mesure.headlines.HeadlinesAdapter;
import me.samen.mesure.headlines.HeadlinesApi;
import me.samen.mesure.headlines.HeadlinesPresenter;
import retrofit2.Retrofit;

/**
 * @author satosh.dhanyamraju
 */
@Module
class HeadlinesModule {

  @Provides
  @PerActivity
  HeadlinesApi api(Retrofit retrofit) {
    return retrofit.create(HeadlinesApi.class);
  }

  @Provides
  @PerActivity
  HeadlinesAdapter adapter(HeadlinesPresenter presenter) {
    return new HeadlinesAdapter(presenter);
  }

  @Provides
  @PerActivity
  HeadlinesPresenter presenter(HeadlinesApi headlinesApi) {
    return new HeadlinesPresenter(headlinesApi);
  }
}