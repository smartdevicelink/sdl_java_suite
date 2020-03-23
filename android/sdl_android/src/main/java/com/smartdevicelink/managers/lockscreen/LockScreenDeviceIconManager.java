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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class LockScreenDeviceIconManager {

    private Context context;

    LockScreenDeviceIconManager(Context context) {
        this.context = context;
    }

    boolean updateCachedImage(String iconIUrl) throws JSONException {
        SharedPreferences sharedPref = this.context.getSharedPreferences("sdl", Context.MODE_PRIVATE);
        String iconParameters = sharedPref.getString(iconIUrl, null);
        if(iconParameters == null) {
            return true;
        } else {
            JSONObject jsonObject = new JSONObject(iconParameters);
            long lastUpdatedTime = (long) jsonObject.get("lastUpdatedTime");
            long currentTime = System.currentTimeMillis();

            long timeDifference = currentTime - lastUpdatedTime;
            long daysBetweenLastUpdate = timeDifference / (1000 * 60 * 60 * 24);

            return daysBetweenLastUpdate >= 30;
        }
    }

    void saveFileToCache(Bitmap icon, String iconUrl) throws IOException, JSONException {
        File f = new File(this.context.getCacheDir(), iconUrl);
        f.createNewFile();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        JSONObject iconParams = buildDeviceIconParameters(f.getAbsolutePath());
        writeDeviceIconParametersToSystemPreferences(iconUrl, iconParams);
    }

    Bitmap getFileFromCache(String iconUrl) throws JSONException {
        SharedPreferences sharedPref = this.context.getSharedPreferences("sdl", Context.MODE_PRIVATE);
        String iconParameters = sharedPref.getString(iconUrl, null);

        if (iconParameters != null) {
            JSONObject jsonObject = new JSONObject("storedUrl");
            String storedUrl = jsonObject.getString("storedUrl");
            Bitmap bitmap = BitmapFactory.decodeFile(storedUrl);
            return bitmap;
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
