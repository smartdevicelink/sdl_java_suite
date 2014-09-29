package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
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
    		store.put(Names.longitudeDegrees, longitudeDegrees);
    	} else {
    		store.remove(Names.longitudeDegrees);
    	}
    }
    
    /**
     * get longitude degrees 
     * @return longitude degrees
     */
    public Double getLongitudeDegrees() {
    	return (Double) store.get(Names.longitudeDegrees);
    }
    
    /**
     * set latitude degrees
     * @param latitudeDegrees latitude degrees
     */
    public void setLatitudeDegrees(Double latitudeDegrees) {
    	if (latitudeDegrees != null) {
    		store.put(Names.latitudeDegrees, latitudeDegrees);
    	} else {
    		store.remove(Names.latitudeDegrees);
    	}
    }
    
    /**
     * get  latitude degrees
     * @return latitude degrees
     */
    public Double getLatitudeDegrees() {
    	return (Double) store.get(Names.latitudeDegrees);
    }
    
    /**
     * set utc year
     * @param utcYear utc year
     */
    public void setUtcYear(Integer utcYear) {
    	if (utcYear != null) {
    		store.put(Names.utcYear, utcYear);
    	} else {
    		store.remove(Names.utcYear);
    	}
    }
    
    /**
     * get utc year
     * @return utc year
     */
    public Integer getUtcYear() {
    	return (Integer) store.get(Names.utcYear);
    }
    
    /**
     * set utc month
     * @param utcMonth utc month
     */
    public void setUtcMonth(Integer utcMonth) {
    	if (utcMonth != null) {
    		store.put(Names.utcMonth, utcMonth);
    	} else {
    		store.remove(Names.utcMonth);
    	}
    }
    
    /**
     * get utc month
     * @return utc month
     */
    public Integer getUtcMonth() {
    	return (Integer) store.get(Names.utcMonth);
    }
    
    /**
     * set utc day
     * @param utcDay utc day
     */
    public void setUtcDay(Integer utcDay) {
    	if (utcDay != null) {
    		store.put(Names.utcDay, utcDay);
    	} else {
    		store.remove(Names.utcDay);
    	}
    }
    
    /**
     * get utc day
     * @return utc day
     */
    public Integer getUtcDay() {
    	return (Integer) store.get(Names.utcDay);
    }
    
    /**
     * set utc hours
     * @param utcHours utc hours
     */
    public void setUtcHours(Integer utcHours) {
    	if (utcHours != null) {
    		store.put(Names.utcHours, utcHours);
    	} else {
    		store.remove(Names.utcHours);
    	}
    }
    
    /**
     * get utc hours
     * @return utc hours
     */
    public Integer getUtcHours() {
    	return (Integer) store.get(Names.utcHours);
    }
    
    /**
     * set utc minutes
     * @param utcMinutes utc minutes
     */
    public void setUtcMinutes(Integer utcMinutes) {
    	if (utcMinutes != null) {
    		store.put(Names.utcMinutes, utcMinutes);
    	} else {
    		store.remove(Names.utcMinutes);
    	}
    }
    
    /**
     * get utc minutes
     * @return utc minutes
     */
    public Integer getUtcMinutes() {
    	return (Integer) store.get(Names.utcMinutes);
    }
    
    /**
     * set utc seconds
     * @param utcSeconds utc seconds
     */
    public void setUtcSeconds(Integer utcSeconds) {
    	if (utcSeconds != null) {
    		store.put(Names.utcSeconds, utcSeconds);
    	} else {
    		store.remove(Names.utcSeconds);
    	}
    }
    
    /**
     * get utc seconds
     * @return utc seconds
     */
    public Integer getUtcSeconds() {
    	return (Integer) store.get(Names.utcSeconds);
    }
    public void setCompassDirection(CompassDirection compassDirection) {
    	if (compassDirection != null) {
    		store.put(Names.compassDirection, compassDirection);
    	} else {
    		store.remove(Names.compassDirection);
    	}
    }
    public CompassDirection getCompassDirection() {
        Object obj = store.get(Names.compassDirection);
        if (obj instanceof CompassDirection) {
            return (CompassDirection) obj;
        } else if (obj instanceof String) {
        	CompassDirection theCode = null;
            try {
                theCode = CompassDirection.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.compassDirection, e);
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
    		store.put(Names.pdop, pdop);
    	} else {
    		store.remove(Names.pdop);
    	}
    }
    
    /**
     * get  the positional dilution of precision
     */
    public Double getPdop() {
    	return (Double) store.get(Names.pdop);
    }
    
    /**
     * set the horizontal dilution of precision
     * @param hdop the horizontal dilution of precision
     */
    public void setHdop(Double hdop) {
    	if (hdop != null) {
    		store.put(Names.hdop, hdop);
    	} else {
    		store.remove(Names.hdop);
    	}
    }
    
    /**
     * get  the horizontal dilution of precision
     * @return the horizontal dilution of precision
     */
    public Double getHdop() {
    	return (Double) store.get(Names.hdop);
    }
    
    /**
     * set the vertical dilution of precision
     * @param vdop the vertical dilution of precision
     */
    public void setVdop(Double vdop) {
    	if (vdop != null) {
    		store.put(Names.vdop, vdop);
    	} else {
    		store.remove(Names.vdop);
    	}
    }
    
    /**
     * get  the vertical dilution of precision
     * @return the vertical dilution of precision
     */
    public Double getVdop() {
    	return (Double) store.get(Names.vdop);
    }
    
    /**
     * set what coordinates based on 
     * @param actual True, if coordinates are based on satellites.False, if based on dead reckoning
     */
    public void setActual(Boolean actual) {
    	if (actual != null) {
    		store.put(Names.actual, actual);
    	} else {
    		store.remove(Names.actual);
    	}
    }
    
    /**
     * get what coordinates based on 
     * @return True, if coordinates are based on satellites.False, if based on dead reckoning
     */
    public Boolean getActual() {
    	return (Boolean) store.get(Names.actual);
    }
    
    /**
     * set the number of satellites in view
     * @param satellites the number of satellites in view
     */
    public void setSatellites(Integer satellites) {
    	if (satellites != null) {
    		store.put(Names.satellites, satellites);
    	} else {
    		store.remove(Names.satellites);
    	}
    }
    
    /**
     * get  the number of satellites in view
     * @return the number of satellites in view
     */
    public Integer getSatellites() {
    	return (Integer) store.get(Names.satellites);
    }
    public void setDimension(Dimension dimension) {
    	if (dimension != null) {
    		store.put(Names.dimension, dimension);
    	} else {
    		store.remove(Names.dimension);
    	}
    }
    public Dimension getDimension() {
        Object obj = store.get(Names.dimension);
        if (obj instanceof Dimension) {
            return (Dimension) obj;
        } else if (obj instanceof String) {
        	Dimension theCode = null;
            try {
                theCode = Dimension.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.dimension, e);
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
    		store.put(Names.altitude, altitude);
    	} else {
    		store.remove(Names.altitude);
    	}
    }
    
    /**
     * get altitude in meters
     * @return altitude in meters
     */
    public Double getAltitude() {
    	return (Double) store.get(Names.altitude);
    }
    
    /**
     * set the heading.North is 0, East is 90, etc.
     * @param heading the heading. 
     */
    public void setHeading(Double heading) {
    	if (heading != null) {
    		store.put(Names.heading, heading);
    	} else {
    		store.remove(Names.heading);
    	}
    }
    
    /**
     * get the heading
     */
    public Double getHeading() {
    	return (Double) store.get(Names.heading);
    }
    
    /**
     * set speed in KPH
     * @param speed the speed
     */
    public void setSpeed(Double speed) {
    	if (speed != null) {
    		store.put(Names.speed, speed);
    	} else {
    		store.remove(Names.speed);
    	}
    }
    
    /**
     * get the speed in KPH
     * @return the speed in KPH
     */
    public Double getSpeed() {
    	return (Double) store.get(Names.speed);
    }
}
