package com.t3h.documentmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.t3h.documentmanager.databinding.ActivityMainBinding;
import com.t3h.documentmanager.dialog.LoadingDialog;

import java.net.Authenticator;

public class MainActivity extends AppCompatActivity implements ViewListener {

    public static final String EXTRA_TYPE_FILE = "extra_type_file";
    private ActivityMainBinding binding;
    private FileManager fileManager = new FileManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initAct();
    }

    private void initAct() {
        if (checkPermission() == false){
            return;
        }
        setCountFile();
        binding.setListener(this);
    }

    private void setCountFile() {
        binding.tvAllDoc.setText("ALL DOC ("+fileManager.getAllFile().size()+")");
        binding.tvDoc.setText("ALL DOC ("+fileManager.getDOCFile().size()+")");
        binding.tvPdf.setText("ALL PDF ("+fileManager.getPDFFile().size()+")");
        binding.tvPpt.setText("ALL PPT ("+fileManager.getPPTFile().size()+")");
        binding.tvTxt.setText("ALL TXT ("+fileManager.getTXTFile().size()+")");
        binding.tvXls.setText("ALL XLS ("+fileManager.getCSVFile().size()+")");
    }


    private final String[] PERMISSION ={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for (String p : PERMISSION){
                if(checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

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
    public void onClickALLDOC() {
        Intent intent = new Intent(this,DocumentActivity.class);
        intent.putExtra(EXTRA_TYPE_FILE,1);
        LoadingDialog.show(this);
        startActivity(intent);

    }

    @Override
    public void onClickPDF() {
        Intent intent = new Intent(this,DocumentActivity.class);
        intent.putExtra(EXTRA_TYPE_FILE,2);
        LoadingDialog.show(this);
        startActivity(intent);
    }

    @Override
    public void onClickDOC() {
        Intent intent = new Intent(this,DocumentActivity.class);
        intent.putExtra(EXTRA_TYPE_FILE,3);
        LoadingDialog.show(this);
        startActivity(intent);

    }

    @Override
    public void onClickTXT() {
        Intent intent = new Intent(this,DocumentActivity.class);
        intent.putExtra(EXTRA_TYPE_FILE,4);
        LoadingDialog.show(this);
        startActivity(intent);

    }

    @Override
    public void onClickXLS() {
        Intent intent = new Intent(this,DocumentActivity.class);
        intent.putExtra(EXTRA_TYPE_FILE,5);
        LoadingDialog.show(this);
        startActivity(intent);

    }

    @Override
    public void onClickPPT() {
        Intent intent = new Intent(this,DocumentActivity.class);
        intent.putExtra(EXTRA_TYPE_FILE,6);
        LoadingDialog.show(this);
        startActivity(intent);

    }

}
