package com.example.imageloader.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;


public class UserDefineImageView extends ImageView {

    public UserDefineImageView(Context context) {
        super(context);
    }

    public UserDefineImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserDefineImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        layoutParams.height = bm.getHeight();
        this.setLayoutParams(layoutParams);
    }
}
