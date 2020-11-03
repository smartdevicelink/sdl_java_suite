package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.enums.CompassDirection;
import com.smartdevicelink.proxy.rpc.enums.Dimension;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.GPSData}
 */
public class GPSDataTests extends TestCase {

    private GPSData msg;

    @Override
    public void setUp() {
        msg = new GPSData();

        msg.setActual(TestValues.GENERAL_BOOLEAN);
        msg.setAltitude(TestValues.GENERAL_DOUBLE);
        msg.setCompassDirection(TestValues.GENERAL_COMPASSDIRECTION);
        msg.setDimension(TestValues.GENERAL_DIMENSION);
        msg.setHdop(TestValues.GENERAL_DOUBLE);
        msg.setHeading(TestValues.GENERAL_DOUBLE);
        msg.setLatitudeDegrees(TestValues.GENERAL_DOUBLE);
        msg.setLongitudeDegrees(TestValues.GENERAL_DOUBLE);
        msg.setPdop(TestValues.GENERAL_DOUBLE);
        msg.setSatellites(TestValues.GENERAL_INT);
        msg.setSpeed(TestValues.GENERAL_DOUBLE);
        msg.setUtcDay(TestValues.GENERAL_INT);
        msg.setUtcHours(TestValues.GENERAL_INT);
        msg.setUtcMinutes(TestValues.GENERAL_INT);
        msg.setUtcMonth(TestValues.GENERAL_INT);
        msg.setUtcSeconds(TestValues.GENERAL_INT);
        msg.setUtcYear(TestValues.GENERAL_INT);
        msg.setVdop(TestValues.GENERAL_DOUBLE);
        msg.setShifted(TestValues.GENERAL_BOOLEAN);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
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
        boolean shifted = msg.getShifted();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, actual);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, latitude);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, longitude);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, altitude);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, heading);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, speed);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, pdop);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, hdop);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DOUBLE, vdop);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, utcYear);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, utcMin);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, utcMonths);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, utcDay);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, utcHours);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, utcSec);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, satellites);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_DIMENSION, dimension);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_COMPASSDIRECTION, direction);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, shifted);

        // Invalid/Null Tests
        GPSData msg = new GPSData();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getActual());
        assertNull(TestValues.NULL, msg.getAltitude());
        assertNull(TestValues.NULL, msg.getCompassDirection());
        assertNull(TestValues.NULL, msg.getDimension());
        assertNull(TestValues.NULL, msg.getHdop());
        assertNull(TestValues.NULL, msg.getHeading());
        assertNull(TestValues.NULL, msg.getLatitudeDegrees());
        assertNull(TestValues.NULL, msg.getLongitudeDegrees());
        assertNull(TestValues.NULL, msg.getPdop());
        assertNull(TestValues.NULL, msg.getSatellites());
        assertNull(TestValues.NULL, msg.getSpeed());
        assertNull(TestValues.NULL, msg.getUtcDay());
        assertNull(TestValues.NULL, msg.getUtcHours());
        assertNull(TestValues.NULL, msg.getUtcMinutes());
        assertNull(TestValues.NULL, msg.getUtcMonth());
        assertNull(TestValues.NULL, msg.getUtcSeconds());
        assertNull(TestValues.NULL, msg.getUtcYear());
        assertNull(TestValues.NULL, msg.getVdop());
        assertNull(TestValues.NULL, msg.getShifted());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(GPSData.KEY_ACTUAL, TestValues.GENERAL_BOOLEAN);
            reference.put(GPSData.KEY_LATITUDE_DEGREES, TestValues.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_LONGITUDE_DEGREES, TestValues.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_ALTITUDE, TestValues.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_HEADING, TestValues.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_SPEED, TestValues.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_PDOP, TestValues.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_HDOP, TestValues.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_VDOP, TestValues.GENERAL_DOUBLE);
            reference.put(GPSData.KEY_UTC_YEAR, TestValues.GENERAL_INT);
            reference.put(GPSData.KEY_UTC_MONTH, TestValues.GENERAL_INT);
            reference.put(GPSData.KEY_UTC_HOURS, TestValues.GENERAL_INT);
            reference.put(GPSData.KEY_UTC_DAY, TestValues.GENERAL_INT);
            reference.put(GPSData.KEY_UTC_MINUTES, TestValues.GENERAL_INT);
            reference.put(GPSData.KEY_UTC_SECONDS, TestValues.GENERAL_INT);
            reference.put(GPSData.KEY_SATELLITES, TestValues.GENERAL_INT);
            reference.put(GPSData.KEY_DIMENSION, TestValues.GENERAL_DIMENSION);
            reference.put(GPSData.KEY_COMPASS_DIRECTION, TestValues.GENERAL_COMPASSDIRECTION);
            reference.put(GPSData.KEY_SHIFTED, TestValues.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
