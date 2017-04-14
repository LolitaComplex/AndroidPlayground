package com.doing.architecture.mvp.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-14.
 */

public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {


    private View mChildScrollView;

    public ScrollChildSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ScrollChildSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mChildScrollView != null) {
            return ViewCompat.canScrollVertically(mChildScrollView, -1);
        }
        return super.canChildScrollUp();
    }

    public void setChildScrollView(View childScrollView) {
        mChildScrollView = childScrollView;
    }
}
