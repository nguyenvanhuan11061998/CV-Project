package com.t3h.documentmanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog {
    private static Dialog progessDialog;

    public static void show(Context context){
        dismiss();

        progessDialog = new ProgressDialog
                .Builder(context)
                .setMessage(" Please wait ... ")
                .setCancelable(false)
                .create();
        progessDialog.show();
    }

    public static void dismiss() {
        if (progessDialog != null && progessDialog.isShowing()){
            progessDialog.dismiss();
        }
    }
}
