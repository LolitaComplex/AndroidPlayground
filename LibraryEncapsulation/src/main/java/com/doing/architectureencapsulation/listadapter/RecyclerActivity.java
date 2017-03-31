package com.doing.architectureencapsulation.listadapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doing.architectureencapsulation.R;
import com.doing.architectureencapsulation.listadapter.recycler.BaseViewHolder;
import com.doing.architectureencapsulation.listadapter.recycler.CommonAdapter;
import com.doing.architectureencapsulation.listadapter.recycler.HeaderAndFooterAdapter;
import com.doing.architectureencapsulation.listadapter.recycler.LoadMoreAdapter;
import com.doing.architectureencapsulation.listadapter.threetype.ThreeTypeAdapter;
import com.doing.architectureencapsulation.listadapter.threetype.entity.ThreeType;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerActivity";

    private RecyclerView mRecycler;

    private List<String> buf;
    private List<ThreeType> list;

    public static void start(Context context) {
        context.startActivity(new Intent(context, RecyclerActivity.class));
    }

    public RecyclerActivity(){
        buf = new ArrayList<>();
        list = new ArrayList<>();
        for(int x = 0; x< 100; x++) {
            buf.add("这是条目：" + (x + 1));
            list.add(new ThreeType("", "阿斯达所多"));
            list.add(new ThreeType("这是条目：" + (x + 1)));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        mRecycler = (RecyclerView) findViewById(R.id.RecyclerActivity_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        // 普通的条目
//        mRecycler.setAdapter(new CommonAdapter<String>(this, buf, android.R.layout.simple_list_item_1) {
//            @Override
//            public void convert(BaseViewHolder holder, String data, int position) {
//                holder.setText(android.R.id.text1, data);
//            }
//        });
        // 多类型条目
        ThreeTypeAdapter threeTypeAdapter = new ThreeTypeAdapter(this, list);
        HeaderAndFooterAdapter headerAndFooterAdapter = new HeaderAndFooterAdapter(threeTypeAdapter);
        LoadMoreAdapter loadMoreAdapter = new LoadMoreAdapter(headerAndFooterAdapter);
        mRecycler.setAdapter(loadMoreAdapter);

        initFooterAndHead(headerAndFooterAdapter);
        initFooterAndHead(headerAndFooterAdapter);
        initFooterAndHead(headerAndFooterAdapter);

        TextView textView = new TextView(this);
        textView.setText("加载更多");
        loadMoreAdapter.setLoadMoreView(textView);
        loadMoreAdapter.setOnLoadMoreListener(new LoadMoreAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore: 加载更多" );
            }
        });
    }

    private void initFooterAndHead(HeaderAndFooterAdapter adapter) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);
        adapter.addHeaderView(imageView);
        adapter.addFooterView(imageView);
    }
}
