package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DateTime;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.LocationDetails;
import com.smartdevicelink.proxy.rpc.NavigationInstruction;
import com.smartdevicelink.proxy.rpc.enums.Direction;
import com.smartdevicelink.proxy.rpc.enums.NavigationAction;
import com.smartdevicelink.proxy.rpc.enums.NavigationJunction;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.NavigationInstruction}
 */
public class NavigationInstructionTests extends TestCase {

    private NavigationInstruction msg;

    @Override
    public void setUp() {
        msg = new NavigationInstruction();

        msg.setLocationDetails(TestValues.GENERAL_LOCATIONDETAILS);
        msg.setAction(TestValues.GENERAL_NAVIGATIONACTION);
        msg.setEta(TestValues.GENERAL_DATETIME);
        msg.setBearing(TestValues.GENERAL_INTEGER);
        msg.setJunctionType(TestValues.GENERAL_NAVIGATION_JUNCTION);
        msg.setDrivingSide(TestValues.GENERAL_DIRECTION);
        msg.setDetails(TestValues.GENERAL_STRING);
        msg.setImage(TestValues.GENERAL_IMAGE);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        LocationDetails locationDetails = msg.getLocationDetails();
        NavigationAction action = msg.getAction();
        DateTime eta = msg.getEta();
        Integer bearing = msg.getBearing();
        NavigationJunction junctionType = msg.getJunctionType();
        Direction drivingSide = msg.getDrivingSide();
        String details = msg.getDetails();
        Image image = msg.getImage();

        // Valid Tests
        assertEquals(TestValues.GENERAL_LOCATIONDETAILS, locationDetails);
        assertEquals(TestValues.GENERAL_NAVIGATIONACTION, action);
        assertEquals(TestValues.GENERAL_DATETIME, eta);
        assertEquals(TestValues.GENERAL_INTEGER, bearing);
        assertEquals(TestValues.GENERAL_NAVIGATION_JUNCTION, junctionType);
        assertEquals(TestValues.GENERAL_DIRECTION, drivingSide);
        assertEquals(TestValues.GENERAL_STRING, details);
        assertEquals(TestValues.GENERAL_IMAGE, image);

        // Invalid/Null Tests
        NavigationInstruction msg = new NavigationInstruction();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getLocationDetails());
        assertNull(TestValues.NULL, msg.getAction());
        assertNull(TestValues.NULL, msg.getEta());
        assertNull(TestValues.NULL, msg.getBearing());
        assertNull(TestValues.NULL, msg.getJunctionType());
        assertNull(TestValues.NULL, msg.getDrivingSide());
        assertNull(TestValues.NULL, msg.getDetails());
        assertNull(TestValues.NULL, msg.getImage());
    }

    public void testRequiredConstructor() {
        NavigationInstruction msg = new NavigationInstruction(TestValues.GENERAL_LOCATIONDETAILS, TestValues.GENERAL_NAVIGATIONACTION);
        assertNotNull(TestValues.NOT_NULL, msg);

        LocationDetails locationDetails = msg.getLocationDetails();
        NavigationAction action = msg.getAction();

        assertEquals(TestValues.GENERAL_LOCATIONDETAILS, locationDetails);
        assertEquals(TestValues.GENERAL_NAVIGATIONACTION, action);
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(NavigationInstruction.KEY_LOCATION_DETAILS, TestValues.GENERAL_LOCATIONDETAILS);
            reference.put(NavigationInstruction.KEY_ACTION, TestValues.GENERAL_NAVIGATIONACTION);
            reference.put(NavigationInstruction.KEY_ETA, TestValues.GENERAL_DATETIME);
            reference.put(NavigationInstruction.KEY_BEARING, TestValues.GENERAL_INTEGER);
            reference.put(NavigationInstruction.KEY_JUNCTION_TYPE, TestValues.GENERAL_NAVIGATION_JUNCTION);
            reference.put(NavigationInstruction.KEY_DRIVING_SIDE, TestValues.GENERAL_DIRECTION);
            reference.put(NavigationInstruction.KEY_DETAILS, TestValues.GENERAL_STRING);
            reference.put(NavigationInstruction.KEY_IMAGE, TestValues.GENERAL_IMAGE);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if (key.equals(NavigationInstruction.KEY_IMAGE)) {
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Image refIcon1 = new Image(JsonRPCMarshaller.deserializeJSONObject(testEquals));
                    assertTrue(TestValues.TRUE, Validator.validateImage(refIcon1, msg.getImage()));
                } else if (key.equals(NavigationInstruction.KEY_LOCATION_DETAILS)) {
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateLocationDetails(TestValues.GENERAL_LOCATIONDETAILS, new LocationDetails(hashTest)));
                } else if (key.equals(NavigationInstruction.KEY_ETA)) {
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateDateTime(TestValues.GENERAL_DATETIME, new DateTime(hashTest)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}