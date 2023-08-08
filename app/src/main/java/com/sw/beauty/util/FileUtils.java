package com.sw.beauty.util;

import android.os.Environment;

import androidx.annotation.NonNull;

import com.sw.beauty.SApp;
import com.sw.beauty.bean.Model;
import com.sw.beauty.my.ModelManItem;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class FileUtils {

    public static String getFilePath(String modelName) {
        return getSubDir() + modelName;
    }

    @NonNull
    private static String getSubDir() {
        File sdCard = SApp.s.getExternalFilesDir(null);
        String subDir = sdCard + "/" + "3dmodel/";
        if (!isDirectoryExists(subDir)) {
            createDirectory(subDir);
        }
        return subDir;
    }

    public static boolean isDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.exists() && directory.isDirectory();
    }

    public static boolean createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.mkdirs();
    }

    public static String getModelFileNameById(String idAndName) {
        return idAndName + ".model";
    }

    public static String getModelFile(Model model) {
        return getFilePath(getModelFileNameById(model.getId() + "_" + model.getName()));
    }

    public static ArrayList<ModelManItem> getAllModelItems() {
        ArrayList<ModelManItem> items = new ArrayList<>();
        File f = new File(getSubDir());
        File[] files = f.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File ff = files[i];
                String name = ff.getName();
                if (name.endsWith(".model")) {
                    ModelManItem item = new ModelManItem();
                    item.setFileName(name);
                    String ss = name.replace(".model", "").split("_")[1];
                    item.setName(ss);
                    item.setFileSize(getFormattedFileSize(ff.length()));
                    items.add(item);
                }
            }
        }
        return items;
    }

    public static String getFormattedFileSize(long size) {
        if (size <= 0) {
            return "0 KB";
        }

        final String[] units = {"KB", "MB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.#");
        return decimalFormat.format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups - 1];
    }

    public static String getFilePath(ModelManItem item) {
        if (item != null) {
            return getFilePath(item.getFileName());
        }
        return "";
    }
}
