package com.smartdevicelink.managers.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.smartdevicelink.util.DebugTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class LockScreenDeviceIconManager {

    private Context context;
    private static final String SDL_DEVICE_STATUS_SHARED_PREFS = "sdl.lockScreenIcon";
    private static final String STORED_ICON_PATH = "sdl/lock_screen_icon/";
    private static final String LAST_UPDATED_TIME = "lastUpdatedTime";
    private static final String STORED_URL = "storedUrl";
    private static final String TAG = "LockScreenManager";


    LockScreenDeviceIconManager(Context context) {
        this.context = context;
        File lockScreenDirectory = new File(context.getCacheDir(), STORED_ICON_PATH);
        lockScreenDirectory.mkdirs();
    }

    boolean updateCachedImage(String iconUrl) {
        String iconHash = getMD5HashFromIconUrl(iconUrl);
        SharedPreferences sharedPref = this.context.getSharedPreferences(SDL_DEVICE_STATUS_SHARED_PREFS, Context.MODE_PRIVATE);
        String iconParameters = sharedPref.getString(iconHash, null);
        if(iconParameters == null) {
            DebugTool.logInfo("No Icon Details Found In Shared Preferences");
            return true;
        } else {
            DebugTool.logInfo("Icon Details Found");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(iconParameters);
                long lastUpdatedTime = 0;
                lastUpdatedTime = (long) jsonObject.get(LAST_UPDATED_TIME);
                long currentTime = System.currentTimeMillis();

                long timeDifference = currentTime - lastUpdatedTime;
                long daysBetweenLastUpdate = timeDifference / (1000 * 60 * 60 * 24);
                return daysBetweenLastUpdate >= 30;
            } catch (JSONException e) {
                e.printStackTrace();
                DebugTool.logError("Exception Trying to read shared preferences");
                return true;
            }
        }
    }

    void saveFileToCache(Bitmap icon, String iconUrl) {

        String iconHash = getMD5HashFromIconUrl(iconUrl);

        File f = new File(this.context.getCacheDir() + "/" + STORED_ICON_PATH, iconHash);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapData = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            DebugTool.logError("Failed to save icon to cache");
            e.printStackTrace();
            return;
        }

        JSONObject iconParams;
        try {
            iconParams = buildDeviceIconParameters(f.getAbsolutePath());
            writeDeviceIconParametersToSystemPreferences(iconHash, iconParams);
        } catch (JSONException e) {
            DebugTool.logError("Failed to save to shared preferences, clearing cache icon directory");
            clearIconDirectory();
            e.printStackTrace();
        }
    }

    Bitmap getFileFromCache(String iconUrl) {
        String iconHash = getMD5HashFromIconUrl(iconUrl);
        SharedPreferences sharedPref = this.context.getSharedPreferences(SDL_DEVICE_STATUS_SHARED_PREFS, Context.MODE_PRIVATE);
        String iconParameters = sharedPref.getString(iconHash, null);

        if (iconParameters != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(iconParameters);
                String storedUrl = jsonObject.getString(STORED_URL);
                Bitmap cachedIcon = BitmapFactory.decodeFile(storedUrl);
                if(cachedIcon == null) {
                    DebugTool.logError("Failed to get Bitmap from decoding file cache");
                    clearIconDirectory();
                    return null;
                } else {
                    return cachedIcon;
                }
            } catch (JSONException e) {
                DebugTool.logError("Failed to get file from cache, removing shared pref");
                sharedPref.edit().remove(iconHash).commit();
                e.printStackTrace();
                return null;
            }
        } else {
            DebugTool.logError("Failed to get system preferences");
            return null;
        }
    }

    private void writeDeviceIconParametersToSystemPreferences(String iconHash, JSONObject jsonObject) {
        SharedPreferences sharedPref = this.context.getSharedPreferences(SDL_DEVICE_STATUS_SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(iconHash, jsonObject.toString());
        editor.commit();
    }

    private JSONObject buildDeviceIconParameters(String storedUrl) throws JSONException {
        JSONObject parametersJson = new JSONObject();
        parametersJson.put(STORED_URL, storedUrl);
        parametersJson.put(LAST_UPDATED_TIME, System.currentTimeMillis());
        return parametersJson;
    }

    private String getMD5HashFromIconUrl(String iconUrl) {
        String iconHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(iconUrl.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            iconHash = hashtext;
        } catch (NoSuchAlgorithmException e) {
            DebugTool.logError("Unable to hash icon url");
            e.printStackTrace();
        }
        return iconHash;
    }

    private void clearIconDirectory() {
        File iconDir = new File(context.getCacheDir() + "/" + STORED_ICON_PATH);
        for (File child : iconDir.listFiles()) {
            child.delete();
        }
    }
}
