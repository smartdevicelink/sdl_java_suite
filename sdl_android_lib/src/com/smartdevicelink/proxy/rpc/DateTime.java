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

    public Integer getMilliSecond() {
        return (Integer) store.get(KEY_MILLISECOND);
    }

    public void setMilliSecond(Integer milliSecond) {
        if (milliSecond != null) {
            store.put(KEY_MILLISECOND, milliSecond);
        } else {
            store.remove(KEY_MILLISECOND);
        }
    }

    public Integer getSecond() {
        return (Integer) store.get(KEY_SECOND);
    }

    public void setSecond(Integer second) {
        if (second != null) {
            store.put(KEY_SECOND, second);
        } else {
            store.remove(KEY_SECOND);
        }
    }

    public Integer getMinute() {
        return (Integer) store.get(KEY_MINUTE);
    }

    public void setMinute(Integer minute) {
        if (minute != null) {
            store.put(KEY_MINUTE, minute);
        } else {
            store.remove(KEY_MINUTE);
        }
    }

    public Integer getHour() {
        return (Integer) store.get(KEY_HOUR);
    }

    public void setHour(Integer hour) {
        if (hour != null) {
            store.put(KEY_HOUR, hour);
        } else {
            store.remove(KEY_HOUR);
        }
    }

    public Integer getDay() {
        return (Integer) store.get(KEY_DAY);
    }

    public void setDay(Integer day) {
        if (day != null) {
            store.put(KEY_DAY, day);
        } else {
            store.remove(KEY_DAY);
        }
    }

    public Integer getMonth() {
        return (Integer) store.get(KEY_MONTH);
    }

    public void setMonth(Integer month) {
        if (month != null) {
            store.put(KEY_MONTH, month);
        } else {
            store.remove(KEY_MONTH);
        }
    }

    public Integer getYear() {
        return (Integer) store.get(KEY_YEAR);
    }

    public void setYear(Integer year) {
        if (year != null) {
            store.put(KEY_YEAR, year);
        } else {
            store.remove(KEY_YEAR);
        }
    }

    public Integer getTzHour() {
        return (Integer) store.get(KEY_TZ_HOUR);
    }

    public void setTzHour(Integer tzHour) {
        if (tzHour != null) {
            store.put(KEY_TZ_HOUR, tzHour);
        } else {
            store.remove(KEY_TZ_HOUR);
        }
    }

    public Integer getTzMinute() {
        return (Integer) store.get(KEY_TZ_MINUTE);
    }

    public void setTzMinute(Integer tzMinute) {
        if (tzMinute != null) {
            store.put(KEY_TZ_MINUTE, tzMinute);
        } else {
            store.remove(KEY_TZ_MINUTE);
        }
    }
}
