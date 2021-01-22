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
import androidx.annotation.Nullable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingIndicator;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

/**
 * Sets the media clock/timer value and the update method (e.g.count-up,
 * count-down, etc.)
 *
 * <p>Function Group: Base </p>
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>startTime</td>
 * 			<td>StartTime</td>
 * 			<td>StartTime struct specifying hour, minute, second values to which media clock timer is set.</td>
 *                 <td>N</td>
 * 			<td> </td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>endTime</td>
 * 			<td>StartTime</td>
 * 			<td> EndTime can be provided for "COUNTUP" and "COUNTDOWN"; to be used to calculate any visual progress bar (if not provided, this feature is ignored)
 * If endTime is greater then startTime for COUNTDOWN or less than startTime for COUNTUP, then the request will return an INVALID_DATA.
 * endTime will be ignored for "RESUME", and "CLEAR"
 * endTime can be sent for "PAUSE", in which case it will update the paused endTime</td>
 *                 <td>N</td>
 * 			<td>Array must have at least one element.<p>Only optional it helpPrompt has been specified</p> minsize: 1; maxsize: 100</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>updateMode</td>
 * 			<td>UpdateMode</td>
 * 			<td>Specifies how the media clock/timer is to be updated (COUNTUP/COUNTDOWN/PAUSE/RESUME), based at the startTime.</td>
 *                 <td>Y</td>
 * 			<td>If "updateMode" is COUNTUP or COUNTDOWN, this parameter must be provided. Will be ignored for PAUSE,RESUME and CLEAR</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>audioStreamingIndicator</td>
 * 			<td>AudioStreamingIndicator</td>
 * 			<td></td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 5.0</td>
 * 		</tr>
 * 	    <tr>
 *          <td>countRate</td>
 *          <td>Float</td>
 *          <td>The value of this parameter is the amount that the media clock timer will advance per 1.0 seconds of real time. Values less than 1.0 will therefore advance the timer slower than real-time, while values greater than 1.0 will advance the timer faster than real-time.e.g. If this parameter is set to `0.5`, the timer will advance one second per two seconds real-time, or at 50% speed. If this parameter is set to `2.0`, the timer will advance two seconds per one second real-time, or at 200% speed.</td>

 *          <td>N</td>
 *          <td>{"num_min_value": 0.1, "num_max_value": 100.0}</td>
 *          <td>
 *             @since SmartDeviceLink 7.1.0
 *          </td>
 *      </tr>
 *
 *  </table>
 *
 * <p><b>Response </b></p>
 *
 * <p><b> Non-default Result Codes: </b></p>
 *
 * 	<p> SUCCESS </p>
 * 	<p> INVALID_DATA</p>
 * 	<p> OUT_OF_MEMORY</p>
 *  <p>   TOO_MANY_PENDING_REQUESTS</p>
 *   <p>  APPLICATION_NOT_REGISTERED</p>
 *    <p> GENERIC_ERROR</p>
 *   <p>   REJECTED </p>
 *    <p>  IGNORED </p>
 *
 * @since SmartDeviceLink 1.0
 */
public class SetMediaClockTimer extends RPCRequest {
    public static final String KEY_START_TIME = "startTime";
    public static final String KEY_END_TIME = "endTime";
    public static final String KEY_UPDATE_MODE = "updateMode";
    public static final String KEY_AUDIO_STREAMING_INDICATOR = "audioStreamingIndicator";
    public static final String KEY_COUNT_RATE = "countRate";

    /**
     * Constructs a new SetMediaClockTimer object
     */
    public SetMediaClockTimer() {
        super(FunctionID.SET_MEDIA_CLOCK_TIMER.toString());
    }

    /**
     * Constructs a new SetMediaClockTimer object indicated by the Hashtable
     * parameter
     * <p></p>
     *
     * @param hash The Hashtable to use
     */
    public SetMediaClockTimer(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new SetMediaClockTimer object
     *
     * @param updateMode a Enumeration value (COUNTUP/COUNTDOWN/PAUSE/RESUME) <br>
     *                   <b>Notes: </b>
     *                   <ul>
     *                         <li>When updateMode is PAUSE, RESUME or CLEAR, the start time value
     *                         is ignored</li>
     *                         <li>When updateMode is RESUME, the timer resumes counting from
     *                         the timer's value when it was paused</li>
     *                   </ul>
     */
    public SetMediaClockTimer(@NonNull UpdateMode updateMode) {
        this();
        setUpdateMode(updateMode);
    }

    private SetMediaClockTimer(@NonNull UpdateMode updateMode, @Nullable StartTime startTime, @Nullable StartTime endTime, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        this();
        this.setUpdateMode(updateMode);
        if (startTime != null) {
            this.setStartTime(startTime);
        }
        if (endTime != null) {
            this.setEndTime(endTime);
        }
        if (audioStreamingIndicator != null) {
            this.setAudioStreamingIndicator(audioStreamingIndicator);
        }
    }

    /**
     * Create a media clock timer that counts up, e.g from 0:00 to 4:18.
     *
     * @param startTimeInterval       The start time interval, e.g. (0) 0:00
     * @param endTimeInterval         The end time interval, e.g. (258) 4:18
     * @param audioStreamingIndicator playPauseIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer countUpFromStartTimeInterval(@NonNull Integer startTimeInterval, @NonNull Integer endTimeInterval, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.COUNTUP, new StartTime(startTimeInterval), new StartTime(endTimeInterval), audioStreamingIndicator);
    }

    /**
     * Create a media clock timer that counts up, e.g from 0:00 to 4:18.
     *
     * @param startTime               The start time interval, e.g. 0:00
     * @param endTime                 The end time interval, e.g. 4:18
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer countUpFromStartTime(@NonNull StartTime startTime, @NonNull StartTime endTime, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.COUNTUP, startTime, endTime, audioStreamingIndicator);
    }

    /**
     * Create a media clock timer that counts down, e.g. from 4:18 to 0:00
     * This will fail if endTime is greater than startTime
     *
     * @param startTimeInterval       The start time interval, e.g. (258) 4:18
     * @param endTimeInterval         The end time interval, e.g. (0) 0:00
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer countDownFromStartTimeInterval(@NonNull Integer startTimeInterval, @NonNull Integer endTimeInterval, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.COUNTDOWN, new StartTime(startTimeInterval), new StartTime(endTimeInterval), audioStreamingIndicator);
    }

    /**
     * Create a media clock timer that counts down, e.g. from 4:18 to 0:00
     * This will fail if endTime is greater than startTime
     *
     * @param startTime               The start time interval, e.g. 4:18
     * @param endTime                 The end time interval, e.g. 0:00
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer countDownFromStartTime(@NonNull StartTime startTime, @NonNull StartTime endTime, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.COUNTDOWN, startTime, endTime, audioStreamingIndicator);
    }

    /**
     * Pause an existing (counting up / down) media clock timer
     *
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer pauseWithPlayPauseIndicator(@Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.PAUSE, null, null, audioStreamingIndicator);
    }

    /**
     * Update a pause time (or pause and update the time) on a media clock timer
     *
     * @param startTimeInterval       The new start time interval
     * @param endTimeInterval         The new end time interval
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer updatePauseWithNewStartTimeInterval(@NonNull Integer startTimeInterval, @NonNull Integer endTimeInterval, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.PAUSE, new StartTime(startTimeInterval), new StartTime(endTimeInterval), audioStreamingIndicator);
    }

    /**
     * Update a pause time (or pause and update the time) on a media clock timer
     *
     * @param startTime               The new start time
     * @param endTime                 The new end time
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer updatePauseWithNewStartTime(@NonNull StartTime startTime, @NonNull StartTime endTime, @Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.PAUSE, startTime, endTime, audioStreamingIndicator);
    }

    /**
     * Resume a paused media clock timer. It resumes at the same time at which it was paused.
     *
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer resumeWithPlayPauseIndicator(@Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.RESUME, null, null, audioStreamingIndicator);
    }

    /**
     * Remove a media clock timer from the screen
     *
     * @param audioStreamingIndicator An optional audio indicator to change the play/pause button
     * @return An object of SetMediaClockTimer
     */
    public static SetMediaClockTimer clearWithPlayPauseIndicator(@Nullable AudioStreamingIndicator audioStreamingIndicator) {
        return new SetMediaClockTimer(UpdateMode.CLEAR, null, null, audioStreamingIndicator);
    }

    /**
     * Gets the Start Time which media clock timer is set
     *
     * @return StartTime -a StartTime object specifying hour, minute, second
     * values
     */
    public StartTime getStartTime() {
        return (StartTime) getObject(StartTime.class, KEY_START_TIME);
    }

    /**
     * Sets a Start Time with specifying hour, minute, second values
     *
     * @param startTime a startTime object with specifying hour, minute, second values
     *                  <p></p>
     *                  <b>Notes: </b>
     *                  <ul>
     *                  <li>If "updateMode" is COUNTUP or COUNTDOWN, this parameter
     *                  must be provided</li>
     *                  <li>Will be ignored for PAUSE/RESUME and CLEAR</li>
     *                  </ul>
     */
    public SetMediaClockTimer setStartTime(StartTime startTime) {
        setParameters(KEY_START_TIME, startTime);
        return this;
    }

    public StartTime getEndTime() {
        return (StartTime) getObject(StartTime.class, KEY_END_TIME);
    }

    public SetMediaClockTimer setEndTime(StartTime endTime) {
        setParameters(KEY_END_TIME, endTime);
        return this;
    }

    /**
     * Gets the media clock/timer update mode (COUNTUP/COUNTDOWN/PAUSE/RESUME)
     *
     * @return UpdateMode -a Enumeration value (COUNTUP/COUNTDOWN/PAUSE/RESUME)
     */
    public UpdateMode getUpdateMode() {
        return (UpdateMode) getObject(UpdateMode.class, KEY_UPDATE_MODE);
    }

    /**
     * Sets the media clock/timer update mode (COUNTUP/COUNTDOWN/PAUSE/RESUME)
     *
     * @param updateMode a Enumeration value (COUNTUP/COUNTDOWN/PAUSE/RESUME)
     *                   <p></p>
     *                   <b>Notes: </b>
     *                   <ul>
     *                   <li>When updateMode is PAUSE, RESUME or CLEAR, the start time value
     *                   is ignored</li>
     *                   <li>When updateMode is RESUME, the timer resumes counting from
     *                   the timer's value when it was paused</li>
     *                   </ul>
     */
    public SetMediaClockTimer setUpdateMode(@NonNull UpdateMode updateMode) {
        setParameters(KEY_UPDATE_MODE, updateMode);
        return this;
    }

    /**
     * Gets the playback status of a media app
     *
     * @return AudioStreamingIndicator - a Enumeration value
     */
    public AudioStreamingIndicator getAudioStreamingIndicator() {
        return (AudioStreamingIndicator) getObject(AudioStreamingIndicator.class, KEY_AUDIO_STREAMING_INDICATOR);
    }

    /**
     * Sets the playback status of a media app
     */
    public SetMediaClockTimer setAudioStreamingIndicator(AudioStreamingIndicator audioStreamingIndicator) {
        setParameters(KEY_AUDIO_STREAMING_INDICATOR, audioStreamingIndicator);
        return this;
    }

    /**
     * Sets the countRate.
     *
     * @param countRate The value of this parameter is the amount that the media clock timer will advance per 1.0
     * seconds of real time. Values less than 1.0 will therefore advance the timer slower than
     * real-time, while values greater than 1.0 will advance the timer faster than real-time.
     * e.g. If this parameter is set to `0.5`, the timer will advance one second per two seconds
     * real-time, or at 50% speed. If this parameter is set to `2.0`, the timer will advance two
     * seconds per one second real-time, or at 200% speed.
     * {"num_min_value": 0.1, "num_max_value": 100.0}
     * @since SmartDeviceLink 7.1.0
     */
    public SetMediaClockTimer setCountRate(Float countRate) {
        setParameters(KEY_COUNT_RATE, countRate);
        return this;
    }

    /**
     * Gets the countRate.
     *
     * @return Float The value of this parameter is the amount that the media clock timer will advance per 1.0
     * seconds of real time. Values less than 1.0 will therefore advance the timer slower than
     * real-time, while values greater than 1.0 will advance the timer faster than real-time.
     * e.g. If this parameter is set to `0.5`, the timer will advance one second per two seconds
     * real-time, or at 50% speed. If this parameter is set to `2.0`, the timer will advance two
     * seconds per one second real-time, or at 200% speed.
     * {"num_min_value": 0.1, "num_max_value": 100.0}
     * @since SmartDeviceLink 7.1.0
     */
    public Float getCountRate() {
        Object object = getParameters(KEY_COUNT_RATE);
        return SdlDataTypeConverter.objectToFloat(object);
    }
}
