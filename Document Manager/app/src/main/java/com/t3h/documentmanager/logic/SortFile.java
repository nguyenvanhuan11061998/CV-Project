package com.t3h.documentmanager.logic;

import com.t3h.documentmanager.model.ItemFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SortFile {

    public ArrayList<ItemFile> sort1(ArrayList<ItemFile> arr){
        ArrayList<String> arrNameFile = new ArrayList<>();
        for (ItemFile f: arr) {
            arrNameFile.add(f.getName());
        }

        Collections.sort(arrNameFile);

        ArrayList<ItemFile> newArrFile = new ArrayList<>();
        for (String nameFile :arrNameFile) {
            for (ItemFile f: arr) {
                if (nameFile == f.getName()){
                    newArrFile.add(f);
                }
            }
        }
        return newArrFile;
    }


    public ArrayList<ItemFile> sort2(ArrayList<ItemFile> arr){
        ArrayList<String> arrNameFile = new ArrayList<>();
        for (ItemFile f: arr) {
            arrNameFile.add(f.getName());
        }

        Collections.sort(arrNameFile);

        ArrayList<ItemFile> newArrFile = new ArrayList<>();
        for (int i = arrNameFile.size()-1;i>0;i--) {
            for (ItemFile f: arr) {
                if (arrNameFile.get(i) == f.getName()){
                    newArrFile.add(f);
                }
            }
        }
        return newArrFile;
    }


    public ArrayList<ItemFile> sort3(ArrayList<ItemFile> arr1) {
        ArrayList<ItemFile> arr = arr1;
        ItemFile itemFile;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        for (int i = 0; i < arr.size()-1; i++) {
            for (int j = 0; j < arr.size()-1-i; j++) {
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = format.parse(arr.get(j).getDate());
                    date2 = format.parse(arr.get(j+1).getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (date1.compareTo(date2) > 0){
                    itemFile = arr.get(j);
                    arr.set(j,arr.get(j+1));
                    arr.set(j+1,itemFile);
                }
            }
        }
        return arr;
    }


    public ArrayList<ItemFile> sort4(ArrayList<ItemFile> arr1) {
        ArrayList<ItemFile> arr = arr1;
        ItemFile itemFile;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        for (int i = 0; i < arr.size()-1; i++) {
            for (int j = 0; j < arr.size()-1-i; j++) {
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = format.parse(arr.get(j).getDate());
                    date2 = format.parse(arr.get(j+1).getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (date1.compareTo(date2) < 0){
                    itemFile = arr.get(j);
                    arr.set(j,arr.get(j+1));
                    arr.set(j+1,itemFile);
                }
            }
        }
        return arr;
    }

    public ArrayList<ItemFile> sort5(ArrayList<ItemFile> arr) {
        ItemFile file;
        for (int i = 0; i < arr.size()-1; i++) {
            for (int j = 0; j < arr.size()-1-i; j++) {
                int index1 = arr.get(j).getSize().indexOf("Kb");
                int index2 = arr.get(j+1).getSize().indexOf("Kb");
                String size1 = arr.get(j).getSize().substring(0,index1);
                String size2 = arr.get(j+1).getSize().substring(0,index2);

                int sizeFile1 = Integer.parseInt(size1);
                int sizeFile2 = Integer.parseInt(size2);

                if (sizeFile1 > sizeFile2){
                    file = arr.get(j);
                    arr.set(j,arr.get(j+1));
                    arr.set(j+1,file);
                }
            }
        }
        return arr;
    }

    public ArrayList<ItemFile> sort6(ArrayList<ItemFile> arr) {
        ItemFile file;
        for (int i = 0; i < arr.size()-1; i++) {
            for (int j = 0; j < arr.size()-1-i; j++) {
                int index1 = arr.get(j).getSize().indexOf("Kb");
                int index2 = arr.get(j+1).getSize().indexOf("Kb");
                String size1 = arr.get(j).getSize().substring(0,index1);
                String size2 = arr.get(j+1).getSize().substring(0,index2);

                int sizeFile1 = Integer.parseInt(size1);
                int sizeFile2 = Integer.parseInt(size2);

                if (sizeFile1 < sizeFile2){
                    file = arr.get(j);
                    arr.set(j,arr.get(j+1));
                    arr.set(j+1,file);
                }
            }
        }
        return arr;
    }

}
