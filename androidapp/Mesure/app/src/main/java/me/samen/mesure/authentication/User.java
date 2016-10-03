package me.samen.mesure.authentication;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by satyanarayana.avv on 03-10-2016.
 */

@IgnoreExtraProperties
public class User {

  public String uid;
  public String displayName;
  public String email;
  public String profileUrl;


  public User() {
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
  }

  public User(String username, String email, String displayName, String profileUrl) {
    this.uid = username;
    this.email = email;
    this.displayName = displayName;
    this.profileUrl = profileUrl;
  }

}
