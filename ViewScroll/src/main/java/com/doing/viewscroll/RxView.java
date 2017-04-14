package com.doing.viewscroll;

import android.view.View;

import rx.Observable;
import rx.Subscriber;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-11.
 */

public class RxView {

    public Observable onClick(final View view){
        return Observable.create(new Observable.OnSubscribe<View>() {
            @Override
            public void call(final Subscriber<? super View> subscriber) {
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subscriber.onNext(v);
                        subscriber.onCompleted();
                    }
                };

                view.setOnClickListener(onClickListener);
            }
        });
    }
}
