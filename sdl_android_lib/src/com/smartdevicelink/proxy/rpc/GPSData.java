package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.CompassDirection;
import com.smartdevicelink.proxy.rpc.enums.Dimension;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.util.DebugTool;

/**
 * Describes the GPS data. Not all data will be available on all carlines.
 * <p><b>Parameter List
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
 * 			<td>Minvalue: 1<b>Maxvalue: 23
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>utcMinutes</td>
 * 			<td>Integer</td>
 * 			<td>Minvalue: 1<b>Maxvalue: 59
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 * 		<tr>
 * 			<td>utcSeconds</td>
 * 			<td>Integer</td>
 * 			<td>Minvalue: 1<b>Maxvalue: 59
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
 *					<b>Minvalue: -10000
 *					<b>Maxvalue: 10000
 * 			</td>
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
	public static final String longitudeDegrees = "longitudeDegrees";
	public static final String latitudeDegrees = "latitudeDegrees";
	public static final String utcYear = "utcYear";
	public static final String utcMonth = "utcMonth";
	public static final String utcDay = "utcDay";
	public static final String utcHours = "utcHours";
	public static final String utcMinutes = "utcMinutes";
	public static final String utcSeconds = "utcSeconds";
	public static final String compassDirection = "compassDirection";
	public static final String pdop = "pdop";
	public static final String vdop = "vdop";
	public static final String hdop = "hdop";
	public static final String actual = "actual";
	public static final String satellites = "satellites";
	public static final String dimension = "dimension";
	public static final String altitude = "altitude";
	public static final String heading = "heading";
	public static final String speed = "speed";

	/**
	 * Constructs a newly allocated GPSData object
	 */
    public GPSData() { }
    
    /**
     * Constructs a newly allocated GPSData object indicated by the Hashtable parameter 
     * @param hash The Hashtable to use
     */    
    public GPSData(Hashtable hash) {
        super(hash);
    }
    
    /**
     * set longitude degrees
     * @param longitudeDegrees
     */
    public void setLongitudeDegrees(Double longitudeDegrees) {
    	if (longitudeDegrees != null) {
    		store.put(GPSData.longitudeDegrees, longitudeDegrees);
    	} else {
    		store.remove(GPSData.longitudeDegrees);
    	}
    }
    
    /**
     * get longitude degrees 
     * @return longitude degrees
     */
    public Double getLongitudeDegrees() {
    	return (Double) store.get(GPSData.longitudeDegrees);
    }
    
    /**
     * set latitude degrees
     * @param latitudeDegrees latitude degrees
     */
    public void setLatitudeDegrees(Double latitudeDegrees) {
    	if (latitudeDegrees != null) {
    		store.put(GPSData.latitudeDegrees, latitudeDegrees);
    	} else {
    		store.remove(GPSData.latitudeDegrees);
    	}
    }
    
    /**
     * get  latitude degrees
     * @return latitude degrees
     */
    public Double getLatitudeDegrees() {
    	return (Double) store.get(GPSData.latitudeDegrees);
    }
    
    /**
     * set utc year
     * @param utcYear utc year
     */
    public void setUtcYear(Integer utcYear) {
    	if (utcYear != null) {
    		store.put(GPSData.utcYear, utcYear);
    	} else {
    		store.remove(GPSData.utcYear);
    	}
    }
    
    /**
     * get utc year
     * @return utc year
     */
    public Integer getUtcYear() {
    	return (Integer) store.get(GPSData.utcYear);
    }
    
    /**
     * set utc month
     * @param utcMonth utc month
     */
    public void setUtcMonth(Integer utcMonth) {
    	if (utcMonth != null) {
    		store.put(GPSData.utcMonth, utcMonth);
    	} else {
    		store.remove(GPSData.utcMonth);
    	}
    }
    
    /**
     * get utc month
     * @return utc month
     */
    public Integer getUtcMonth() {
    	return (Integer) store.get(GPSData.utcMonth);
    }
    
    /**
     * set utc day
     * @param utcDay utc day
     */
    public void setUtcDay(Integer utcDay) {
    	if (utcDay != null) {
    		store.put(GPSData.utcDay, utcDay);
    	} else {
    		store.remove(GPSData.utcDay);
    	}
    }
    
    /**
     * get utc day
     * @return utc day
     */
    public Integer getUtcDay() {
    	return (Integer) store.get(GPSData.utcDay);
    }
    
    /**
     * set utc hours
     * @param utcHours utc hours
     */
    public void setUtcHours(Integer utcHours) {
    	if (utcHours != null) {
    		store.put(GPSData.utcHours, utcHours);
    	} else {
    		store.remove(GPSData.utcHours);
    	}
    }
    
    /**
     * get utc hours
     * @return utc hours
     */
    public Integer getUtcHours() {
    	return (Integer) store.get(GPSData.utcHours);
    }
    
    /**
     * set utc minutes
     * @param utcMinutes utc minutes
     */
    public void setUtcMinutes(Integer utcMinutes) {
    	if (utcMinutes != null) {
    		store.put(GPSData.utcMinutes, utcMinutes);
    	} else {
    		store.remove(GPSData.utcMinutes);
    	}
    }
    
    /**
     * get utc minutes
     * @return utc minutes
     */
    public Integer getUtcMinutes() {
    	return (Integer) store.get(GPSData.utcMinutes);
    }
    
    /**
     * set utc seconds
     * @param utcSeconds utc seconds
     */
    public void setUtcSeconds(Integer utcSeconds) {
    	if (utcSeconds != null) {
    		store.put(GPSData.utcSeconds, utcSeconds);
    	} else {
    		store.remove(GPSData.utcSeconds);
    	}
    }
    
    /**
     * get utc seconds
     * @return utc seconds
     */
    public Integer getUtcSeconds() {
    	return (Integer) store.get(GPSData.utcSeconds);
    }
    public void setCompassDirection(CompassDirection compassDirection) {
    	if (compassDirection != null) {
    		store.put(GPSData.compassDirection, compassDirection);
    	} else {
    		store.remove(GPSData.compassDirection);
    	}
    }
    public CompassDirection getCompassDirection() {
        Object obj = store.get(GPSData.compassDirection);
        if (obj instanceof CompassDirection) {
            return (CompassDirection) obj;
        } else if (obj instanceof String) {
        	CompassDirection theCode = null;
            try {
                theCode = CompassDirection.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GPSData.compassDirection, e);
            }
            return theCode;
        }
        return null;
    }
    
    /**
     * set the positional dilution of precision
     * @param pdop the positional dilution of precision
     */
    public void setPdop(Double pdop) {
    	if (pdop != null) {
    		store.put(GPSData.pdop, pdop);
    	} else {
    		store.remove(GPSData.pdop);
    	}
    }
    
    /**
     * get  the positional dilution of precision
     */
    public Double getPdop() {
    	return (Double) store.get(GPSData.pdop);
    }
    
    /**
     * set the horizontal dilution of precision
     * @param hdop the horizontal dilution of precision
     */
    public void setHdop(Double hdop) {
    	if (hdop != null) {
    		store.put(GPSData.hdop, hdop);
    	} else {
    		store.remove(GPSData.hdop);
    	}
    }
    
    /**
     * get  the horizontal dilution of precision
     * @return the horizontal dilution of precision
     */
    public Double getHdop() {
    	return (Double) store.get(GPSData.hdop);
    }
    
    /**
     * set the vertical dilution of precision
     * @param vdop the vertical dilution of precision
     */
    public void setVdop(Double vdop) {
    	if (vdop != null) {
    		store.put(GPSData.vdop, vdop);
    	} else {
    		store.remove(GPSData.vdop);
    	}
    }
    
    /**
     * get  the vertical dilution of precision
     * @return the vertical dilution of precision
     */
    public Double getVdop() {
    	return (Double) store.get(GPSData.vdop);
    }
    
    /**
     * set what coordinates based on 
     * @param actual True, if coordinates are based on satellites.False, if based on dead reckoning
     */
    public void setActual(Boolean actual) {
    	if (actual != null) {
    		store.put(GPSData.actual, actual);
    	} else {
    		store.remove(GPSData.actual);
    	}
    }
    
    /**
     * get what coordinates based on 
     * @return True, if coordinates are based on satellites.False, if based on dead reckoning
     */
    public Boolean getActual() {
    	return (Boolean) store.get(GPSData.actual);
    }
    
    /**
     * set the number of satellites in view
     * @param satellites the number of satellites in view
     */
    public void setSatellites(Integer satellites) {
    	if (satellites != null) {
    		store.put(GPSData.satellites, satellites);
    	} else {
    		store.remove(GPSData.satellites);
    	}
    }
    
    /**
     * get  the number of satellites in view
     * @return the number of satellites in view
     */
    public Integer getSatellites() {
    	return (Integer) store.get(GPSData.satellites);
    }
    public void setDimension(Dimension dimension) {
    	if (dimension != null) {
    		store.put(GPSData.dimension, dimension);
    	} else {
    		store.remove(GPSData.dimension);
    	}
    }
    public Dimension getDimension() {
        Object obj = store.get(GPSData.dimension);
        if (obj instanceof Dimension) {
            return (Dimension) obj;
        } else if (obj instanceof String) {
        	Dimension theCode = null;
            try {
                theCode = Dimension.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + GPSData.dimension, e);
            }
            return theCode;
        }
        return null;
    }
    
    /**
     * set altitude in meters
     * @param altitude altitude in meters
     */
    public void setAltitude(Double altitude) {
    	if (altitude != null) {
    		store.put(GPSData.altitude, altitude);
    	} else {
    		store.remove(GPSData.altitude);
    	}
    }
    
    /**
     * get altitude in meters
     * @return altitude in meters
     */
    public Double getAltitude() {
    	return (Double) store.get(GPSData.altitude);
    }
    
    /**
     * set the heading.North is 0, East is 90, etc.
     * @param heading the heading. 
     */
    public void setHeading(Double heading) {
    	if (heading != null) {
    		store.put(GPSData.heading, heading);
    	} else {
    		store.remove(GPSData.heading);
    	}
    }
    
    /**
     * get the heading
     */
    public Double getHeading() {
    	return (Double) store.get(GPSData.heading);
    }
    
    /**
     * set speed in KPH
     * @param speed the speed
     */
    public void setSpeed(Double speed) {
    	if (speed != null) {
    		store.put(GPSData.speed, speed);
    	} else {
    		store.remove(GPSData.speed);
    	}
    }
    
    /**
     * get the speed in KPH
     * @return the speed in KPH
     */
    public Double getSpeed() {
    	return (Double) store.get(GPSData.speed);
    }
}
