package com.dong.tool;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 杜营 on 2016/3/3.
 *
 */
public class ShowToast {

    private static Toast toast;

    private ShowToast(){}

    public static void showText(Context context,String text,int duration){
        if(toast==null){
            toast = Toast.makeText(context,text,duration);
        }
        toast.setText(text);
        toast.setDuration(duration);
        toast.show();
    }
}
