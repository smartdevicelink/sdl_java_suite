package com.smartdevicelink.test.rpc.datatypes;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.test.utils.JsonUtils;

public class HMIPermissionsTests extends TestCase{

    private static final List<HMILevel> allowedList        = Arrays.asList(new HMILevel[] { HMILevel.HMI_FULL,
            HMILevel.HMI_BACKGROUND                       });
    private static final List<HMILevel> userDisallowedList = Arrays.asList(new HMILevel[] { HMILevel.HMI_LIMITED,
            HMILevel.HMI_NONE                             });

    private HMIPermissions              msg;

    @Override
    public void setUp(){
        msg = new HMIPermissions();

        msg.setAllowed(allowedList);
        msg.setUserDisallowed(userDisallowedList);
    }

    public void testAllowed(){
        List<HMILevel> copy = msg.getAllowed();

        assertNotSame("Variable under test was not defensive copied.", allowedList, copy);
        assertEquals("List size didn't match expected size.", allowedList.size(), copy.size());

        for(int i = 0; i < allowedList.size(); i++){
            assertEquals("Input value didn't match expected value.", allowedList.get(i), copy.get(i));
        }
    }

    public void testUserDisallowed(){
        List<HMILevel> copy = msg.getUserDisallowed();

        assertNotSame("Variable under test was not defensive copied.", userDisallowedList, copy);
        assertEquals("List size didn't match expected size.", userDisallowedList.size(), copy.size());

        for(int i = 0; i < userDisallowedList.size(); i++){
            assertEquals("Input value didn't match expected value.", userDisallowedList.get(i), copy.get(i));
        }
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(HMIPermissions.KEY_ALLOWED, JsonUtils.createJsonArray(allowedList));
            reference.put(HMIPermissions.KEY_USER_DISALLOWED, JsonUtils.createJsonArray(userDisallowedList));

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                List<String> referenceList = JsonUtils.readStringListFromJsonObject(reference, key);
                List<String> underTestList = JsonUtils.readStringListFromJsonObject(underTest, key);

                assertEquals("JSON list size didn't match expected size.", referenceList.size(), underTestList.size());
                for(int i = 0; i < referenceList.size(); i++){
                    assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
                            referenceList.get(i), underTestList.get(i));
                }
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        HMIPermissions msg = new HMIPermissions();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Allowed list wasn't set, but getter method returned an object.", msg.getAllowed());
        assertNull("User disallowed list wasn't set, but getter method returned an object.", msg.getUserDisallowed());
    }
}
