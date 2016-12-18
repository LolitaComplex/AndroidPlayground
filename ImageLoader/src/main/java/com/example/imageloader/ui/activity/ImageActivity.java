package com.example.imageloader.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.imageloader.R;
import com.example.imageloader.data.engine.ImageLoader;

public class ImageActivity extends AppCompatActivity {

    private static final String TAG = "ImageLoader";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(new ImageAdapter(this,
                getResources().getStringArray(R.array.image_buf)));

    }

    private static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImgaeHolder> {

        private String[] data;
        private LayoutInflater layoutInflater;
        private ImageLoader loader;

        private int width;
        private int height;

        public ImageAdapter(Context context, String[] data) {
            this.data = data;
            layoutInflater = LayoutInflater.from(context);
            loader = ImageLoader.from(context);
            width = context.getResources().getDisplayMetrics().widthPixels / 2
                    - dip2px(context, 10);
            height = context.getResources().getDisplayMetrics().heightPixels;
        }


        @Override
        public ImgaeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.item_image, parent, false);
            View view = itemView.findViewById(R.id.iv);
            view.setLayoutParams(new CardView.LayoutParams(
                    width, height / 2
            ));
            return new ImgaeHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ImgaeHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder: " + data[position]);
            loader.url(data[position]).size(width, height).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        class ImgaeHolder extends RecyclerView.ViewHolder {
            ImageView image;

            ImgaeHolder(View view) {
                super(view);
                image = (ImageView) view.findViewById(R.id.iv);
            }
        }
    }



    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
