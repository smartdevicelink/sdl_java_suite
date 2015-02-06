package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;

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
    
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			DeleteFile cmd = new DeleteFile(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(parameters, DeleteFile.KEY_SDL_FILE_NAME), cmd.getSdlFileName());

		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
}
