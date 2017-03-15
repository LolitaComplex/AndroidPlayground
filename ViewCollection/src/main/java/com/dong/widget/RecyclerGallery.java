package com.dong.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.dong.lib.utils.LogUtils;


/**
 * Created by 杜营 on 2016/6/21.
 *
 */
public class RecyclerGallery extends RecyclerView{

    private View mCurrentView;

    private OnCurrentItemChangeListener mCurrentItemChangeListener;

    public RecyclerGallery(Context context) {
        super(context);
    }

    public RecyclerGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addOnScrollListener(new GalleryScrollListener());
    }

    public RecyclerGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public interface OnCurrentItemChangeListener{
        void onItemChange(View currentView, int position);
    }

    public void setOnCurrentItemChangeListener(OnCurrentItemChangeListener listener) {
        mCurrentItemChangeListener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mCurrentView = getChildAt(0);
        if(mCurrentItemChangeListener != null){
            mCurrentItemChangeListener.onItemChange(mCurrentView,getChildAdapterPosition(mCurrentView));
        }
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getAction() == MotionEvent.ACTION_MOVE) {
            mCurrentView = getChildAt(0);

            mCurrentItemChangeListener.onItemChange(mCurrentView,getChildAdapterPosition(mCurrentView));
        }

        return super.onTouchEvent(e);
    }*/

    private class GalleryScrollListener extends OnScrollListener{

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            View newView = getChildAt(0);//获取当前第一个View
            LogUtils.e("Doing",newState+"状态");
            //当已经滑动状态改变，比如拖动、放开、flag等等状态，才会触发图片改变的回调
            if(mCurrentItemChangeListener != null){
                //当当前View与上一个View不同时就替换，减少替换次数增加效率
                if(newView != null && newView != mCurrentView){
                    mCurrentView = newView;
                    mCurrentItemChangeListener.onItemChange(mCurrentView,getChildAdapterPosition(mCurrentView));
                }
            }
        }
    }

}
