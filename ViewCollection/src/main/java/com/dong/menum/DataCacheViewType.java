package com.dong.menum;

import java.io.Serializable;

/**
 * Created by Dy on 2016/5/23.
 *
 */
public enum  DataCacheViewType implements Serializable,ViewConllectionType {

    SaveDataInBundle,
    SaveDataInFragment;

    private String className;
    DataCacheViewType(){
        className = "com.dong.view.datacache."+name()+"Fragment";
    }

    public String getTypeClassName(){
        return className;
    }
}
