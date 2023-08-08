package com.sw.beauty.util;

import android.os.Environment;

import com.sw.beauty.SApp;
import com.sw.beauty.bean.Model;

import java.io.File;

public class FileUtils {

    public static String getFilePath(String modelName) {
        File sdCard = SApp.s.getExternalFilesDir(null);
        String subDir = sdCard + "/" + "3dmodel/";
        if (!isDirectoryExists(subDir)) {
            createDirectory(subDir);
        }
        return subDir + modelName;
    }

    public static boolean isDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.exists() && directory.isDirectory();
    }

    public static boolean createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.mkdirs();
    }

    public static String getModelFileNameById(String id) {
        return id + ".model";
    }

    public static String getModelFile(Model model) {
        return getFilePath(getModelFileNameById(model.getId() + "-" + model.getName()));
    }

}
