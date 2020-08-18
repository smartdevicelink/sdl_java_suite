package com.smartdevicelink.managers.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class LockScreenDeviceIconManagerTests {

    TemporaryFolder tempFolder = new TemporaryFolder();
    private LockScreenDeviceIconManager lockScreenDeviceIconManager;
    private static final String ICON_URL = "https://i.imgur.com/TgkvOIZ.png";
    private static final String LAST_UPDATED_TIME = "lastUpdatedTime";
    private static final String STORED_PATH = "storedPath";

    @Test
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

    @Test
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

    @Test
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

    @Test
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
