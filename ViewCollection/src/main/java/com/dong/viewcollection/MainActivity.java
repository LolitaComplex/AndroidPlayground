package com.dong.viewcollection;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dong.lib.base.BaseActivity;
import com.dong.menum.ViewType;
import com.dong.tool.ShowToast;
import com.dong.view.BaseFragment;
import com.dong.view.ListFragment;
import com.dong.view.SlidingMenuFragment;

import java.util.List;

public class MainActivity extends BaseActivity implements ActivityCallback{


    private BaseFragment mCurrentFragment;
    private BaseFragment mSpecialFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Doing", "onCreate");
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        if(savedInstanceState == null || !savedInstanceState.getBoolean("isConfigChange",false)){
            ViewType[] values = ViewType.values();
            ListFragment<ViewType> listFragment = ListFragment.newInstance(values);
            addFragment(listFragment, listFragment.getFragmentName(), false);
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isConfigChange", true);
        Log.e("Doing", "onSaveInstanceState");
        ShowToast.showText(this, "onSaveInstanceState()-->onDestroy()-->onCreate()", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Doing", "onDestroy");
    }

    public void addFragment(Fragment fragment,String tag,boolean stack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_enter_animate,0,R.anim.fragment_enter_animate,0);
        fragmentTransaction.replace(R.id.rl_main, fragment, tag);
        if(stack){
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }

    private BaseFragment getPopStackFragment(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        return (BaseFragment) fragments.get(fragments.size()-1);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(mCurrentFragment == null || !mCurrentFragment.onWindowFoucuChange()){
            super.onWindowFocusChanged(hasFocus);
        }
    }

    @Override
    public void setSelectedFragment(BaseFragment fragment) {
        mCurrentFragment = fragment;
    }

    @Override
    public void setSpecificFragment(BaseFragment fragment) {
        this.mSpecialFragment = fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_design_coordinator,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mSpecialFragment != null && mSpecialFragment.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Class<? extends BaseFragment> currentClass = mCurrentFragment.getClass();

        if(currentClass == SlidingMenuFragment.class) {
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_search1).setVisible(false);
        }else{
            menu.findItem(R.id.action_search).setVisible(true);
            menu.findItem(R.id.action_search1).setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        if(mSpecialFragment != null) {
            mSpecialFragment.onOptionsItemSelected(null);
        }

        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onBackPressed() {
        if(!mCurrentFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
