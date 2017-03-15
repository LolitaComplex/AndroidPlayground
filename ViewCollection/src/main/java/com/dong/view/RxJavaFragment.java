package com.dong.view;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dong.lib.utils.LogUtils;
import com.dong.viewcollection.R;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 杜营 on 2016/8/8.
 *
 */
public class RxJavaFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppCompatTextView textView = new AppCompatTextView(mContext);
        textView.setText("RxJavaFragment");
        textView.setTextColor(Color.RED);
        textView.setPadding(40, 30, 40, 30);
        textView.setBackgroundResource(R.drawable.shape_solid);
        return textView;
    }

    @Override
    protected void loadData() {
        //创建Rx观察者方式1
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtils.d(TAG, "Log Completed！！");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "Log Error");
            }

            @Override
            public void onNext(String s) {
                LogUtils.d(TAG, "Log Success " + s);
            }


        };


        //创建Rx观察者方式2
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onStart() {
                //特有方法，在subscriber最开始之前调用，
                LogUtils.d(TAG, "Log Start！！");
            }

            @Override
            public void onCompleted() {
                LogUtils.d(TAG, "Log Completed！！");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "Log Error");
            }

            @Override
            public void onNext(String s) {
                LogUtils.d(TAG, "Log Success " + s);
            }
        };

        subscriber.unsubscribe();//接触观察者的一切引用，可以在onPause()、onStop()时调用，释放资源，避免内容泄露

        Observable observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Rx第一部");
                subscriber.onNext("Rx第二部");
                subscriber.onNext("Rx第三部");
                subscriber.onCompleted();
            }
        });

        Observable observable2 = Observable.just("RxJust第一部", "RxJust第二部", "RxJust第三部");//等价于observable1

        String[] fromArr = {"RxFrom第一部", "RxFrom第二部", "RxFrom第三部"};
        Observable observable3 = Observable.from(fromArr);//等价于observable1

        observable1.subscribe(subscriber);
        observable2.subscribe(observer);
        observable3.subscribe(subscriber);
        /**
         *  subscribe的核心源码
         *  public Subscription subscribe(Subscriber subscriber) {
         subscriber.onStart();
         onSubscribe.call(subscriber);   //事件发送的逻辑是在subscribe之后才执行的，观察者这时才会做出行动
         return subscriber;
         }
         *
         */

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String str) {
                LogUtils.d(TAG, "Log Success " + str);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                LogUtils.d(TAG, "Log Error " + throwable.getLocalizedMessage());
            }
        };

        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {
                LogUtils.d(TAG, "Log Complete！！");
            }
        };

        observable3.subscribe(onNextAction, onErrorAction, onCompleteAction);

        String[] arr = {"一", "二", "三"};

        Observable.just("1", "2", "3")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return Integer.parseInt(s);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        LogUtils.d(TAG, integer + "");
                    }
                });

    }
}
