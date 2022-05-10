package com.twl.myapplication.badtoken;

import android.app.Dialog;
import android.content.Context;
import android.widget.PopupWindow;

import androidx.core.widget.PopupWindowCompat;

public class DialogShow {
    private Context context;
    private Dialog dialog;

    private void doSomething() {
        if (ActivityUtils.isValid(context)) {
            dialog.show();
        }
    }

    private void doSomething2() {
        dialog.show();
        if (ActivityUtils.isValid(context)) {
            if(1==1){
                new PopupWindow().showAsDropDown(null,0,0,0);
            }
        }

        new PopupWindow().showAsDropDown(null);
        new PopupWindow().showAsDropDown(null,0,0,0);
        new PopupWindow().showAtLocation(null,0,0,0);
        PopupWindowCompat.showAsDropDown(null,null,0,0,0);
        if (ActivityUtils.isValid(context)) {
            new PopupWindow().showAtLocation(null,0,0,0);
        }
    }
}
