package com.smartdevicelink.transport.utl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.VehicleType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class VehicleTypeHolder {

    private static final String SDL_DEVICE_VEHICLES_PREFS = "sdl.device.vehicles";
    private static final String TAG = "VehicleTypeHolder";

    private static final Object LOCK = new Object(), RUNNING_LOCK = new Object();

    public static void saveVehicleType(Context context, VehicleType vehicleType, String address) {
        synchronized (LOCK) {

            if (vehicleType == null || address == null) {
                return;
            }
            try {
                SharedPreferences preferences = context.getSharedPreferences(SDL_DEVICE_VEHICLES_PREFS, Context.MODE_PRIVATE);

                String jsonString = vehicleType.serializeJSON().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(address, jsonString);
                editor.commit();
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public static Hashtable<String, Object> getVehicleTypeFromPrefs(Context context, String address) {
        synchronized (LOCK) {
            try {
                SharedPreferences preferences = context.getSharedPreferences(SDL_DEVICE_VEHICLES_PREFS, Context.MODE_PRIVATE);
                String storedVehicleTypeSerialized = preferences.getString(address, null);

                if (storedVehicleTypeSerialized == null) {
                    return null;
                } else {
                    JSONObject object = new JSONObject(storedVehicleTypeSerialized);
                    return JsonRPCMarshaller.deserializeJSONObject(object);
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
    }
}
