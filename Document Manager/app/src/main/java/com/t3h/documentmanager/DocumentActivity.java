package com.t3h.documentmanager;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.t3h.documentmanager.databinding.ActivityDocumentBinding;
import com.t3h.documentmanager.dialog.Dialog_SortFile;
import com.t3h.documentmanager.dialog.Dialog_UiDelete;
import com.t3h.documentmanager.dialog.LoadingDialog;
import com.t3h.documentmanager.logic.SortFile;
import com.t3h.documentmanager.model.ItemFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class DocumentActivity extends AppCompatActivity implements FileAdapter.ItemClickListener, ClickCheckedListener, SearchView.OnQueryTextListener,ViewDocumentActListener, Dialog_UiDelete.calbackDialog, Dialog_SortFile.calbackSortFile {

    private FileManager fileManager = new FileManager();
    private ActivityDocumentBinding binding;
    private FileAdapter adapter;
    private ArrayList<ItemFile> arrayFile;
    private TextView tv_TotalDoc;
    private Dialog_UiDelete delete;
    private Dialog_SortFile sortFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_document);
        checkTypeFile();
        initAct();
    }

// ========== Khởi tạo act và đưa file theo loại vào adapter ========================================

    private void checkTypeFile() {
        Intent intent = getIntent();
        int indexCheck = intent.getIntExtra(MainActivity.EXTRA_TYPE_FILE,0);
        switch (indexCheck){
            case 1:
                arrayFile = fileManager.getItemFile(fileManager.getAllFile());
                break;
            case 2:
                arrayFile = fileManager.getItemFile(fileManager.getPDFFile());
                break;
            case 3:
                arrayFile = fileManager.getItemFile(fileManager.getDOCFile());
                break;
            case 4:
                arrayFile = fileManager.getItemFile(fileManager.getTXTFile());
                break;
            case 5:
                arrayFile = fileManager.getItemFile(fileManager.getCSVFile());
                break;
            case 6:
                arrayFile = fileManager.getItemFile(fileManager.getPPTFile());
                break;
        }
    }

    private void initAct() {
        tv_TotalDoc = findViewById(R.id.tv_Total_doc);

        adapter = new FileAdapter(this);
        binding.lvFile.setAdapter(adapter);
        adapter.setData(arrayFile);
        adapter.setListener(this);
        binding.setListener(this);

        tv_TotalDoc.setText(getTotalDoc());

        delete = new Dialog_UiDelete(this);
        delete.setCalback(this);

        sortFile = new Dialog_SortFile(this);
        sortFile.setCalback(this);

        LoadingDialog.dismiss();
    }

//======================== Xử lý click vào item và checked==========================================================================


    @Override
    public void onClickItem(ItemFile itemFile) {
        File file = new File(itemFile.getPath());
        FileOpen fileOpen = new FileOpen();
        try {
            fileOpen.openFile(this,file);
    } catch (IOException e) {
        e.printStackTrace();
    }

    }

    @Override
    public boolean onLongClickItem(ItemFile file) {
        for (ItemFile f:adapter.getData()){
            f.setDisplay(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public void clickCheck(ItemFile file) {
        int index = arrayFile.indexOf(file);
        boolean checked = arrayFile.get(index).getChecked();
        if (checked){
            arrayFile.get(index).setChecked(false);
        }else {
            arrayFile.get(index).setChecked(true);
        }
        adapter.notifyDataSetChanged();
        tv_TotalDoc.setText(getTotalDoc());
    }


//========================== Xử lý các item menu trên AppBar========================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.item_search_View).getActionView();
        searchView.setOnQueryTextListener(this);

        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_setting:
                break;
            case R.id.option_refresh:
                initAct();
                break;
            case R.id.option_share:
                onShare();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    private void search(String s){
        ArrayList<ItemFile> newArr = new ArrayList<>();
        for (ItemFile f: arrayFile) {
            if (f.getName().contains(s)){
                newArr.add(f);
            }
        }
        adapter.setData(newArr);
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.isEmpty()) {
            adapter.setData(arrayFile);
            return false;
        }else {
            search(s);
            return true;
        }
    }

//======================= Hiển thị số file đã checked ===========================================================================

    private String getTotalDoc(){
        int countChecked = 0;
        for (ItemFile f: adapter.getData()) {
            if (f.getChecked() == true)
                countChecked ++;
        }

        return countChecked+"/"+adapter.getData().size();
    }

//========================= Xử lý các button dưới màn hình =========================================================================

// Xóa file
    @Override
    public void onDelete() {
        if (countChecked()==0){
            Toast.makeText(this,"Vui lòng chọn File muốn xóa!",Toast.LENGTH_LONG).show();
            return;
        }
        delete.show();
    }

    @Override
    public void onClickDelete() {

        ArrayList<ItemFile> arr = adapter.getData();
        for (int i= arr.size()-1; i>0 ; i--) {
            if (arr.get(i).getChecked() == true){
                File file = new File(arr.get(i).getPath());
                file.delete();
                arr.remove(arr.get(i));
            }
        }
        adapter.setData(arr);
    }

// Chia sẻ file
    @Override
    public void onShare() {

        if (countChecked() == 0){
            Toast.makeText(this,"Vui lòng chọn File muốn chia sẻ !",Toast.LENGTH_LONG).show();
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
    // set the type to 'email'
        shareIntent.setType("vnd.android.cursor.dir/email");
        shareIntent.setType("text/plain");
    // the attachment

        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,arrPathShare());
    // the mail subject
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        startActivity(Intent.createChooser(shareIntent , "Send email..."));
    }

    private ArrayList<Uri> arrPathShare(){
        ArrayList<Uri> arrPath = new ArrayList<>();
        for (ItemFile f: adapter.getData()){
            if (f.getChecked()==true){
                File file = new File(f.getPath());
                Uri path = Uri.fromFile(file);
                arrPath.add(path);
            }
        }
        return arrPath;
    }


//Sắp xếp file
    @Override
    public void onSort() {
        sortFile.show();
    }

    @Override
    public void onClickSortFile(int index) {
        SortFile sortFile = new SortFile();
        ArrayList<ItemFile> newArrFile;
        switch (index){
            case 0:
                newArrFile = sortFile.sort1(adapter.getData());
                adapter.setData(newArrFile);
                break;
            case 1:
                newArrFile = sortFile.sort2(adapter.getData());
                adapter.setData(newArrFile);
                break;
            case 2:
                newArrFile = sortFile.sort3(adapter.getData());
                adapter.setData(newArrFile);
                break;
            case 3:
                newArrFile = sortFile.sort4(adapter.getData());
                adapter.setData(newArrFile);
                break;
            case 4:
                newArrFile = sortFile.sort5(adapter.getData());
                adapter.setData(newArrFile);
                break;
            case 5:
                newArrFile = sortFile.sort6(adapter.getData());
                adapter.setData(newArrFile);
                break;
        }

    }


// Chọn tất cả file
    @Override
    public void onSelectAll() {
        if (adapter.getData().get(0).getChecked() == false) {
            for (ItemFile f : adapter.getData()) {
                f.setChecked(true);
            }
        } else {
            for (ItemFile f : adapter.getData()) {
                f.setChecked(false);
            }
        }
        adapter.notifyDataSetChanged();
    }

//=================Count checked of list file =====================================================
    private int countChecked() {
        int count = 0;
        for (ItemFile f : adapter.getData()) {
            if (f.getChecked()) {
                count++;
            }
        }
        return count;
    }

}

