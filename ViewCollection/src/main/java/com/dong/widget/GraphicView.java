package com.dong.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Dy on 2016/5/18.
 *
 */
public class GraphicView extends View{


    public GraphicView(Context context) {
        super(context);
    }

    public GraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        //绘画第一部：设置壁纸样式
        canvas.drawColor(Color.WHITE);

        //绘画第二部：设置画笔样式
        Paint paint = new Paint();
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);//是填充还是勾线
        paint.setStrokeWidth(5);

        int viewWidth = this.getWidth();//因为已经onLayout了，所以getWidth已经有了值

        //绘画第三部：开始绘画，圆形
        canvas.drawCircle(viewWidth / 10 + 10, viewWidth / 10 + 10, viewWidth / 10, paint);
        //矩形
        canvas.drawRect(2 * viewWidth / 10 + 20, 10, 4 * viewWidth / 10 + 20, 2 * viewWidth / 10 + 10, paint);

        //圆角矩形
        RectF rect = new RectF(4 * viewWidth / 10 + 30, 10, 8 * viewWidth / 10 + 30, 2 * viewWidth / 10 + 10);
        canvas.drawRoundRect(rect,100,100,paint);

        //椭圆
        RectF rectOval = new RectF(8 * viewWidth / 10 + 40,10,viewWidth,2 * viewWidth / 10 + 10);
        canvas.drawOval(rectOval,paint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        //通过Path路径绘制类，绘制三角形
        Path pathTriangle = new Path();
        pathTriangle.moveTo(10, 2 * viewWidth / 10 + 20);
        pathTriangle.lineTo(10, 4 * viewWidth / 10 + 20);
        pathTriangle.lineTo(2 * viewWidth / 10, 4 * viewWidth / 10 + 20);
        pathTriangle.close();
        canvas.drawPath(pathTriangle, paint);

        paint.setStyle(Paint.Style.STROKE);
        //绘制渐变填充
        Shader shader = new LinearGradient(0,0,100,100,new int[]{Color.RED,Color.DKGRAY,Color.GRAY,Color.LTGRAY,Color.WHITE,Color.GREEN,Color.BLUE},null,Shader.TileMode.REPEAT);
        paint.setShader(shader);
        paint.setShadowLayer(10, 10, 10, Color.RED);
        RectF ovalRect = new RectF(10, 4 * viewWidth / 10 + 30, 2 * viewWidth / 10 + 10, 8 * viewWidth / 10 + 30);
        canvas.drawOval(ovalRect, paint);

        paint.setStyle(Paint.Style.FILL);
        Path pathShaderTriangle = new Path();
        pathShaderTriangle.moveTo(3 * viewWidth / 10 + 20,4 * viewWidth / 10 + 30);
        pathShaderTriangle.lineTo(2 * viewWidth / 10 + 20, 8 * viewWidth / 10 + 30);
        pathShaderTriangle.lineTo(4 * viewWidth / 10 + 20, 8 * viewWidth / 10 + 30);
        pathShaderTriangle.close();
        canvas.drawPath(pathShaderTriangle,paint);


        //绘制文字
        paint.setTextSize(70);
        paint.setShader(null);
        canvas.drawText("学习绘制各种图形文字中",10,viewWidth + 50,paint);
    }
}
