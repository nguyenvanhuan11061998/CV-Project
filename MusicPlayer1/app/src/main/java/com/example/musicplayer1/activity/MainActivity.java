package com.example.musicplayer1.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;


import com.example.musicplayer1.App;
import com.example.musicplayer1.adapter.PagerAdapter;
import com.example.musicplayer1.base.BaseActivity;
import com.example.musicplayer1.fragment.AlbumFragment;
import com.example.musicplayer1.fragment.ArtistFragment;
import com.example.musicplayer1.fragment.MusicFragment;
import com.example.musicplayer1.R;
import com.example.musicplayer1.databinding.ActivityMainBinding;
import com.example.musicplayer1.fragment.OnlineFragment;
import com.example.musicplayer1.service.Mp3Service;

public class MainActivity extends BaseActivity<ActivityMainBinding>
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private MusicFragment fmMusic = new MusicFragment();
    private ArtistFragment fmArist = new ArtistFragment();
    private AlbumFragment fmAlbum = new AlbumFragment();
    private OnlineFragment fmOnline = new OnlineFragment();
    private PagerAdapter adapter;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Mp3Service.MP3Binder binder = (Mp3Service.MP3Binder) service;                                       //đưa service vào application context
            App app = (App)  getApplicationContext();
            app.setService(binder.getService());
            binding.playView.registerState();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private final String[] PERMISSION ={
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission() == true){
            initAct();
        }else {
            finish();
        }
    }

    @Override
    protected void initAct() {
        if (checkPermission() == false){
            return;
        }

        Intent intent = new Intent(this,Mp3Service.class);
        bindService(intent,connection,BIND_AUTO_CREATE);

        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        App app = (App) getApplicationContext();
        if (app.getUser() == null){
            adapter = new PagerAdapter(this,getSupportFragmentManager(),fmMusic,fmAlbum,fmArist);
        }else {
            adapter = new PagerAdapter(this,getSupportFragmentManager(),fmMusic,fmAlbum,fmArist,fmOnline);
        }

        binding.pager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.pager);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }



    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_music:
                binding.pager.setCurrentItem(0);
                break;
            case R.id.nav_album:
                binding.pager.setCurrentItem(1);
                break;
            case R.id.nav_artist:
                binding.pager.setCurrentItem(2);
                break;
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


//Xin quyền đọc dữ liệu
    private boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for (String p: PERMISSION){
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(PERMISSION,0);
                    return false;
                }
            }
        }
        return true;
    }
//SearchView:

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        fmMusic.chageMusic(s);
        return false;
    }
}
