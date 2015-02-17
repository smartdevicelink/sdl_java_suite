package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.CompassDirection;
import com.smartdevicelink.proxy.rpc.enums.Dimension;
import com.smartdevicelink.util.JsonUtils;

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
public class GPSData extends RPCObject {
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

	private Double longitude, latitude, pdop, vdop, hdop, altitude, heading, speed;
	private Integer year, month, day, hours, minutes, seconds, satellites;
	private Boolean actual;
	private String compassDirection; // represents CompassDirection enum
	private String dimension; // represents Dimension enum
	
	/**
	 * Constructs a newly allocated GPSData object
	 */
    public GPSData() { }
    
    /**
     * Creates a GPSData object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public GPSData(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.longitude = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_LONGITUDE_DEGREES);
            this.latitude = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_LATITUDE_DEGREES);
            this.pdop = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_PDOP);
            this.vdop = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_VDOP);
            this.hdop = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_HDOP);
            this.altitude = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_ALTITUDE);
            this.heading = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_HEADING);
            this.speed = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_SPEED);
            
            this.year = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_UTC_YEAR);
            this.month = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_UTC_MONTH);
            this.day = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_UTC_DAY);
            this.hours = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_UTC_HOURS);
            this.minutes = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_UTC_MINUTES);
            this.seconds = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_UTC_SECONDS);
            this.satellites = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_SATELLITES);
            
            this.actual = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_ACTUAL);
            
            this.compassDirection = JsonUtils.readStringFromJsonObject(jsonObject, KEY_COMPASS_DIRECTION);
            this.dimension = JsonUtils.readStringFromJsonObject(jsonObject, KEY_DIMENSION);
            break;
        }
    }
    
    /**
     * set longitude degrees
     * @param longitudeDegrees
     */
    public void setLongitudeDegrees(Double longitudeDegrees) {
    	this.longitude = longitudeDegrees;
    }
    
    /**
     * get longitude degrees 
     * @return longitude degrees
     */
    public Double getLongitudeDegrees() {
    	return this.longitude;
    }
    
    /**
     * set latitude degrees
     * @param latitudeDegrees latitude degrees
     */
    public void setLatitudeDegrees(Double latitudeDegrees) {
    	this.latitude = latitudeDegrees;
    }
    
    /**
     * get  latitude degrees
     * @return latitude degrees
     */
    public Double getLatitudeDegrees() {
    	return this.latitude;
    }
    
    /**
     * set utc year
     * @param utcYear utc year
     */
    public void setUtcYear(Integer utcYear) {
    	this.year = utcYear;
    }
    
    /**
     * get utc year
     * @return utc year
     */
    public Integer getUtcYear() {
    	return this.year;
    }
    
    /**
     * set utc month
     * @param utcMonth utc month
     */
    public void setUtcMonth(Integer utcMonth) {
    	this.month = utcMonth;
    }
    
    /**
     * get utc month
     * @return utc month
     */
    public Integer getUtcMonth() {
    	return this.month;
    }
    
    /**
     * set utc day
     * @param utcDay utc day
     */
    public void setUtcDay(Integer utcDay) {
    	this.day = utcDay;
    }
    
    /**
     * get utc day
     * @return utc day
     */
    public Integer getUtcDay() {
    	return this.day;
    }
    
    /**
     * set utc hours
     * @param utcHours utc hours
     */
    public void setUtcHours(Integer utcHours) {
    	this.hours = utcHours;
    }
    
    /**
     * get utc hours
     * @return utc hours
     */
    public Integer getUtcHours() {
    	return this.hours;
    }
    
    /**
     * set utc minutes
     * @param utcMinutes utc minutes
     */
    public void setUtcMinutes(Integer utcMinutes) {
    	this.minutes = utcMinutes;
    }
    
    /**
     * get utc minutes
     * @return utc minutes
     */
    public Integer getUtcMinutes() {
    	return this.minutes;
    }
    
    /**
     * set utc seconds
     * @param utcSeconds utc seconds
     */
    public void setUtcSeconds(Integer utcSeconds) {
    	this.seconds = utcSeconds;
    }
    
    /**
     * get utc seconds
     * @return utc seconds
     */
    public Integer getUtcSeconds() {
    	return this.seconds;
    }
    
    public void setCompassDirection(CompassDirection compassDirection) {
    	this.compassDirection = (compassDirection == null) ? null : compassDirection.getJsonName(sdlVersion);
    }
    
    public CompassDirection getCompassDirection() {
        return CompassDirection.valueForJsonName(this.compassDirection, sdlVersion);
    }
    
    /**
     * set the positional dilution of precision
     * @param pdop the positional dilution of precision
     */
    public void setPdop(Double pdop) {
    	this.pdop = pdop;
    }
    
    /**
     * get  the positional dilution of precision
     */
    public Double getPdop() {
    	return pdop;
    }
    
    /**
     * set the horizontal dilution of precision
     * @param hdop the horizontal dilution of precision
     */
    public void setHdop(Double hdop) {
    	this.hdop = hdop;
    }
    
    /**
     * get  the horizontal dilution of precision
     * @return the horizontal dilution of precision
     */
    public Double getHdop() {
    	return this.hdop;
    }
    
    /**
     * set the vertical dilution of precision
     * @param vdop the vertical dilution of precision
     */
    public void setVdop(Double vdop) {
    	this.vdop = vdop;
    }
    
    /**
     * get  the vertical dilution of precision
     * @return the vertical dilution of precision
     */
    public Double getVdop() {
    	return this.vdop;
    }
    
    /**
     * set what coordinates based on 
     * @param actual True, if coordinates are based on satellites.False, if based on dead reckoning
     */
    public void setActual(Boolean actual) {
    	this.actual = actual;
    }
    
    /**
     * get what coordinates based on 
     * @return True, if coordinates are based on satellites.False, if based on dead reckoning
     */
    public Boolean getActual() {
    	return this.actual;
    }
    
    /**
     * set the number of satellites in view
     * @param satellites the number of satellites in view
     */
    public void setSatellites(Integer satellites) {
    	this.satellites = satellites;
    }
    
    /**
     * get  the number of satellites in view
     * @return the number of satellites in view
     */
    public Integer getSatellites() {
    	return this.satellites;
    }
    
    public void setDimension(Dimension dimension) {
    	this.dimension = (dimension == null) ? null : dimension.getJsonName(sdlVersion);
    }
    
    public Dimension getDimension() {
        return Dimension.valueForJsonName(this.dimension, sdlVersion);
    }
    
    /**
     * set altitude in meters
     * @param altitude altitude in meters
     */
    public void setAltitude(Double altitude) {
    	this.altitude = altitude;
    }
    
    /**
     * get altitude in meters
     * @return altitude in meters
     */
    public Double getAltitude() {
    	return this.altitude;
    }
    
    /**
     * set the heading.North is 0, East is 90, etc.
     * @param heading the heading. 
     */
    public void setHeading(Double heading) {
    	this.heading = heading;
    }
    
    /**
     * get the heading
     */
    public Double getHeading() {
    	return this.heading;
    }
    
    /**
     * set speed in KPH
     * @param speed the speed
     */
    public void setSpeed(Double speed) {
    	this.speed = speed;
    }
    
    /**
     * get the speed in KPH
     * @return the speed in KPH
     */
    public Double getSpeed() {
    	return this.speed;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_LONGITUDE_DEGREES, this.longitude);
            JsonUtils.addToJsonObject(result, KEY_LATITUDE_DEGREES, this.latitude);
            JsonUtils.addToJsonObject(result, KEY_PDOP, this.pdop);
            JsonUtils.addToJsonObject(result, KEY_VDOP, this.vdop);
            JsonUtils.addToJsonObject(result, KEY_HDOP, this.hdop);
            JsonUtils.addToJsonObject(result, KEY_ALTITUDE, this.altitude);
            JsonUtils.addToJsonObject(result, KEY_HEADING, this.heading);
            JsonUtils.addToJsonObject(result, KEY_SPEED, this.speed);
            JsonUtils.addToJsonObject(result, KEY_UTC_YEAR, this.year);
            JsonUtils.addToJsonObject(result, KEY_UTC_MONTH, this.month);
            JsonUtils.addToJsonObject(result, KEY_UTC_DAY, this.day);
            JsonUtils.addToJsonObject(result, KEY_UTC_HOURS, this.hours);
            JsonUtils.addToJsonObject(result, KEY_UTC_MINUTES, this.minutes);
            JsonUtils.addToJsonObject(result, KEY_UTC_SECONDS, this.seconds);
            JsonUtils.addToJsonObject(result, KEY_SATELLITES, this.satellites);
            JsonUtils.addToJsonObject(result, KEY_ACTUAL, this.actual);
            JsonUtils.addToJsonObject(result, KEY_COMPASS_DIRECTION, this.compassDirection);
            JsonUtils.addToJsonObject(result, KEY_DIMENSION, this.dimension);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actual == null) ? 0 : actual.hashCode());
		result = prime * result + ((altitude == null) ? 0 : altitude.hashCode());
		result = prime * result + ((compassDirection == null) ? 0 : compassDirection.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((dimension == null) ? 0 : dimension.hashCode());
		result = prime * result + ((hdop == null) ? 0 : hdop.hashCode());
		result = prime * result + ((heading == null) ? 0 : heading.hashCode());
		result = prime * result + ((hours == null) ? 0 : hours.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((minutes == null) ? 0 : minutes.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((pdop == null) ? 0 : pdop.hashCode());
		result = prime * result + ((satellites == null) ? 0 : satellites.hashCode());
		result = prime * result + ((seconds == null) ? 0 : seconds.hashCode());
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		result = prime * result + ((vdop == null) ? 0 : vdop.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		GPSData other = (GPSData) obj;
		if (actual == null) {
			if (other.actual != null) { 
				return false;
			}
		} else if (!actual.equals(other.actual)) { 
			return false;
		}
		if (altitude == null) {
			if (other.altitude != null) { 
				return false;
			}
		} else if (!altitude.equals(other.altitude)) { 
			return false;
		}
		if (compassDirection == null) {
			if (other.compassDirection != null) { 
				return false;
			}
		} else if (!compassDirection.equals(other.compassDirection)) { 
			return false;
		}
		if (day == null) {
			if (other.day != null) { 
				return false;
			}
		} else if (!day.equals(other.day)) { 
			return false;
		}
		if (dimension == null) {
			if (other.dimension != null) { 
				return false;
			}
		} else if (!dimension.equals(other.dimension)) { 
			return false;
		}
		if (hdop == null) {
			if (other.hdop != null) { 
				return false;
			}
		} else if (!hdop.equals(other.hdop)) { 
			return false;
		}
		if (heading == null) {
			if (other.heading != null) { 
				return false;
			}
		} else if (!heading.equals(other.heading)) { 
			return false;
		}
		if (hours == null) {
			if (other.hours != null) { 
				return false;
			}
		} else if (!hours.equals(other.hours)) { 
			return false;
		}
		if (latitude == null) {
			if (other.latitude != null) { 
				return false;
			}
		} else if (!latitude.equals(other.latitude)) { 
			return false;
		}
		if (longitude == null) {
			if (other.longitude != null) { 
				return false;
			}
		} else if (!longitude.equals(other.longitude)) { 
			return false;
		}
		if (minutes == null) {
			if (other.minutes != null) { 
				return false;
			}
		} else if (!minutes.equals(other.minutes)) { 
			return false;
		}
		if (month == null) {
			if (other.month != null) { 
				return false;
			}
		} else if (!month.equals(other.month)) { 
			return false;
		}
		if (pdop == null) {
			if (other.pdop != null) { 
				return false;
			}
		} else if (!pdop.equals(other.pdop)) { 
			return false;
		}
		if (satellites == null) {
			if (other.satellites != null) { 
				return false;
			}
		} else if (!satellites.equals(other.satellites)) { 
			return false;
		}
		if (seconds == null) {
			if (other.seconds != null) { 
				return false;
			}
		} else if (!seconds.equals(other.seconds)) { 
			return false;
		}
		if (speed == null) {
			if (other.speed != null) { 
				return false;
			}
		} else if (!speed.equals(other.speed)) { 
			return false;
		}
		if (vdop == null) {
			if (other.vdop != null) { 
				return false;
			}
		} else if (!vdop.equals(other.vdop)) { 
			return false;
		}
		if (year == null) {
			if (other.year != null) { 
				return false;
			}
		} else if (!year.equals(other.year)) { 
			return false;
		}
		return true;
	}
}
