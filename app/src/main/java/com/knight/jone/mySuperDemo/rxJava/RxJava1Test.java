package com.knight.jone.mySuperDemo.rxJava;


import android.content.Intent;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import rx.Emitter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Action7;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJava1Test {

    public static final String TAG = "RxJavaTest";

    public void s() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        });

        observable.subscribe(subscriber);

        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {

            }
        });
    }

    public static void actionFlatMapIterable() {

        List<Integer> list = Arrays.asList(1, 2, 3);
        Observable.from(list)
                .flatMapIterable(new Func1<Integer, Iterable<String>>() {
                    @Override
                    public Iterable<String> call(Integer integer) {
                        return null;
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("RxJava1Test", "call: " + s);
            }
        });
    }

    public static void actionFlatMapIterable2() {

        List<Integer> list = Arrays.asList(1, 2, 3);
        List<String> list2 = Arrays.asList("a", "b", "c");

        Observable<String> stringObservable = Observable.from(list)
                .flatMapIterable(new Func1<Integer, Iterable<String>>() {
                    @Override
                    public Iterable<String> call(Integer integer) {
                        return list2;
                    }
                });
        stringObservable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("RxJava1Test", "call: " + s);
            }
        });
    }

    public static void testAll() {
        Observable.just(1, 2, 2, 3, 4)
                .all(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        Log.d(TAG, "call: " + integer);
                        return integer < 3;
                    }
                })
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        Log.d(TAG, "doOnNext call: " + aBoolean);
                    }
                })
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.d(TAG, "onNext: " + aBoolean);
                    }
                });
    }

    public static void testContainsEmpty() {

        Observable.just(1, 2, 3, 4)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "call: doOnNext:" + integer);
                    }
                })
                .contains(1)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.d(TAG, "onNext: " + aBoolean);
                    }
                });

        Observable.just(1, 3, 4)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "call: " + integer);
                    }
                })
                .isEmpty()
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.d(TAG, "onNext: " + aBoolean);

                    }
                });
    }

    public static void test() {
        Observable.unsafeCreate(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                //subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation())
                .defaultIfEmpty(4)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }
                });
    }
}
