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
import com.smartdevicelink.test.utils.Validator;

public class HMIPermissionsTests extends TestCase{

    private final List<HMILevel> ALLOWED_LIST        = Arrays.asList(new HMILevel[] { HMILevel.HMI_FULL,
            HMILevel.HMI_BACKGROUND                       });
    private static final HMILevel ALLOWED_ITEM_CHANGED        = HMILevel.HMI_BACKGROUND;
    	
    private final List<HMILevel> USER_DISALLOWED_LIST = Arrays.asList(new HMILevel[] { HMILevel.HMI_LIMITED,
            HMILevel.HMI_NONE                             });
    private static final HMILevel USER_DISALLOWED_ITEM_CHANGED = HMILevel.HMI_FULL;

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
    
    public void testGetAllowed() {
    	List<HMILevel> copy1 = msg.getAllowed();
    	copy1.set(0, ALLOWED_ITEM_CHANGED);
    	List<HMILevel> copy2 = msg.getAllowed();
    	
    	assertNotSame("Allowed lists were not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateHmiLevelLists(copy1, copy2));
    }
    
    public void testSetAllowed() {
    	List<HMILevel> copy1 = msg.getAllowed();
    	msg.setAllowed(copy1);
    	copy1.set(0, ALLOWED_ITEM_CHANGED);
    	List<HMILevel> copy2 = msg.getAllowed();
    	
    	assertNotSame("Allowed lists were not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateHmiLevelLists(copy1, copy2));
    }

    public void testUserDisallowed(){
        List<HMILevel> copy = msg.getUserDisallowed();

        assertEquals("List size didn't match expected size.", USER_DISALLOWED_LIST.size(), copy.size());

        for(int i = 0; i < USER_DISALLOWED_LIST.size(); i++){
            assertEquals("Input value didn't match expected value.", USER_DISALLOWED_LIST.get(i), copy.get(i));
        }
    }
    
    public void testGetUserDisallowed() {
    	List<HMILevel> copy1 = msg.getUserDisallowed();
    	copy1.set(0, USER_DISALLOWED_ITEM_CHANGED);
    	List<HMILevel> copy2 = msg.getUserDisallowed();
    	
    	assertNotSame("Allowed lists were not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateHmiLevelLists(copy1, copy2));
    }
    
    public void testSetUserDisallowed() {
    	List<HMILevel> copy1 = msg.getUserDisallowed();
    	msg.setUserDisallowed(copy1);
    	copy1.set(0, USER_DISALLOWED_ITEM_CHANGED);
    	List<HMILevel> copy2 = msg.getUserDisallowed();
    	
    	assertNotSame("User disallowed lists were not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateHmiLevelLists(copy1, copy2));
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
