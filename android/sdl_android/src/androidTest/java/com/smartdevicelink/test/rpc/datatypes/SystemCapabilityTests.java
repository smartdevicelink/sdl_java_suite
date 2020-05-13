package com.smartdevicelink.test.rpc.datatypes;

import android.util.Log;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AppServicesCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.NavigationCapability;
import com.smartdevicelink.proxy.rpc.PhoneCapability;
import com.smartdevicelink.proxy.rpc.RemoteControlCapabilities;
import com.smartdevicelink.proxy.rpc.SystemCapability;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.SystemCapability}
 */
public class SystemCapabilityTests extends TestCase {

    private SystemCapability msg;

    @Override
    public void setUp() {
        msg = new SystemCapability();

        msg.setSystemCapabilityType(Test.GENERAL_SYSTEMCAPABILITYTYPE);
        msg.setCapabilityForType(SystemCapabilityType.NAVIGATION, Test.GENERAL_NAVIGATIONCAPABILITY);
        msg.setCapabilityForType(SystemCapabilityType.PHONE_CALL, Test.GENERAL_PHONECAPABILITY);
        msg.setCapabilityForType(SystemCapabilityType.REMOTE_CONTROL, Test.GENERAL_REMOTECONTROLCAPABILITIES);
        msg.setCapabilityForType(SystemCapabilityType.APP_SERVICES, Test.GENERAL_APP_SERVICE_CAPABILITIES);
        msg.setCapabilityForType(SystemCapabilityType.DISPLAYS, Test.GENERAL_DISPLAYCAPABILITY_LIST);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        SystemCapabilityType testType = msg.getSystemCapabilityType();
        NavigationCapability testNavigationCapability = (NavigationCapability) msg.getCapabilityForType(SystemCapabilityType.NAVIGATION);
        PhoneCapability testPhoneCapability = (PhoneCapability) msg.getCapabilityForType(SystemCapabilityType.PHONE_CALL);
        RemoteControlCapabilities testRemoteControlCapabilities = (RemoteControlCapabilities) msg.getCapabilityForType(SystemCapabilityType.REMOTE_CONTROL);
        AppServicesCapabilities testAppServicesCapabilities = (AppServicesCapabilities) msg.getCapabilityForType(SystemCapabilityType.APP_SERVICES);
        List<DisplayCapability> displayCapabilities = (List<DisplayCapability>) msg.getCapabilityForType(SystemCapabilityType.DISPLAYS);

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_SYSTEMCAPABILITYTYPE, testType);
        assertTrue(Test.TRUE, Validator.validateNavigationCapability(Test.GENERAL_NAVIGATIONCAPABILITY, testNavigationCapability));
        assertTrue(Test.TRUE, Validator.validatePhoneCapability(Test.GENERAL_PHONECAPABILITY, testPhoneCapability));
        assertTrue(Test.TRUE, Validator.validateRemoteControlCapabilities(Test.GENERAL_REMOTECONTROLCAPABILITIES, testRemoteControlCapabilities));
        assertTrue(Test.TRUE, Validator.validateAppServiceCapabilities(Test.GENERAL_APP_SERVICE_CAPABILITIES, testAppServicesCapabilities));

        for(int i = 0; i < Test.GENERAL_DISPLAYCAPABILITY_LIST.size(); i++){
            assertTrue(Test.TRUE, Validator.validateDisplayCapability(Test.GENERAL_DISPLAYCAPABILITY_LIST.get(i), displayCapabilities.get(i)));
        }

        // Invalid/Null Tests
        SystemCapability msg = new SystemCapability();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getSystemCapabilityType());
        assertNull(Test.NULL, msg.getCapabilityForType(SystemCapabilityType.NAVIGATION));
        assertNull(Test.NULL, msg.getCapabilityForType(SystemCapabilityType.PHONE_CALL));
        assertNull(Test.NULL, msg.getCapabilityForType(SystemCapabilityType.REMOTE_CONTROL));
        assertNull(Test.NULL, msg.getCapabilityForType(SystemCapabilityType.APP_SERVICES));
        assertNull(Test.NULL, msg.getCapabilityForType(SystemCapabilityType.DISPLAYS));
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(SystemCapability.KEY_SYSTEM_CAPABILITY_TYPE, Test.GENERAL_SYSTEMCAPABILITYTYPE);
            reference.put(SystemCapability.KEY_NAVIGATION_CAPABILITY, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_NAVIGATIONCAPABILITY.getStore()));
            reference.put(SystemCapability.KEY_PHONE_CAPABILITY, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_PHONECAPABILITY.getStore()));
            reference.put(SystemCapability.KEY_REMOTE_CONTROL_CAPABILITY, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_REMOTECONTROLCAPABILITIES.getStore()));
            reference.put(SystemCapability.KEY_APP_SERVICES_CAPABILITIES, JsonRPCMarshaller.serializeHashtable(Test.GENERAL_APP_SERVICE_CAPABILITIES.getStore()));
            reference.put(SystemCapability.KEY_DISPLAY_CAPABILITIES, Test.JSON_DISPLAYCAPABILITY_LIST);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if(key.equals(SystemCapability.KEY_NAVIGATION_CAPABILITY)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(Test.TRUE, Validator.validateNavigationCapability( new NavigationCapability(hashReference), new NavigationCapability(hashTest)));
                } else if(key.equals(SystemCapability.KEY_PHONE_CAPABILITY)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(Test.TRUE, Validator.validatePhoneCapability( new PhoneCapability(hashReference), new PhoneCapability(hashTest)));
                } else if(key.equals(SystemCapability.KEY_REMOTE_CONTROL_CAPABILITY)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(Test.TRUE, Validator.validateRemoteControlCapabilities( new RemoteControlCapabilities(hashReference), new RemoteControlCapabilities(hashTest)));
                } else if(key.equals(SystemCapability.KEY_APP_SERVICES_CAPABILITIES)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    Log.i("TEST REF", hashReference.toString());
                    Log.i("TEST TEST", hashTest.toString());
                    assertTrue(Test.TRUE, Validator.validateAppServiceCapabilities( new AppServicesCapabilities(hashReference), new AppServicesCapabilities(hashTest)));
                } else if(key.equals(SystemCapability.KEY_DISPLAY_CAPABILITIES)){
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

                    for(int i = 0; i < referenceArray.length(); i++){
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest= JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(Test.TRUE, Validator.validateDisplayCapability(new DisplayCapability(hashReference), new DisplayCapability(hashTest)));
                    }
                } else{
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}