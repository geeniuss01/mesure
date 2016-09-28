/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.sample;

import java.util.List;

/**
 * @author satosh.dhanyamraju
 */
public class APIResponse {

  public List<Items> items;
  public boolean hasMore;
  public int quotaMax;
  public int quotaRemaining;

  public static class Items {
    public boolean hasSynonyms;
    public boolean isModeratorOnly;
    public boolean isRequired;
    public int count;
    public String name;

    public Items(String name) {
      this.name = name;
    }
  }
}
