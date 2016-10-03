/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure;

import android.app.Application;

import me.samen.mesure.common.di.AppComponent;
import me.samen.mesure.common.di.AppModule;
import me.samen.mesure.common.di.DaggerAppComponent;

/**
 * @author satosh.dhanyamraju
 */
public class MesureApp extends Application{
  public static final String BASE_URL_SO = "https://api.stackexchange.com/";
  public static final String BASE_URL_DH = "http://api-news.dailyhunt.in/";
  public AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    appComponent = DaggerAppComponent.builder().appModule(new AppModule(this, BASE_URL_DH)).build();
  }

}
