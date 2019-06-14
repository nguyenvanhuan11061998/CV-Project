package com.example.newminiproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.newminiproject.AsyncTask.DownloadAsync;
import com.example.newminiproject.Database.AppDatabase;
import com.example.newminiproject.Fragment.FragmentDaLuu;
import com.example.newminiproject.Fragment.FragmentTinTuc;
import com.example.newminiproject.Fragment.FragmentYeuThich;
import com.example.newminiproject.Model.News;
import com.example.newminiproject.Utils.DialogUtils;

import java.util.List;


public class WebActivity extends AppCompatActivity implements View.OnClickListener, DownloadAsync.downloadCallBack {
    private static final int REQUEST_WEBVIEW = 5;
    private FloatingActionButton fbSaveWeb;
    private News item = new News();

    private String pathFileOrLink;
    private WebView wvWebNews;

    private boolean checkExit = false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        initView();
    }


    private void initView() {
        fbSaveWeb = findViewById(R.id.fb_Save);
        fbSaveWeb.setOnClickListener(this);

        wvWebNews = findViewById(R.id.wv_WebView);
        wvWebNews.setWebViewClient(new WebViewClient());

        Intent intent =getIntent();
        if (intent.getStringExtra(FragmentYeuThich.EXTRA_PATHFILE_YT )!= null){
            pathFileOrLink = intent.getStringExtra(FragmentYeuThich.EXTRA_PATHFILE_YT);
        }else if (intent.getStringExtra(FragmentDaLuu.EXTRA_PATHFILEDL) != null){
            pathFileOrLink = intent.getStringExtra(FragmentDaLuu.EXTRA_PATHFILEDL);
        }else if (intent.getStringExtra(FragmentTinTuc.EXTRA_LINK)!=null){
            pathFileOrLink = intent.getStringExtra(FragmentTinTuc.EXTRA_LINK);
            item.setLink(intent.getStringExtra(FragmentTinTuc.EXTRA_LINK));
            item.setTitle(intent.getStringExtra(FragmentTinTuc.EXTRA_TITLE));
            item.setDesc(intent.getStringExtra(FragmentTinTuc.EXTRA_DESC));
            item.setPubDate(intent.getStringExtra(FragmentTinTuc.EXTRA_PUBDATE));
            item.setImage(intent.getStringExtra(FragmentTinTuc.EXTRA_IMAGE));
            checkExit = true;
        }

        wvWebNews.loadUrl(pathFileOrLink);

        WebSettings settings = wvWebNews.getSettings();
        settings.setJavaScriptEnabled(true);

        DialogUtils.dismiss();

    }


    @Override
    public void onBackPressed() {
        if (wvWebNews.canGoBack()) {
            wvWebNews.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        String link = item.getLink();
        if (checkExit == true){
            DialogUtils.show(this);
            String currentPath = Environment.getExternalStorageDirectory().getPath();
            String path = currentPath + "/FilterNews/" + System.currentTimeMillis()+".html";
            item.setPathFile(path);
            DownloadAsync async = new DownloadAsync(this);
            async.execute(link,path);

            AppDatabase.getInstance(this).getNewsDao().insert(item);

            setResult(RESULT_OK);
            checkExit = false;

        }
        else {
            Toast.makeText(this,"Tin tức đã được lưu từ trước",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void downloadFinish(String path) {
        Toast.makeText(this,"Lưu tin tức thành công",Toast.LENGTH_LONG).show();
        DialogUtils.dismiss();
    }


//permission
//    private final String[] PERMISSION_LIST = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE
//    };
//
//    private boolean checkPermission(){
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
//            return  true;
//        }
//        for (String p : PERMISSION_LIST){
//            int accept = checkSelfPermission(p);
//            if(accept == PackageManager.PERMISSION_DENIED){
//                return false;
//            }
//        }
//        return true;
//    }
}
