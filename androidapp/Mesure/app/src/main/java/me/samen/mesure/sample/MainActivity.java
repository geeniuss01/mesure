package me.samen.mesure.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.samen.mesure.MesureApp;
import me.samen.mesure.R;
import me.samen.mesure.common.di.AppComponent;
import me.samen.mesure.headlines.HeadlinesActivity;
import me.samen.mesure.sample.di.DaggerStackComponent;
import me.samen.mesure.sample.di.StackModule;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
  private static final String LOG_TAG = "MainActivity";
  private static final String TAG = "MainActivity";
  @BindView(R.id.text1)
  TextView textView1;
  @Inject
  SamplePresenter samplePresenter;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();
  private Subscription subscribe;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    startActivity(new Intent(this, HeadlinesActivity.class));
    finish();
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    AppComponent appComponent = ((MesureApp) getApplication()).appComponent;
    DaggerStackComponent.builder().appComponent(appComponent).stackModule(new StackModule())
        .build().inject(this);
  }

  @OnClick(R.id.text1)
  public void startLoading() {
    if (subscribe != null && !subscribe.isUnsubscribed()) {
      //no duplicate calls
      Toast.makeText(this, "wait", Toast.LENGTH_SHORT).show();
      return;
    }
    textView1.setText(R.string.loading);
    subscribe = samplePresenter.getTags()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(textView1::setText,
            e -> Log.e(LOG_TAG, e.toString(), e));
    compositeSubscription.add(subscribe);
  }


  @Override
  protected void onDestroy() {
    compositeSubscription.unsubscribe();
    super.onDestroy();
  }
}
