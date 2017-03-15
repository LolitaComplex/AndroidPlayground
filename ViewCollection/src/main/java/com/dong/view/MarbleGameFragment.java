package com.dong.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dy on 2016/5/18.
 *
 */
public class MarbleGameFragment extends BaseFragment {

    //桌面宽度
    private int tableWidth;
    //桌面高度
    private int tableHeight;
    //球拍的位置
    private int racketY;
    // 线面定义球拍的高度和宽度
    private final int RACKET_HEIGHT = 30;
    private final int RACKEY_WIDTH = 90;
    // 小球的大小
    private final int BALL_SIZE = 16;
    // 小球纵向的运行速度
    private int ySpeed = 15;
    Random random = new Random();
    // 返回一个 -0.5~0.5的比率，用于控制小球的运行方向
    private double xyRate = random.nextDouble() - 0.5;
    // 小球的横向运行速度
    private int xSpeed = (int) (ySpeed * xyRate * 2);
    // ballX和ballY代表小球的坐标
    private int ballX = random.nextInt(200) + 20;
    private int ballY = random.nextInt(10) + 20;
    // racketX 代表球拍的水平位置
    private int racketX = random.nextInt(200);
    private boolean isLose = false;
    private Timer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WindowManager windowManager = mContext.getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        tableWidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels;

        //初始化球拍的位置
        racketY = tableHeight - 80;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MarbleGameView gameView = new MarbleGameView(mContext);
        initListener(gameView);
        return gameView;
    }

    private void initListener(final View view) {
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_A:
                        if (racketX > 0) {
                            racketX -= 10;
                        }
                        break;
                    case KeyEvent.KEYCODE_D:
                        if (racketX < tableWidth - RACKEY_WIDTH) {
                            racketX += 10;
                        }
                        break;
                }
                view.invalidate();
                return true;
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {

            private int downX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getX();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int moveX = (int) event.getX();
                        int distenceX = Math.abs(moveX - downX);
                        if(moveX > downX && racketX < tableWidth - RACKEY_WIDTH){
                            racketX += distenceX;
                            downX = moveX;
                        }else if(moveX < downX && racketX > 0){
                            racketX -= distenceX;
                            downX = moveX;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //如果小球碰到左边边界或者右边边界时
                if (ballX <= 0 || ballX >= tableWidth - BALL_SIZE){
                    xSpeed = -xSpeed;
                }

                //如果小球上边线超出球拍的位置，并且横向不在球盘范围内，游戏失败并结束
                if (ballY >= racketY - BALL_SIZE && (ballX < racketX || ballX > racketX + RACKEY_WIDTH)) {
                    timer.cancel();
                    isLose = true;
                }
                //
                else if (ballY <= 0 || ballY >= racketY - BALL_SIZE && ballX > racketX && ballX <= racketX + RACKEY_WIDTH) {
                    ySpeed = -ySpeed;
                }

                ballX += xSpeed;
                ballY += ySpeed;

                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });
            }
        }, 0, 100);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        timer.cancel();
    }

    private class MarbleGameView extends View {

        private Paint paint = new Paint();

        public MarbleGameView(Context context) {
            super(context);
            setFocusable(true);
        }


        public MarbleGameView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MarbleGameView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            if(isLose){
                paint.setColor(Color.RED);
                paint.setTextSize(50);
                canvas.drawText("游戏已结束",tableWidth / 2 - 100,200,paint);
            }else{
                paint.setColor(Color.BLACK);
                canvas.drawCircle(ballX, ballY, BALL_SIZE, paint);
                paint.setColor(Color.rgb(80, 80, 200));
                canvas.drawRect(racketX, racketY, racketX + RACKEY_WIDTH, racketY + RACKET_HEIGHT, paint);
            }

        }
    }

}
