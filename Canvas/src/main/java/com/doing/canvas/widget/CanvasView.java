package main.java.com.doing.canvas.widget;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.doing.canvas.R;

import java.security.Key;

/**
 * Created by Doing on 2016/10/26.
 *
 */
public class CanvasView extends View {

    private final static int PADDING = 100;
    private final static int TEXT_DISTENCE = 40;

    private int mWidthSize;
    private int mHeightSize;

    private String[] xTitleBuf = {"第1季度","第2季度","第3季度","第4季度","第5季度","啦啦啦啦"};
    private float[] mValueBuf = {0.6f, 0.2f, 0.9f, 0.5f, 0.1f,0.8f};

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("Canvasiew", "changed=" + changed + "l=" + l + ",t=" + t + ",r=" + r + ",b" + b);
    }

    private int unspecified = 0;
    private int atmost = 100;
    private int exactly = 1000;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        widthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidthSize / 2, MeasureSpec.EXACTLY);

        mHeightSize = MeasureSpec.getSize(heightMeasureSpec) - getNavigationBarHeight();
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeightSize / 2, MeasureSpec.EXACTLY);

        Log.e("ConvasView", "宽度" + mWidthSize + "::::高度:" + mHeightSize);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            Log.e("CanvasView", "" + unspecified++);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            Log.e("CanvasView", "" + atmost++);
        } else if (widthMode == MeasureSpec.EXACTLY) {
            Log.e("CanvasView", "" + exactly++);
        }
//        this.measure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画坐标系
        Paint paint = initPathPaint();
        Path path = new Path();
        path.moveTo(PADDING, PADDING);
        path.lineTo(PADDING, mHeightSize - PADDING);
        path.lineTo(mWidthSize - PADDING, mHeightSize - PADDING);
        canvas.drawPath(path, paint);

        initTextPatint();

        //画表头与坐标
        paint = initTextPatint();
        for (int x = 0; x < 9; x++) {
            int textDistence = (mHeightSize - 2 * PADDING) / 8;
            canvas.drawText(x + "", PADDING - TEXT_DISTENCE, mHeightSize - x * textDistence - PADDING, paint);
        }
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);

        String title = "我的坐标系";
        canvas.drawText(title, mWidthSize / 2, PADDING - 5, paint);
        float textWidth = paint.measureText(title);
        paint = initPathPaint();
        paint.setColor(Color.RED);
        canvas.drawLine((mWidthSize - textWidth) / 2, PADDING + 5, (mWidthSize + textWidth) / 2, PADDING + 5, paint);

        //话横坐标轴（查表法，记录一组组数据）
        for (int x = 0; x < xTitleBuf.length; x++) {
            canvasXItem(canvas, x);
        }

        drawProfit(canvas);
    }

    private void drawProfit(Canvas canvas) {
        canvas.save();

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.profit);
        canvas.translate(mWidthSize / 2 - 200, mHeightSize / 2 - 200);
        canvas.rotate(-30f, 200, 200);

        Path cirlcePath = new Path();
        Path.Direction direction = Path.Direction.CW;
        cirlcePath.addCircle(200, 200, 200, direction);

        //设置Drawable的位置大小，四个坐标点是以Canvas为原点的
        drawable.setBounds(0, 0, 400, 400);
        canvas.clipPath(cirlcePath, Region.Op.REPLACE);

        drawable.draw(canvas);


//        Paint paint = new Paint();
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        RectF rectF = new RectF(0, 0, mWidthSize, mHeightSize);
//        canvas.saveLayer(rectF, null, Canvas.ALL_SAVE_FLAG);

//        canvas.restore();
    }

    private void canvasXItem(Canvas canvas, int index) {
        int itemDistence = (mWidthSize - 3 * PADDING) / xTitleBuf.length;
        int startX = PADDING + (index + 1) * itemDistence;
        Paint paint = initPathPaint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        float top = (1-mValueBuf[index]) * (mHeightSize - 2 * PADDING) + PADDING;
        canvas.drawRect(startX - 20, top, startX + 20, mHeightSize - PADDING - 3, paint);

        canvas.save();
        paint = initTextPatint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setColor(Color.GREEN);
        paint.setTextSize(25);
        canvas.rotate(-30f, startX, mHeightSize - PADDING + 35);
        canvas.drawText(xTitleBuf[index], startX, mHeightSize - PADDING + 35, paint);
        canvas.restore();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile("", options);

        options.inScaled = true;

    }

    private Paint initTextPatint() {
        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.RIGHT);

        return paint;
    }

    private Paint initPathPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setShadowLayer(5, 0, 3, Color.DKGRAY);
        paint.setPathEffect(new CornerPathEffect(5));
        return paint;
    }

    private int getNavigationBarHeight() {
        Resources resources = getContext().getResources();
        int result = 0;
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        if (resourceId > 0)
            result = resources.getDimensionPixelSize(resourceId);
        return result;
    }

    public void animator() {
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.5f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder rotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, rotation);
        animator.setDuration(2000);
        animator.start();
    }
}
