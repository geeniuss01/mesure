/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.sample.di;

import dagger.Component;
import me.samen.mesure.di.AppComponent;
import me.samen.mesure.di.PerActivity;
import me.samen.mesure.sample.MainActivity;
import me.samen.mesure.sample.SamplePresenter;
import me.samen.mesure.sample.StackexchangeApi;

/**
 * @author satosh.dhanyamraju
 */
@PerActivity
@Component(modules = {StackModule.class}, dependencies = {AppComponent.class})
public interface StackComponent {
  StackexchangeApi api();

  SamplePresenter presenter();

  void inject(MainActivity mainActivity);
}
