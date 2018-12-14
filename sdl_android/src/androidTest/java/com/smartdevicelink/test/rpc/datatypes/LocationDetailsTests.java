package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Coordinate;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.LocationDetails;
import com.smartdevicelink.proxy.rpc.OasisAddress;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
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
    public void setUp(){
        msg = Test.GENERAL_LOCATIONDETAILS;
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        List<String> field1 = msg.getAddressLines();
        String field2 = msg.getLocationDescription();
        String field3 = msg.getLocationName();
        String field4 = msg.getPhoneNumber();
        Coordinate field5 = msg.getCoordinate();
        Image field6 = msg.getLocationImage();
        OasisAddress field7 = msg.getSearchAddress();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST, field1);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, field2);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, field3);
        assertEquals(Test.MATCH, Test.GENERAL_STRING, field4);
        assertEquals(Test.MATCH, Test.GENERAL_COORDINATE, field5);
        assertEquals(Test.MATCH, Test.GENERAL_IMAGE, field6);
        assertEquals(Test.MATCH, Test.GENERAL_OASISADDRESS, field7);

        // Invalid/Null Tests
        LocationDetails msg = new LocationDetails();
        assertNotNull(Test.NOT_NULL, msg);


        assertNull(msg.getAddressLines());
        assertNull(msg.getLocationDescription());
        assertNull(msg.getLocationName());
        assertNull(msg.getPhoneNumber());
        assertNull(msg.getCoordinate());
        assertNull(msg.getLocationImage());
        assertNull(msg.getSearchAddress());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(LocationDetails.KEY_ADDRESS_LINES, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
            reference.put(LocationDetails.KEY_LOCATION_DESCRIPTION, Test.GENERAL_STRING);
            reference.put(LocationDetails.KEY_LOCATION_NAME, Test.GENERAL_STRING);
            reference.put(LocationDetails.KEY_PHONE_NUMBER, Test.GENERAL_STRING);
            reference.put(LocationDetails.KEY_COORDINATE, Test.GENERAL_COORDINATE);
            reference.put(LocationDetails.KEY_LOCATION_IMAGE, Test.GENERAL_IMAGE);
            reference.put(LocationDetails.KEY_SEARCH_ADDRESS, Test.GENERAL_OASISADDRESS);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, LocationDetails.KEY_ADDRESS_LINES),
                    JsonUtils.readObjectFromJsonObject(underTest, LocationDetails.KEY_ADDRESS_LINES));

            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(reference, LocationDetails.KEY_LOCATION_DESCRIPTION),
                    JsonUtils.readStringFromJsonObject(underTest, LocationDetails.KEY_LOCATION_DESCRIPTION));

            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(reference, LocationDetails.KEY_LOCATION_NAME),
                    JsonUtils.readStringFromJsonObject(underTest, LocationDetails.KEY_LOCATION_NAME));

            assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(reference, LocationDetails.KEY_PHONE_NUMBER),
                    JsonUtils.readStringFromJsonObject(underTest, LocationDetails.KEY_PHONE_NUMBER));

            assertTrue(Validator.validateCoordinate(
                    (Coordinate) JsonUtils.readObjectFromJsonObject(reference, LocationDetails.KEY_COORDINATE),
                    new Coordinate(JsonRPCMarshaller.deserializeJSONObject( (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, LocationDetails.KEY_COORDINATE))))
                    );

            assertTrue(Validator.validateImage(
                    (Image) JsonUtils.readObjectFromJsonObject(reference, LocationDetails.KEY_LOCATION_IMAGE),
                    new Image(JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(underTest, LocationDetails.KEY_LOCATION_IMAGE)))
                    ));

            assertTrue(Validator.validateOasisAddress(
                    (OasisAddress) JsonUtils.readObjectFromJsonObject(reference, LocationDetails.KEY_SEARCH_ADDRESS),
                    new OasisAddress(JsonRPCMarshaller.deserializeJSONObject((JSONObject) JsonUtils.readObjectFromJsonObject(underTest, LocationDetails.KEY_SEARCH_ADDRESS)))
            ));

        } catch(JSONException e){
            fail(Test.JSON_FAIL);
        }
    }
}
