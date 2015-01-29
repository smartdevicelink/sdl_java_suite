package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.enums.CompassDirection;
import com.smartdevicelink.proxy.rpc.enums.Dimension;
import com.smartdevicelink.test.utils.JsonUtils;

public class GPSDataTests extends TestCase{

    private static final double           LATITUDE    = 81.823;
    private static final double           LONGITUDE   = -104.219;
    private static final double           ALTITUDE    = 1.461;
    private static final double           HEADING     = 52.739;
    private static final double           SPEED       = 73.41;
    private static final double           PDOP        = 1.433;
    private static final double           HDOP        = 0.137;
    private static final double           VDOP        = 3.935;
    private static final int              UTC_YEAR    = 2014;
    private static final int              UTC_MONTH   = 7;
    private static final int              UTC_DAY     = 24;
    private static final int              UTC_HOURS   = 15;
    private static final int              UTC_MINUTES = 38;
    private static final int              UTC_SECONDS = 42;
    private static final int              SATELLITES  = 3;
    private static final Dimension        DIMENSION   = Dimension._2D;
    private static final CompassDirection DIRECTION   = CompassDirection.NORTHEAST;
    private static final boolean          ACTUAL      = true;

    private GPSData                       msg;

    @Override
    public void setUp(){
        msg = new GPSData();

        msg.setActual(ACTUAL);
        msg.setAltitude(ALTITUDE);
        msg.setCompassDirection(DIRECTION);
        msg.setDimension(DIMENSION);
        msg.setHdop(HDOP);
        msg.setHeading(HEADING);
        msg.setLatitudeDegrees(LATITUDE);
        msg.setLongitudeDegrees(LONGITUDE);
        msg.setPdop(PDOP);
        msg.setSatellites(SATELLITES);
        msg.setSpeed(SPEED);
        msg.setUtcDay(UTC_DAY);
        msg.setUtcHours(UTC_HOURS);
        msg.setUtcMinutes(UTC_MINUTES);
        msg.setUtcMonth(UTC_MONTH);
        msg.setUtcSeconds(UTC_SECONDS);
        msg.setUtcYear(UTC_YEAR);
        msg.setVdop(VDOP);

    }

    public void testActual(){
        boolean copy = msg.getActual();
        assertEquals("Input value didn't match expected value.", ACTUAL, copy);
    }

    public void testLatitudeDegrees(){
        double copy = msg.getLatitudeDegrees();
        assertEquals("Input value didn't match expected value.", LATITUDE, copy);
    }

    public void testLongitudeDegrees(){
        double copy = msg.getLongitudeDegrees();
        assertEquals("Input value didn't match expected value.", LONGITUDE, copy);
    }

    public void testAltitude(){
        double copy = msg.getAltitude();
        assertEquals("Input value didn't match expected value.", ALTITUDE, copy);
    }

    public void testHeading(){
        double copy = msg.getHeading();
        assertEquals("Input value didn't match expected value.", HEADING, copy);
    }

    public void testSpeed(){
        double copy = msg.getSpeed();
        assertEquals("Input value didn't match expected value.", SPEED, copy);
    }

    public void testPdop(){
        double copy = msg.getPdop();
        assertEquals("Input value didn't match expected value.", PDOP, copy);
    }

    public void testHdop(){
        double copy = msg.getHdop();
        assertEquals("Input value didn't match expected value.", HDOP, copy);
    }

    public void testVdop(){
        double copy = msg.getVdop();
        assertEquals("Input value didn't match expected value.", VDOP, copy);
    }

    public void testUtcYear(){
        int copy = msg.getUtcYear();
        assertEquals("Input value didn't match expected value.", UTC_YEAR, copy);
    }

    public void testUtcMinutes(){
        int copy = msg.getUtcMinutes();
        assertEquals("Input value didn't match expected value.", UTC_MINUTES, copy);
    }

    public void testUtcMonths(){
        int copy = msg.getUtcMonth();
        assertEquals("Input value didn't match expected value.", UTC_MONTH, copy);
    }

    public void testUtcDay(){
        int copy = msg.getUtcDay();
        assertEquals("Input value didn't match expected value.", UTC_DAY, copy);
    }

    public void testUtcHours(){
        int copy = msg.getUtcHours();
        assertEquals("Input value didn't match expected value.", UTC_HOURS, copy);
    }

    public void testUtcSeconds(){
        int copy = msg.getUtcSeconds();
        assertEquals("Input value didn't match expected value.", UTC_SECONDS, copy);
    }

    public void testSatellites(){
        int copy = msg.getSatellites();
        assertEquals("Input value didn't match expected value.", SATELLITES, copy);
    }

    public void testDimension(){
        Dimension copy = msg.getDimension();
        assertEquals("Input value didn't match expected value.", DIMENSION, copy);
    }

    public void testCompassDirection(){
        CompassDirection copy = msg.getCompassDirection();
        assertEquals("Input value didn't match expected value.", DIRECTION, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(GPSData.KEY_ACTUAL, ACTUAL);
            reference.put(GPSData.KEY_LATITUDE_DEGREES, LATITUDE);
            reference.put(GPSData.KEY_LONGITUDE_DEGREES, LONGITUDE);
            reference.put(GPSData.KEY_ALTITUDE, ALTITUDE);
            reference.put(GPSData.KEY_HEADING, HEADING);
            reference.put(GPSData.KEY_SPEED, SPEED);
            reference.put(GPSData.KEY_PDOP, PDOP);
            reference.put(GPSData.KEY_HDOP, HDOP);
            reference.put(GPSData.KEY_VDOP, VDOP);
            reference.put(GPSData.KEY_UTC_YEAR, UTC_YEAR);
            reference.put(GPSData.KEY_UTC_MONTH, UTC_MONTH);
            reference.put(GPSData.KEY_UTC_HOURS, UTC_HOURS);
            reference.put(GPSData.KEY_UTC_DAY, UTC_DAY);
            reference.put(GPSData.KEY_UTC_MINUTES, UTC_MINUTES);
            reference.put(GPSData.KEY_UTC_SECONDS, UTC_SECONDS);
            reference.put(GPSData.KEY_SATELLITES, SATELLITES);
            reference.put(GPSData.KEY_DIMENSION, DIMENSION);
            reference.put(GPSData.KEY_COMPASS_DIRECTION, DIRECTION);

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
                        JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        GPSData msg = new GPSData();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Actual wasn't set, but getter method returned an object.", msg.getActual());
        assertNull("Altitude wasn't set, but getter method returned an object.", msg.getAltitude());
        assertNull("Compass direction wasn't set, but getter method returned an object.", msg.getCompassDirection());
        assertNull("Dimension wasn't set, but getter method returned an object.", msg.getDimension());
        assertNull("Hdop wasn't set, but getter method returned an object.", msg.getHdop());
        assertNull("Heading wasn't set, but getter method returned an object.", msg.getHeading());
        assertNull("Latitude degrees wasn't set, but getter method returned an object.", msg.getLatitudeDegrees());
        assertNull("Longitude degrees wasn't set, but getter method returned an object.", msg.getLongitudeDegrees());
        assertNull("Pdop wasn't set, but getter method returned an object.", msg.getPdop());
        assertNull("Satellites wasn't set, but getter method returned an object.", msg.getSatellites());
        assertNull("Speed wasn't set, but getter method returned an object.", msg.getSpeed());
        assertNull("UTC day wasn't set, but getter method returned an object.", msg.getUtcDay());
        assertNull("UTC hours wasn't set, but getter method returned an object.", msg.getUtcHours());
        assertNull("UTC minutes wasn't set, but getter method returned an object.", msg.getUtcMinutes());
        assertNull("UTC month wasn't set, but getter method returned an object.", msg.getUtcMonth());
        assertNull("UTC seconds wasn't set, but getter method returned an object.", msg.getUtcSeconds());
        assertNull("UTC year wasn't set, but getter method returned an object.", msg.getUtcYear());
        assertNull("Vdop wasn't set, but getter method returned an object.", msg.getVdop());
    }
}
