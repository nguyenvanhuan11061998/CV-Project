package com.example.musicplayer1.view;

import android.app.Dialog;
import android.content.Context;

import com.example.musicplayer1.R;

public class MP3ProgessDialog extends Dialog {


    public MP3ProgessDialog(Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        setContentView(R.layout.progess_dialog);
        setCancelable(false);
    }
}
