package com.doing.newfeature;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doing.newfeature.jellybean.JellyBeanActivity;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;

import static android.R.attr.key;
import static android.R.id.text1;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.doing.newfeature.jellybean.JellyBeanActivity.instance;

public class FeatureActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FeatureActivity";


    private Executor mExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            command.run();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);

        findViewById(R.id.FeatureActivity_btn_4x).setOnClickListener(this);
        Log.i(TAG, "onCreate: ");

        int width = getResources().getDisplayMetrics().widthPixels;
        int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(300, View.MeasureSpec.AT_MOST);
        TextView text1 = (TextView) findViewById(R.id.item_text1);
        TextView text2 = (TextView) findViewById(R.id.item_text2);
        TextView text3 = (TextView) findViewById(R.id.item_text3);
        text1.measure(widthSpec, heightSpec);
        text2.measure(widthSpec, heightSpec);
        text3.measure(widthSpec, heightSpec);
        View line = findViewById(R.id.item_line);
        ViewGroup.LayoutParams layoutParams = line.getLayoutParams();
        int margin = (int) (getResources().getDisplayMetrics().density * 60);
        layoutParams.height = text1.getMeasuredHeight() + text2.getMeasuredHeight() + text3.getMeasuredHeight() + margin;
        line.setLayoutParams(layoutParams);

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.FeatureActivity_rv_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setAdapter(new RecyclerView.Adapter<TestViewHoder>() {
//            @Override
//            public TestViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View inflate = LayoutInflater.from(FeatureActivity.this).inflate(R.layout.item_test, parent, false);
//                TestViewHoder holder = new TestViewHoder(inflate);
//
//                int width = getResources().getDisplayMetrics().widthPixels;
//                int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
//                int heightSpec = View.MeasureSpec.makeMeasureSpec(300, View.MeasureSpec.AT_MOST);
//                holder.text1.measure(widthSpec, heightSpec);
//                holder.text2.measure(widthSpec, heightSpec);
//                holder.text3.measure(widthSpec, heightSpec);
//                Log.i(TAG, "onCreateViewHolder: " + holder.text1.getMeasuredHeight());
//
//                return holder;
//            }
//
//            @Override
//            public void onBindViewHolder(TestViewHoder holder, int position) {
//                switch (position % 3) {
//                    case 0:
//                        setVisible(holder.text1);
//                        setVisible(holder.text2);
//                        setVisible(holder.text3);
//                        break;
//
//                    case 1:
//                        setGone(holder.text1);
//                        setVisible(holder.text2);
//                        setVisible(holder.text3);
//                        break;
//
//                    case 2:
//                        setGone(holder.text1);
//                        setGone(holder.text2);
//                        setVisible(holder.text3);
//                        break;
//                }
//            }
//
//            @Override
//            public int getItemCount() {
//                return 1;
//            }
//
//        });
    }

    public void setGone(View view) {
        view.setVisibility(View.GONE);
    }

    public void setVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    static class TestViewHoder extends RecyclerView.ViewHolder{

        TextView text1;
        TextView text2;
        TextView text3;


        public TestViewHoder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.item_text1);
            text2 = (TextView) itemView.findViewById(R.id.item_text2);
            text3 = (TextView) itemView.findViewById(R.id.item_text3);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FeatureActivity_btn_4x:
                JellyBeanActivity.start(this);
                break;
            
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (JellyBeanActivity.instance != null) {
            Log.i(TAG, "=============" + JellyBeanActivity.instance.toString());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent: ");
        super.onNewIntent(intent);
    }
}
