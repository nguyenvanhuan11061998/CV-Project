package com.example.newminiproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newminiproject.Adapter.PagerAdapter;
import com.example.newminiproject.AsyncTask.XMLAsync;
import com.example.newminiproject.Fragment.FragmentDaLuu;
import com.example.newminiproject.Fragment.FragmentTinTuc;
import com.example.newminiproject.Fragment.FragmentYeuThich;
import com.example.newminiproject.Model.News;
import com.example.newminiproject.Utils.DialogUtils;

import java.util.List;

import javax.xml.parsers.SAXParser;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, XMLAsync.ParserXMLCallback {
    public static final int REQUEST_MAIN = 2;
    public static final int REQUEST_REGISTER = 1;
    private FragmentDaLuu fmDaLuu = new FragmentDaLuu();
    private FragmentTinTuc fmTinTuc = new FragmentTinTuc();
    private FragmentYeuThich fmYeuThich = new FragmentYeuThich();

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabbTitle;

    public FragmentDaLuu getFmDaLuu() {
        return fmDaLuu;
    }

    public FragmentTinTuc getFmTinTuc() {
        return fmTinTuc;
    }

    public FragmentYeuThich getFmYeuThich() {
        return fmYeuThich;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
    }

    private void initFragment() {
        pager = findViewById(R.id.pager);
        tabbTitle = findViewById(R.id.tab_table);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),fmTinTuc,fmDaLuu,fmYeuThich);
        pager.setAdapter(pagerAdapter);
        tabbTitle.setupWithViewPager(pager);
        pager.setCurrentItem(0);

    }

//Search View


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.search_menu).getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if(s.isEmpty()){
            return false;
        }
        DialogUtils.show(this);
        XMLAsync async = new XMLAsync(this);
        async.execute(s);
        return false;
    }

    @Override
    public void onParserFinish(List<News> arr) {
        DialogUtils.dismiss();
        fmTinTuc.setArrNews(arr);
        resertAdapter(0);

    }

    public void resertAdapter(int i){
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(i);
    }

    @Override
    public boolean onQueryTextChange(String s)   {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK){
                resertAdapter(0);
            }
    }
}
