/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

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
     * <p>Constructs a newly allocated RPCResponse object using function name</p>
     *
     * @param functionName a string that indicates the function's name
     */
    public RPCResponse(String functionName) {
        super(functionName, RPCMessage.KEY_RESPONSE);
    }

    /**
     * <p>Constructs a newly allocated RPCResponse object indicated by the Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public RPCResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * <p>Constructs a newly allocated RPCResponse object using a RPCMessage object</p>
     *
     * @param rpcMsg The {@linkplain RPCMessage} to use
     */
    public RPCResponse(RPCMessage rpcMsg) {
        super(preprocessMsg(rpcMsg));
    }

    static RPCMessage preprocessMsg(RPCMessage rpcMsg) {
        if (!RPCMessage.KEY_RESPONSE.equals(rpcMsg.getMessageType())) {
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
        return (Integer) function.get(RPCMessage.KEY_CORRELATION_ID);
    }

    /**
     * <p>
     * Set the correlationID
     * </p>
     *
     * @param correlationID the ID of the response
     */
    public RPCResponse setCorrelationID(Integer correlationID) {
        if (correlationID != null) {
            function.put(RPCMessage.KEY_CORRELATION_ID, correlationID);
        } else {
            function.remove(RPCMessage.KEY_CORRELATION_ID);
        }
        return this;
    }

    /**
     * <p>
     * Returns Success whether the request is successfully processed
     * </p>
     *
     * @return Boolean  the status of whether the request is successfully done
     */
    public Boolean getSuccess() {
        return (Boolean) parameters.get(RPCResponse.KEY_SUCCESS);
    }

    /**
     * <p>
     * Set the Success status
     * </p>
     *
     * @param success whether the request is successfully processed
     */
    public RPCResponse setSuccess(@NonNull Boolean success) {
        if (success != null) {
            parameters.put(RPCResponse.KEY_SUCCESS, success);
        }
        return this;
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
     * @param resultCode whether the request is successfully processed
     */
    public RPCResponse setResultCode(@NonNull Result resultCode) {
        if (resultCode != null) {
            parameters.put(RPCResponse.KEY_RESULT_CODE, resultCode);
        }
        return this;
    }

    /**
     * <p>
     * Returns a string of text representing additional information returned from SDL
     * </p>
     *
     * @return String  A string of text representing additional information returned from SDL
     */
    public String getInfo() {
        return (String) parameters.get(RPCResponse.KEY_INFO);
    }

    /**
     * <p>
     * Set a string of text representing additional information returned from SDL
     * </p>
     *
     * @param info a string of text representing additional information returned from SDL
     */
    public RPCResponse setInfo(String info) {
        if (info != null) {
            parameters.put(RPCResponse.KEY_INFO, info);
        }
        return this;
    }
}
