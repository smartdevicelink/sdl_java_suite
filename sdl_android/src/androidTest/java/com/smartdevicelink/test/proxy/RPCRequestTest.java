package com.smartdevicelink.test.proxy;

import android.test.AndroidTestCase;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.GetSystemCapability;
import com.smartdevicelink.test.Config;
import com.smartdevicelink.test.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class RPCRequestTest extends AndroidTestCase {

    public static final int  SDL_VERSION_UNDER_TEST = Config.SDL_VERSION_UNDER_TEST;

    private static final int CORR_ID = 402;

    protected RPCRequest msg;

    @Override
    public void setUp(){
        this.msg = new GetSystemCapability();
        
    }

    public void testCreation(){
        assertNotNull("Object creation failed.", msg);
    }

    public void testGetCorrelationId(){
        assertNotNull(this.msg.getCorrelationID());
    }
    public void testSettingCorrelationId(){
        assertNotNull(this.msg.getCorrelationID());
        msg.setCorrelationID(CORR_ID);
        assertEquals("Correlation ID doesn't match expected ID.", CORR_ID, (int)msg.getCorrelationID());
    
    }


}