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
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class OnPermissionsChangeTests extends BaseRpcTests{

    private static final String RPC_NAME = FunctionID.ADD_COMMAND;
    private final List<HMILevel> HMI_ALLOWED = Arrays.asList(new HMILevel[]{HMILevel.HMI_FULL, HMILevel.HMI_BACKGROUND});
    private final List<HMILevel> HMI_DISALLOWED = Arrays.asList(new HMILevel[]{HMILevel.HMI_LIMITED, HMILevel.HMI_NONE});
    private final List<String> PARAMETERS_ALLOWED = Arrays.asList(new String[]{AddCommand.KEY_CMD_ICON,
            AddCommand.KEY_CMD_ID, AddCommand.KEY_MENU_PARAMS, AddCommand.KEY_VR_COMMANDS});
    private final List<String> PARAMETERS_DISALLOWED = Arrays.asList(new String[]{AddCommand.KEY_MENU_PARAMS, AddCommand.KEY_VR_COMMANDS});
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

    public void testNull(){
        OnPermissionsChange msg = new OnPermissionsChange();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Permission item wasn't set, but getter method returned an object.", msg.getPermissionItem());
    }
}
