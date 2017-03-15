package com.dong.view.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.dong.lib.utils.UIUtils;
import com.dong.view.BaseFragment;

/**
 * Created by 杜营 on 2016/6/15.
 *
 */
public class ViewAnimatorFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = drawBall();

        view.animate().alpha(0).scaleX(0.5f).alpha(1.0f).translationY(UIUtils.getWindowSize().bottom - 700).setDuration(3000);
        return view;
    }

    public View drawBall(){
        Bitmap bitmap = Bitmap.createBitmap(200,200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new RadialGradient(100, 100, 80, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP));
        canvas.drawCircle(100, 100, 80, paint);

        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(bitmap);

        return imageView;
    }
}
