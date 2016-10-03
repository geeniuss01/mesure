/*
 * Copyright (c) 2016 Newshunt. All rights reserved.
 */
package me.samen.mesure.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.samen.mesure.R;
import me.samen.mesure.headlines.HeadlinesActivity;

/**
 * @author satosh.dhanyamraju
 */
public class AuthActivity extends AppCompatActivity implements GoogleApiClient
    .OnConnectionFailedListener{

  private static final String TAG = "AuthActivity";
  private static final int RC_SIGN_IN = 1111;
  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;

  private GoogleApiClient mGoogleApiClient;
  private DatabaseReference mDatabase;
  @BindView(R.id.sign_in_button)
  SignInButton signInButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);
    ButterKnife.bind(this);

    initGoogleSignInPrams();
    initFirebaseParams();

  }

  /**
   * Firebase Auth and Database initialisations
   */
  private void initFirebaseParams() {
    mAuth = FirebaseAuth.getInstance();
    mDatabase = FirebaseDatabase.getInstance().getReference();

    mAuthListener = firebaseAuth -> {
      FirebaseUser user = firebaseAuth.getCurrentUser();
      if (user != null) {
        writeNewUser(user.getUid(), user.getEmail(), user.getDisplayName(), user.getPhotoUrl().toString());
        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
      } else {
        Log.d(TAG, "onAuthStateChanged:signed_out");
      }

    };
  }

  /**
   * Google Sign in Initializations
   */
  private void initGoogleSignInPrams() {

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();

    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();
  }

  @Override
  protected void onStart() {
    super.onStart();
    if(mAuth != null){
      mAuth.addAuthStateListener(mAuthListener);
    }

  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if (result.isSuccess()) {
        GoogleSignInAccount account = result.getSignInAccount();
        firebaseAuthWithGoogle(account);
      }
    }
  }

  private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
    // showProgressDialog();
    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (!task.isSuccessful()) {
              Log.w(TAG, "signInWithCredential", task.getException());
              Toast.makeText(AuthActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
            } else {
              startActivity(new Intent(AuthActivity.this, HeadlinesActivity.class));
              finish();
            }

          }
        });
  }


  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Toast.makeText(AuthActivity.this, "Authentication failed, Try Again",
        Toast.LENGTH_SHORT).show();
  }

  public void signIn() {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @OnClick(R.id.sign_in_button)
  public void onGoogleSignInClick(View v){
    signIn();
  }

  @Override
  public void onStop() {
    super.onStop();
    if (mAuthListener != null) {
      mAuth.removeAuthStateListener(mAuthListener);
    }
  }

  private void writeNewUser(String userId, String displayname, String email, String profileUrl) {
    User user = new User(userId, email, displayname,profileUrl);
    mDatabase.child("users").child(userId).setValue(user);
  }
}
