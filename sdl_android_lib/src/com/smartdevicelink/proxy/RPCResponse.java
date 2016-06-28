/**
 * 
 */
package com.smartdevicelink.proxy;

import java.util.Hashtable;

import com.smartdevicelink.proxy.rpc.enums.Result;

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
 * <p>The result code provides additional information about a response returning a
 * failed outcome.</p>
 * 
 * 
 * <p>Any response can have at least one, or possibly more, of the following result
 * code values: SUCCESS, INVALID_DATA, OUT_OF_MEMORY, TOO_MANY_PENDING_REQUESTS,
 * APPLICATION_NOT_REGISTERED, GENERIC_ERROR,REJECTED.</p>
 * 
 * 
 * <p>Any additional result codes for a given operation can be found in related
 * RPCs</p>
 * 
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
	/**
	*<p>Constructs a newly allocated RPCResponse object using function name</p>
	*@param functionName a string that indicates the function's name
	*/
	public RPCResponse(String functionName) {
		super(functionName, RPCMessage.KEY_RESPONSE);
	}
	/**
     *<p>Constructs a newly allocated RPCResponse object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */   
	public RPCResponse(Hashtable<String, Object> hash) {
		super(hash);
	}
	/**
     *<p>Constructs a newly allocated RPCResponse object using a RPCMessage object</p>
     *@param rpcMsg The {@linkplain RPCMessage} to use
     */   
	public RPCResponse(RPCMessage rpcMsg) {
		super(preprocessMsg(rpcMsg));
	}
	
	static RPCMessage preprocessMsg (RPCMessage rpcMsg) {
		if (rpcMsg.getMessageType() != RPCMessage.KEY_RESPONSE) {
			rpcMsg.messageType = RPCMessage.KEY_RESPONSE;
		}
		
		return rpcMsg;
	}

	/**
	 * <p>
	 * Returns correlationID the ID of the request
	 * </p>
	 * 
	 * @return int  the ID of the request
	 */
	public Integer getCorrelationID() {
		return (Integer)function.get(RPCMessage.KEY_CORRELATION_ID);
	}
	
	/**
	 * <p>
	 * Set the correlationID
	 * </p>
	 * 
	 * @param correlationID
	 *            the ID of the response
	 */
	public void setCorrelationID(Integer correlationID) {
		if (correlationID != null) {
            function.put(RPCMessage.KEY_CORRELATION_ID, correlationID );
        } else {
        	function.remove(RPCMessage.KEY_CORRELATION_ID);
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
        return (Boolean) parameters.get( RPCResponse.KEY_SUCCESS );
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
        if (success != null) {
            parameters.put(RPCResponse.KEY_SUCCESS, success );
        }
    }
	/**
	 * <p>
	 * Returns ResultCode additional information about a response returning a failed outcome
	 * </p>
	 * 
	 * @return {@linkplain Result}  the status of whether the request is successfully done
	 */
    public Result getResultCode() {
        Object obj = parameters.get(RPCResponse.KEY_RESULT_CODE);
        if (obj instanceof Result) {
            return (Result) obj;
        } else if (obj instanceof String) {
            return Result.valueForString((String) obj);
        }
        return null;
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
        if (resultCode != null) {
            parameters.put(RPCResponse.KEY_RESULT_CODE, resultCode );
        }
    }
	/**
	 * <p>
	 * Returns a string of text representing additional information returned from SDL
	 * </p>
	 * 
	 * @return String  A string of text representing additional information returned from SDL
	 */
    public String getInfo() {
        return (String) parameters.get( RPCResponse.KEY_INFO );
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
        if (info != null) {
            parameters.put(RPCResponse.KEY_INFO, info );
        }
    }
}
