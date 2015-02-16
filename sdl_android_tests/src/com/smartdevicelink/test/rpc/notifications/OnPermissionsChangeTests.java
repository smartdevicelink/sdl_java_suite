package com.smartdevicelink.test.rpc.notifications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.enums.SdlCommand;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class OnPermissionsChangeTests extends BaseRpcTests{

    private static final String RPC_NAME = SdlCommand.ADD_COMMAND.name();
    private final List<HMILevel> HMI_ALLOWED = Arrays.asList(new HMILevel[]{HMILevel.HMI_FULL, HMILevel.HMI_BACKGROUND});
    private final List<HMILevel> HMI_DISALLOWED = Arrays.asList(new HMILevel[]{HMILevel.HMI_LIMITED, HMILevel.HMI_NONE});
    private final List<String> PARAMETERS_ALLOWED = Arrays.asList(new String[]{AddCommand.KEY_CMD_ICON,
            AddCommand.KEY_CMD_ID, AddCommand.KEY_MENU_PARAMS, AddCommand.KEY_VR_COMMANDS});
    private static final List<String> PARAMETERS_DISALLOWED = Arrays.asList(new String[]{AddCommand.KEY_MENU_PARAMS, AddCommand.KEY_VR_COMMANDS});
    private static final String RPC_NAME_CHANGED = "illegal";
    private static final HMILevel HMI_ALLOWED_CHANGED = HMILevel.HMI_BACKGROUND;
    private static final HMILevel HMI_DISALLOWED_CHANGED = HMILevel.HMI_LIMITED;
    private static final String PARAMETERS_ALLOWED_CHANGED = "Changed parameter";
    private static final String PARAMETERS_DISALLOWED_CHANGED = "Don't include me";
    private List<PermissionItem> permissionList;
    private PermissionItem permissionItem;
    private HMIPermissions hmiPermissions;
    private ParameterPermissions parameterPermissions;

    @Override
    protected RPCMessage createMessage(){
        createPermissionList();

        OnPermissionsChange msg = new OnPermissionsChange();

        msg.setPermissionItem(permissionList);
        

        return msg;
    }

    private void createPermissionList(){
        permissionList = new ArrayList<PermissionItem>(1);
        
        permissionItem = new PermissionItem();
        
        hmiPermissions = new HMIPermissions();
        hmiPermissions.setAllowed(HMI_ALLOWED);
        hmiPermissions.setUserDisallowed(HMI_DISALLOWED);
        permissionItem.setHMIPermissions(hmiPermissions);
        
        parameterPermissions = new ParameterPermissions();
        parameterPermissions.setAllowed(PARAMETERS_ALLOWED);
        parameterPermissions.setUserDisallowed(PARAMETERS_DISALLOWED);
        permissionItem.setParameterPermissions(parameterPermissions);
        
        permissionItem.setRpcName(RPC_NAME);
        
        permissionList.add(permissionItem);
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_NOTIFICATION;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.ON_PERMISSIONS_CHANGE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();
        JSONObject permissionItemJson = new JSONObject();
        JSONObject hmiPermissionsJson = new JSONObject();
        JSONObject parameterPermissionsJson = new JSONObject();
        JSONArray permissionListJson = new JSONArray();

        try{
            hmiPermissionsJson.put(HMIPermissions.KEY_ALLOWED, JsonUtils.createJsonArrayOfJsonNames(HMI_ALLOWED, sdlVersion));
            hmiPermissionsJson.put(HMIPermissions.KEY_USER_DISALLOWED, JsonUtils.createJsonArrayOfJsonNames(HMI_DISALLOWED, sdlVersion));
            
            parameterPermissionsJson.put(ParameterPermissions.KEY_ALLOWED, JsonUtils.createJsonArray(PARAMETERS_ALLOWED));
            parameterPermissionsJson.put(ParameterPermissions.KEY_USER_DISALLOWED, JsonUtils.createJsonArray(PARAMETERS_DISALLOWED));
            
            permissionItemJson.put(PermissionItem.KEY_RPC_NAME, RPC_NAME);
            permissionItemJson.put(PermissionItem.KEY_HMI_PERMISSIONS, hmiPermissionsJson);
            permissionItemJson.put(PermissionItem.KEY_PARAMETER_PERMISSIONS, parameterPermissionsJson);
            
            permissionListJson.put(permissionItemJson);
            
            result.put(OnPermissionsChange.KEY_PERMISSION_ITEM, permissionListJson);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testPermissionItem(){
        List<PermissionItem> data = ( (OnPermissionsChange) msg ).getPermissionItem();

        assertEquals("Input size didn't match expected size.", permissionList.size(), data.size());
        for(int i=0; i<data.size(); i++){
            assertTrue("Data didn't match input data.", Validator.validatePermissionItem(permissionList.get(i), data.get(i)));
        }
    }
    
    public void testGetPermissionItem() {
    	List<PermissionItem> copy1 = ( (OnPermissionsChange) msg).getPermissionItem();
    	PermissionItem firstPermissionItem = copy1.get(0);
    	HMIPermissions firstHmiPermissions = firstPermissionItem.getHMIPermissions();
    	ParameterPermissions firstParameterPermissions = firstPermissionItem.getParameterPermissions();
    	//change the first item in each of the following:
    	List<HMILevel> firstAllowedInHmiPermissions = firstHmiPermissions.getAllowed();
    	List<HMILevel> firstDisallowedInHmiPermissions = firstHmiPermissions.getUserDisallowed();
    	List<String> firstAllowedInParameterPermissions = firstParameterPermissions.getAllowed();
    	List<String> firstDisallowedInParameterPermissions = firstParameterPermissions.getUserDisallowed();
    	//set the variables to something different
    	firstPermissionItem.setRpcName(RPC_NAME_CHANGED);
    	firstAllowedInHmiPermissions.set(0, HMI_ALLOWED_CHANGED);
    	firstDisallowedInHmiPermissions.set(0, HMI_DISALLOWED_CHANGED);
    	firstAllowedInParameterPermissions.set(0, PARAMETERS_ALLOWED_CHANGED);
    	firstDisallowedInParameterPermissions.set(0, PARAMETERS_DISALLOWED_CHANGED);
    	
    	List<PermissionItem> copy2 = ( (OnPermissionsChange) msg).getPermissionItem();
    	
    	assertNotSame("Permission item list was not defensive copied", copy1, copy2);
    	
    	//test the first object for different values, and test the rest of the objects in the list for same values
    	PermissionItem permissionItemFirst1 = copy1.get(0);
    	PermissionItem permissionItemFirst2 = copy2.get(0);
		
		assertNotSame("Permission item was not defensive copied", permissionItemFirst1, permissionItemFirst2);
		
		HMIPermissions firstHmiPermissions1 = permissionItemFirst1.getHMIPermissions();
		HMIPermissions firstHmiPermissions2 = permissionItemFirst2.getHMIPermissions();
		ParameterPermissions firstParameterPermissions1 = permissionItemFirst1.getParameterPermissions();
		ParameterPermissions firstParameterPermissions2 = permissionItemFirst2.getParameterPermissions();

		assertNotSame("First HMI permission item not defensive copied", firstHmiPermissions1, firstHmiPermissions2);
		assertNotSame("First parameter permission item not defensive copied", firstParameterPermissions1, firstParameterPermissions2);
		
    	List<HMILevel> firstAllowedInHmiPermissions1 = firstHmiPermissions1.getAllowed();
    	List<HMILevel> firstAllowedInHmiPermissions2 = firstHmiPermissions2.getAllowed();
    	List<HMILevel> firstDisallowedInHmiPermissions1 = firstHmiPermissions1.getUserDisallowed();
    	List<HMILevel> firstDisallowedInHmiPermissions2 = firstHmiPermissions2.getUserDisallowed();
    	List<String> firstAllowedInParameterPermissions1 = firstParameterPermissions1.getAllowed();
    	List<String> firstAllowedInParameterPermissions2 = firstParameterPermissions2.getAllowed();
    	List<String> firstDisallowedInParameterPermissions1 = firstParameterPermissions1.getUserDisallowed();
    	List<String> firstDisallowedInParameterPermissions2 = firstParameterPermissions2.getUserDisallowed();
    	
    	assertNotSame("First HMI Level allowed item not defensive copied", firstAllowedInHmiPermissions1, firstAllowedInHmiPermissions2);
    	assertNotSame("First HMI Level disallowed item not defensive copied", firstDisallowedInHmiPermissions1, firstDisallowedInHmiPermissions2);
    	assertNotSame("First parameter allowed item not defensive copied", firstAllowedInParameterPermissions1, firstAllowedInParameterPermissions2);
    	assertNotSame("First parameter disallowed item not defensive copied", firstDisallowedInParameterPermissions1, firstDisallowedInParameterPermissions2);
    	
    	assertFalse("First permission item objects matched", Validator.validatePermissionItem(permissionItemFirst1, permissionItemFirst2));
		
    	for (int index = 1; index < copy1.size(); index++) {
    		PermissionItem permItemCopy1 = copy1.get(index);
    		PermissionItem permItemCopy2 = copy2.get(index);
    		
    		assertNotSame("Permission item was not defensive copied", permItemCopy1, permItemCopy2);

    		HMIPermissions firstHmiPermissionsCopy1 = permItemCopy1.getHMIPermissions();
    		HMIPermissions firstHmiPermissionsCopy2 = permItemCopy2.getHMIPermissions();
    		ParameterPermissions firstParameterPermissionsCopy1 = permItemCopy1.getParameterPermissions();
    		ParameterPermissions firstParameterPermissionsCopy2 = permItemCopy2.getParameterPermissions();

    		assertNotSame("First HMI permission item not defensive copied", firstHmiPermissionsCopy1, firstHmiPermissionsCopy2);
    		assertNotSame("First parameter permission item not defensive copied", firstParameterPermissionsCopy1, firstParameterPermissionsCopy2);
    		
        	List<HMILevel> allowedInHmiPermissionsCopy1 = firstHmiPermissionsCopy1.getAllowed();
        	List<HMILevel> allowedInHmiPermissionsCopy2 = firstHmiPermissionsCopy2.getAllowed();
        	List<HMILevel> disallowedInHmiPermissionsCopy1 = firstHmiPermissionsCopy1.getUserDisallowed();
        	List<HMILevel> disallowedInHmiPermissionsCopy2 = firstHmiPermissionsCopy2.getUserDisallowed();
        	List<String> allowedInParameterPermissionsCopy1 = firstParameterPermissionsCopy1.getAllowed();
        	List<String> allowedInParameterPermissionsCopy2 = firstParameterPermissionsCopy2.getAllowed();
        	List<String> disallowedInParameterPermissionsCopy1 = firstParameterPermissionsCopy1.getUserDisallowed();
        	List<String> disallowedInParameterPermissionsCopy2 = firstParameterPermissionsCopy2.getUserDisallowed();
        	
        	assertNotSame("First HMI Level allowed item not defensive copied", allowedInHmiPermissionsCopy1, allowedInHmiPermissionsCopy2);
        	assertNotSame("First HMI Level disallowed item not defensive copied", disallowedInHmiPermissionsCopy1, disallowedInHmiPermissionsCopy2);
        	assertNotSame("First parameter allowed item not defensive copied", allowedInParameterPermissionsCopy1, allowedInParameterPermissionsCopy2);
        	assertNotSame("First parameter disallowed item not defensive copied", disallowedInParameterPermissionsCopy1, disallowedInParameterPermissionsCopy2);
    		
    		assertFalse("Input value didn't match expected value", Validator.validatePermissionItem(permItemCopy1, permItemCopy2));
    	}
    	
    }
    
    public void testSetPermissionItem() {
    	List<PermissionItem> copy1 = ( (OnPermissionsChange) msg).getPermissionItem();
    	PermissionItem firstPermissionItem = copy1.get(0);
    	HMIPermissions firstHmiPermissions = firstPermissionItem.getHMIPermissions();
    	ParameterPermissions firstParameterPermissions = firstPermissionItem.getParameterPermissions();
    	//change the first item in each of the following:
    	List<HMILevel> firstAllowedInHmiPermissions = firstHmiPermissions.getAllowed();
    	List<HMILevel> firstDisallowedInHmiPermissions = firstHmiPermissions.getUserDisallowed();
    	List<String> firstAllowedInParameterPermissions = firstParameterPermissions.getAllowed();
    	List<String> firstDisallowedInParameterPermissions = firstParameterPermissions.getUserDisallowed();
    	( (OnPermissionsChange) msg).setPermissionItem(copy1);
    	//set the variables to something different
    	firstPermissionItem.setRpcName(RPC_NAME_CHANGED);
    	firstAllowedInHmiPermissions.set(0, HMI_ALLOWED_CHANGED);
    	firstDisallowedInHmiPermissions.set(0, HMI_DISALLOWED_CHANGED);
    	firstAllowedInParameterPermissions.set(0, PARAMETERS_ALLOWED_CHANGED);
    	firstDisallowedInParameterPermissions.set(0, PARAMETERS_DISALLOWED_CHANGED);
    	
    	List<PermissionItem> copy2 = ( (OnPermissionsChange) msg).getPermissionItem();
    	
    	assertNotSame("Permission item list was not defensive copied", copy1, copy2);
    	
    	//test the first object for different values, and test the rest of the objects in the list for same values
    	PermissionItem permissionItemFirst1 = copy1.get(0);
    	PermissionItem permissionItemFirst2 = copy2.get(0);
		
		assertNotSame("Permission item was not defensive copied", permissionItemFirst1, permissionItemFirst2);
		
		HMIPermissions firstHmiPermissions1 = permissionItemFirst1.getHMIPermissions();
		HMIPermissions firstHmiPermissions2 = permissionItemFirst2.getHMIPermissions();
		ParameterPermissions firstParameterPermissions1 = permissionItemFirst1.getParameterPermissions();
		ParameterPermissions firstParameterPermissions2 = permissionItemFirst2.getParameterPermissions();

		assertNotSame("First HMI permission item not defensive copied", firstHmiPermissions1, firstHmiPermissions2);
		assertNotSame("First parameter permission item not defensive copied", firstParameterPermissions1, firstParameterPermissions2);
		
    	List<HMILevel> firstAllowedInHmiPermissions1 = firstHmiPermissions1.getAllowed();
    	List<HMILevel> firstAllowedInHmiPermissions2 = firstHmiPermissions2.getAllowed();
    	List<HMILevel> firstDisallowedInHmiPermissions1 = firstHmiPermissions1.getUserDisallowed();
    	List<HMILevel> firstDisallowedInHmiPermissions2 = firstHmiPermissions2.getUserDisallowed();
    	List<String> firstAllowedInParameterPermissions1 = firstParameterPermissions1.getAllowed();
    	List<String> firstAllowedInParameterPermissions2 = firstParameterPermissions2.getAllowed();
    	List<String> firstDisallowedInParameterPermissions1 = firstParameterPermissions1.getUserDisallowed();
    	List<String> firstDisallowedInParameterPermissions2 = firstParameterPermissions2.getUserDisallowed();
    	
    	assertNotSame("First HMI Level allowed item not defensive copied", firstAllowedInHmiPermissions1, firstAllowedInHmiPermissions2);
    	assertNotSame("First HMI Level disallowed item not defensive copied", firstDisallowedInHmiPermissions1, firstDisallowedInHmiPermissions2);
    	assertNotSame("First parameter allowed item not defensive copied", firstAllowedInParameterPermissions1, firstAllowedInParameterPermissions2);
    	assertNotSame("First parameter disallowed item not defensive copied", firstDisallowedInParameterPermissions1, firstDisallowedInParameterPermissions2);
    	
    	assertFalse("First permission item objects matched", Validator.validatePermissionItem(permissionItemFirst1, permissionItemFirst2));
		
    	for (int index = 1; index < copy1.size(); index++) {
    		PermissionItem permItemCopy1 = copy1.get(index);
    		PermissionItem permItemCopy2 = copy2.get(index);
    		
    		assertNotSame("Permission item was not defensive copied", permItemCopy1, permItemCopy2);
    		
    		HMIPermissions firstHmiPermissionsCopy1 = permItemCopy1.getHMIPermissions();
    		HMIPermissions firstHmiPermissionsCopy2 = permItemCopy2.getHMIPermissions();
    		ParameterPermissions firstParameterPermissionsCopy1 = permItemCopy1.getParameterPermissions();
    		ParameterPermissions firstParameterPermissionsCopy2 = permItemCopy2.getParameterPermissions();

    		assertNotSame("First HMI permission item not defensive copied", firstHmiPermissionsCopy1, firstHmiPermissionsCopy2);
    		assertNotSame("First parameter permission item not defensive copied", firstParameterPermissionsCopy1, firstParameterPermissionsCopy2);
    		
        	List<HMILevel> allowedInHmiPermissionsCopy1 = firstHmiPermissionsCopy1.getAllowed();
        	List<HMILevel> allowedInHmiPermissionsCopy2 = firstHmiPermissionsCopy2.getAllowed();
        	List<HMILevel> disallowedInHmiPermissionsCopy1 = firstHmiPermissionsCopy1.getUserDisallowed();
        	List<HMILevel> disallowedInHmiPermissionsCopy2 = firstHmiPermissionsCopy2.getUserDisallowed();
        	List<String> allowedInParameterPermissionsCopy1 = firstParameterPermissionsCopy1.getAllowed();
        	List<String> allowedInParameterPermissionsCopy2 = firstParameterPermissionsCopy2.getAllowed();
        	List<String> disallowedInParameterPermissionsCopy1 = firstParameterPermissionsCopy1.getUserDisallowed();
        	List<String> disallowedInParameterPermissionsCopy2 = firstParameterPermissionsCopy2.getUserDisallowed();
        	
        	assertNotSame("First HMI Level allowed item not defensive copied", allowedInHmiPermissionsCopy1, allowedInHmiPermissionsCopy2);
        	assertNotSame("First HMI Level disallowed item not defensive copied", disallowedInHmiPermissionsCopy1, disallowedInHmiPermissionsCopy2);
        	assertNotSame("First parameter allowed item not defensive copied", allowedInParameterPermissionsCopy1, allowedInParameterPermissionsCopy2);
        	assertNotSame("First parameter disallowed item not defensive copied", disallowedInParameterPermissionsCopy1, disallowedInParameterPermissionsCopy2);
    		
    		assertFalse("Input value didn't match expected value", Validator.validatePermissionItem(permItemCopy1, permItemCopy2));
    	}
    	
    }

    public void testNull(){
        OnPermissionsChange msg = new OnPermissionsChange();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Permission item wasn't set, but getter method returned an object.", msg.getPermissionItem());
    }
}
