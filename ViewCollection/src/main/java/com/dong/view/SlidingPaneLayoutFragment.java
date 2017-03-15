package com.dong.view;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.dong.adapter.DividerItemDecoration;
import com.dong.adapter.SimpleAdapter;
import com.dong.lib.utils.ToastUtil;
import com.dong.viewcollection.R;
import com.dong.widget.DrawerArrowDrawableToggle;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by 杜营 on 2016/8/24.
 *
 */
public class SlidingPaneLayoutFragment extends BaseFragment {

    @BindView(R.id.SlidingFragment_toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.SlidingFragment_drawer)
    protected SlidingPaneLayout mDrawer;

    @BindView(R.id.SlidingFragment_recycler)
    protected RecyclerView mRecyclerView;

    /**
     * 这是NavigateIcon系统内置的动画效果
     */
    private DrawerArrowDrawableToggle mNavigateIcon;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sdlidingpanelayout, container, false);
    }

    @Override
    protected void loadData() {
        mNavigateIcon = new DrawerArrowDrawableToggle(mContext);

        if (mToolbar != null) {
            mContext.setSupportActionBar(mToolbar);

            ActionBar supportActionBar = mContext.getSupportActionBar();
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);

            mContext.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
            mContext.invalidateOptionsMenu();
            mContext.setSpecificFragment(this);

            mToolbar.setNavigationIcon(mNavigateIcon);
        }

        mDrawer.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                mNavigateIcon.setPosition(slideOffset);
            }

            @Override
            public void onPanelOpened(View panel) {
                mNavigateIcon.setPosition(1f);
            }

            @Override
            public void onPanelClosed(View panel) {
                mNavigateIcon.setPosition(0f);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext
                , LinearLayoutManager.VERTICAL, false));
        SimpleAdapter adapter = new SimpleAdapter(Arrays.asList("主页内容1"
                , "主页内容2", "主页内容3", "主页内容4", "主页内容5"));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext
                , DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnSimpleItemClickListener(new SimpleAdapter.OnSimpleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.show("主页内容" + position);
            }
        });
        //---------------------------------------这套方案失败---------------------------------------
       /* mActionBarToggle = new ActionBarDrawerToggle(mContext, new DrawerLayout(mContext),mToolbar
                , R.string.app_name, R.string.add_button){


            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                //不能走父类的super.onOptionsItemSelected(item)
                if(item == null || item.getItemId() == android.R.id.home){
                    if(!mDrawer.isOpen()) {
                        mDrawer.openPane();
                    }else {
                        mDrawer.closePane();
                    }
                }
                return true;
            }
        };

        mActionBarToggle.setDrawerIndicatorEnabled(false);
        mActionBarToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mActionBarToggle.onOptionsItemSelected(null);
            }
        });

        mDrawer.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                mActionBarToggle.onDrawerSlide(panel, slideOffset);
            }

            @Override
            public void onPanelOpened(View panel) {
                mActionBarToggle.onDrawerOpened(panel);
            }

            @Override
            public void onPanelClosed(View panel) {
                mActionBarToggle.onDrawerClosed(panel);
            }
        });

        mActionBarToggle.syncState();
        */
        //---------------------------------------这套方案失败---------------------------------------
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* if(item == null && mActionBarToggle != null){
            mActionBarToggle.syncState();
            mContext.getSupportActionBar().setDisplayHomeAsUpEnabled(mActionBarToggle.isDrawerIndicatorEnabled());
        }*/
        if(item.getItemId() == android.R.id.home){
            float position = mNavigateIcon.getPosition();
            float endPostion = (position == 1f) ? 0f : 1f;
            float startPostion = (position != 1f) ? 0f : 1f;

            ValueAnimator animator = ValueAnimator.ofFloat(startPostion, endPostion);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    mNavigateIcon.setPosition(value);
                }
            });
            animator.start();
            if (startPostion < endPostion) {
                mDrawer.openPane();
            } else {
                mDrawer.closePane();
            }
            return true;
        }

        return false;
    }
}
