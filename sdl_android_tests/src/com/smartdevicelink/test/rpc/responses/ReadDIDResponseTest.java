package com.smartdevicelink.test.rpc.responses;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DIDResult;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.test.BaseRpcTests;
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
		item1.setData("info");
		item1.setResultCode(VehicleDataResultCode.IGNORED);
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

}
