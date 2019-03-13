package com.smartdevicelink.util;

import java.io.IOException;

import android.graphics.Bitmap;

/**
 * @see AndroidTools
 */
@Deprecated
public class HttpUtils{

    public static Bitmap downloadImage(String urlStr) throws IOException{
		return AndroidTools.downloadImage(urlStr);
    }

}
