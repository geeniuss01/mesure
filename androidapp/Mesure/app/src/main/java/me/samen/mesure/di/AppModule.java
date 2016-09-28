/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author satosh.dhanyamraju
 */
@Module
public class AppModule {
  Context appContext;

  public AppModule(Context appContext) {
    this.appContext = appContext;
  }


  @Provides
  @Singleton
  Retrofit retrofit() {
    return new Retrofit.Builder().baseUrl("https://api.stackexchange.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

  }
}
