package com.smartdevicelink.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

public class HttpUtils{

    public static Bitmap downloadImage(String urlStr) throws IOException{
        URL url = new URL(urlStr);
        URLConnection connection = url.openConnection();
        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
        Bitmap result = BitmapFactory.decodeStream(bis);
        bis.close();
        return result;
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
