/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.di;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * @author satosh.dhanyamraju
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
  Retrofit retrofit();
}
