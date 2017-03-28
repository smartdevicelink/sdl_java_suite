package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.CompassDirection;
import com.smartdevicelink.proxy.rpc.enums.Dimension;
import com.smartdevicelink.util.SdlDataTypeConverter;
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
 * 			<td>Positional Dilution of Precision<b>Minvalue: 0<b>Maxvalue: 31
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>hdop</td>
 * 			<td>Integer</td>
 * 			<td>Horizontal Dilution of Precision<b>Minvalue: 0<b>Maxvalue: 31
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>vdop</td>
 * 			<td>Integer</td>
 * 			<td>Vertical  Dilution of Precision<b>Minvalue: 0<b>Maxvalue: 31
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
 *					<b>Maxvalue: 400
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
     * set longitude degrees
     * @param longitudeDegrees
     */
    public void setLongitudeDegrees(Double longitudeDegrees) {
    	if (longitudeDegrees != null) {
    		store.put(KEY_LONGITUDE_DEGREES, longitudeDegrees);
    	} else {
    		store.remove(KEY_LONGITUDE_DEGREES);
    	}
    }
    
    /**
     * get longitude degrees 
     * @return longitude degrees
     */
    public Double getLongitudeDegrees() {
    	Object object = store.get(KEY_LONGITUDE_DEGREES);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set latitude degrees
     * @param latitudeDegrees latitude degrees
     */
    public void setLatitudeDegrees(Double latitudeDegrees) {
    	if (latitudeDegrees != null) {
    		store.put(KEY_LATITUDE_DEGREES, latitudeDegrees);
    	} else {
    		store.remove(KEY_LATITUDE_DEGREES);
    	}
    }
    
    /**
     * get  latitude degrees
     * @return latitude degrees
     */
    public Double getLatitudeDegrees() {
    	Object object = store.get(KEY_LATITUDE_DEGREES);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set utc year
     * @param utcYear utc year
     */
    public void setUtcYear(Integer utcYear) {
    	if (utcYear != null) {
    		store.put(KEY_UTC_YEAR, utcYear);
    	} else {
    		store.remove(KEY_UTC_YEAR);
    	}
    }
    
    /**
     * get utc year
     * @return utc year
     */
    public Integer getUtcYear() {
    	return (Integer) store.get(KEY_UTC_YEAR);
    }
    
    /**
     * set utc month
     * @param utcMonth utc month
     */
    public void setUtcMonth(Integer utcMonth) {
    	if (utcMonth != null) {
    		store.put(KEY_UTC_MONTH, utcMonth);
    	} else {
    		store.remove(KEY_UTC_MONTH);
    	}
    }
    
    /**
     * get utc month
     * @return utc month
     */
    public Integer getUtcMonth() {
    	return (Integer) store.get(KEY_UTC_MONTH);
    }
    
    /**
     * set utc day
     * @param utcDay utc day
     */
    public void setUtcDay(Integer utcDay) {
    	if (utcDay != null) {
    		store.put(KEY_UTC_DAY, utcDay);
    	} else {
    		store.remove(KEY_UTC_DAY);
    	}
    }
    
    /**
     * get utc day
     * @return utc day
     */
    public Integer getUtcDay() {
    	return (Integer) store.get(KEY_UTC_DAY);
    }
    
    /**
     * set utc hours
     * @param utcHours utc hours
     */
    public void setUtcHours(Integer utcHours) {
    	if (utcHours != null) {
    		store.put(KEY_UTC_HOURS, utcHours);
    	} else {
    		store.remove(KEY_UTC_HOURS);
    	}
    }
    
    /**
     * get utc hours
     * @return utc hours
     */
    public Integer getUtcHours() {
    	return (Integer) store.get(KEY_UTC_HOURS);
    }
    
    /**
     * set utc minutes
     * @param utcMinutes utc minutes
     */
    public void setUtcMinutes(Integer utcMinutes) {
    	if (utcMinutes != null) {
    		store.put(KEY_UTC_MINUTES, utcMinutes);
    	} else {
    		store.remove(KEY_UTC_MINUTES);
    	}
    }
    
    /**
     * get utc minutes
     * @return utc minutes
     */
    public Integer getUtcMinutes() {
    	return (Integer) store.get(KEY_UTC_MINUTES);
    }
    
    /**
     * set utc seconds
     * @param utcSeconds utc seconds
     */
    public void setUtcSeconds(Integer utcSeconds) {
    	if (utcSeconds != null) {
    		store.put(KEY_UTC_SECONDS, utcSeconds);
    	} else {
    		store.remove(KEY_UTC_SECONDS);
    	}
    }
    
    /**
     * get utc seconds
     * @return utc seconds
     */
    public Integer getUtcSeconds() {
    	return (Integer) store.get(KEY_UTC_SECONDS);
    }
    public void setCompassDirection(CompassDirection compassDirection) {
    	if (compassDirection != null) {
    		store.put(KEY_COMPASS_DIRECTION, compassDirection);
    	} else {
    		store.remove(KEY_COMPASS_DIRECTION);
    	}
    }
    public CompassDirection getCompassDirection() {
        Object obj = store.get(KEY_COMPASS_DIRECTION);
        if (obj instanceof CompassDirection) {
            return (CompassDirection) obj;
        } else if (obj instanceof String) {
        	return CompassDirection.valueForString((String) obj);
        }
        return null;
    }
    
    /**
     * set the positional dilution of precision
     * @param pdop the positional dilution of precision
     */
    public void setPdop(Double pdop) {
    	if (pdop != null) {
    		store.put(KEY_PDOP, pdop);
    	} else {
    		store.remove(KEY_PDOP);
    	}
    }
    
    /**
     * get  the positional dilution of precision
     */
    public Double getPdop() {
    	Object object = store.get(KEY_PDOP);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set the horizontal dilution of precision
     * @param hdop the horizontal dilution of precision
     */
    public void setHdop(Double hdop) {
    	if (hdop != null) {
    		store.put(KEY_HDOP, hdop);
    	} else {
    		store.remove(KEY_HDOP);
    	}
    }
    
    /**
     * get  the horizontal dilution of precision
     * @return the horizontal dilution of precision
     */
    public Double getHdop() {
    	Object object = store.get(KEY_HDOP);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set the vertical dilution of precision
     * @param vdop the vertical dilution of precision
     */
    public void setVdop(Double vdop) {
    	if (vdop != null) {
    		store.put(KEY_VDOP, vdop);
    	} else {
    		store.remove(KEY_VDOP);
    	}
    }
    
    /**
     * get  the vertical dilution of precision
     * @return the vertical dilution of precision
     */
    public Double getVdop() {
    	Object object = store.get(KEY_VDOP);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set what coordinates based on 
     * @param actual True, if coordinates are based on satellites.False, if based on dead reckoning
     */
    public void setActual(Boolean actual) {
    	if (actual != null) {
    		store.put(KEY_ACTUAL, actual);
    	} else {
    		store.remove(KEY_ACTUAL);
    	}
    }
    
    /**
     * get what coordinates based on 
     * @return True, if coordinates are based on satellites.False, if based on dead reckoning
     */
    public Boolean getActual() {
    	return (Boolean) store.get(KEY_ACTUAL);
    }
    
    /**
     * set the number of satellites in view
     * @param satellites the number of satellites in view
     */
    public void setSatellites(Integer satellites) {
    	if (satellites != null) {
    		store.put(KEY_SATELLITES, satellites);
    	} else {
    		store.remove(KEY_SATELLITES);
    	}
    }
    
    /**
     * get  the number of satellites in view
     * @return the number of satellites in view
     */
    public Integer getSatellites() {
    	return (Integer) store.get(KEY_SATELLITES);
    }
    public void setDimension(Dimension dimension) {
    	if (dimension != null) {
    		store.put(KEY_DIMENSION, dimension);
    	} else {
    		store.remove(KEY_DIMENSION);
    	}
    }
    public Dimension getDimension() {
        Object obj = store.get(KEY_DIMENSION);
        if (obj instanceof Dimension) {
            return (Dimension) obj;
        } else if (obj instanceof String) {
        	return Dimension.valueForString((String) obj);
        }
        return null;
    }
    
    /**
     * set altitude in meters
     * @param altitude altitude in meters
     */
    public void setAltitude(Double altitude) {
    	if (altitude != null) {
    		store.put(KEY_ALTITUDE, altitude);
    	} else {
    		store.remove(KEY_ALTITUDE);
    	}
    }
    
    /**
     * get altitude in meters
     * @return altitude in meters
     */
    public Double getAltitude() {
    	Object object = store.get(KEY_ALTITUDE);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set the heading.North is 0, East is 90, etc.
     * @param heading the heading. 
     */
    public void setHeading(Double heading) {
    	if (heading != null) {
    		store.put(KEY_HEADING, heading);
    	} else {
    		store.remove(KEY_HEADING);
    	}
    }
    
    /**
     * get the heading
     */
    public Double getHeading() {
    	Object object = store.get(KEY_HEADING);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
    
    /**
     * set speed in KPH
     * @param speed the speed
     */
    public void setSpeed(Double speed) {
    	if (speed != null) {
    		store.put(KEY_SPEED, speed);
    	} else {
    		store.remove(KEY_SPEED);
    	}
    }
    
    /**
     * get the speed in KPH
     * @return the speed in KPH
     */
    public Double getSpeed() {
    	Object object = store.get(KEY_SPEED);
    	return SdlDataTypeConverter.objectToDouble(object);
    }
}
