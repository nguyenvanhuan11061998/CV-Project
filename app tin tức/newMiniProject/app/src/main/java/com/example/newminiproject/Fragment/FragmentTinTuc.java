package com.example.newminiproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.newminiproject.Adapter.NewsAdapter;
import com.example.newminiproject.Model.News;
import com.example.newminiproject.R;
import com.example.newminiproject.WebActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentTinTuc extends BaseFragment implements NewsAdapter.NewsItemListener {
    public static final int REQUEST_FM_TINTUC = 3;
    public static final String EXTRA_LINK = "extra_link";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_DESC = "extra_desc";
    public static final String EXTRA_PUBDATE = "extra_pubDate";
    public static final String EXTRA_IMAGE = "extra_image";

    private NewsAdapter adapter;
    private RecyclerView lvNews;

    private TextView tvHienThi1;
    private List<News> arrNews = new ArrayList<>();

    public void setArrNews(List<News> arrNews) {
        this.arrNews = arrNews;
    }

    @Override
    public String getTitle() {
        return "Tin Tức";
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tintuc;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewTinTuc();
    }

    private void initViewTinTuc() {
        tvHienThi1 = findViewById(R.id.tvHienThi1);
        adapter = new NewsAdapter(getContext());
        lvNews = findViewById(R.id.lv_New_TinTuc);
        lvNews.setAdapter(adapter);
        adapter.setData(arrNews);


        adapter.setItemListener(this);

        try {
            if (arrNews.size() > 0){
                tvHienThi1.setText("");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onclickItem(int i) {
        News news = arrNews.get(i);


//        getParent().searchWeb(link);
        Intent intent = new Intent(getContext(),WebActivity.class);
        intent.putExtra(EXTRA_LINK,news.getLink());
        intent.putExtra(EXTRA_TITLE,news.getTitle());
        intent.putExtra(EXTRA_DESC,news.getDesc());
        intent.putExtra(EXTRA_PUBDATE,news.getPubDate());
        intent.putExtra(EXTRA_IMAGE,news.getImage());

        startActivityForResult(intent,REQUEST_FM_TINTUC);

    }

    @Override
    public void onLongClickItem(View v,int i) {
    }
}
