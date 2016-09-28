package me.samen.mesure.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.annimon.stream.Stream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.samen.mesure.MesureApp;
import me.samen.mesure.R;
import me.samen.mesure.di.AppComponent;
import me.samen.mesure.sample.di.DaggerStackComponent;
import me.samen.mesure.sample.di.StackModule;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
  private static final String LOG_TAG = "MainActivity";
  @BindView(R.id.tagsRV)
  RecyclerView tagsRecyclerview;
  @BindView(R.id.text1)
  TextView textView1;
  @Inject
  SamplePresenter samplePresenter;
  private Subscription subscribe;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    AppComponent appComponent = ((MesureApp) getApplication()).appComponent;
    DaggerStackComponent.builder().appComponent(appComponent).stackModule(new StackModule())
        .build().inject(this);
  }

  @OnClick(R.id.text1)
  public void startLoading() {
    textView1.setText(R.string.loading);
    if (subscribe != null) samplePresenter.getTags();
    subscribe = samplePresenter.getTags()
        .map(l ->
            Stream.of(l).reduce(
                (value1, value2) ->
                    new APIResponse.Items(value1.name + "\n" + value2.name)
            )
        )
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(l -> textView1.setText(l.get().name),
            e -> Log.e(LOG_TAG, e.toString(), e));
  }

  @Override
  protected void onDestroy() {
    subscribe.unsubscribe();
    super.onDestroy();
  }
}
