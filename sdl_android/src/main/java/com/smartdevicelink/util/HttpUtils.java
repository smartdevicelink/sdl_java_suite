package com.smartdevicelink.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpUtils{

    public static Bitmap downloadImage(String urlStr) throws IOException{
        URL url = new URL(urlStr);
        URLConnection connection = url.openConnection();
        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
        Bitmap result = BitmapFactory.decodeStream(bis);
        bis.close();
        return result;
    }

}
