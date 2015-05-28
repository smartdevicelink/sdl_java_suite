package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.ECallInfo;
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ECallInfo}
 */
public class ECallInfoTests extends TestCase{

    private ECallInfo msg;

    @Override
    public void setUp(){
        msg = new ECallInfo();

        msg.setAuxECallNotificationStatus(Test.GENERAL_VEHICLEDATANOTIFICATIONSTATUS);
        msg.setECallConfirmationStatus(Test.GENERAL_ECALLCONFIRMATIONSTATUS);
        msg.setECallNotificationStatus(Test.GENERAL_VEHICLEDATANOTIFICATIONSTATUS);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        VehicleDataNotificationStatus auxEcall = msg.getAuxECallNotificationStatus();
        VehicleDataNotificationStatus ecallNotify = msg.getECallNotificationStatus();
        ECallConfirmationStatus ecallConfirm = msg.getECallConfirmationStatus();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_VEHICLEDATANOTIFICATIONSTATUS, auxEcall);
        assertEquals(Test.MATCH, Test.GENERAL_VEHICLEDATANOTIFICATIONSTATUS, ecallNotify);
        assertEquals(Test.MATCH, Test.GENERAL_ECALLCONFIRMATIONSTATUS, ecallConfirm);
        
        // Invalid/Null Tests
        ECallInfo msg = new ECallInfo();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getECallConfirmationStatus());
        assertNull(Test.NULL, msg.getECallNotificationStatus());
        assertNull(Test.NULL, msg.getAuxECallNotificationStatus());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ECallInfo.KEY_AUX_E_CALL_NOTIFICATION_STATUS, Test.GENERAL_VEHICLEDATANOTIFICATIONSTATUS);
            reference.put(ECallInfo.KEY_E_CALL_NOTIFICATION_STATUS, Test.GENERAL_VEHICLEDATANOTIFICATIONSTATUS);
            reference.put(ECallInfo.KEY_E_CALL_CONFIRMATION_STATUS, Test.GENERAL_ECALLCONFIRMATIONSTATUS);

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