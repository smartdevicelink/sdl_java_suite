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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class DateTime extends RPCStruct{
    public static final String KEY_MILLISECOND = "millisecond";
    public static final String KEY_SECOND = "second";
    public static final String KEY_MINUTE = "minute";
    public static final String KEY_HOUR = "hour";
    public static final String KEY_DAY = "day";
    public static final String KEY_MONTH = "month";
    public static final String KEY_YEAR = "year";
    public static final String KEY_TZ_HOUR = "tz_hour";
    public static final String KEY_TZ_MINUTE = "tz_minute";

    public DateTime() {
    }

    public DateTime(Hashtable<String, Object> hash) {
        super(hash);
    }

	
    /**
	* Gets the Milliseconds portion of the DateTime class
	* 
	* @return Integer - Milliseconds associated with this DateTime class 
	* 
    */
    public Integer getMilliSecond() {
        return getInteger(KEY_MILLISECOND);
    }

	/**
 	* Sets the Milliseconds portion of the DateTime class
	* 
	* @param milliSecond
	* The milliseconds associated with this DateTime class
	* 
	*/    
    public void setMilliSecond(Integer milliSecond) {
        setValue(KEY_MILLISECOND, milliSecond);
    }

    
    /**
	* Gets the Seconds portion of the DateTime class
	* 
	* @return Integer - Seconds associated with this DateTime class 
	* 
    */
    public Integer getSecond() {
        return getInteger(KEY_SECOND);
    }
    
	/**
 	* Sets the Seconds portion of the DateTime class
	* 
	* @param second
	* The Seconds associated with this DateTime class
	* 
	*/
    public void setSecond(Integer second) {
        setValue(KEY_SECOND, second);
    }

    
    /**
	* Gets the Minutes portion of the DateTime class
	* 
	* @return Integer - Minutes associated with this DateTime class 
	* 
    */    
    public Integer getMinute() {
        return getInteger(KEY_MINUTE);
    }
    
	/**
 	* Sets the Minutes portion of the DateTime class
	* 
	* @param minute
	* The Minutes associated with this DateTime class
	* 
	*/ 
    public void setMinute(Integer minute) {
        setValue(KEY_MINUTE, minute);
    }

    /**
	* Gets the Hours portion of the DateTime class.
	* 
	* @return Integer - Hours associated with this DateTime class.
	* 
    */
    public Integer getHour() {
        return getInteger(KEY_HOUR);
    }

	/**
 	* Sets the Hours portion of the DateTime class.  
	* 
	* @param hour
	* The Hours associated with this DateTime class.  This structure is used to store hours in a 24 hour format.
	* 
	*/ 
    public void setHour(Integer hour) {
        setValue(KEY_HOUR, hour);
    }

    /**
	* Gets the Day portion of the DateTime class.
	* 
	* @return Integer - Day of the month associated with this DateTime class 
	* 
    */
    public Integer getDay() {
        return getInteger(KEY_DAY);
    }

	/**
 	* Sets the Day portion of the DateTime class
	* 
	* @param day
	* The Day of the month associated with this DateTime class
	* 
	*/ 
    public void setDay(Integer day) {
        setValue(KEY_DAY, day);
    }

    /**
	* Gets the Month portion of the DateTime class.
	* 
	* @return Integer - Month of the year associated with this DateTime class 
	* 
    */    
    public Integer getMonth() {
        return getInteger(KEY_MONTH);
    }

	/**
 	* Sets the Month portion of the DateTime class
	* 
	* @param month
	* The Month of the year associate with this DateTime class
	* 
	*/ 
    public void setMonth(Integer month) {
        setValue(KEY_MONTH, month);
    }

    /**
	* Gets the Year portion of the DateTime class.
	* 
	* @return Integer - The year in YYYY format associated with this DateTime class 
	* 
    */
    public Integer getYear() {
        return getInteger(KEY_YEAR);
    }

	/**
 	* Sets the Year portion of the DateTime class
	* 
	* @param year
	* The Year in YYYY format associated with this DateTime class
	* 
	*/ 
    public void setYear(Integer year) {
        setValue(KEY_YEAR, year);
    }

    /**
	* Gets the Time Zone Hours portion of the DateTime class.
	* 
	* @return Integer - The time zone offset in Hours with regard to UTC time associated with this DateTime class 
	* 
    */
    public Integer getTzHour() {
        return getInteger(KEY_TZ_HOUR);
    }

	/**
 	* Sets the Time Zone Hours portion of the DateTime class
	* 
	* @param tzHour
	* The time zone offset in Hours with regard to UTC time associated with this DateTime class
	* 
	*/ 
    public void setTzHour(Integer tzHour) {
        setValue(KEY_TZ_HOUR, tzHour);
    }
    
    /**
	* Gets the Time Zone Minutes portion of the DateTime class.
	* 
	* @return Integer - The time zone offset in minutes with regard to UTC associated with this DateTime class 
	* 
    */
    public Integer getTzMinute() {
        return getInteger(KEY_TZ_MINUTE);
    }

	/**
 	* Sets the Time Zone Minutes portion of the DateTime class
	* 
	* @param tzMinute
	* The time zone offset in Minutes with regard to UTC associated with this DateTime class
	*/ 
    public void setTzMinute(Integer tzMinute) {
        setValue(KEY_TZ_MINUTE, tzMinute);
    }
}
