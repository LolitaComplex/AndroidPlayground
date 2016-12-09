package com.doing.viewscroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.doing.viewscroll.ui.ScrollView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class MainActivity extends AppCompatActivity {

    private int mark;


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
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                ((ScrollView) v).smoothScrollTo(0, 0);
                            }
                        });

                mark = index + 1;
            }
        });

    }
}
