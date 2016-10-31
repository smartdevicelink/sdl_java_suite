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
        return (Integer) store.get(KEY_MILLISECOND);
    }

	/**
 	* Sets the Milliseconds portion of the DateTime class
	* 
	* @param milliSecond
	* The milliseconds associated with this DateTime class
	* 
	*/    
    public void setMilliSecond(Integer milliSecond) {
        if (milliSecond != null) {
            store.put(KEY_MILLISECOND, milliSecond);
        } else {
            store.remove(KEY_MILLISECOND);
        }
    }

    
    /**
	* Gets the Seconds portion of the DateTime class
	* 
	* @return Integer - Seconds associated with this DateTime class 
	* 
    */
    public Integer getSecond() {
        return (Integer) store.get(KEY_SECOND);
    }
    
	/**
 	* Sets the Seconds portion of the DateTime class
	* 
	* @param second
	* The Seconds associated with this DateTime class
	* 
	*/
    public void setSecond(Integer second) {
        if (second != null) {
            store.put(KEY_SECOND, second);
        } else {
            store.remove(KEY_SECOND);
        }
    }

    
    /**
	* Gets the Minutes portion of the DateTime class
	* 
	* @return Integer - Minutes associated with this DateTime class 
	* 
    */    
    public Integer getMinute() {
        return (Integer) store.get(KEY_MINUTE);
    }
    
	/**
 	* Sets the Minutes portion of the DateTime class
	* 
	* @param minute
	* The Minutes associated with this DateTime class
	* 
	*/ 
    public void setMinute(Integer minute) {
        if (minute != null) {
            store.put(KEY_MINUTE, minute);
        } else {
            store.remove(KEY_MINUTE);
        }
    }

    /**
	* Gets the Hours portion of the DateTime class.
	* 
	* @return Integer - Hours associated with this DateTime class.
	* 
    */
    public Integer getHour() {
        return (Integer) store.get(KEY_HOUR);
    }

	/**
 	* Sets the Hours portion of the DateTime class.  
	* 
	* @param hour
	* The Hours associated with this DateTime class.  This structure is used to store hours in a 24 hour format.
	* 
	*/ 
    public void setHour(Integer hour) {
        if (hour != null) {
            store.put(KEY_HOUR, hour);
        } else {
            store.remove(KEY_HOUR);
        }
    }

    /**
	* Gets the Day portion of the DateTime class.
	* 
	* @return Integer - Day of the month associated with this DateTime class 
	* 
    */
    public Integer getDay() {
        return (Integer) store.get(KEY_DAY);
    }

	/**
 	* Sets the Day portion of the DateTime class
	* 
	* @param day
	* The Day of the month associated with this DateTime class
	* 
	*/ 
    public void setDay(Integer day) {
        if (day != null) {
            store.put(KEY_DAY, day);
        } else {
            store.remove(KEY_DAY);
        }
    }

    /**
	* Gets the Month portion of the DateTime class.
	* 
	* @return Integer - Month of the year associated with this DateTime class 
	* 
    */    
    public Integer getMonth() {
        return (Integer) store.get(KEY_MONTH);
    }

	/**
 	* Sets the Month portion of the DateTime class
	* 
	* @param month
	* The Month of the year associate with this DateTime class
	* 
	*/ 
    public void setMonth(Integer month) {
        if (month != null) {
            store.put(KEY_MONTH, month);
        } else {
            store.remove(KEY_MONTH);
        }
    }

    /**
	* Gets the Year portion of the DateTime class.
	* 
	* @return Integer - The year in YYYY format associated with this DateTime class 
	* 
    */
    public Integer getYear() {
        return (Integer) store.get(KEY_YEAR);
    }

	/**
 	* Sets the Year portion of the DateTime class
	* 
	* @param year
	* The Year in YYYY format associated with this DateTime class
	* 
	*/ 
    public void setYear(Integer year) {
        if (year != null) {
            store.put(KEY_YEAR, year);
        } else {
            store.remove(KEY_YEAR);
        }
    }

    /**
	* Gets the Time Zone Hours portion of the DateTime class.
	* 
	* @return Integer - The time zone offset in Hours with regard to UTC time associated with this DateTime class 
	* 
    */
    public Integer getTzHour() {
        return (Integer) store.get(KEY_TZ_HOUR);
    }

	/**
 	* Sets the Time Zone Hours portion of the DateTime class
	* 
	* @param tzHour
	* The time zone offset in Hours with regard to UTC time associated with this DateTime class
	* 
	*/ 
    public void setTzHour(Integer tzHour) {
        if (tzHour != null) {
            store.put(KEY_TZ_HOUR, tzHour);
        } else {
            store.remove(KEY_TZ_HOUR);
        }
    }
    
    /**
	* Gets the Time Zone Minutes portion of the DateTime class.
	* 
	* @return Integer - The time zone offset in minutes with regard to UTC associated with this DateTime class 
	* 
    */
    public Integer getTzMinute() {
        return (Integer) store.get(KEY_TZ_MINUTE);
    }

	/**
 	* Sets the Time Zone Minutes portion of the DateTime class
	* 
	* @param tzMinute
	* The time zone offset in Minutes with regard to UTC associated with this DateTime class
	*/ 
    public void setTzMinute(Integer tzMinute) {
        if (tzMinute != null) {
            store.put(KEY_TZ_MINUTE, tzMinute);
        } else {
            store.remove(KEY_TZ_MINUTE);
        }
    }
}
