package com.t3h.documentmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.t3h.documentmanager.R;
import com.t3h.documentmanager.databinding.DialogUiDeleteBinding;

public class Dialog_UiDelete extends Dialog implements View.OnClickListener {

    private calbackDialog calback;

    public void setCalback(calbackDialog calback) {
        this.calback = calback;
    }

    private DialogUiDeleteBinding binding;

    public Dialog_UiDelete( Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_ui_delete,null,false);
        setContentView(binding.getRoot());

        binding.btnDeleteOK.setOnClickListener(this);
        binding.btnDeleteCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Delete_OK:
                if (calback!= null){
                    calback.onClickDelete();
                }
                dismiss();
                break;
            case R.id.btn_Delete_Cancel:
                dismiss();
                break;
        }
    }

    public interface calbackDialog{
        void onClickDelete();
    }
}
