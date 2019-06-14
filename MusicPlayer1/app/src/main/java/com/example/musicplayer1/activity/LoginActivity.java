package com.example.musicplayer1.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.musicplayer1.App;
import com.example.musicplayer1.R;
import com.example.musicplayer1.api.ApiBuilder;
import com.example.musicplayer1.api.response.UserResponse;
import com.example.musicplayer1.base.BaseActivity;
import com.example.musicplayer1.databinding.ActivityLoginBinding;
import com.example.musicplayer1.view.MP3ProgessDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements LoginListener, Callback<UserResponse> {
    private MP3ProgessDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initAct() {
        binding.setListener(this);
        dialog = new MP3ProgessDialog(this);
    }


    @Override
    public void onLogin() {
        String userName = binding.edtUserName.getText().toString();
        String password = binding.edtPassword.getText().toString();
        if (userName.isEmpty() || password.isEmpty()){
            Toast.makeText(this,R.string.input_empty, Toast.LENGTH_LONG).show();
            return;
        }

        dialog.show();
        ApiBuilder.getApi(this).login(userName,password).enqueue(this);
    }

    @Override
    public void onGuest() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

        dialog.dismiss();

        if (response.code() == 200){
            UserResponse user = response.body();
            App app = (App) getApplicationContext();
            app.setUser(user);
            onGuest();
        }else {
            Toast.makeText(this,response.message(),Toast.LENGTH_LONG).show();
        }

        Log.e(getClass().getName(),"================= "+response.message());

    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        Toast.makeText(this,t.getMessage(),Toast.LENGTH_LONG).show();

//        Log.e(getClass().getName(),"================= "+t.getMessage());
        dialog.dismiss();
    }
}
