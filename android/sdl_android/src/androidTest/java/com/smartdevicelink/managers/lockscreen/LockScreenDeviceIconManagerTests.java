package com.smartdevicelink.managers.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.util.AndroidTools;

import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LockScreenDeviceIconManagerTests extends AndroidTestCase2 {

    private LockScreenDeviceIconManager lockScreenDeviceIconManager;
    private static final String ICON_URL = "http://i.imgur.com/TgkvOIZ.png";
    private static final String LAST_UPDATED_TIME = "lastUpdatedTime";
    private static final String STORED_URL = "storedUrl";
    private static final String INVALID_JSON_STRING = "Invalid JSON";

    public void setup() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUpdateCacheImageShouldReturnTrueWhenSharedPreferencesDoesNotExist() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(null);

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.updateCachedImage(ICON_URL);
        assertTrue(shouldUpdate);
    }

    public void testUpdateCacheImageShouldReturnTrueWhenUnableToReadSharedPreference() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(INVALID_JSON_STRING);


        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.updateCachedImage(ICON_URL);
        assertTrue(shouldUpdate);
    }

    public void testUpdateCacheImageShouldReturnTrueSharedPreferenceReturnsAnOutdatedIcon() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(buildJSONAsString(35));

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.updateCachedImage(ICON_URL);
        assertTrue(shouldUpdate);
    }

    public void testUpdateCacheImageShouldReturnFalseWhenSharedPreferenceReturnsAnUpdatedIcon() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(buildJSONAsString(15));

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.updateCachedImage(ICON_URL);
        assertFalse(shouldUpdate);
    }

    public void testSaveFileToCacheShouldReturnBeforeWritingSharedPrefsIfSavingToCacheFails() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor sharedPrefsEditor = Mockito.mock(SharedPreferences.Editor.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.edit()).thenReturn(sharedPrefsEditor);

        Bitmap deviceLogo = null;
        try {
            deviceLogo = AndroidTools.downloadImage(ICON_URL);
            lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
            lockScreenDeviceIconManager.saveFileToCache(deviceLogo, ICON_URL);
            verify(sharedPrefs, times(0)).edit();
            verify(sharedPrefsEditor, times(0)).putString(anyString(), anyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO Add Test For Passing saveFileToCache

    public void testGetFileFromCacheShouldReturnNullIfFailedToGetSystemPref() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(null);

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        Bitmap cachedIcon = lockScreenDeviceIconManager.getFileFromCache(ICON_URL);
        assertNull(cachedIcon);
    }

    public void testGetFileFromCacheShouldReturnNullIfInvalidDataFromSharedPref() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor sharedPrefsEditor = Mockito.mock(SharedPreferences.Editor.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.edit()).thenReturn(sharedPrefsEditor);
        Mockito.when(sharedPrefsEditor.remove(anyString())).thenReturn(sharedPrefsEditor);
        Mockito.when(sharedPrefsEditor.commit()).thenReturn(true);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(INVALID_JSON_STRING);


        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        Bitmap cachedIcon = lockScreenDeviceIconManager.getFileFromCache(ICON_URL);
        assertNull(cachedIcon);
    }

    //TODO Add test for passing getFileFromCache

    //TODO Add test for failing to read file from cache

    private String buildJSONAsString(long DaysOld) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(STORED_URL, "STORED_URL");
            long timeDifferenceInMilliSeconds = DaysOld * 1000 * 60 * 60 * 24;
            jsonObject.put(LAST_UPDATED_TIME, System.currentTimeMillis() - timeDifferenceInMilliSeconds);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}
