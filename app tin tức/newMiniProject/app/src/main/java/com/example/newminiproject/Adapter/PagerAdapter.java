package com.example.newminiproject.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.newminiproject.Fragment.BaseFragment;

public class PagerAdapter extends FragmentPagerAdapter {                                                                    //quản lý hiển thị các Fragment lên màn hình
    private BaseFragment[] arrFragment;                                                                                     //Một mảng Fragment để xử lý

    public PagerAdapter(FragmentManager fm,BaseFragment...arrFragments) {                                                   //Truyền vào một mảng Fragment
        super(fm);
        this.arrFragment = arrFragments;
    }

    @Override
    public Fragment getItem(int i) {                                                                                        //lấy ra từng Fragment tại vị trí i
        return arrFragment[i];
    }

    @Override
    public int getCount() {                                                                                                 //đưa ra số lượng Fragment của mảng Fragment
        return arrFragment.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {                                                                        //lấy ra title của các Fragmnet tương ứng
        return arrFragment[position].getTitle();
    }
}

