package me.samen.mesure.headlines;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.samen.mesure.MesureApp;
import me.samen.mesure.R;
import me.samen.mesure.headlines.di.DaggerHeadlinesComponent;
import me.samen.mesure.headlines.di.HeadlinesComponent;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HeadlinesActivity extends AppCompatActivity {
  private static final String TAG = "HeadlinesActivity";
  @Inject
  HeadlinesPresenter presenter;
  @Inject
  HeadlinesAdapter adapter;
  @BindView(R.id.headlinesRV)
  RecyclerView recyclerView;
  private HeadlinesComponent headlinesComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_headlines);
    headlinesComponent = DaggerHeadlinesComponent.builder()
        .appComponent(((MesureApp) getApplication()).appComponent)
        .build();
    headlinesComponent.inject(this);
    ButterKnife.bind(this);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
    presenter.firstPage().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers
        .mainThread()).subscribe(adapter::addAll,adapter::onError);
  }

  @Override
  protected void onDestroy() {
    adapter.unsubscribe();
    super.onDestroy();
  }
}