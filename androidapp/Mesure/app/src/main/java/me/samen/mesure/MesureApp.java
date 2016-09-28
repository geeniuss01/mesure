/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure;

import android.app.Application;

import me.samen.mesure.di.AppComponent;
import me.samen.mesure.di.AppModule;
import me.samen.mesure.di.DaggerAppComponent;

/**
 * @author satosh.dhanyamraju
 */
public class MesureApp extends Application{

  public AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
  }

}
