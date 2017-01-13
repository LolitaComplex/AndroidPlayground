package com.doing.viewscroll;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ViewDragHelperActivity extends AppCompatActivity {

    private static final String TAG = "ViewDragHelperActivity";
    private LinearLayout recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdraghelper);

        LayoutInflater inflater = LayoutInflater.from(this);
        recyclerView = (LinearLayout) findViewById(R.id.recycler);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );



        for (int x = 0, position = 0; x < 20; x++) {
            LinearLayout linearLayout = new LinearLayout(this);
            recyclerView.addView(linearLayout, params);
            for (int y = 0; y < 3; y++) {
                ImageView imageView = (ImageView) inflater.inflate(R.layout.item_recycler,
                        linearLayout, false);
                int id = ViewDragHelperActivity.this.getResources().getIdentifier(
                        "image_" + position % 8, "drawable", getPackageName());
                imageView.setImageResource(id);
                linearLayout.addView(imageView);
                position++;
            }
        }
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        recyclerView.setAdapter(new RecyclerAdapter(this));
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<Holder> {

        private LayoutInflater inflater;

        public RecyclerAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = inflater.inflate(R.layout.item_recycler, parent, false);
            return new Holder(inflate);
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {
            int id = ViewDragHelperActivity.this.getResources().getIdentifier(
                    "image_" + position % 8, "drawable", getPackageName()
            );
            holder.imageView.setImageResource(id);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewDragHelperActivity.this, "" + position, Toast.LENGTH_SHORT).show();
//                    recyclerView.setCurrentView(v);
                }
            });
            Log.d(TAG, "onBindViewHolder: " + holder.imageView);
        }

        @Override
        public int getItemCount() {
            return 200;
        }
    }

    private class Holder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}

