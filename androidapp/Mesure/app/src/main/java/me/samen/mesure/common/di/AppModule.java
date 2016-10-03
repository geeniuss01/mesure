/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.common.di;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author satosh.dhanyamraju
 */
@Module
public class AppModule {
  private String baseUrl;

  public AppModule(Context appContext, String baseUrl) {
    this.baseUrl = baseUrl;
  }


  @Provides
  @Singleton
  Retrofit retrofit() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(loggingInterceptor);

    return new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(httpClient.build())
        .build();

  }

  @Provides
  @Singleton
  @Named("BASE_URL")
  String baseUrl() {
    return baseUrl;
  }
}

