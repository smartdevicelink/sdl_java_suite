package com.smartdevicelink.managers.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.util.AndroidTools;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LockScreenDeviceIconManagerTests extends AndroidTestCase2 {

    TemporaryFolder tempFolder = new TemporaryFolder();
    private LockScreenDeviceIconManager lockScreenDeviceIconManager;
    private static final String ICON_URL = "http://i.imgur.com/TgkvOIZ.png";
    private static final String LAST_UPDATED_TIME = "lastUpdatedTime";
    private static final String STORED_PATH = "storedPath";
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
        boolean shouldUpdate = lockScreenDeviceIconManager.shouldUpdateCachedImage(ICON_URL);
        assertTrue(shouldUpdate);
    }

    public void testUpdateCacheImageShouldReturnTrueWhenUnableToReadSharedPreference() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor sharedPrefsEditor = Mockito.mock(SharedPreferences.Editor.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn("");
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.edit()).thenReturn(sharedPrefsEditor);
        Mockito.when(sharedPrefsEditor.remove(anyString())).thenReturn(sharedPrefsEditor);
        Mockito.when(sharedPrefsEditor.commit()).thenReturn(true);


        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.shouldUpdateCachedImage(ICON_URL);
        assertTrue(shouldUpdate);
    }

    public void testUpdateCacheImageShouldReturnTrueSharedPreferenceReturnsAnOutdatedIcon() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(daysToMillisecondsAsString(35));

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.shouldUpdateCachedImage(ICON_URL);
        assertTrue(shouldUpdate);
    }

    public void testUpdateCacheImageShouldReturnFalseWhenSharedPreferenceReturnsAnUpdatedIcon() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(daysToMillisecondsAsString(15));

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.shouldUpdateCachedImage(ICON_URL);
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

    public void testSaveFileToCacheShouldWriteToSharedPrefsIfSaveIconIsSuccessful() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor sharedPrefsEditor = Mockito.mock(SharedPreferences.Editor.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.edit()).thenReturn(sharedPrefsEditor);
        try {
            tempFolder.create();
            Mockito.when(context.getCacheDir()).thenReturn(tempFolder.newFolder());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap deviceLogo = null;
        try {
            deviceLogo = AndroidTools.downloadImage(ICON_URL);
            lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
            lockScreenDeviceIconManager.saveFileToCache(deviceLogo, ICON_URL);
            verify(sharedPrefs, times(1)).edit();
            verify(sharedPrefsEditor, times(1)).putString(anyString(), anyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn("");


        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        Bitmap cachedIcon = lockScreenDeviceIconManager.getFileFromCache(ICON_URL);
        assertNull(cachedIcon);
    }

    public void testGetFileFromCacheShouldReturnNullIfFailedToFindIcon() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor sharedPrefsEditor = Mockito.mock(SharedPreferences.Editor.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.edit()).thenReturn(sharedPrefsEditor);
        Mockito.when(sharedPrefsEditor.remove(anyString())).thenReturn(sharedPrefsEditor);
        Mockito.when(sharedPrefsEditor.commit()).thenReturn(true);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(buildJSONAsString(15, ""));

        try {
            tempFolder.create();
            Mockito.when(context.getCacheDir()).thenReturn(tempFolder.newFolder());
        } catch (IOException e) {
            e.printStackTrace();
        }


        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        Bitmap cachedIcon = lockScreenDeviceIconManager.getFileFromCache(ICON_URL);
        assertNull(cachedIcon);
    }

    public void testGetFileFromCacheShouldReturnBitmapIfIconFoundInCache() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor sharedPrefsEditor = Mockito.mock(SharedPreferences.Editor.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.edit()).thenReturn(sharedPrefsEditor);
        Mockito.when(sharedPrefsEditor.remove(anyString())).thenReturn(sharedPrefsEditor);
        Mockito.when(sharedPrefsEditor.commit()).thenReturn(true);
        Bitmap deviceLogo = null;

        try {
            tempFolder.create();
            File newFolder = tempFolder.newFolder();
            Mockito.when(context.getCacheDir()).thenReturn(newFolder);
            deviceLogo = AndroidTools.downloadImage(ICON_URL);
            Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(buildJSONAsString(15, newFolder.getPath() + "/sdl/lock_screen_icon/" + getMD5HashFromIconUrl(ICON_URL)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        lockScreenDeviceIconManager.saveFileToCache(deviceLogo, ICON_URL);
        Bitmap cachedIcon = lockScreenDeviceIconManager.getFileFromCache(ICON_URL);
        assertNotNull(cachedIcon);
    }

    private String buildJSONAsString(long DaysOld, String cahceIconPath) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(STORED_PATH, cahceIconPath);
            long timeDifferenceInMilliSeconds = DaysOld * 1000 * 60 * 60 * 24;
            jsonObject.put(LAST_UPDATED_TIME, System.currentTimeMillis() - timeDifferenceInMilliSeconds);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
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
            e.printStackTrace();
        }
        return iconHash;
    }

    private String daysToMillisecondsAsString(int days) {
        long milliSeconds = (long) days * 24 * 60 * 60 * 1000;
        long previousDay = System.currentTimeMillis() - milliSeconds;
        return previousDay + "";
    }


}
