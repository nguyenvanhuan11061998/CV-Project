package com.t3h.documentmanager;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class AppBinding {

    @BindingAdapter("thumb")
    public static void setThumb(ImageView im, String img){
        if (img.equals("txt")){
            Glide.with(im).load(R.drawable.document).into(im);
        }else if (img.equals("pdf")){
            Glide.with(im).load(R.drawable.pdf).into(im);
        }else if (img.equals("doc")){
            Glide.with(im).load(R.drawable.doc).into(im);
        }else if (img.equals("ppt")){
            Glide.with(im).load(R.drawable.ppt).into(im);
        }else if (img.equals("csv")){
            Glide.with(im).load(R.drawable.excel).into(im);
        }
    }

}
