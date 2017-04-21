package com.doing.viewscroll;

import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.doing.viewscroll.ui.ScrollView;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static rx.schedulers.Schedulers.start;


public class MainActivity extends AppCompatActivity {

    private int mark;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.MainActivity_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int index = mark % 3;
                switch (index) {
                    case 0:
                        ((ScrollView) v).smoothScrollTo(-500, 0);
                        break;

                    case 1:
                        ((ScrollView) v).smoothScrollTo(0, -500);
                        break;

                    case 2:
                        ((ScrollView) v).smoothScrollTo(-600, -600);
                        break;
                }


                Observable.interval(1, TimeUnit.SECONDS)
                        .take(1)
                        .takeWhile(new Func1<Long, Boolean>() {
                            @Override
                            public Boolean call(Long aLong) {
                                return null;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                ((ScrollView) v).smoothScrollTo(0, 0);
                            }
                        });

                mark = index + 1;

//                ObjectAnimator.ofFloat(new ViewWrapwer(v), "verticalOffset", 100).start();
            }
        });
        getSystemService(Context.ACTIVITY_SERVICE);

        findViewById(R.id.btn_vdh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        ViewDragHelperActivity.class));
            }
        });




//        findViewById(R.id.MainActivity_iv1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ObjectAnimator.ofFloat(v, "translationY", 100).start();
//            }
//        });
//        findViewById(R.id.MainActivity_iv1).offsetTopAndBottom(100);
    }

    private class ViewWrapwer {

        private View view;

        public ViewWrapwer(View view){
            this.view = view;
        }

        public void setVerticalOffset(float offset) {
            int offsetLayout = (int) offset;
            view.offsetTopAndBottom(offsetLayout);
//            view.setTranslationY(offset);
            Log.d(TAG, "setVerticalOffset: " + offset);
        }

        public float getVerticalOffset() {
            return 0;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w(TAG, "dispatchTouchEvent: " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w(TAG, "onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
