package com.dong.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.dong.tool.ShowToast;

/**
 * Created by Dy on 2016/5/20.
 *
 */
public class ShowDialogFragment extends android.support.v4.app.DialogFragment {
    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getActivity());
        textView.setText("您好,应用因为意外情况已终止。~~~~(>_<)~~~~");
        textView.setTextSize(20);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        layout.addView(textView);


        Scanner scanner = new Scanner(System.in);
        return layout;
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("通过onCreatDialog创建的");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShowToast.showText(getActivity(),"取消",Toast.LENGTH_LONG);
            }
        });


        return dialog.create();
    }

    public static void showDialog(FragmentManager manager,String tag){
        ShowDialogFragment dialogFragment = new ShowDialogFragment();
        dialogFragment.show(manager,tag);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.e("DialogFragment", "onCancel");
        if(getFragmentManager() != null) {
            dismiss();
        }
    }
}
