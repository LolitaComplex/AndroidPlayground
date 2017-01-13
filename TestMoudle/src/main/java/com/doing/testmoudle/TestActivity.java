package com.doing.testmoudle;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.AtomicFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.doing.testmoudle.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.R.attr.button;
import static android.R.attr.y;
import static com.doing.testmoudle.R.layout.dialog;


public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    private Handler handler = new Handler(){
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {
                sendEmptyMessageDelayed(1,5000);
            } else if (msg.what == 1) {
                new Thread(){
                    @Override
                    public void run() {
                        sendEmptyMessage(2);
                    }
                }.start();
                SystemClock.sleep(2000);
            } else if (msg.what == 2) {
                Log.d(TAG, "13秒过去了：" + SystemClock.elapsedRealtimeNanos());
            }
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(android.R.id.content);
        //验证SystemClock的时间准确值
//        LogUtil.d(TAG, "elapsedRealtime : " + SystemClock.elapsedRealtime());//在AlarmManager中使用
//        LogUtil.d(TAG, "uptimeMillis : " + SystemClock.uptimeMillis());//在Handler中使用
//        LogUtil.d(TAG, "elapsedRealtimeNanos : " + SystemClock.elapsedRealtimeNanos());
//        LogUtil.d(TAG, "currentThreadTimeMillis : " + SystemClock.currentThreadTimeMillis());

        //判断隐式意图是否匹配成功的API
        PackageManager packageManager = getPackageManager();
//        assert packageManager.resolveActivity(new Intent(),
//                PackageManager.MATCH_DEFAULT_ONLY) != null;

//        assert new Intent().resolveActivity(getPackageManager())
//                != null;

        CopyOnWriteArrayList<String> keyWord = new CopyOnWriteArrayList<>(new String[30]);
        keyWord.add("");
        ArrayList<String> list = new ArrayList<>();

        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        boolean result = atomicBoolean.compareAndSet(true, false);

        View view = findViewById(R.id.activity_test);
//        view.getLeft();
//        view.getX();
//        view.getTranslationX();

//        ObjectAnimator.ofInt(new ViewWrapper(view), "marginLeft", 1000).setDuration(20000).start();
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        TextView textView = new TextView(this);


//        getWindow().setAttributes();

        final String[] buf = {"芙兰朵露","蕾米莉亚","十六夜咲夜","帕秋莉"};
        final ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
            , ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView inflate = (TextView) LayoutInflater.from(TestActivity.this)
                .inflate(android.R.layout.simple_list_item_1, null);
        inflate.setText(buf[2]);
        inflate.measure(0, 0);

        final int measuredWidth = inflate.getMeasuredWidth();
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,buf));
        final Button button = (Button) findViewById(R.id.btn_pop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow(listView,
                        measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext()
                        ,R.drawable.menu_bg));
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
//                attributes.alpha = .5f;
                getWindow().setAttributes(attributes);
                int[] location = new int[2];
                button.getLocationOnScreen(location);
//                popupWindow.showAtLocation(button, Gravity.NO_GRAVITY,
//                        location[0], location[1] + button.getHeight());
                popupWindow.showAsDropDown(button);

                handler.sendEmptyMessageDelayed(0, 5000);
            }
        });

    }

    private class ViewWrapper{
        View mView;

        private ViewWrapper(View view){
            this.mView = view;
        }

        public void setMarginLeft(int width){
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
            layoutParams.leftMargin = width;
            mView.setLayoutParams(layoutParams);
        }

        public int getMarginLeft(){
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
            return layoutParams.leftMargin;
        }
    }

    private int animatorRes;
}
