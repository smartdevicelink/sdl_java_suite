package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Coordinate;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.LocationDetails;
import com.smartdevicelink.proxy.rpc.OasisAddress;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by austinkirk on 6/7/17.
 */

public class LocationDetailsTests extends TestCase {
    private LocationDetails msg;

    @Override
    public void setUp() {
        msg = TestValues.GENERAL_LOCATIONDETAILS;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        List<String> field1 = msg.getAddressLines();
        String field2 = msg.getLocationDescription();
        String field3 = msg.getLocationName();
        String field4 = msg.getPhoneNumber();
        Coordinate field5 = msg.getCoordinate();
        Image field6 = msg.getLocationImage();
        OasisAddress field7 = msg.getSearchAddress();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST, field1);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, field2);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, field3);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, field4);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_COORDINATE, field5);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_IMAGE, field6);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_OASISADDRESS, field7);

        // Invalid/Null Tests
        LocationDetails msg = new LocationDetails();
        assertNotNull(TestValues.NOT_NULL, msg);


        assertNull(msg.getAddressLines());
        assertNull(msg.getLocationDescription());
        assertNull(msg.getLocationName());
        assertNull(msg.getPhoneNumber());
        assertNull(msg.getCoordinate());
        assertNull(msg.getLocationImage());
        assertNull(msg.getSearchAddress());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(LocationDetails.KEY_ADDRESS_LINES, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
            reference.put(LocationDetails.KEY_LOCATION_DESCRIPTION, TestValues.GENERAL_STRING);
            reference.put(LocationDetails.KEY_LOCATION_NAME, TestValues.GENERAL_STRING);
            reference.put(LocationDetails.KEY_PHONE_NUMBER, TestValues.GENERAL_STRING);
            reference.put(LocationDetails.KEY_COORDINATE, TestValues.GENERAL_COORDINATE);
            reference.put(LocationDetails.KEY_LOCATION_IMAGE, TestValues.GENERAL_IMAGE);
            reference.put(LocationDetails.KEY_SEARCH_ADDRESS, TestValues.GENERAL_OASISADDRESS);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, LocationDetails.KEY_ADDRESS_LINES),
                    JsonUtils.readObjectFromJsonObject(underTest, LocationDetails.KEY_ADDRESS_LINES));

            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(reference, LocationDetails.KEY_LOCATION_DESCRIPTION),
                    JsonUtils.readStringFromJsonObject(underTest, LocationDetails.KEY_LOCATION_DESCRIPTION));

            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(reference, LocationDetails.KEY_LOCATION_NAME),
                    JsonUtils.readStringFromJsonObject(underTest, LocationDetails.KEY_LOCATION_NAME));

            assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(reference, LocationDetails.KEY_PHONE_NUMBER),
                    JsonUtils.readStringFromJsonObject(underTest, LocationDetails.KEY_PHONE_NUMBER));

            assertTrue(Validator.validateCoordinate(
                    (Coordinate) JsonUtils.readObjectFromJsonObject(reference, LocationDetails.KEY_COORDINATE),
                    new Coordinate(JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(underTest, LocationDetails.KEY_COORDINATE))))
            );

            assertTrue(Validator.validateImage(
                    (Image) JsonUtils.readObjectFromJsonObject(reference, LocationDetails.KEY_LOCATION_IMAGE),
                    new Image(JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(underTest, LocationDetails.KEY_LOCATION_IMAGE)))
            ));

            assertTrue(Validator.validateOasisAddress(
                    (OasisAddress) JsonUtils.readObjectFromJsonObject(reference, LocationDetails.KEY_SEARCH_ADDRESS),
                    new OasisAddress(JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(underTest, LocationDetails.KEY_SEARCH_ADDRESS)))
            ));

        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
