package com.lpf.quickandroid.retrofit_rxjava;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lpf.quickandroid.MainActivity;
import com.lpf.quickandroid.R;
import com.lpf.quickandroid.splash.ItemSplash;
import com.squareup.haha.perflib.Main;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.socks.library.KLog.D;

public class RetrofitForMovieActivity extends RxAppCompatActivity {

    @BindView(R.id.getData)
    Button getData;
    @BindView(R.id.getData2)
    Button getData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_for_movie);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovie();
            }
        });

        getData2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getMovie2();
//                testClick();
//                testFlatMap();
                combineLocalAndNetData();
            }
        });

    }

    // mock 倒计时
    private void testClick() {
        final long count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .compose(this.<Long>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        getData2.setEnabled(false);
                        getData2.setTextColor(Color.GRAY);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        getData2.setText(aLong + "秒重发");
                        if (aLong == 0) {
                            getData2.setTextColor(Color.RED);
                            getData2.setText("GoGoGo");
                            getData2.setEnabled(true);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //Retrofit
    private void getMovie() {
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieEntity> call = movieService.getTopMovie(0, 10);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                Toast.makeText(RetrofitForMovieActivity.this, "获得" + response.body().getSubjects().size() + "条数据!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                Toast.makeText(RetrofitForMovieActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Observable
    private void getMovie2() {
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        MovieService movieService = retrofit.create(MovieService.class);
        movieService.getTopMovie2(0, 10)
                .compose(this.<MovieEntity>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieEntity>() {
                    @Override
                    public void accept(@NonNull MovieEntity movieEntity) throws Exception {

                    }
                });
    }

    //Flowable
    private void getMovie3() {
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        MovieService movieService = retrofit.create(MovieService.class);
        movieService.getTopMovie3(0, 10)
                .compose(this.<MovieEntity>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //FlatMap
    private void testFlatMap() {

        final MovieService movieService = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MovieService.class);

        final RequestEntity testData = new RequestEntity(0, 10);
        Observable.just(testData)
                .flatMap(new Function<RequestEntity, ObservableSource<MovieEntity>>() {
                    @Override
                    public ObservableSource<MovieEntity> apply(@NonNull RequestEntity requestEntity) throws Exception {
                        return movieService.getTopMovie2(testData.getStart(), testData.getCount());
                    }
                })
                .flatMap(new Function<MovieEntity, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull MovieEntity movieEntity) throws Exception {
                        String title = movieEntity.getTitle();
                        return Observable.just(title);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Toast.makeText(RetrofitForMovieActivity.this, "title is " + s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // combine local and net datas
    private void combineLocalAndNetData(){

        final MovieService movieService = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MovieService.class);

        final RequestEntity testData = new RequestEntity(0, 10);
        Observable.just(testData)
                .flatMap(new Function<RequestEntity, ObservableSource<MovieEntity>>() {
                    @Override
                    public ObservableSource<MovieEntity> apply(@NonNull RequestEntity requestEntity) throws Exception {
                        return movieService.getTopMovie2(testData.getStart(), testData.getCount());
                    }
                })
                .flatMap(new Function<MovieEntity, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(@NonNull MovieEntity movieEntity) throws Exception {
                        List<String> mockDatas = new ArrayList<String>();
                        mockDatas.add("mock Data1");
                        mockDatas.add("mock Data2");
//                        mockDatas.addAll(movieEntity.getSubjects().get(0).getGenres());
//                        return Observable.just(mockDatas);
                        //下面这样会输出两次
                        Observable<List<String>> localData = Observable.just(mockDatas);
                        Observable<List<String>> netData = Observable.just(movieEntity.getSubjects().get(0).getGenres());
                        return Observable.merge(localData,netData);
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        getData2.setEnabled(false);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> strings) throws Exception {
                        getData2.setEnabled(true);
                        Toast.makeText(RetrofitForMovieActivity.this, "combineData:"+strings.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}































