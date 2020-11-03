package com.smartdevicelink.test.util;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.util.DebugTool;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.util.DebugTool}
 */
public class DebugToolTests extends TestCase {

    /**
     * This is a unit test for the following methods :
     * {@link com.smartdevicelink.util.DebugTool#enableDebugTool()}
     * {@link com.smartdevicelink.util.DebugTool#disableDebugTool()}
     * {@link com.smartdevicelink.util.DebugTool#isDebugEnabled()}
     */
    public void testDebugEnableMethods() {
        DebugTool.enableDebugTool();
        assertTrue(TestValues.TRUE, DebugTool.isDebugEnabled());
        DebugTool.disableDebugTool();
        assertFalse(TestValues.FALSE, DebugTool.isDebugEnabled());
    }

    // NOTE : No testing can currently be done for the logging methods.
}