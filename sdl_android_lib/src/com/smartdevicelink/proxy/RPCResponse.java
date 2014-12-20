/**
 * 
 */
package com.smartdevicelink.proxy;

import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.util.JsonUtils;

/**
 * Result sent by SDL after an RPC is processed, consists of four parts: 
 * <ul>
 * <li>
 * CorrelationID:
 * <ul>
 * An integer value correlating the response to the corresponding request.
 * </ul>
 * </li> <li>Success:
 * <ul>
 * A Boolean indicating whether the original request was successfully processed.
 * </ul>
 * </li> <li>ResultCode:
 * <ul>
 * 
 * The result code provides additional information about a response returning a
 * failed outcome.
 * <br>
 * 
 * Any response can have at least one, or possibly more, of the following result
 * code values: SUCCESS, INVALID_DATA, OUT_OF_MEMORY, TOO_MANY_PENDING_REQUESTS,
 * APPLICATION_NOT_REGISTERED, GENERIC_ERROR,REJECTED.
 * <br>
 * 
 * Any additional result codes for a given operation can be found in related
 * RPCs
 * <br>
 * </ul>
 * </li> <li>Info:
 * <ul>
 * A string of text representing additional information returned from SDL. This
 * could be useful in debugging.
 * </ul>
 * </li>
 * </ul>
 */
public class RPCResponse extends RPCMessage {
	public static final String KEY_SUCCESS = "success";
	public static final String KEY_INFO = "info";
	public static final String KEY_RESULT_CODE = "resultCode";
	
	protected String resultCode, info;
	protected Boolean success;
	
	/**
	*<p>Constructs a newly allocated RPCResponse object using function name</p>
	*@param functionName a string that indicates the function's name
	*/
	public RPCResponse(String functionName) {
		super(functionName, RPCMessage.KEY_RESPONSE);
	}
	
	/**
     *<p>Constructs a newly allocated RPCResponse object using a RPCMessage object</p>
     *@param rpcMsg The {@linkplain RPCMessage} to use
     */   
	public RPCResponse(RPCMessage rpcMsg) {
		super(rpcMsg);
	}
	
	public RPCResponse(JSONObject json, int sdlVersion){
	    super(RPCMessage.KEY_RESPONSE, json);
	    switch(sdlVersion){
	    default:
	        this.resultCode = JsonUtils.readStringFromJsonObject(json, KEY_RESULT_CODE);
	        this.info = JsonUtils.readStringFromJsonObject(json, KEY_INFO);
	        this.success = JsonUtils.readBooleanFromJsonObject(json, KEY_SUCCESS);
	        break;
	    }
	}
	
	/**
	 * <p>
	 * Returns Success whether the request is successfully processed
	 * </p>
	 * 
	 * @return Boolean  the status of whether the request is successfully done
	 */
	public Boolean getSuccess() {
        return this.success;
    }
	
	/**
	 * <p>
	 * Set the Success status
	 * </p>
	 * 
	 * @param success
	 *             whether the request is successfully processed
	 */
    public void setSuccess( Boolean success ) {
        this.success = success;
    }
    
	/**
	 * <p>
	 * Returns ResultCode additional information about a response returning a failed outcome
	 * </p>
	 * 
	 * @return {@linkplain Result}  the status of whether the request is successfully done
	 */
    public Result getResultCode() {
        return Result.valueForJsonName(this.resultCode, sdlVersion);
    }
    
	/**
	 * <p>
	 * Set the additional information about a response returning a failed outcome
	 * </p>
	 * 
	 * @param resultCode
	 *             whether the request is successfully processed
	 */
    public void setResultCode( Result resultCode ) {
        this.resultCode = resultCode.getJsonName(sdlVersion);
    }
    
	/**
	 * <p>
	 * Returns a string of text representing additional information returned from SDL
	 * </p>
	 * 
	 * @return String  A string of text representing additional information returned from SDL
	 */
    public String getInfo() {
        return this.info;
    }
    
	/**
	 * <p>
	 * Set a string of text representing additional information returned from SDL
	 * </p>
	 * 
	 * @param info
	 *             a string of text representing additional information returned from SDL
	 */
    public void setInfo( String info ) {
        this.info = info;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = new JSONObject();
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_INFO, this.info);
            JsonUtils.addToJsonObject(result, KEY_RESULT_CODE, this.resultCode);
            JsonUtils.addToJsonObject(result, KEY_SUCCESS, this.success);
            break;
        }
        
        return result;
    }
}
