package com.doing.toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.General_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setLogo(R.mipmap.ic_launcher);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);

        MenuItem item = menu.findItem(R.id.account);
//        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem menuItem) {
//                //当Menu中按钮点击展开时回调
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
//                //当Menu中按钮点击关闭时回调
//                return false;
//            }
//        });
        
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        });
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //输出完成时
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //输入文字改变时
                return false;
            }
        });

        item = menu.findItem(R.id.shpae);
//        ShareActionProvider provider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setType("image/*");
//        provider.setShareIntent(intent);

        item = menu.findItem(R.id.more);
        UserProvider userProvider = (UserProvider) MenuItemCompat.getActionProvider(item);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:

                break;

            case R.id.shpae:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
