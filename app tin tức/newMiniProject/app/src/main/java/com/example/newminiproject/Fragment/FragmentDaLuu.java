package com.example.newminiproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newminiproject.Adapter.NewsAdapter;
import com.example.newminiproject.Database.AppDatabase;
import com.example.newminiproject.Database.AppDatabaseFavorite;
import com.example.newminiproject.Model.News;
import com.example.newminiproject.R;
import com.example.newminiproject.Utils.DiaLogUtil_Delete;
import com.example.newminiproject.Utils.DialogUtils;
import com.example.newminiproject.WebActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentDaLuu extends BaseFragment implements NewsAdapter.NewsItemListener, DiaLogUtil_Delete.calbackDialogDelete {
    public static final String EXTRA_PATHFILEDL = "extra_pathFileDaLuu";
    public static final int REQUEST_FM_DALUU = 4;
    private RecyclerView lvNews;
    private NewsAdapter adapter;
    private List<News> arrNews;
    private TextView tvHienThi2;

    private PopupMenu popupMenu;
    private DiaLogUtil_Delete delete;

    int indexNew;

    @Override
    public String getTitle() {
        return "Đã Lưu";
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_daluu;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        initViewDaLuu();
    }

    private void initViewDaLuu() {
        tvHienThi2 = findViewById(R.id.tvHienThi2);
        arrNews = new ArrayList<>();
        lvNews = findViewById(R.id.lv_News_DaLuu);
        adapter = new NewsAdapter(getContext());
        lvNews.setAdapter(adapter);
        arrNews = AppDatabase.getInstance(getContext()).getNewsDao().getAll();
        adapter.setData(arrNews);

        adapter.setItemListener(this);
        if (arrNews.size()>0){
            tvHienThi2.setText("");
        }

        delete = new DiaLogUtil_Delete(getContext());
        delete.setCalback(this);
    }

    @Override
    public void onclickItem(int i) {

        DialogUtils.show(getContext());
        String link = "file://"+arrNews.get(i).getPathFile();
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra(EXTRA_PATHFILEDL,link);
        startActivityForResult(intent,REQUEST_FM_DALUU);
    }

    @Override
    public void onLongClickItem(View v, final int i) {
        popupMenu = new PopupMenu(getContext(),v);
        popupMenu.inflate(R.menu.popup_menu1);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                List<News> arrNewsYT = AppDatabaseFavorite.getInstance(getContext()).getNewsDao().getAll();
                String title = arrNews.get(i).getTitle();

                switch (menuItem.getItemId()){
                    case R.id.item_Xoa:
                        indexNew = i;
                        delete.show();
                        break;
                    case R.id.item_YeuThich:
                        boolean check = true;
                        for (News item : arrNewsYT) {
                            if (item.getTitle().equals(title)){
                               check = false;
                            }
                        }
                        if (check == false){
                            Toast.makeText(getContext(),"Tin tức này đã thêm vào mục yêu thích trước đó",Toast.LENGTH_LONG).show();
                        }else {
                            AppDatabaseFavorite.getInstance(getContext()).getNewsDao().insert(arrNews.get(i));
                            Toast.makeText(getContext(), "Thêm vào mục yêu thích thành công", Toast.LENGTH_LONG).show();
                            getParent().resertAdapter(1);
                        }
                    }
                return false;
            }
        });
    }

    @Override
    public void onclickDelete() {
        List<News> arrNewsYT = AppDatabaseFavorite.getInstance(getContext()).getNewsDao().getAll();
        String title = arrNews.get(indexNew).getTitle();

        AppDatabase.getInstance(getContext()).getNewsDao().delete(arrNews.get(indexNew));
        Toast.makeText(getContext(),"Tin tức đã được xóa",Toast.LENGTH_LONG).show();
        for (News item : arrNewsYT) {
            if (item.getTitle().equals(title)){
                AppDatabaseFavorite.getInstance(getContext()).getNewsDao().delete(item);
            }
        }

        getParent().resertAdapter(1);
    }
}
