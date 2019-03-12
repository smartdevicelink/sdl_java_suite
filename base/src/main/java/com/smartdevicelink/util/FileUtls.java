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


    /**
     *
     * @param file should include path and name
     * @return
     */
    public static boolean doesFileExist(String file){
        try{
            return false;//Files.exists(new Path(file), (LinkOption)null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static byte[] getFileData(String path){
        byte[] data = null;
        if(path != null && path.length() > 0) {
            if (doesFileExist(path)) {
                try {
                   return Files.readAllBytes(new File("/path/to/file").toPath());
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
