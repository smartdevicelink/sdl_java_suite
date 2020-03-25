package com.smartdevicelink.managers.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;

import com.smartdevicelink.AndroidTestCase2;

import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;

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

    public void testShouldReturnTrueWhenSharedPreferencesDoesNotExist() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(null);

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.updateCachedImage(ICON_URL);
        assertTrue(shouldUpdate);
    }

    public void testShouldReturnTrueWhenUnableToReadSharedPreference() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(INVALID_JSON_STRING);


        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.updateCachedImage(ICON_URL);
        assertTrue(shouldUpdate);
    }

    public void testShouldReturnTrueSharedPreferenceReturnsAnOutdatedIcon() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(buildJSONAsString(35));

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.updateCachedImage(ICON_URL);
        assertTrue(shouldUpdate);
    }

    public void testShouldReturnFalseWhenSharedPreferenceReturnsAnUpdatedIcon() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(sharedPrefs.getString(anyString(), (String) isNull())).thenReturn(buildJSONAsString(15));

        lockScreenDeviceIconManager = new LockScreenDeviceIconManager(context);
        boolean shouldUpdate = lockScreenDeviceIconManager.updateCachedImage(ICON_URL);
        assertFalse(shouldUpdate);
    }

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
