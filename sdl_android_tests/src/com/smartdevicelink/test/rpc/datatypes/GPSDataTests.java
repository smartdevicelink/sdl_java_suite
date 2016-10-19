package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.enums.CompassDirection;
import com.smartdevicelink.proxy.rpc.enums.Dimension;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.GPSData}
 */
public class GPSDataTests extends TestCase{

    private GPSData msg;

    @Override
    public void setUp(){
        msg = new GPSData();

        msg.setActual(Test.GENERAL_BOOLEAN);
        msg.setAltitude(Test.GENERAL_DOUBLE);
        msg.setCompassDirection(Test.GENERAL_COMPASSDIRECTION);
        msg.setDimension(Test.GENERAL_DIMENSION);
        msg.setHdop(Test.GENERAL_DOUBLE);
        msg.setHeading(Test.GENERAL_DOUBLE);
        msg.setLatitudeDegrees(Test.GENERAL_DOUBLE);
        msg.setLongitudeDegrees(Test.GENERAL_DOUBLE);
        msg.setPdop(Test.GENERAL_DOUBLE);
        msg.setSatellites(Test.GENERAL_INT);
        msg.setSpeed(Test.GENERAL_DOUBLE);
        msg.setUtcDay(Test.GENERAL_INT);
        msg.setUtcHours(Test.GENERAL_INT);
        msg.setUtcMinutes(Test.GENERAL_INT);
        msg.setUtcMonth(Test.GENERAL_INT);
        msg.setUtcSeconds(Test.GENERAL_INT);
        msg.setUtcYear(Test.GENERAL_INT);
        msg.setVdop(Test.GENERAL_DOUBLE);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        boolean actual = msg.getActual();
        double latitude = msg.getLatitudeDegrees();
        double longitude = msg.getLongitudeDegrees();
        double altitude = msg.getAltitude();
        double heading = msg.getHeading();
        double speed = msg.getSpeed();
        double pdop = msg.getPdop();
        double hdop = msg.getHdop();
        double vdop = msg.getVdop();
        int utcYear = msg.getUtcYear();
        int utcMin = msg.getUtcMinutes();
        int utcMonths = msg.getUtcMonth();
        int utcDay = msg.getUtcDay();
        int utcHours = msg.getUtcHours();
        int utcSec = msg.getUtcSeconds();
        int satellites = msg.getSatellites();
        Dimension dimension = msg.getDimension();
        CompassDirection direction = msg.getCompassDirection();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, actual);
        assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, latitude);
        assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, longitude);
        assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, altitude);
        assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, heading);
        assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, speed);
        assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, pdop);
        assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, hdop);
        assertEquals(Test.MATCH, Test.GENERAL_DOUBLE, vdop);
        assertEquals(Test.MATCH, Test.GENERAL_INT, utcYear);
        assertEquals(Test.MATCH, Test.GENERAL_INT, utcMin);
        assertEquals(Test.MATCH, Test.GENERAL_INT, utcMonths);
        assertEquals(Test.MATCH, Test.GENERAL_INT, utcDay);
        assertEquals(Test.MATCH, Test.GENERAL_INT, utcHours);
        assertEquals(Test.MATCH, Test.GENERAL_INT, utcSec);
        assertEquals(Test.MATCH, Test.GENERAL_INT, satellites);
        assertEquals(Test.MATCH, Test.GENERAL_DIMENSION, dimension);
        assertEquals(Test.MATCH, Test.GENERAL_COMPASSDIRECTION, direction);
        
        // Invalid/Null Tests
        GPSData msg = new GPSData();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getActual());
        assertNull(Test.NULL, msg.getAltitude());
        assertNull(Test.NULL, msg.getCompassDirection());
        assertNull(Test.NULL, msg.getDimension());
        assertNull(Test.NULL, msg.getHdop());
        assertNull(Test.NULL, msg.getHeading());
        assertNull(Test.NULL, msg.getLatitudeDegrees());
        assertNull(Test.NULL, msg.getLongitudeDegrees());
        assertNull(Test.NULL, msg.getPdop());
        assertNull(Test.NULL, msg.getSatellites());
        assertNull(Test.NULL, msg.getSpeed());
        assertNull(Test.NULL, msg.getUtcDay());
        assertNull(Test.NULL, msg.getUtcHours());
        assertNull(Test.NULL, msg.getUtcMinutes());
        assertNull(Test.NULL, msg.getUtcMonth());
        assertNull(Test.NULL, msg.getUtcSeconds());
        assertNull(Test.NULL, msg.getUtcYear());
        assertNull(Test.NULL, msg.getVdop());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(GPSData.KEY_ACTUAL, Test.GENERAL_BOOLEAN);
            reference.put(GPSData.KEY_LATITUDE_DEGREES, Test.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_LONGITUDE_DEGREES, Test.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_ALTITUDE, Test.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_HEADING, Test.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_SPEED, Test.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_PDOP, Test.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_HDOP, Test.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_VDOP, Test.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_UTC_YEAR, Test.GENERAL_INT);
            reference.put(GPSData.KEY_UTC_MONTH, Test.GENERAL_INT);
            reference.put(GPSData.KEY_UTC_HOURS, Test.GENERAL_INT);
            reference.put(GPSData.KEY_UTC_DAY, Test.GENERAL_INT);
            reference.put(GPSData.KEY_UTC_MINUTES, Test.GENERAL_INT);
            reference.put(GPSData.KEY_UTC_SECONDS, Test.GENERAL_INT);
            reference.put(GPSData.KEY_SATELLITES, Test.GENERAL_INT);
            reference.put(GPSData.KEY_DIMENSION, Test.GENERAL_DIMENSION);
            reference.put(GPSData.KEY_COMPASS_DIRECTION, Test.GENERAL_COMPASSDIRECTION);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}