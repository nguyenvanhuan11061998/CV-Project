package com.example.newminiproject.Utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtils {
    private static Dialog progessDialog;

    public static void show(Context context){
        dismiss();

        progessDialog = new ProgressDialog
                .Builder(context)
                .setMessage("Loading ... ")
                .setCancelable(false)
                .create();
        progessDialog.show();
    }

    public static void dismiss() {
        if (progessDialog != null && progessDialog.isShowing())
            progessDialog.dismiss();
    }
}
