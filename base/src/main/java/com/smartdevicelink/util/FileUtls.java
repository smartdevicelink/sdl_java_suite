package com.smartdevicelink.util;

import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public class FileUtls {



    public static byte[] getFileData(String file){
        return getFileData(file,null);
    }

    public static byte[] getFileData(String filePath, String fileName){
        if(filePath != null && filePath.length() > 0) {
            File file;
            if(fileName != null && fileName.length() > 0 ){
                file = new File(filePath, fileName);
            }else{
                file = new File(filePath);
            }
            if (file.exists() && file.isFile() && file.canRead()) {
                try {
                   return Files.readAllBytes(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static byte[] downloadFile(@NonNull String urlStr){
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[4096];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
        }catch (Exception e){
            DebugTool.logError("Unable to download file - " + urlStr, e);
            return null;
        }
    }
}
