package com.dong.menum;

import java.io.Serializable;

/**
 * Created by 杜营 on 2016/2/25.
 *
 */
public enum ViewType implements Serializable,ViewConllectionType {
    Spinner,
    SearchView,
    TabHost,
    Notification,
    ActionBarTab,
    raphic,
    MarbleGame,
    Dialog,
    DataCache,
    UserDefineViewPager,
    Animation,
    NoOOMHorizontalScrollView,
    RecyclerViewTest,
    RecyclerViewGallery,
    OkHttp,
    Volley,
    Design,
    RxJava,
    Retrofit,
    SlidingPaneLayout,
    CommonAdapter,
    CardView,
    BottomSheet;

    private String className;
    ViewType(){
        className = "com.dong.view."+name()+"Fragment";
    }

    public String getTypeClassName(){
        return className;
    }

}
