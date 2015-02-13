package com.smartdevicelink.test.rpc.responses;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DIDResult;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class ReadDIDResponseTest extends BaseRpcTests {

	private List<DIDResult> didResults;
	
	@Override
	protected RPCMessage createMessage() {
		ReadDIDResponse msg = new ReadDIDResponse();

		createCustomObjects();
		
		msg.setDidResult(didResults);

		return msg;
	}
	
	public void createCustomObjects () {
		didResults = new ArrayList<DIDResult>(2);

		DIDResult item1 = new DIDResult();
		item1.setData("data");
		item1.setResultCode(VehicleDataResultCode.SUCCESS);
		
		DIDResult item2 = new DIDResult();
		item2.setData("info");
		item2.setResultCode(VehicleDataResultCode.IGNORED);
		didResults.add(item1);
		didResults.add(item2);
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.READ_DID;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result    = new JSONObject(),
				   didResult = new JSONObject();
		JSONArray  didResults = new JSONArray();

		try {
			didResult.put(DIDResult.KEY_DATA, "data");
			didResult.put(DIDResult.KEY_RESULT_CODE, VehicleDataResultCode.SUCCESS);
			didResults.put(didResult);
			
			didResult = new JSONObject();
			didResult.put(DIDResult.KEY_DATA, "info");
			didResult.put(DIDResult.KEY_RESULT_CODE, VehicleDataResultCode.IGNORED);
			didResults.put(didResult);
			
			result.put(ReadDIDResponse.KEY_DID_RESULT, didResults);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testDidResults() {
		List<DIDResult> copy = ( (ReadDIDResponse) msg ).getDidResult();
		
		assertNotNull("Did results were null.", copy);
		assertNotSame("Did results were not defensive copied.", didResults, copy);
		assertTrue("Did results didn't match input data.", Validator.validateDIDResults(didResults, copy));
	}

	public void testNull() {
		ReadDIDResponse msg = new ReadDIDResponse();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Did response wasn't set, but getter method returned an object.", msg.getDidResult());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			ReadDIDResponse cmd = new ReadDIDResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONArray didResultArray = JsonUtils.readJsonArrayFromJsonObject(parameters, ReadDIDResponse.KEY_DID_RESULT);
			List<DIDResult> didResultList = new ArrayList<DIDResult>();
			for (int index = 0; index < didResultArray.length(); index++) {
				DIDResult chunk = new DIDResult(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)didResultArray.get(index)) );
				didResultList.add(chunk);
			}
			assertTrue("TTSChunk list doesn't match input TTSChunk list",  Validator.validateDIDResults(didResultList, cmd.getDidResult()));
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
