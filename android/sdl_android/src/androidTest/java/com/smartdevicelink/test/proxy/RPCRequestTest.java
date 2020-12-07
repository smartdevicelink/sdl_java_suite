package com.smartdevicelink.test.proxy;


import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.GetSystemCapability;
import com.smartdevicelink.test.Config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class RPCRequestTest {

    public static final int SDL_VERSION_UNDER_TEST = Config.SDL_VERSION_UNDER_TEST;

    private static final int CORR_ID = 402;

    protected RPCRequest msg;

    @Before
    public void setUp() {
        this.msg = new GetSystemCapability();

    }

    @Test
    public void testCreation() {
        assertNotNull("Object creation failed.", msg);
    }

    @Test
    public void testGetCorrelationId() {
        assertNotNull(this.msg.getCorrelationID());
    }

    @Test
    public void testSettingCorrelationId() {
        assertNotNull(this.msg.getCorrelationID());
        msg.setCorrelationID(CORR_ID);
        assertEquals("Correlation ID doesn't match expected ID.", CORR_ID, (int) msg.getCorrelationID());

    }


}