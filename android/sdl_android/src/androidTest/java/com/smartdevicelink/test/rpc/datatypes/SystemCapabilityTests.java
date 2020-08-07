package com.smartdevicelink.test.rpc.datatypes;

import android.util.Log;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AppServicesCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.DriverDistractionCapability;
import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.proxy.rpc.NavigationCapability;
import com.smartdevicelink.proxy.rpc.PhoneCapability;
import com.smartdevicelink.proxy.rpc.RemoteControlCapabilities;
import com.smartdevicelink.proxy.rpc.SystemCapability;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
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

        msg.setSystemCapabilityType(TestValues.GENERAL_SYSTEMCAPABILITYTYPE);
        msg.setCapabilityForType(SystemCapabilityType.NAVIGATION, TestValues.GENERAL_NAVIGATIONCAPABILITY);
        msg.setCapabilityForType(SystemCapabilityType.PHONE_CALL, TestValues.GENERAL_PHONECAPABILITY);
        msg.setCapabilityForType(SystemCapabilityType.REMOTE_CONTROL, TestValues.GENERAL_REMOTECONTROLCAPABILITIES);
        msg.setCapabilityForType(SystemCapabilityType.APP_SERVICES, TestValues.GENERAL_APP_SERVICE_CAPABILITIES);
        msg.setCapabilityForType(SystemCapabilityType.DISPLAYS, TestValues.GENERAL_DISPLAYCAPABILITY_LIST);
        msg.setCapabilityForType(SystemCapabilityType.DRIVER_DISTRACTION, TestValues.GENERAL_DRIVERDISTRACTIONCAPABILITY);

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
        DriverDistractionCapability testDriverDistractionCapability = (DriverDistractionCapability) msg.getCapabilityForType(SystemCapabilityType.DRIVER_DISTRACTION);

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SYSTEMCAPABILITYTYPE, testType);
        assertTrue(TestValues.TRUE, Validator.validateNavigationCapability(TestValues.GENERAL_NAVIGATIONCAPABILITY, testNavigationCapability));
        assertTrue(TestValues.TRUE, Validator.validatePhoneCapability(TestValues.GENERAL_PHONECAPABILITY, testPhoneCapability));
        assertTrue(TestValues.TRUE, Validator.validateRemoteControlCapabilities(TestValues.GENERAL_REMOTECONTROLCAPABILITIES, testRemoteControlCapabilities));
        assertTrue(TestValues.TRUE, Validator.validateAppServiceCapabilities(TestValues.GENERAL_APP_SERVICE_CAPABILITIES, testAppServicesCapabilities));
        assertTrue(TestValues.TRUE, Validator.validateDriverDistractionCapability(TestValues.GENERAL_DRIVERDISTRACTIONCAPABILITY, testDriverDistractionCapability));


        for(int i = 0; i < TestValues.GENERAL_DISPLAYCAPABILITY_LIST.size(); i++){
            assertTrue(TestValues.TRUE, Validator.validateDisplayCapability(TestValues.GENERAL_DISPLAYCAPABILITY_LIST.get(i), displayCapabilities.get(i)));
        }

        // Invalid/Null Tests
        SystemCapability msg = new SystemCapability();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getSystemCapabilityType());
        assertNull(TestValues.NULL, msg.getCapabilityForType(SystemCapabilityType.NAVIGATION));
        assertNull(TestValues.NULL, msg.getCapabilityForType(SystemCapabilityType.PHONE_CALL));
        assertNull(TestValues.NULL, msg.getCapabilityForType(SystemCapabilityType.REMOTE_CONTROL));
        assertNull(TestValues.NULL, msg.getCapabilityForType(SystemCapabilityType.APP_SERVICES));
        assertNull(TestValues.NULL, msg.getCapabilityForType(SystemCapabilityType.DISPLAYS));
        assertNull(TestValues.NULL, msg.getCapabilityForType(SystemCapabilityType.DRIVER_DISTRACTION));

        // Testing Setting an HMICapability as a SystemCapability
        HMICapabilities hmiCapabilities = new HMICapabilities();
        msg.setCapabilityForType(SystemCapabilityType.HMI, hmiCapabilities);
        assertNull(TestValues.NULL, msg.getCapabilityForType(SystemCapabilityType.HMI));

    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(SystemCapability.KEY_SYSTEM_CAPABILITY_TYPE, TestValues.GENERAL_SYSTEMCAPABILITYTYPE);
            reference.put(SystemCapability.KEY_NAVIGATION_CAPABILITY, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_NAVIGATIONCAPABILITY.getStore()));
            reference.put(SystemCapability.KEY_PHONE_CAPABILITY, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_PHONECAPABILITY.getStore()));
            reference.put(SystemCapability.KEY_REMOTE_CONTROL_CAPABILITY, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_REMOTECONTROLCAPABILITIES.getStore()));
            reference.put(SystemCapability.KEY_APP_SERVICES_CAPABILITIES, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_APP_SERVICE_CAPABILITIES.getStore()));
            reference.put(SystemCapability.KEY_DISPLAY_CAPABILITIES, TestValues.JSON_DISPLAYCAPABILITY_LIST);
            reference.put(SystemCapability.KEY_DRIVER_DISTRACTION_CAPABILITY, JsonRPCMarshaller.serializeHashtable(TestValues.GENERAL_DRIVERDISTRACTIONCAPABILITY.getStore()));

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();

                if(key.equals(SystemCapability.KEY_NAVIGATION_CAPABILITY)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateNavigationCapability( new NavigationCapability(hashReference), new NavigationCapability(hashTest)));
                } else if(key.equals(SystemCapability.KEY_PHONE_CAPABILITY)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validatePhoneCapability( new PhoneCapability(hashReference), new PhoneCapability(hashTest)));
                } else if(key.equals(SystemCapability.KEY_REMOTE_CONTROL_CAPABILITY)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateRemoteControlCapabilities( new RemoteControlCapabilities(hashReference), new RemoteControlCapabilities(hashTest)));
                } else if(key.equals(SystemCapability.KEY_APP_SERVICES_CAPABILITIES)){
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    Log.i("TEST REF", hashReference.toString());
                    Log.i("TEST TEST", hashTest.toString());
                    assertTrue(TestValues.TRUE, Validator.validateAppServiceCapabilities( new AppServicesCapabilities(hashReference), new AppServicesCapabilities(hashTest)));
                } else if(key.equals(SystemCapability.KEY_DISPLAY_CAPABILITIES)){
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for(int i = 0; i < referenceArray.length(); i++){
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest= JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateDisplayCapability(new DisplayCapability(hashReference), new DisplayCapability(hashTest)));
                    }
                } else if (key.equals(SystemCapability.KEY_DRIVER_DISTRACTION_CAPABILITY)) {
                    JSONObject objectEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(TestValues.TRUE, Validator.validateDriverDistractionCapability(new DriverDistractionCapability(hashReference), new DriverDistractionCapability(hashTest)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}