/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.headlines;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;

/**
 * @author satosh.dhanyamraju
 */
public class HeadlinesPresenter {
  private static final String TAG = "HeadlinesPresenter";
  private final HeadlinesApi api;
  private String nextPgUrl;

  public HeadlinesPresenter(@NotNull HeadlinesApi api) {
    this.api = api;
  }

  Observable<List<HeadlinesResponse.Story>> firstPage() {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("users");
    users.child("test1").setValue("activity1");
    users.push();
    
    return api.headlines().map(r -> {
      Log.i(TAG, "firstPage: url="+r.data.nextPageUrl +" pg="+r.data.pageNumber);
      nextPgUrl = r.data.nextPageUrl;
      return r.data.stories;
    });
  }

  Observable<List<HeadlinesResponse.Story>> nextPage() {
    return api.nextpage(nextPgUrl).map(r -> {
      nextPgUrl = r.data.nextPageUrl;
      return r.data.stories;
    });
  }
}
