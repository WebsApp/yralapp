package com.webapps.viral.utils;

import android.os.Environment;
import android.os.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Tools {
    public boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
