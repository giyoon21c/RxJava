package com.example.android.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create only one item to emit using just
        Observable<String> myObservable
                = Observable.just("Hello from Observable!"); // emits "hello"

        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                // Called when the observable has no more data to emit
                Log.d("MY OBSERVER", "No more data to emit");
            }

            @Override
            public void onError(Throwable e) {
                // Called when the observable encounters an error
            }

            @Override
            public void onNext(String s) {
                // Called each time the observable emits data
                Log.d("MY OBSERVER", s);
            }
        };

        //Subscription mySubscriber = myObservable.subscribe(myObserver);
        Subscription sub1 = myObservable.subscribe(myObserver);

        // When only a single method is used (onCompleted and onError not used)
//        Action1<String> myAction = new Action1<String>() {
//            @Override
//            public void call (String s){
//                Log.d("My Action", s);
//            }
//        };

        Subscription sub2 = myObservable.subscribe(new Action1<String>() {
            @Override
            public void call (String s){
                Log.d("My Action", s);
            }
        });

        sub2.unsubscribe();

        // using operator from to return string from array of strings
        Observable<String> myArrayObservable =
                Observable.from(new String[]{"hello", "gracias", "bonjurno", "goodbye", "salam", "shalom"});

        // subscribe method is waiting for one string at a time...
        myArrayObservable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("RXResult", s);
            }
        });

        // using operator from to return integer from array of integers
        Observable<Integer> myIntegerArrayObservable =
                Observable.from(new Integer[]{1, 2, 3, 4, 5, 6});

        // subscribe method is waiting for one string at a time...
        myIntegerArrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                Log.d("RXResult", String.valueOf(i));
            }
        });

        // using operator from to return integer from array of integers
        Observable<Integer> myFuncArrayObservable =
                Observable.from(new Integer[]{1, 2, 3, 4, 5, 6});

        // subscribe method is waiting for one string at a time...
        // java7 emulating lambda
        myFuncArrayObservable.map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer * integer;
            }
        }).skip(2).filter(new Func1<Integer, Boolean>() { // skip first two numbers: ie, 1 and 2
            @Override
            public Boolean call(Integer integer) { // select even numbers only
                return integer % 2 == 0;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                Log.d("RXResult", String.valueOf(i));
            }
        });

        // handling async jobs
        // Using subscribeOn and observeOn, you can explicitly specify which thread should run
        // the background job and which thread should handle the user interface updates.

        

    }
}
