package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.ECallInfo;
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;
import com.smartdevicelink.test.utils.JsonUtils;

public class ECallInfoTests extends TestCase{

    private static final ECallConfirmationStatus       CONFIRMATION_STATUS     = ECallConfirmationStatus.CALL_IN_PROGRESS;
    private static final VehicleDataNotificationStatus AUX_NOTIFICATION_STATUS = VehicleDataNotificationStatus.NORMAL;
    private static final VehicleDataNotificationStatus NOTIFICATION_STATUS     = VehicleDataNotificationStatus.ACTIVE;

    private ECallInfo                                  msg;

    @Override
    public void setUp(){
        msg = new ECallInfo();

        msg.setAuxECallNotificationStatus(AUX_NOTIFICATION_STATUS);
        msg.setECallConfirmationStatus(CONFIRMATION_STATUS);
        msg.setECallNotificationStatus(NOTIFICATION_STATUS);
    }

    public void testAuxECallNotificationStatus(){
        VehicleDataNotificationStatus copy = msg.getAuxECallNotificationStatus();
        assertEquals("Input value didn't match expected value.", AUX_NOTIFICATION_STATUS, copy);
    }

    public void testECallNotificationStatus(){
        VehicleDataNotificationStatus copy = msg.getECallNotificationStatus();
        assertEquals("Input value didn't match expected value.", NOTIFICATION_STATUS, copy);
    }

    public void testECallConfirmationStatus(){
        ECallConfirmationStatus copy = msg.getECallConfirmationStatus();
        assertEquals("Input value didn't match expected value.", CONFIRMATION_STATUS, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ECallInfo.KEY_AUX_E_CALL_NOTIFICATION_STATUS, AUX_NOTIFICATION_STATUS);
            reference.put(ECallInfo.KEY_E_CALL_NOTIFICATION_STATUS, NOTIFICATION_STATUS);
            reference.put(ECallInfo.KEY_E_CALL_CONFIRMATION_STATUS, CONFIRMATION_STATUS);

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
        ECallInfo msg = new ECallInfo();
        assertNotNull("Null object creation failed.", msg);

        assertNull("ECall confirmation status wasn't set, but getter method returned an object.",
                msg.getECallConfirmationStatus());
        assertNull("ECall notification status wasn't set, but getter method returned an object.",
                msg.getECallNotificationStatus());
        assertNull("Aux ECall notification status wasn't set, but getter method returned an object.",
                msg.getAuxECallNotificationStatus());
    }
}
