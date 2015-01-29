package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.test.BaseRpcTests;

public class DeleteFileTests extends BaseRpcTests{

    private static final String FILENAME = "file.png";
    
    @Override
    protected RPCMessage createMessage(){
        DeleteFile msg = new DeleteFile();

         msg.setSdlFileName(FILENAME);

        return msg;
    }

    @Override
    protected String getMessageType(){
        return RPCMessage.KEY_REQUEST;
    }

    @Override
    protected String getCommandType(){
        return FunctionID.DELETE_FILE;
    }

    @Override
    protected JSONObject getExpectedParameters(int sdlVersion){
        JSONObject result = new JSONObject();

        try{
             result.put(DeleteFile.KEY_SDL_FILE_NAME, FILENAME);
        }catch(JSONException e){
            /* do nothing */
        }

        return result;
    }

    public void testSmartDeviceLinkFileName(){
         String filename = ( (DeleteFile) msg ).getSdlFileName();
         assertEquals("Filename didn't match input filename.", FILENAME, filename);
    }

    public void testNull(){
        DeleteFile msg = new DeleteFile();
        assertNotNull("Null object creation failed.", msg);

        testNullBase(msg);

         assertNull("Filename wasn't set, but getter method returned an object.", msg.getSdlFileName());
    }
}
