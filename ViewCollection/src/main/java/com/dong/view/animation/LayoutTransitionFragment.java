package com.dong.view.animation;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.dong.lib.utils.ToastUtil;
import com.dong.view.BaseFragment;
import com.dong.viewcollection.R;

/**
 * Created by 杜营 on 2016/6/15.
 *
 */
public class LayoutTransitionFragment extends BaseFragment implements OnCheckedChangeListener, View.OnClickListener {


    private LinearLayout viewGroup;
    private CheckBox mAppear;
    private CheckBox mChangeAppear;
    private CheckBox mDisappear;
    private CheckBox mChangeDisappear;

    private int mIndex = 1;
    private Button mAddButton;
    private GridLayout gridLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_layout_transition, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View view) {
        viewGroup = (LinearLayout) view.findViewById(R.id.container);

        mAppear = (CheckBox) view.findViewById(R.id.cb_appearing);
        mChangeAppear = (CheckBox) view.findViewById(R.id.cb_change_appearing);
        mDisappear = (CheckBox) view.findViewById(R.id.cb_disappearing);
        mChangeDisappear = (CheckBox) view.findViewById(R.id.cb_change_disappearing);

        mAddButton = (Button) view.findViewById(R.id.btn_add_button);

        mAppear.setOnCheckedChangeListener(this);
        mChangeAppear.setOnCheckedChangeListener(this);
        mDisappear.setOnCheckedChangeListener(this);
        mChangeDisappear.setOnCheckedChangeListener(this);
        mAddButton.setOnClickListener(this);

        gridLayout = new GridLayout(mContext);
        gridLayout.setColumnCount(4);
        gridLayout.addView(makeButton());

        viewGroup.addView(gridLayout);

        LayoutTransition layoutTransition = new LayoutTransition();
        gridLayout.setLayoutTransition(layoutTransition);//默认开启所有四种动画效果
    }

    @Override
    public void onClick(View view){
        ToastUtil.show("添加按钮成功");
        gridLayout.addView(makeButton(),1);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setAnimator(LayoutTransition.APPEARING,(mAppear.isChecked()? ObjectAnimator.ofFloat(null,"scaleX",0,1):null));
        setLayoutAnimator(layoutTransition, LayoutTransition.CHANGE_APPEARING, mChangeAppear);
        setLayoutAnimator(layoutTransition,LayoutTransition.DISAPPEARING,mDisappear);
        setLayoutAnimator(layoutTransition,LayoutTransition.CHANGE_DISAPPEARING,mChangeDisappear);
        gridLayout.setLayoutTransition(layoutTransition);
    }

    public Button makeButton(){
        final Button button = new Button(mContext);
        button.setText(mIndex+++"");
        button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridLayout.removeView(button);
            }
        });
        return button;
    }

    public void setLayoutAnimator(LayoutTransition layoutTransition,int transitionType,CheckBox checkBox){
        layoutTransition.setAnimator(transitionType,(checkBox.isChecked() ? layoutTransition.getAnimator(transitionType):null));
    }
}
