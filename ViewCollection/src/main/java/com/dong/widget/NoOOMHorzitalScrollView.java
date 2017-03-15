package com.dong.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.dong.lib.utils.LogUtils;

import java.util.LinkedList;

/**
 * Created by 杜营 on 2016/6/16.
 * 设计主旨思想：让ScrollView一直在[0,ChildItemWidth]这个这段区域内滚动。
 *            当超出这段区域后，删除ScrollView中Child中的第一个View，并且初始化滑动位置至0
 *            当小于这段区域后，删除ScrollView中Child中的最后一个View，并且初始化滑动位置至ChildItemWidth
 */
public class NoOOMHorzitalScrollView extends HorizontalScrollView implements View.OnClickListener {

    private ViewGroup mContainer;
    private int mWindowWidth;
    private HorizontalScrollViewAdapter mAdapter;
    private int mViewWidth;
    private int mViewHeight;
    private int mScreentViewConut;
    private int mCurrentIndex;

    private int mFirstViewIndex;

    private OnItemClickListener mOnItemClickListener;
    private CurrentImageChangeListener mCurrentImageChangeListener;

    private LinkedList<View> viewBuf = new LinkedList<>();

    /**
     * 设置条目添加的回调接口
     */
    public interface OnItemClickListener{
        void onClick(View view, int pos);
    }

    /**
     * 设置选择条目改变的回调接口
     */
    public interface CurrentImageChangeListener{
        void onCurrentImageChanged(int position, View viewindicator);
    }

    public NoOOMHorzitalScrollView(Context context) {
        super(context);
    }

    public NoOOMHorzitalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        mWindowWidth = metrics.widthPixels;
    }

    public NoOOMHorzitalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoOOMHorzitalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mContainer = (ViewGroup) getChildAt(0);
    }

    public void setAdapter(HorizontalScrollViewAdapter adapter){
        this.mAdapter = adapter;
        //因为setAdapter中一般在onCreat()方法中，所以仍在没有对View进行初始化，onMeasure,onLayout,onDraw流程都没有走呢
        mContainer = (ViewGroup) getChildAt(0);
        //获得适配器中第一个View
        View firstView = adapter.getView(0,null,mContainer);
        mContainer.addView(firstView);

        if(mViewWidth == 0 && mViewHeight==0){
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            firstView.measure(widthMeasureSpec, heightMeasureSpec);

            mViewWidth = firstView.getMeasuredWidth();
            mViewHeight = firstView.getMeasuredHeight();

            mScreentViewConut = mWindowWidth / mViewWidth + 2;
                LogUtils.e("Doing","mScreentViewConut = "+mScreentViewConut+",mViewWidth"+mViewWidth);
        }

        initFirstScreenView(mScreentViewConut);
    }

    private void initFirstScreenView(int mScreentViewConut) {
        mContainer = (ViewGroup) getChildAt(0);
        mContainer.removeAllViews();


        for (int x = 0; x < mScreentViewConut; x++) {
            View view = mAdapter.getView(x, null, mContainer);
            view.setOnClickListener(this);

            mContainer.addView(view);
            mCurrentIndex = x;

            viewBuf.offerLast(view);
        }

        String data = "3530323435354532343735353534353735343534353535343535353535353535353534303544374434363734";

        StringBuilder sb = new StringBuilder(data);
        int sub = 0;
        for (int i = 0; i < sb.length(); i+=2) {
            int number = Integer.parseInt(sb.substring(i, i + 2),16);
            sub += number;
        }
        String hexString = Integer.toHexString(sub);
    }

    /**
     * 加载下一张图片
     */
    private void loadNextImg() {
        LogUtils.d("Doing","Debug:--->loadNextImg()");
        if(mCurrentIndex == mAdapter.getCount()-1){
            return;
        }

        //移出第一张图片，也将水平滚动位置置0
        scrollTo(0,0);

        mContainer.removeViewAt(0);

        //获取下一张图片，并且设置onClick点击事件，且加入容器中
        View view = mAdapter.getView(++mCurrentIndex,null,mContainer);
        view.setOnClickListener(this);
        mFirstViewIndex++;
        mContainer.addView(view);

        viewBuf.pollFirst();
        viewBuf.offerLast(view);

        notifyImgeChange();
        if(mCurrentImageChangeListener != null)
            mCurrentImageChangeListener.onCurrentImageChanged(mFirstViewIndex,view);
    }

    /**
     * 加载上一张图片
     */
    private void loadPreImg() {
        LogUtils.d("Doing","Debug:--->loadPreImg()");
        //如果当前已经是第一张图片，这返回
        if(mFirstViewIndex == 0){
            return;
        }
        //获取当前应该显示为第一张图片的下标
        int index = mCurrentIndex - mScreentViewConut;
        if(index >= 0){
            int oldViewPos = mContainer.getChildCount() - 1;
            mContainer.removeViewAt(oldViewPos);

            //将此View放入第一的位置
            View view = mAdapter.getView(index,null,mContainer);
            mContainer.addView(view, 0);
            view.setOnClickListener(this);
            //水平滚动位置向左移动view宽度个像素
            scrollTo(mViewWidth,0);
            //当前位置--，当前显示的第一个View--
            mCurrentIndex--;
            mFirstViewIndex--;

            viewBuf.offerFirst(view);
            viewBuf.pollLast();

            notifyImgeChange();
            if(mCurrentImageChangeListener != null)
                mCurrentImageChangeListener.onCurrentImageChanged(mFirstViewIndex,view);
        }
    }


    @Override
    public void onClick(View v) {
        for(int x=0; x<mContainer.getChildCount(); x++){
            mContainer.getChildAt(x).setBackgroundColor(Color.WHITE);
        }
        v.setBackgroundColor(Color.BLUE);
        if(mOnItemClickListener != null){
            mOnItemClickListener.onClick(v,viewBuf.indexOf(v)+mFirstViewIndex);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                int scrollX = getScrollX();
                //如果当前scrollX为View的宽度，加载下一张，移出第一张
                if(scrollX >= mViewWidth){
                    loadNextImg();
                }

                //如果当前scrollX = 0，往前设置一张，移出最后一张
                if(scrollX == 0){
                    loadPreImg();
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    public void setOnItemCLickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public void setOnCurrentImageChangeListener(CurrentImageChangeListener listener){
        this.mCurrentImageChangeListener = listener;
    }

    private void notifyImgeChange(){
        for(int x=0; x< mContainer.getChildCount();x++){
            mContainer.getChildAt(x).setBackgroundColor(Color.WHITE);
        }
        mContainer.getChildAt(0).setBackgroundColor(Color.BLUE);
    }

}
