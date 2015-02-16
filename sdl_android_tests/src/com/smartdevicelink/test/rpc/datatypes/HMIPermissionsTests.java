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

    private final List<HMILevel> ALLOWED_LIST        = Arrays.asList(new HMILevel[] { HMILevel.HMI_FULL,
            HMILevel.HMI_BACKGROUND                       });
    	
    private final List<HMILevel> USER_DISALLOWED_LIST = Arrays.asList(new HMILevel[] { HMILevel.HMI_LIMITED,
            HMILevel.HMI_NONE                             });

    private HMIPermissions              msg;

    @Override
    public void setUp(){
        msg = new HMIPermissions();

        msg.setAllowed(ALLOWED_LIST);
        msg.setUserDisallowed(USER_DISALLOWED_LIST);
    }

    public void testAllowed(){
        List<HMILevel> copy = msg.getAllowed();

        assertEquals("List size didn't match expected size.", ALLOWED_LIST.size(), copy.size());

        for(int i = 0; i < ALLOWED_LIST.size(); i++){
            assertEquals("Input value didn't match expected value.", ALLOWED_LIST.get(i), copy.get(i));
        }
    }

    public void testUserDisallowed(){
        List<HMILevel> copy = msg.getUserDisallowed();

        assertEquals("List size didn't match expected size.", USER_DISALLOWED_LIST.size(), copy.size());

        for(int i = 0; i < USER_DISALLOWED_LIST.size(); i++){
            assertEquals("Input value didn't match expected value.", USER_DISALLOWED_LIST.get(i), copy.get(i));
        }
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(HMIPermissions.KEY_ALLOWED, JsonUtils.createJsonArray(ALLOWED_LIST));
            reference.put(HMIPermissions.KEY_USER_DISALLOWED, JsonUtils.createJsonArray(USER_DISALLOWED_LIST));

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
