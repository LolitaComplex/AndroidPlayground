package com.doing.toolbar;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

public class UserProvider extends ActionProvider {

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public UserProvider(Context context) {
        super(context);
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public View onCreateActionView(MenuItem forItem) {
        return super.onCreateActionView(forItem);
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add("SubMenu1").setIcon(R.drawable.account)
            .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Toast.makeText(getContext(), "点击了SubMenu1", Toast.LENGTH_SHORT).show();
                    return false;
                }
            })
        ;
        subMenu.addSubMenu("SubMenu2").setIcon(R.drawable.shape);
    }

    @Override
    public boolean hasSubMenu() {
        //判断有子菜单的模板方法
        return true;
    }
}
