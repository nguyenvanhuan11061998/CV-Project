package com.t3h.documentmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.t3h.documentmanager.R;
import com.t3h.documentmanager.databinding.DialogSortFileBinding;

public class Dialog_SortFile extends Dialog implements View.OnClickListener {

    private DialogSortFileBinding binding;
    private calbackSortFile calback;

    public void setCalback(calbackSortFile calback) {
        this.calback = calback;
    }

    public Dialog_SortFile(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_sort_file,null,false);

        setContentView(binding.getRoot());

        binding.rbBtn1.setOnClickListener(this);
        binding.rbBtn2.setOnClickListener(this);
        binding.rbBtn3.setOnClickListener(this);
        binding.rbBtn4.setOnClickListener(this);
        binding.rbBtn5.setOnClickListener(this);
        binding.rbBtn6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        RadioGroup group = findViewById(R.id.radio_Group);
        int isChecked = group.getCheckedRadioButtonId();
        switch (isChecked){
            case R.id.rb_btn1:
                calback.onClickSortFile(0);
                dismiss();
                break;
            case R.id.rb_btn2:
                calback.onClickSortFile(1);
                dismiss();
                break;
            case R.id.rb_btn3:
                calback.onClickSortFile(2);
                dismiss();
                break;
            case R.id.rb_btn4:
                calback.onClickSortFile(3);
                dismiss();
                break;
            case R.id.rb_btn5:
                calback.onClickSortFile(4);
                dismiss();
                break;
            case R.id.rb_btn6:
                calback.onClickSortFile(5);
                dismiss();
                break;
        }
    }

    public interface calbackSortFile{
        void onClickSortFile(int index);
    }
}
