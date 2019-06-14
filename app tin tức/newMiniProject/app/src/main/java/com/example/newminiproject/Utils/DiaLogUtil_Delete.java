package com.example.newminiproject.Utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.example.newminiproject.R;
import com.example.newminiproject.databinding.UiDialogDeleteBinding;

public class DiaLogUtil_Delete extends Dialog implements View.OnClickListener {
    private calbackDialogDelete calback;

    public void setCalback(calbackDialogDelete calback) {
        this.calback = calback;
    }

    private UiDialogDeleteBinding binding;

    public DiaLogUtil_Delete( Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.ui_dialog_delete,null,false);
        setContentView(binding.getRoot());

        binding.btnDeleteOK.setOnClickListener(this);
        binding.btnDeleteCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Delete_OK:
                if (calback!=null){
                    calback.onclickDelete();
                }
                dismiss();
                break;
            case R.id.btn_Delete_Cancel:
                dismiss();
                break;
        }
    }

    public interface calbackDialogDelete{
        void onclickDelete();
    }
}
