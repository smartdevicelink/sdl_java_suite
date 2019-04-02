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

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.CompassDirection;
import com.smartdevicelink.proxy.rpc.enums.Dimension;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

/**
 * Describes the GPS data. Not all data will be available on all carlines.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>longitudeDegrees</td>
 * 			<td>Double</td>
 * 			<td>Minvalue: - 180
 * 					<b>Maxvalue: 180
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>latitudeDegrees</td>
 * 			<td>Double</td>
 * 			<td>Minvalue: - 90<b>Maxvalue: 90
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>utcYear</td>
 * 			<td>Integer</td>
 * 			<td>Minvalue: 2010<b>Maxvalue: 2100
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>utcMonth</td>
 * 			<td>Integer</td>
 * 			<td>Minvalue: 1<b>Maxvalue: 12
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>utcDay</td>
 * 			<td>Integer</td>
 * 			<td>Minvalue: 1<b>Maxvalue: 31
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>utcHours</td>
 * 			<td>Integer</td>
 * 			<td>Minvalue: 0<b>Maxvalue: 23
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>utcMinutes</td>
 * 			<td>Integer</td>
 * 			<td>Minvalue: 0<b>Maxvalue: 59
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>utcSeconds</td>
 * 			<td>Integer</td>
 * 			<td>Minvalue: 0<b>Maxvalue: 59
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>pdop</td>
 * 			<td>Integer</td>
 * 			<td>Positional Dilution of Precision. If undefined or unavailable, then value shall be set to 0.<b>Minvalue: 0<b>Maxvalue: 1000
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>hdop</td>
 * 			<td>Integer</td>
 * 			<td>Horizontal Dilution of Precision. If value is unknown, value shall be set to 0.<b>Minvalue: 0<b>Maxvalue: 1000
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>vdop</td>
 * 			<td>Integer</td>
 * 			<td>Vertical  Dilution of Precision. If value is unknown, value shall be set to 0.<b>Minvalue: 0<b>Maxvalue: 1000
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>actual</td>
 * 			<td>Boolean</td>
 * 			<td>True, if coordinates are based on satellites.
 *					False, if based on dead reckoning
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>satellites</td>
 * 			<td>Integer</td>
 * 			<td>Number of satellites in view
 *					<b>Minvalue: 0
 *					<b>Maxvalue: 31
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>altitude</td>
 * 			<td>Integer</td>
 * 			<td>Altitude in meters
 *					<b>Minvalue: -10000</b>
 *					<b>Maxvalue: 10000</b>
 * 			<b>Note:</b> SYNC uses Mean Sea Level for calculating GPS. </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>heading</td>
 * 			<td>Double</td>
 * 			<td>The heading. North is 0, East is 90, etc.
 *					<b>Minvalue: 0
 *					<b>Maxvalue: 359.99
 *					<b>Resolution is 0.01
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>speed</td>
 * 			<td>Integer</td>
 * 			<td>The speed in KPH
 *					<b>Minvalue: 0
 *					<b>Maxvalue: 500
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class GPSData extends RPCStruct {
	public static final String KEY_LONGITUDE_DEGREES = "longitudeDegrees";
	public static final String KEY_LATITUDE_DEGREES = "latitudeDegrees";
	public static final String KEY_UTC_YEAR = "utcYear";
	public static final String KEY_UTC_MONTH = "utcMonth";
	public static final String KEY_UTC_DAY = "utcDay";
	public static final String KEY_UTC_HOURS = "utcHours";
	public static final String KEY_UTC_MINUTES = "utcMinutes";
	public static final String KEY_UTC_SECONDS = "utcSeconds";
	public static final String KEY_COMPASS_DIRECTION = "compassDirection";
	public static final String KEY_PDOP = "pdop";
	public static final String KEY_VDOP = "vdop";
	public static final String KEY_HDOP = "hdop";
	public static final String KEY_ACTUAL = "actual";
	public static final String KEY_SATELLITES = "satellites";
	public static final String KEY_DIMENSION = "dimension";
	public static final String KEY_ALTITUDE = "altitude";
	public static final String KEY_HEADING = "heading";
	public static final String KEY_SPEED = "speed";

	/**
	 * Constructs a newly allocated GPSData object
	 */
    public GPSData() { }

    /**
     * Constructs a newly allocated GPSData object indicated by the Hashtable parameter 
     * @param hash The Hashtable to use
     */    
    public GPSData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated GPSData object
     * @deprecated Use {@link #GPSData(@NonNull Double, @NonNull Double)()} instead
     */
    @Deprecated
    public GPSData(@NonNull Double longitudeDegrees, @NonNull Double latitudeDegrees, @NonNull Integer utcYear,
                   @NonNull Integer utcMonth, @NonNull Integer utcDay, @NonNull Integer utcHours,
                   @NonNull Integer utcMinutes, @NonNull Integer utcSeconds, @NonNull CompassDirection compassDirection,
                   @NonNull Double pdop, @NonNull Double hdop, @NonNull Double vdop, @NonNull Boolean actual,
                   @NonNull Integer satellites, @NonNull Dimension dimension, @NonNull Double altitude, @NonNull Double heading, @NonNull Double speed) {
        this();
        setLongitudeDegrees(longitudeDegrees);
        setLatitudeDegrees(latitudeDegrees);
        setUtcYear(utcYear);
        setUtcMonth(utcMonth);
        setUtcDay(utcDay);
        setUtcHours(utcHours);
        setUtcMinutes(utcMinutes);
        setUtcSeconds(utcSeconds);
        setCompassDirection(compassDirection);
        setPdop(pdop);
        setHdop(hdop);
        setVdop(vdop);
        setActual(actual);
        setSatellites(satellites);
        setDimension(dimension);
        setAltitude(altitude);
        setHeading(heading);
        setSpeed(speed);
    }

    /**
     * Constructs a newly allocated GPSData object
     */
    public GPSData(@NonNull Double longitudeDegrees, @NonNull Double latitudeDegrees) {
        this();
        setLongitudeDegrees(longitudeDegrees);
        setLatitudeDegrees(latitudeDegrees);
    }
    /**
     * set longitude degrees
     * @param longitudeDegrees degrees of the longitudinal position
     */
    public void setLongitudeDegrees(@NonNull Double longitudeDegrees) {
    	setValue(KEY_LONGITUDE_DEGREES, longitudeDegrees);
    }
    
    /**
     * get longitude degrees 
     * @return longitude degrees
     */
    public Double getLongitudeDegrees() {
    	Object object = getValue(KEY_LONGITUDE_DEGREES);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set latitude degrees
     * @param latitudeDegrees degrees of the latitudinal position
     */
    public void setLatitudeDegrees(@NonNull Double latitudeDegrees) {
    	setValue(KEY_LATITUDE_DEGREES, latitudeDegrees);
    }
    
    /**
     * get  latitude degrees
     * @return latitude degrees
     */
    public Double getLatitudeDegrees() {
    	Object object = getValue(KEY_LATITUDE_DEGREES);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set utc year
     * @param utcYear utc year
     */
    public void setUtcYear(Integer utcYear) {
    	setValue(KEY_UTC_YEAR, utcYear);
    }
    
    /**
     * get utc year
     * @return utc year
     */
    public Integer getUtcYear() {
    	return getInteger(KEY_UTC_YEAR);
    }
    
    /**
     * set utc month
     * @param utcMonth utc month
     */
    public void setUtcMonth(Integer utcMonth) {
    	setValue(KEY_UTC_MONTH, utcMonth);
    }
    
    /**
     * get utc month
     * @return utc month
     */
    public Integer getUtcMonth() {
    	return getInteger(KEY_UTC_MONTH);
    }
    
    /**
     * set utc day
     * @param utcDay utc day
     */
    public void setUtcDay(Integer utcDay) {
    	setValue(KEY_UTC_DAY, utcDay);
    }
    
    /**
     * get utc day
     * @return utc day
     */
    public Integer getUtcDay() {
    	return getInteger(KEY_UTC_DAY);
    }
    
    /**
     * set utc hours
     * @param utcHours utc hours
     */
    public void setUtcHours(Integer utcHours) {
    	setValue(KEY_UTC_HOURS, utcHours);
    }
    
    /**
     * get utc hours
     * @return utc hours
     */
    public Integer getUtcHours() {
    	return getInteger(KEY_UTC_HOURS);
    }
    
    /**
     * set utc minutes
     * @param utcMinutes utc minutes
     */
    public void setUtcMinutes(Integer utcMinutes) {
    	setValue(KEY_UTC_MINUTES, utcMinutes);
    }
    
    /**
     * get utc minutes
     * @return utc minutes
     */
    public Integer getUtcMinutes() {
    	return getInteger(KEY_UTC_MINUTES);
    }
    
    /**
     * set utc seconds
     * @param utcSeconds utc seconds
     */
    public void setUtcSeconds(Integer utcSeconds) {
    	setValue(KEY_UTC_SECONDS, utcSeconds);
    }
    
    /**
     * get utc seconds
     * @return utc seconds
     */
    public Integer getUtcSeconds() {
    	return getInteger(KEY_UTC_SECONDS);
    }
    public void setCompassDirection(CompassDirection compassDirection) {
    	setValue(KEY_COMPASS_DIRECTION, compassDirection);
    }
    public CompassDirection getCompassDirection() {
		return (CompassDirection) getObject(CompassDirection.class, KEY_COMPASS_DIRECTION);
    }
    
    /**
     * set the positional dilution of precision
     * @param pdop the positional dilution of precision
     */
    public void setPdop(Double pdop) {
    	setValue(KEY_PDOP, pdop);
    }
    
    /**
     * get  the positional dilution of precision
     */
    public Double getPdop() {
    	Object object = getValue(KEY_PDOP);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set the horizontal dilution of precision
     * @param hdop the horizontal dilution of precision
     */
    public void setHdop(Double hdop) {
    	setValue(KEY_HDOP, hdop);
    }
    
    /**
     * get  the horizontal dilution of precision
     * @return the horizontal dilution of precision
     */
    public Double getHdop() {
    	Object object = getValue(KEY_HDOP);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set the vertical dilution of precision
     * @param vdop the vertical dilution of precision
     */
    public void setVdop(Double vdop) {
    	setValue(KEY_VDOP, vdop);
    }
    
    /**
     * get  the vertical dilution of precision
     * @return the vertical dilution of precision
     */
    public Double getVdop() {
    	Object object = getValue(KEY_VDOP);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set what coordinates based on 
     * @param actual True, if coordinates are based on satellites.False, if based on dead reckoning
     */
    public void setActual(Boolean actual) {
    	setValue(KEY_ACTUAL, actual);
    }
    
    /**
     * get what coordinates based on 
     * @return True, if coordinates are based on satellites.False, if based on dead reckoning
     */
    public Boolean getActual() {
    	return getBoolean(KEY_ACTUAL);
    }
    
    /**
     * set the number of satellites in view
     * @param satellites the number of satellites in view
     */
    public void setSatellites(Integer satellites) {
    	setValue(KEY_SATELLITES, satellites);
    }
    
    /**
     * get  the number of satellites in view
     * @return the number of satellites in view
     */
    public Integer getSatellites() {
    	return getInteger(KEY_SATELLITES);
    }
    public void setDimension(Dimension dimension) {
    	setValue(KEY_DIMENSION, dimension);
    }
    public Dimension getDimension() {
		return (Dimension) getObject(Dimension.class, KEY_DIMENSION);
    }
    
    /**
     * set altitude in meters
     * @param altitude altitude in meters
     */
    public void setAltitude(Double altitude) {
    	setValue(KEY_ALTITUDE, altitude);
    }
    
    /**
     * get altitude in meters
     * @return altitude in meters
     */
    public Double getAltitude() {
    	Object object = getValue(KEY_ALTITUDE);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set the heading.North is 0, East is 90, etc.
     * @param heading the heading. 
     */
    public void setHeading(Double heading) {
    	setValue(KEY_HEADING, heading);
    }
    
    /**
     * get the heading
     */
    public Double getHeading() {
    	Object object = getValue(KEY_HEADING);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set speed in KPH
     * @param speed the speed
     */
    public void setSpeed(Double speed) {
    	setValue(KEY_SPEED, speed);
    }
    
    /**
     * get the speed in KPH
     * @return the speed in KPH
     */
    public Double getSpeed() {
    	Object object = getValue(KEY_SPEED);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
}
