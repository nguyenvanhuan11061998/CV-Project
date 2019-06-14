package com.example.newminiproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newminiproject.Adapter.NewsAdapter;
import com.example.newminiproject.Database.AppDatabaseFavorite;
import com.example.newminiproject.Model.News;
import com.example.newminiproject.R;
import com.example.newminiproject.Utils.DiaLogUtil_Delete;
import com.example.newminiproject.WebActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentYeuThich extends BaseFragment implements NewsAdapter.NewsItemListener, DiaLogUtil_Delete.calbackDialogDelete {
    public static final String EXTRA_PATHFILE_YT = "path_file_YT";
    public static final int REQUEST_YEUTHICH = 5;
    private List<News> arrNews;
    private TextView tvHienthi3;
    private RecyclerView lvNews;
    private NewsAdapter adapter;

    private PopupMenu popupMenu;
    private DiaLogUtil_Delete delete;
    int indexNews;

    @Override
    public String getTitle() {
        return "Yêu Thích";
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_yeuthich;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewYeuThich();
    }

    private void initViewYeuThich() {
        tvHienthi3 = findViewById(R.id.tvHienThi3);
        arrNews = new ArrayList<>();
        arrNews = AppDatabaseFavorite.getInstance(getContext()).getNewsDao().getAll();
        lvNews = findViewById(R.id.lv_News_YeuThich);
        adapter = new NewsAdapter(getContext());
        lvNews.setAdapter(adapter);

        adapter.setData(arrNews);
        adapter.setItemListener(this);

        if (arrNews.size()>0){
            tvHienthi3.setText("");
        }

        delete = new DiaLogUtil_Delete(getContext());
        delete.setCalback(this);
    }

    @Override
    public void onclickItem(int i) {
        String pathFile = "file://"+ arrNews.get(i).getPathFile();
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra(EXTRA_PATHFILE_YT,pathFile);
        startActivityForResult(intent,REQUEST_YEUTHICH);
    }

    @Override
    public void onLongClickItem(View v, final int i) {
        popupMenu = new PopupMenu(getContext(),v);
        popupMenu.inflate(R.menu.popup_menu2);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.it_xoa){
                    indexNews = i;
                    delete.show();
                }
                return false;
            }
        });
    }

    @Override
    public void onclickDelete() {
        AppDatabaseFavorite.getInstance(getContext()).getNewsDao().delete(arrNews.get(indexNews));
        getParent().resertAdapter(2);
        Toast.makeText(getContext(),"Xóa khỏi mục yêu thích thành công",Toast.LENGTH_LONG).show();
    }
}
