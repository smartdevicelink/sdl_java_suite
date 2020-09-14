/*
 * Copyright (c) 2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.util;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Possible improvements
 *
 * - Narrow down list of acceptable audio devices
 * - Add ability to listen for when audio devices become available, and then connect
 * - Improve redundant calls to create String arrays for action arrays
 */

public class MediaStreamingStatus {
    private static final String TAG = "MediaStreamingStatus";
    private static final Object BROADCAST_RECEIVER_LOCK = new Object();

    private boolean broadcastReceiverValid = true;
    private final WeakReference<Context> contextWeakReference;
    private Callback callback;
    private final List<String> intentList;

    public MediaStreamingStatus(@NonNull Context context, @NonNull Callback callback){
        contextWeakReference = new WeakReference<>(context);
        this.callback = callback;
        intentList = new ArrayList<>();
        //This is a default action that should be added
        intentList.add(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    }

    public void clear(){
        callback = null;
        unregisterBroadcastReceiver();
        contextWeakReference.clear();

    }

    /*

       Working order
       ---------------------------------------------------------------------------------------------
       1. If API level >= 23, use AudioManager to get connected audio output devices.
          Covers ~ 74.8% of Android devices as of 5/30/2019
          This will return for a number of different supported audio devices. Full list can be seen
          in the isSupportedAudioDevice method.

       2. If API level >= 3 && <=22, use the BluetoothManager to detect A2DP connection.
          Covers ~ 25.2% of Android devices not covered in option 1 as of 5/30/2019
          This will enforce that bluetooth is connected as an audio output. No other type of audio
          device can currently be detected.

       3. If API level <= 2, return false.
          Covers <1% of Android devices not covered by cases 1 and 2.

       Other options considered included:
       - BluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP) == STATE_CONNECTED || STATE_CONNECTING ;
       - MediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_AUDIO).getDeviceType() == MediaRouter.RouteInfo.DEVICE_TYPE_BLUETOOTH ;

    */

    @SuppressLint("MissingPermission")
    public synchronized boolean isAudioOutputAvailable() {
        Context context = contextWeakReference.get();
        if(context == null){ return false;}

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        // If API level 23+ audio manager can iterate over all current devices to see if a supported
        // device is present.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (audioManager != null) {
                AudioDeviceInfo[] deviceInfos = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
                if (deviceInfos != null) {
                    for (AudioDeviceInfo deviceInfo : deviceInfos) {
                        if (deviceInfo != null && isSupportedAudioDevice(deviceInfo.getType())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        //This means the SDK version is < M, and our min is 8 so this API is always available
        if (audioManager != null) {
            return audioManager.isBluetoothA2dpOn();
        }

        return false;
    }

    /**
     * This method will check to ensure that the device is supported. If possible, it will also
     * check against known variables and flags to ensure that that device is connected. This is
     * required as the AudioManager tends to be untrustworthy.
     * @param audioDevice
     * @return
     */
    boolean isSupportedAudioDevice(int audioDevice){
        DebugTool.logInfo(TAG, "Audio device connected: " + audioDevice);
        switch (audioDevice){
            case AudioDeviceInfo.TYPE_BLUETOOTH_A2DP:
              if(isBluetoothActuallyAvailable()) {
                  setupBluetoothBroadcastReceiver();
                  return true; //Make sure this doesn't fall to any other logic after this point
              }
              return false;
            case AudioDeviceInfo.TYPE_DOCK:
            case AudioDeviceInfo.TYPE_USB_ACCESSORY:
            case AudioDeviceInfo.TYPE_USB_DEVICE:
            case AudioDeviceInfo.TYPE_USB_HEADSET:
                if(isUsbActuallyConnected()) {
                    setupUSBBroadcastReceiver();
                    return true;
                }
                return false;
            case AudioDeviceInfo.TYPE_LINE_ANALOG:
            case AudioDeviceInfo.TYPE_LINE_DIGITAL:
            case AudioDeviceInfo.TYPE_WIRED_HEADSET:
            case AudioDeviceInfo.TYPE_WIRED_HEADPHONES:
            case AudioDeviceInfo.TYPE_AUX_LINE:
                setupHeadsetBroadcastReceiver();
                return true;
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    boolean isBluetoothActuallyAvailable(){
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter == null || !adapter.isEnabled() ){
            //False positive
            return false;
        }

        int state = adapter.getProfileConnectionState(BluetoothProfile.A2DP);
        if(state != BluetoothAdapter.STATE_CONNECTING && state != BluetoothAdapter.STATE_CONNECTED){
            //False positive
            return false;
        }

        return true;
    }

    boolean isUsbActuallyConnected(){
        Context context = contextWeakReference.get();
        if(context != null){
           return AndroidTools.isUSBCableConnected(context);
        }
        //default to true
        return true;
    }


    private void setupBluetoothBroadcastReceiver(){
        String[] actions = new String[4];
        actions[0] = BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED;
        actions[1] = BluetoothAdapter.ACTION_STATE_CHANGED;
        actions[2] = BluetoothDevice.ACTION_ACL_DISCONNECTED;
        actions[3] = BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED;

        listenForIntents(actions);
    }

    private void setupHeadsetBroadcastReceiver(){
        String[] actions = new String[1];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actions[0] = AudioManager.ACTION_HEADSET_PLUG;
        }else{
            actions[0] =  "android.intent.action.HEADSET_PLUG";
        }

        listenForIntents(actions);
    }

    private void setupUSBBroadcastReceiver(){
        String[] actions = new String[1];
        actions[0] = Intent.ACTION_BATTERY_CHANGED;

        listenForIntents(actions);
    }

    private void listenForIntents(@NonNull String[] actions){
        if(intentList != null){
            //Add each intent
            int preAddSize = intentList.size();

            for(String action : actions){
                if(action != null && action.length() > 0 && !intentList.contains(action)){
                    intentList.add(action);
                }
            }

            if(preAddSize != intentList.size()){
                synchronized (BROADCAST_RECEIVER_LOCK){
                    broadcastReceiverValid = true;
                }
                updateBroadcastReceiver();
            }
        }
    }

    private void updateBroadcastReceiver() {
        //The broadcast receiver has not been setup for this yet
        Context context = contextWeakReference.get();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            for (String intentAction : intentList) {
                intentFilter.addAction(intentAction);
            }
            unregisterBroadcastReceiver();
            //Re-register receiver
            context.registerReceiver(broadcastReceiver, intentFilter);

        }

    }

    private void unregisterBroadcastReceiver(){
        Context context = contextWeakReference.get();
        if(context != null) {
            try{
                context.unregisterReceiver(broadcastReceiver);
            }catch (Exception e){
                //Ignore the exception
            }
        }
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            synchronized (BROADCAST_RECEIVER_LOCK) {
                if (!isAudioOutputAvailable()) {
                    if (broadcastReceiverValid) {
                        broadcastReceiverValid = false;
                        //No audio device is acceptable any longer
                        if (callback != null) {
                            callback.onAudioNoLongerAvailable();
                        }

                        intentList.clear();
                        unregisterBroadcastReceiver();
                    }
                }
            }
        }
    };


    public interface Callback{
        void onAudioNoLongerAvailable();
    }

}
