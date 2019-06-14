package com.example.musicplayer1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.musicplayer1.R;
import com.example.musicplayer1.api.ApiBuilder;
import com.example.musicplayer1.api.response.MusicResponse;
import com.example.musicplayer1.base.BaseAdapter;
import com.example.musicplayer1.base.BaseFragment;
import com.example.musicplayer1.databinding.FragmentOnlineBinding;
import com.example.musicplayer1.models.Music;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineFragment extends BaseFragment<FragmentOnlineBinding> implements Callback<MusicResponse> ,MediaListener<Music>{
    private BaseAdapter<Music> adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_online;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new BaseAdapter<>(getContext(),R.layout.item_music);
        binding.lvOnline.setAdapter(adapter);
        adapter.setListener(this);

        ApiBuilder.getApi(getContext()).getMusic().enqueue(this);

    }

    @Override
    public int getTitle() {
        return R.string.online;
    }


//khi gọi call back sẽ gọi hai phương thức này

    //Response thành công
    @Override
    public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
        adapter.setData(response.body().getArr());                                                  //
    }

    @Override
    public void onFailure(Call<MusicResponse> call, Throwable t) {
        Toast.makeText(getContext(),"không thể truy cập thông tin bài hát",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemMediaClick(Music music) {
        app.getService().setArrMusic(adapter.getData());
        int index = adapter.getData().indexOf(music);
        app.getService().create(index);
    }
}
