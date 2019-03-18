package com.smartdevicelink.util;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * @see AndroidTools
 */
@Deprecated
public class HttpUtils{

    public static Bitmap downloadImage(String urlStr) throws IOException{
		return AndroidTools.downloadImage(urlStr);
    }

}
