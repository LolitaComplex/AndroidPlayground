package com.doing.canvas.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.doing.canvas.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

public class PixelColorCanvasActivity extends Activity {



    private Paint mPaint;
    private Random mRandom;

    {
        if (mPaint == null) {
            mPaint = new Paint();
        }
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);

        mRandom = new Random();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(
                context, PixelColorCanvasActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pixel_color_canvas);

        ImageView imageView = (ImageView) findViewById(R.id.MainActivity_iv);
        Bitmap argbBitmap = getArgbBitmap();
        File file = new File(Environment.getExternalStorageState(), "bitmap_table.png");
        Log.e("MainActivity", Environment.getExternalStorageState());
        try {
            argbBitmap.compress(Bitmap.CompressFormat.PNG, 50, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(argbBitmap);

    }

    private int getRandomColor(Random random) {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int bule = random.nextInt(256);
        int alpha = random.nextInt(256);
        return Color.argb(alpha, red, green, bule);

    }

    private Bitmap getArgbBitmap() {
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPiexels = widthPixels / 4 * 3;
        Bitmap bitmap = Bitmap.createBitmap(widthPixels, heightPiexels,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawStart(canvas, bitmap);
        return bitmap;
    }

    private void drawStart(Canvas canvas, Bitmap bitmap) {
        int itemHeight = bitmap.getHeight() / 8;
        int itemWidth = bitmap.getWidth() / 12;
        for (int i = 0; i < 9; i++) {
            int sourceY = itemHeight * i;
            Log.e("MainActivity", "itemHeight" + sourceY);

            for (int z = 0; z < 12; z++) {
                int sourceX = itemWidth * z;
                drawItem(i, canvas, sourceX, sourceY, sourceX + itemWidth, sourceY + itemHeight);
                initPaint();
            }
        }

    }

    private void drawItem(int currentPostion,Canvas canvas, int left, int top, int right, int bottom) {
        canvas.drawRect(left, top, right, bottom, mPaint);
        int randomColor = getRandomColor(mRandom);
        StringBuilder hexColorSb = new StringBuilder(Integer.toHexString(randomColor));

        int currentIndex = 0;
        for (int x = 0; x < 2; x++) {
            int itemTop = (top + bottom * x) / (1 + x);
            int itemBottom = (top * (1 - x) + bottom) / (2 - x);

            for (int y = 0; y < 2; y++) {
                int itemLeft = (left + right * y) / (1 + y);
                int itemRight = (left * (1 - y) + right) / (2 - y);

                initItemColorPaint(randomColor);
                canvas.drawRect(itemLeft, itemTop, itemRight, itemBottom, mPaint);
                initItemPaint();
                canvas.drawRect(itemLeft, itemTop, itemRight, itemBottom, mPaint);
                initTextPaint();

                //颜色这里取值有问题，应该创建颜色时就缓存颜色
                String itemColor;
                if (currentIndex == 3) {
                    itemColor = hexColorSb.substring(currentIndex * 2);
                    if (itemColor.length() == 1) {
                        itemColor = 0 + itemColor;
                    } else if (itemColor.length() == 0) {
                        itemColor = "66";
                    }
                }else{
                    itemColor = hexColorSb.substring(currentIndex * 2, currentIndex * 2 + 2);
                }
                canvas.drawText(itemColor,
                        (itemLeft + itemRight) / 2, (itemTop + itemBottom) / 2 + 13, mPaint);
                currentIndex++;
            }
        }
    }

    private void initItemPaint() {
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);


    }

    private void initItemColorPaint(int color) {
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);

    }

    private void initPaint() {
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
    }

    private void initTextPaint() {
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }
}
