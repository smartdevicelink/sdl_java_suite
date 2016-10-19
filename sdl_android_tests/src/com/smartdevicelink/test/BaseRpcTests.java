package com.smartdevicelink.test;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;

public abstract class BaseRpcTests extends AndroidTestCase {

    public static final int  SDL_VERSION_UNDER_TEST = Config.SDL_VERSION_UNDER_TEST;

    private static final int CORR_ID = 402;

    protected RPCMessage msg;

    /**
	 * Sets up the specific RPC message under testing.
	 */
    protected abstract RPCMessage createMessage();

    /**
	 * Retrieves the RPC message type under testing.
	 */
    protected abstract String getMessageType();

    /**
	 * Retrieves the RPC command type under testing.
	 */
    protected abstract String getCommandType();

    /**
	 * Retrieves the JSON translated RPC message under testing.
	 */
    protected abstract JSONObject getExpectedParameters(int sdlVersion);

    @Override
    public void setUp(){
        this.msg = createMessage();
        if (msg instanceof RPCRequest) {
        	((RPCRequest) msg).setCorrelationID(CORR_ID);
        }
        else if (msg instanceof RPCResponse) {
        	((RPCResponse) msg).setCorrelationID(CORR_ID);
        }
        
    }

    public void testCreation(){
        assertNotNull("Object creation failed.", msg);
    }

    public void testCorrelationId(){
    	int correlationId;
    	if (msg instanceof RPCRequest) {
            correlationId = ((RPCRequest) msg).getCorrelationID();  
            assertEquals("Correlation ID doesn't match expected ID.", CORR_ID, correlationId);
    	}
    	else if (msg instanceof RPCResponse) {
            correlationId = ((RPCResponse) msg).getCorrelationID();
            assertEquals("Correlation ID doesn't match expected ID.", CORR_ID, correlationId);
    	}
    
    }

    public void testMessageType(){
        String messageType = msg.getMessageType();
        
        assertNotNull("Message type was null.", messageType);
        assertEquals("Message type was not REQUEST.", getMessageType(), messageType);
    }

    public void testCommandType(){
        String command = msg.getFunctionName();

        assertNotNull("Command was null.", command);
        assertEquals("Command type was not ADD_COMMAND", getCommandType(), command);
    }


    public void testFunctionName(){
        String funcName = msg.getFunctionName();

        assertNotNull("Function name was null.", funcName);
        assertEquals("Function name did not match expected name.", getCommandType(), funcName);
    }

    public void testJson(){
        try{
            JSONObject reference = buildJsonStore();
            JSONObject underTest = msg.serializeJSON();  
            
            assertEquals("Size of JSON under test didn't match expected size.", reference.length(), underTest.length());

            // loop through all values and verifies they match the RPCMessage parameters
            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                Object referenceValue = JsonUtils.readObjectFromJsonObject(reference, key);
                testJsonParameters((JSONObject) referenceValue, (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            // do nothing 
        }
    }
    
    private JSONObject buildJsonStore() throws JSONException{
        JSONObject result = new JSONObject(), command = new JSONObject();

        if (!getMessageType().equals(RPCMessage.KEY_NOTIFICATION)) {
        	command.put(RPCMessage.KEY_CORRELATION_ID, CORR_ID);
        }
        command.put(RPCMessage.KEY_FUNCTION_NAME, msg.getFunctionName());
        command.put(RPCMessage.KEY_PARAMETERS, getExpectedParameters(SDL_VERSION_UNDER_TEST));

        result.put(getMessageType(), command);

        return result;
    }
    
    private void testJsonParameters(JSONObject reference, JSONObject underTest) throws JSONException{
        assertEquals("Size of JSON under test didn't match expected size.", reference.length(), underTest.length());

        Iterator<?> iterator = reference.keys();
        while(iterator.hasNext()){
            String key = (String) iterator.next();
            Object referenceValue = JsonUtils.readObjectFromJsonObject(reference, key);
            if(referenceValue instanceof JSONObject){
                testJsonParameters((JSONObject) referenceValue, (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key));
            }
            else if(referenceValue instanceof JSONArray){
                JSONArray array1 = (JSONArray) referenceValue, array2 = (JSONArray) JsonUtils.readObjectFromJsonObject(underTest, key);
                testJsonArray(array1, array2, key);
            }
            else{
                assertTrue("JSON object didn't match reference object for key \"" + key + "\".", referenceValue.equals(JsonUtils.readObjectFromJsonObject(underTest, key)));
            }
        }
    }
    
    private void testJsonArray(JSONArray reference, JSONArray underTest, String key) throws JSONException{
        assertEquals("Size of JSON array didn't match expected size.", reference.length(), underTest.length());
        int len = reference.length();
        for(int i=0; i<len; i++){
            Object array1Obj = reference.get(i), array2Obj = underTest.get(i);
            if(array1Obj instanceof JSONObject){
                testJsonParameters((JSONObject) array1Obj, (JSONObject) array2Obj);
            }
            else if(array1Obj instanceof JSONArray){
                testJsonArray((JSONArray) array1Obj, (JSONArray) array2Obj, key);
            }
            else{
                assertTrue("JSONArray object didn't match reference object for key \"" + key + "\".", array1Obj.equals(array2Obj));
            }
        }
    }

    // this method must be manually called from the subclass
    protected void testNullBase(RPCMessage msg){
        assertNotNull("RPCMessage was null.", msg);
    	
    	Integer correlationId;
    	if (msg instanceof RPCRequest) {
            correlationId = ((RPCRequest) msg).getCorrelationID();  
            assertNull("Correlation ID of the RPC message was not null.", correlationId);
            //assertEquals("Correlation ID didn't match expected correlation ID.", CORR_ID, (int) correlationId);
    	}
    	else if (msg instanceof RPCResponse) {
            correlationId = ((RPCResponse) msg).getCorrelationID();
            assertNull("Correlation ID of the RPC message was not null.", correlationId);
            //assertEquals("Correlation ID didn't match expected correlation ID.", CORR_ID, (int) correlationId);
    	}

        assertNotNull("Message type of the RPC message was null.", msg.getMessageType());
        
        assertEquals("Message type didn't match expected message type.", getMessageType(), msg.getMessageType());

        assertNotNull("Command type of the RPC message was null.", msg.getMessageType());
        assertEquals("Command type didn't match expected command type.", getCommandType(), msg.getFunctionName());


        try{
            assertTrue("Parameters weren't initialized, but the JSON contained 2 or more objects.", (msg.serializeJSON().length() == 1));
        } catch(JSONException e) {
            //do nothing
        }

    }  
}