/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.headlines.di;

import dagger.Component;
import me.samen.mesure.di.AppComponent;
import me.samen.mesure.di.PerActivity;
import me.samen.mesure.headlines.HeadlinesActivity;

/**
 * @author satosh.dhanyamraju
 */
@PerActivity
@Component(modules = HeadlinesModule.class, dependencies = AppComponent.class)
public interface HeadlinesComponent {
  void inject(HeadlinesActivity activity);
}
