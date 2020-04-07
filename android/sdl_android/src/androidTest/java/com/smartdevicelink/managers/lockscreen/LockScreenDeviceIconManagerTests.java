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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LockScreenDeviceIconManagerTests extends AndroidTestCase2 {

    TemporaryFolder tempFolder = new TemporaryFolder();
    private LockScreenDeviceIconManager lockScreenDeviceIconManager;
    private static final String ICON_URL = "https://i.imgur.com/TgkvOIZ.png";
    private static final String LAST_UPDATED_TIME = "lastUpdatedTime";
    private static final String STORED_PATH = "storedPath";

    public void setup() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRetrieveIconShouldCallOnErrorTwiceWhenGivenURLThatCannotDownloadAndIconIsNotCached() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        final LockScreenDeviceIconManager.OnIconRetrievedListener listener = Mockito.mock(LockScreenDeviceIconManager.OnIconRetrievedListener.class);

        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(null);

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        lockScreenDeviceIconManager.retrieveIcon("", listener);
        verify(listener, times(2)).onError(anyString());
    }

    public void testRetrieveIconShouldCallOnImageOnImageRetrievedWithIconWhenIconUpdateTimeIsNullFromSharedPref() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        final LockScreenDeviceIconManager.OnIconRetrievedListener listener = Mockito.mock(LockScreenDeviceIconManager.OnIconRetrievedListener.class);

        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(null);

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        lockScreenDeviceIconManager.retrieveIcon(ICON_URL, listener);
        verify(listener, times(1)).onImageRetrieved((Bitmap) any());
    }


    public void testRetrieveIconShouldCallOnImageOnImageRetrievedWithIconWhenCachedIconExpired() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        final LockScreenDeviceIconManager.OnIconRetrievedListener listener = Mockito.mock(LockScreenDeviceIconManager.OnIconRetrievedListener.class);

        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(daysToMillisecondsAsString(31));

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        lockScreenDeviceIconManager.retrieveIcon(ICON_URL, listener);
        verify(listener, times(1)).onImageRetrieved((Bitmap) any());
    }

    public void testRetrieveIconShouldCallOnImageRetrievedWithIconWhenCachedIconIsUpToDate() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        final SharedPreferences.Editor sharedPrefsEditor = Mockito.mock(SharedPreferences.Editor.class);
        final LockScreenDeviceIconManager.OnIconRetrievedListener listener = Mockito.mock(LockScreenDeviceIconManager.OnIconRetrievedListener.class);

        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(daysToMillisecondsAsString(15));
        Mockito.when(sharedPrefs.edit()).thenReturn(sharedPrefsEditor);
        Mockito.when(sharedPrefsEditor.clear()).thenReturn(sharedPrefsEditor);

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        lockScreenDeviceIconManager.retrieveIcon(ICON_URL, listener);
        verify(listener, times(1)).onImageRetrieved((Bitmap) any());
    }
    
    private String daysToMillisecondsAsString(int days) {
        long milliSeconds = (long) days * 24 * 60 * 60 * 1000;
        long previousDay = System.currentTimeMillis() - milliSeconds;
        return String.valueOf(previousDay);
    }
}
