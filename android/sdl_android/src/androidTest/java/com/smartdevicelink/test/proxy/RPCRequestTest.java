package com.smartdevicelink.test.proxy;


import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.GetSystemCapability;
import com.smartdevicelink.test.Config;


public class RPCRequestTest extends AndroidTestCase2 {

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