package com.example.newminiproject.AsyncTask;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadAsync extends AsyncTask<String,Void,String> {

    private downloadCallBack downloadCallBack;

    public DownloadAsync(DownloadAsync.downloadCallBack downloadCallBack) {
        this.downloadCallBack = downloadCallBack;
    }

    @Override
    protected String doInBackground(String... strings) {
        String link = strings[0];
        String path = strings[1];

        try {
            File f = new File(path);
            f.getParentFile().mkdir();
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            InputStream in = connection.getInputStream();
            byte[] b = new byte[1024];
            int count = in.read(b);
            while (count != -1){
                out.write(b,0,count);
                count = in.read(b);
            }
            in.close();
            out.close();
            return f.getPath();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        downloadCallBack.downloadFinish(s);
    }


    public interface downloadCallBack{
        void downloadFinish(String path);
    }
}
