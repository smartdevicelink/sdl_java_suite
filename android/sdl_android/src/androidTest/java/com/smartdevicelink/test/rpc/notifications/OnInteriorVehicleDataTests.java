package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ModuleData;
import com.smartdevicelink.proxy.rpc.OnInteriorVehicleData;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.OnInteriorVehicleData}
 */
public class OnInteriorVehicleDataTests extends BaseRpcTests{

    @Override
    protected RPCMessage createMessage(){
        OnInteriorVehicleData msg = new OnInteriorVehicleData();
        msg.setModuleData(TestValues.GENERAL_MODULEDATA);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_INTERIOR_VEHICLE_DATA.toString();
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(OnInteriorVehicleData.KEY_MODULE_DATA, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_MODULEDATA.getStore()));
        }catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }

        return result;
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    @Test
    public void testRpcValues () { 
    	// Test Values
        ModuleData moduleData = ( (OnInteriorVehicleData) msg ).getModuleData();

        // Valid Tests
        assertTrue(TestValues.TRUE, Validator.validateModuleData(TestValues.GENERAL_MODULEDATA, moduleData));

        // Invalid/Null Tests
        OnInteriorVehicleData msg = new OnInteriorVehicleData();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);

        assertNull(TestValues.NULL, msg.getModuleData());
    }
}