/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.headlines;

/**
 * @author satosh.dhanyamraju
 */
class ProgressData implements Visitable {

  public String title;

  ProgressData(String title) {
    this.title = title;
  }

  @Override
  public int type(TypeFactory typeFactory) {
    return typeFactory.type(this);
  }
}
