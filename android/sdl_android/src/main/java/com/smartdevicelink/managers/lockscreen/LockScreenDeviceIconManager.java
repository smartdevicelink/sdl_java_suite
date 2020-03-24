package com.smartdevicelink.managers.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smartdevicelink.proxy.rpc.DateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class LockScreenDeviceIconManager {

    private Context context;

    LockScreenDeviceIconManager(Context context) {
        this.context = context;
    }

    boolean updateCachedImage(String iconIUrl) {
        SharedPreferences sharedPref = this.context.getSharedPreferences("sdl", Context.MODE_PRIVATE);
        String iconParameters = sharedPref.getString(iconIUrl, null);
        if(iconParameters == null) {
            return true;
        } else {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(iconParameters);
                long lastUpdatedTime = 0;
                lastUpdatedTime = (long) jsonObject.get("lastUpdatedTime");
                long currentTime = System.currentTimeMillis();

                long timeDifference = currentTime - lastUpdatedTime;
                long daysBetweenLastUpdate = timeDifference / (1000 * 60 * 60 * 24);

                return daysBetweenLastUpdate >= 30;

            } catch (JSONException e) {
                e.printStackTrace();
                return true;
            }
        }
    }

    void saveFileToCache(Bitmap icon, String iconUrl) {
        File f = new File(this.context.getCacheDir(), iconUrl);
        try {
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = null;
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            JSONObject iconParams;

            iconParams = buildDeviceIconParameters(f.getAbsolutePath());
            writeDeviceIconParametersToSystemPreferences(iconUrl, iconParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Bitmap getFileFromCache(String iconUrl) {
        SharedPreferences sharedPref = this.context.getSharedPreferences("sdl", Context.MODE_PRIVATE);
        String iconParameters = sharedPref.getString(iconUrl, null);

        if (iconParameters != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("storedUrl");
                String storedUrl = jsonObject.getString("storedUrl");
                return BitmapFactory.decodeFile(storedUrl);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private void writeDeviceIconParametersToSystemPreferences(String iconUrl, JSONObject jsonObject) throws JSONException {
        SharedPreferences sharedPref = this.context.getSharedPreferences("sdl", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(jsonObject.getString(iconUrl), jsonObject.toString());
        editor.commit();
    }

    private JSONObject buildDeviceIconParameters(String storedUrl) throws JSONException {
        JSONObject parametersJson = new JSONObject();
        parametersJson.put("storedUrl", storedUrl);
        parametersJson.put("lastUpdatedTime", System.currentTimeMillis());
        return parametersJson;
    }
}
