package com.jorgereina.rxjavapractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //"http://api.openweathermap.org/data/2.5/weather?q=11237&units=imperial&appid=726214b63da745beaf2dcd7eda63442a"
    private static final String BASE_URL = "http://api.openweathermap.org/";
    private static final String TAG = "lagarto";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);

        rxJavaRetrofitExample();
        rxJavaSimpleStringExample();
        rXJavaEmittingMultipleIntegers();


    }

    private void rXJavaEmittingMultipleIntegers() {

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                emitter.onNext(1);
                emitter.onNext(5);
                emitter.onNext(3);
                emitter.onNext(9);
                emitter.onNext(2);

                emitter.onComplete();
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }


    private void rxJavaSimpleStringExample() {

        Observable<String> observable = Observable.just("hello RxJava");

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
                textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }

    private void rxJavaRetrofitExample() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);

        Observable<Weather> weatherObservable = weatherApi.getWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        weatherObservable.subscribe(new Observer<Weather>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Weather weather) {

                Log.d(TAG, "onNext: " + weather.getMain().getTemp());
//                textView.setText(String.valueOf(weather.getMain().getTemp()));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
