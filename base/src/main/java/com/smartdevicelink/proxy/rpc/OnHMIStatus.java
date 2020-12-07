/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
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
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingState;

import java.util.Hashtable;

/**
 * <p>Notifies an application that HMI conditions have changed for the application. This indicates whether the application
 * can speak phrases, display text, perform interactions, receive button presses and events, stream audio, etc. This
 * notification will be sent to the application when there has been a change in any one or several of the indicated
 * states ({@linkplain HMILevel}, {@linkplain AudioStreamingState} or {@linkplain SystemContext}) for the application</p>
 * <p>All three values are, in principle, independent of each other (though there may be some relationships). A value for
 * one parameter should not be interpreted from the value of another parameter.</p>
 * <p>There are no guarantees about the timeliness or latency of the OnHMIStatus notification. Therefore, for example,
 * information such as {@linkplain AudioStreamingState} may not indicate that the audio stream became inaudible to the user
 * exactly when the OnHMIStatus notification was received.</p>
 *
 * <p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>hmiLevel</td>
 * <td>{@linkplain HMILevel}</td>
 * <td>The current HMI Level in effect for the application.</td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>audioStreamingState</td>
 * <td>{@linkplain AudioStreamingState}</td>
 * <td>Current state of audio streaming for the application.
 * When this parameter has a value of NOT_AUDIBLE,
 * the application must stop streaming audio to SDL.
 * Informs app whether any currently streaming audio is
 * audible to user (AUDIBLE) or not (NOT_AUDIBLE). A
 * value of NOT_AUDIBLE means that either the
 * application's audio will not be audible to the user, or
 * that the application's audio should not be audible to
 * the user (i.e. some other application on the mobile
 * device may be streaming audio and the application's
 * audio would be blended with that other audio). </td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>videoStreamingState</td>
 * <td>{@linkplain VideoStreamingState}</td>
 * <td>If it is NOT_STREAMABLE, the app must stop streaming video to SDL Core(stop service).</td>
 * <td>SmartDeviceLink 5.0</td>
 * </tr>
 * <tr>
 * <td>systemContext</td>
 * <td>{@linkplain SystemContext}</td>
 * <td>Indicates that a user-initiated interaction is in-progress
 * (VRSESSION or MENU), or not (MAIN)</td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table>
 * </p>
 *
 * @see RegisterAppInterface
 * @since SmartDeviceLink 1.0
 */
public class OnHMIStatus extends RPCNotification {
    public static final String KEY_AUDIO_STREAMING_STATE = "audioStreamingState";
    public static final String KEY_VIDEO_STREAMING_STATE = "videoStreamingState";
    public static final String KEY_SYSTEM_CONTEXT = "systemContext";
    public static final String KEY_HMI_LEVEL = "hmiLevel";
    public static final String KEY_WINDOW_ID = "windowID";

    private Boolean firstRun;

    /**
     * Constructs a newly allocated OnHMIStatus object
     */
    public OnHMIStatus() {
        super(FunctionID.ON_HMI_STATUS.toString());
    }

    /**
     * <p>Constructs a newly allocated OnHMIStatus object indicated by the Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public OnHMIStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated OnHMIStatus object
     *
     * @param hmiLevel            the HMILevel to set
     * @param audioStreamingState the state of audio streaming of the application
     * @param systemContext       Indicates that a user-initiated interaction is in-progress
     */
    public OnHMIStatus(@NonNull HMILevel hmiLevel, @NonNull AudioStreamingState audioStreamingState, @NonNull SystemContext systemContext) {
        this();
        setHmiLevel(hmiLevel);
        setAudioStreamingState(audioStreamingState);
        setSystemContext(systemContext);
    }

    @Override
    public void format(com.smartdevicelink.util.Version rpcVersion, boolean formatParams) {
        if (rpcVersion.getMajor() < 5) {
            if (getVideoStreamingState() == null) {
                setVideoStreamingState(VideoStreamingState.STREAMABLE);
            }
        }

        super.format(rpcVersion, formatParams);
    }

    /**
     * <p>Get HMILevel in effect for the application</p>
     *
     * @return {@linkplain HMILevel} the current HMI Level in effect for the application
     */
    public HMILevel getHmiLevel() {
        return (HMILevel) getObject(HMILevel.class, KEY_HMI_LEVEL);
    }

    /**
     * <p>Set the HMILevel of OnHMIStatus</p>
     *
     * @param hmiLevel the HMILevel to set
     */
    public OnHMIStatus setHmiLevel(@NonNull HMILevel hmiLevel) {
        setParameters(KEY_HMI_LEVEL, hmiLevel);
        return this;
    }

    /**
     * <p>Get current state of audio streaming for the application</p>
     *
     * @return {@linkplain AudioStreamingState} Returns current state of audio streaming for the application
     */
    public AudioStreamingState getAudioStreamingState() {
        return (AudioStreamingState) getObject(AudioStreamingState.class, KEY_AUDIO_STREAMING_STATE);
    }

    /**
     * <p>Set the audio streaming state</p>
     *
     * @param audioStreamingState the state of audio streaming of the application
     */
    public OnHMIStatus setAudioStreamingState(@NonNull AudioStreamingState audioStreamingState) {
        setParameters(KEY_AUDIO_STREAMING_STATE, audioStreamingState);
        return this;
    }

    /**
     * <p>Get current state of video streaming for the application</p>
     *
     * @return {@linkplain VideoStreamingState} Returns current state of video streaming for the application
     */
    public VideoStreamingState getVideoStreamingState() {
        return (VideoStreamingState) getObject(VideoStreamingState.class, KEY_VIDEO_STREAMING_STATE);
    }

    /**
     * <p>Set the video streaming state</p>
     *
     * @param videoStreamingState the state of video streaming of the application
     */
    public OnHMIStatus setVideoStreamingState(VideoStreamingState videoStreamingState) {
        setParameters(KEY_VIDEO_STREAMING_STATE, videoStreamingState);
        return this;
    }

    /**
     * <p>Get the System Context</p>
     *
     * @return {@linkplain SystemContext} whether a user-initiated interaction is in-progress (VRSESSION or MENU), or not (MAIN).
     */
    public SystemContext getSystemContext() {
        return (SystemContext) getObject(SystemContext.class, KEY_SYSTEM_CONTEXT);
    }

    /**
     * <p>Set the System Context of OnHMIStatus</p>
     *
     * @param systemContext Indicates that a user-initiated interaction is in-progress
     *                      (VRSESSION or MENU), or not (MAIN)
     */
    public OnHMIStatus setSystemContext(@NonNull SystemContext systemContext) {
        setParameters(KEY_SYSTEM_CONTEXT, systemContext);
        return this;
    }

    /**
     * <p>Query whether it's the first run</p>
     *
     * @return boolean whether it's the first run
     */
    public Boolean getFirstRun() {
        return this.firstRun;
    }

    /**
     * <p>Set the firstRun value</p>
     *
     * @param firstRun True if it is the first run, False or not
     */
    public OnHMIStatus setFirstRun(Boolean firstRun) {
        this.firstRun = firstRun;
        return this;
    }

    /**
     * <p>Set the windowID value</p>
     *
     * @param windowID This is the unique ID assigned to the window that this RPC is intended.
     *                 If this param is not included, it will be assumed that this request is specifically for the main window on the main display.
     *                 See PredefinedWindows enum.
     * @since 6.0
     */
    public OnHMIStatus setWindowID(Integer windowID) {
        setParameters(KEY_WINDOW_ID, windowID);
        return this;
    }

    /**
     * <p>Get the windowID value</p>
     *
     * @return Integer This is the unique ID assigned to the window that this RPC is intended.
     * @since 6.0
     */
    public Integer getWindowID() {
        return getInteger(KEY_WINDOW_ID);
    }
}
