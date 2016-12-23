package com.doing.theme;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;

import com.doing.theme.ui.activity.HoloActionBarDarkActivity;
import com.doing.theme.ui.activity.HoloActivity;
import com.doing.theme.ui.activity.HoloLightActivity;
import com.doing.theme.ui.activity.MertialActionBarActivity;
import com.doing.theme.ui.activity.MertialActivity;
import com.doing.theme.ui.activity.MertialLightActivity;
import com.doing.theme.utils.theme.GlobalTheme;
import com.doing.theme.utils.theme.GlobalTheme.ColorTheme;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThemeActivity extends AppCompatActivity {

    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        ButterKnife.bind(this);

        mContainer = ButterKnife.findById(this, R.id.container);
        initActionBar();
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.gereral_toolbar);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.ThemeActivity_btn_holo)
    public void onHoloItemClick(View view) {
        HoloActivity.start(this);
    }

    @OnClick(R.id.ThemeActivity_btn_holoLight)
    public void onHoloLightItemClick(View view) {
        HoloLightActivity.start(this);
    }

    @OnClick(R.id.ThemeActivity_btn_holoActionBarDark)
    public void onHoloDarkActionBarItemClick(View view) {
        HoloActionBarDarkActivity.start(this);
    }

    @OnClick(R.id.ThemeActivity_btn_mertial)
    public void onMertialItemClick(View view) {
        MertialActivity.start(this);
    }

    @OnClick(R.id.ThemeActivity_btn_materialLight)
    public void onMertialLightItemClick(View view) {
        MertialLightActivity.start(this);
    }

    @OnClick(R.id.ThemeActivity_btn_materialActionBarDark)
    public void onMertialDarkActionBarItemClick(View view) {
        MertialActionBarActivity.start(this);
    }

    @OnClick(R.id.ThemeActivity_iv_blue)
    public void switchThemeBlue(View view) {
        GlobalTheme.setCurrentTheme(ColorTheme.BLUE);
        initCircularRevealThemeColor(mContainer, getBackColor(), 200, 0);
        mContainer.requestLayout();
    }

    @OnClick(R.id.ThemeActivity_iv_gray)
    public void switchThemeGray(View view) {
        GlobalTheme.setCurrentTheme(ColorTheme.GRAY);
        initCircularReveal(mContainer, getBackColor());
    }

    @OnClick(R.id.ThemeActivity_iv_purple)
    public void switchThemePurple(View view) {
        GlobalTheme.setCurrentTheme(ColorTheme.PURPLE);
        initCircularReveal(mContainer, getBackColor());
    }

    @OnClick(R.id.ThemeActivity_iv_cp)
    public void switchThemeColorPrimary(View view) {
        GlobalTheme.setCurrentTheme(ColorTheme.COLOR_PRIMARY);
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        initCircularRevealThemeColor(mContainer, getBackColor(), widthPixels - 50, heightPixels - 50);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private int getBackColor() {
        getTheme().applyStyle(GlobalTheme.getCurrentTheme(), true);
        int[] attrs = {R.attr.bgcolor};
        TypedArray typedArray = getTheme().obtainStyledAttributes(attrs);
        int color = typedArray.getColor(0, Color.parseColor("#66ccff"));
        return color;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initCircularRevealThemeColor(View rootView, int color, int circleX, int circleY) {
        rootView.setBackgroundColor(color);
        float finalRadius = (float) Math.hypot(rootView.getWidth(), rootView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(rootView, circleX, circleY, 0, finalRadius);
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initCircularRevealThemeColorReverse(View rootView, int color, int circleX, int circleY) {
        rootView.setBackgroundColor(color);
        float finalRadius = (float) Math.hypot(rootView.getWidth(), rootView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(rootView, circleX, circleY, finalRadius, 0);
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initCircularReveal(View rootView, int color) {
        rootView.setBackgroundColor(color);

        int circleX = rootView.getWidth() / 2;
        int circleY = rootView.getHeight() / 2;
        int finalRadius = rootView.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(rootView, circleX, circleY, 0, finalRadius);
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();

    }
}
