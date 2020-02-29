package com.t3h.documentmanager;

import android.os.Environment;

import com.t3h.documentmanager.model.ItemFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FileManager {

    public FileManager() {
        File rootFile = new File(getPath());
        getFile(rootFile);
    }

    private String rootPath = Environment.getExternalStorageDirectory().getPath();

    public String getPath(){
        File f = new File(rootPath);
        String path = f.getParentFile().getPath();
        return path;
    }
    private ArrayList<File> arrFile = new ArrayList<>();

    private void getFile(File f){
        if (f.isDirectory()){
            File listFileCurrent[] = f.listFiles();
            for (int i = 0; i < listFileCurrent.length; i++) {
                getFile(listFileCurrent[i]);
            }
        }else {
            String fileName = f.getName();
            if (fileName.contains(".docx")||fileName.contains(".txt")||
                    fileName.contains(".pdf")||fileName.contains("csv")||fileName.contains(".ppt")){
                arrFile.add(f);
            }
        }
    }

    public ArrayList<File> getAllFile() {
        return arrFile;
    }

    public ArrayList<File> getPDFFile(){
        ArrayList<File> arrPDFFile = new ArrayList<>();
        for (File f: arrFile) {
            if (f.getName().contains(".pdf")){
                arrPDFFile.add(f);
            }
        }
        return arrPDFFile;
    }

    public ArrayList<File> getTXTFile(){
        ArrayList<File> arrTXTFile = new ArrayList<>();
        for (File f: arrFile) {
            if (f.getName().contains(".txt")){
                arrTXTFile.add(f);
            }
        }
        return arrTXTFile;
    }

    public ArrayList<File> getDOCFile(){
        ArrayList<File> arrDOCFile = new ArrayList<>();
        for (File f: arrFile) {
            if (f.getName().contains(".docx")){
                arrDOCFile.add(f);
            }
        }
        return arrDOCFile;
    }

    public ArrayList<File> getCSVFile(){
        ArrayList<File> arrCSVFile = new ArrayList<>();
        for (File f: arrFile) {
            if (f.getName().contains(".csv")){
                arrCSVFile.add(f);
            }
        }
        return arrCSVFile;
    }

    public ArrayList<File> getPPTFile(){
        ArrayList<File> arrPPTFile = new ArrayList<>();
        for (File f: arrFile) {
            if (f.getName().contains(".ppt")){
                arrPPTFile.add(f);
            }
        }
        return arrPPTFile;
    }

    public ArrayList<ItemFile> getItemFile(ArrayList<File> arr){
        ArrayList<ItemFile> arrItem = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            ItemFile itemFile = new ItemFile();
            itemFile.setName(arr.get(i).getName());
            itemFile.setSize(arr.get(i).length()/1024+"Kb");
            itemFile.setPath(arr.get(i).getPath());
            itemFile.setDate(getDateFile(arr.get(i).lastModified()));
            itemFile.setImage(typeFile(arr.get(i)));

            arrItem.add(itemFile);
        }
        return arrItem;
    }

    private String getDateFile(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String date = format.format(new Date(time));
        return date;
    }

    private String typeFile(File f){
        if (f.getName().contains(".txt")){
            return "txt";
        }else if (f.getName().contains(".ppt")){
            return "ppt";
        }else if (f.getName().contains(".pdf")){
            return "pdf";
        }else if (f.getName().contains(".csv")){
            return "csv";
        }else if (f.getName().contains(".docx")){
            return "doc";
        }
        return null;
    }



}
