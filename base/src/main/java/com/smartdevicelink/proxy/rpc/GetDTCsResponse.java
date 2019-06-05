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
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;
import java.util.List;

/**
 * Get DTCs Response is sent, when GetDTCs has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class GetDTCsResponse extends RPCResponse{

    public static final String KEY_ECU_HEADER = "ecuHeader";
    public static final String KEY_DTC = "dtc";

    public GetDTCsResponse(){
        super(FunctionID.GET_DTCS.toString());
    }

    public GetDTCsResponse(Hashtable<String, Object> hash){
        super(hash);
    }

    /**
     * Constructs a new GetDTCsResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     * @param ecuHeader representation of the ecu header that was returned from the GetDTC request
     */
    public GetDTCsResponse(@NonNull Boolean success, @NonNull Result resultCode, @NonNull Integer ecuHeader) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
        setEcuHeader(ecuHeader);
    }

    @SuppressWarnings("unchecked")
    public List<String> getDtc(){
        return (List<String>) getObject(String.class, KEY_DTC);
    }

    public void setDtc(List<String> dtc){
        setParameters(KEY_DTC, dtc);
    }
    
    public Integer getEcuHeader(){
        return getInteger(KEY_ECU_HEADER);
    }
    
    public void setEcuHeader(@NonNull Integer ecuHeader){
        setParameters(KEY_ECU_HEADER, ecuHeader);
    }

}
