package com.smartdevicelink.test.rpc.responses;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class ListFilesResponseTests extends BaseRpcTests{

    private static final List<String> FILENAMES       = Arrays.asList(new String[] { "turn_left.png", "turn_right.png",
            "proceed.png", "ding.mp3"                });
    private static final int          SPACE_AVAILABLE = 684355;

    @Override
    protected RPCMessage createMessage(){
        ListFilesResponse msg = new ListFilesResponse();

        msg.setFilenames(FILENAMES);
        msg.setSpaceAvailable(SPACE_AVAILABLE);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_RESPONSE;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.LIST_FILES;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
            result.put(ListFilesResponse.KEY_FILENAMES, JsonUtils.createJsonArray(FILENAMES));
            result.put(ListFilesResponse.KEY_SPACE_AVAILABLE, SPACE_AVAILABLE);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testFilenames(){
        List<String> filenames = ( (ListFilesResponse) msg ).getFilenames();
        assertNotSame("Filenames wasn't defensive copied.", FILENAMES, filenames);
        assertEquals("Filenames size didn't match expected size.", FILENAMES.size(), filenames.size());
        assertTrue("Filenames didn't match input filenames.", Validator.validateStringList(FILENAMES, filenames));
    }

    public void testSpaceAvailable(){
        int spaceAvailable = ( (ListFilesResponse) msg ).getSpaceAvailable();
        assertEquals("Space available didn't match expected space available.", SPACE_AVAILABLE, spaceAvailable);
    }

    public void testNull(){
        ListFilesResponse msg = new ListFilesResponse();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

        assertNull("Filenames wasn't set, but getter method returned an object.", msg.getFilenames());
        assertNull("Space available wasn't set, but getter method returned an object.", msg.getSpaceAvailable());
    }

}
