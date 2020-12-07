/*
 * Copyright (c) 2019 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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
package com.smartdevicelink.managers.lifecycle;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.util.Hashtable;
import java.util.Set;

/**
 * Converts a protocol message into an RPC.
 * Built to reduce boiler plate code for each RPC added.
 */
public class RpcConverter {

    private static final String TAG = "RpcConverter";

    private static final String RPC_PACKAGE = "com.smartdevicelink.proxy.rpc.";
    private static final String RESPONSE_KEY = "Response";
    private static final String GENERIC_RESPONSE_STRING = FunctionID.GENERIC_RESPONSE.toString();

    /**
     * Extracts the RPC out of the payload of a given protocol message
     *
     * @param message         protocolMessage that has the RPC in the payload
     * @param protocolVersion RPC spec version that should be used to create RPC
     * @return the extracted RPC
     */
    public static RPCMessage extractRpc(ProtocolMessage message, Version protocolVersion) {
        Hashtable<String, Object> tempTable = convertProtocolMessage(message, protocolVersion);
        if (tempTable != null) {
            try {
                return convertTableToRpc(tempTable);
            } catch (Exception e) {
                DebugTool.logError(TAG, "Error converting RPC", e);
            }
        }
        return null;
    }

    static Hashtable<String, Object> convertProtocolMessage(ProtocolMessage message, Version protocolVersion) {
        Hashtable<String, Object> hash = new Hashtable<>();
        if (protocolVersion != null && protocolVersion.getMajor() > 1) {

            Hashtable<String, Object> hashTemp = new Hashtable<>();
            hashTemp.put(RPCMessage.KEY_CORRELATION_ID, message.getCorrID());
            if (message.getJsonSize() > 0) {
                final Hashtable<String, Object> mHash = JsonRPCMarshaller.unmarshall(message.getData());
                if (mHash != null) {
                    hashTemp.put(RPCMessage.KEY_PARAMETERS, mHash);
                }
            }

            String functionName = FunctionID.getFunctionName(message.getFunctionID());

            if (functionName != null) {
                hashTemp.put(RPCMessage.KEY_FUNCTION_NAME, functionName);
            } else {
                DebugTool.logWarning(TAG, "Dispatch Incoming Message - function name is null unknown RPC.  FunctionId: " + message.getFunctionID());
                return null;
            }

            if (message.getRPCType() == 0x00) {
                hash.put(RPCMessage.KEY_REQUEST, hashTemp);
            } else if (message.getRPCType() == 0x01) {
                hash.put(RPCMessage.KEY_RESPONSE, hashTemp);
            } else if (message.getRPCType() == 0x02) {
                hash.put(RPCMessage.KEY_NOTIFICATION, hashTemp);
            }

            if (message.getBulkData() != null)
                hash.put(RPCStruct.KEY_BULK_DATA, message.getBulkData());
            if (message.getPayloadProtected()) hash.put(RPCStruct.KEY_PROTECTED, true);

            return hash;
        } else {
            return JsonRPCMarshaller.unmarshall(message.getData());
        }
    }


    public static RPCMessage convertTableToRpc(Hashtable<String, Object> rpcHashTable) {

        Hashtable<String, Object> params;
        if (rpcHashTable.containsKey((RPCMessage.KEY_RESPONSE))) {
            params = (Hashtable) rpcHashTable.get((RPCMessage.KEY_RESPONSE));
        } else if (rpcHashTable.containsKey((RPCMessage.KEY_NOTIFICATION))) {
            params = (Hashtable) rpcHashTable.get((RPCMessage.KEY_NOTIFICATION));
        } else if (rpcHashTable.containsKey((RPCMessage.KEY_REQUEST))) {
            params = (Hashtable) rpcHashTable.get((RPCMessage.KEY_REQUEST));
        } else {
            DebugTool.logError(TAG, " Corrupted RPC table.");
            return null;
        }

        if (DebugTool.isDebugEnabled()) {
            if (params != null) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    DebugTool.logInfo(TAG, key + "  -  " + params.get(key));
                }
            }
        }

        if (params != null && params.containsKey(RPCMessage.KEY_FUNCTION_NAME)) {
            StringBuilder rpcClassName = new StringBuilder();
            String functionName = (String) params.get(RPCMessage.KEY_FUNCTION_NAME);
            if (FunctionID.SHOW_CONSTANT_TBT.toString().equals(functionName)) {
                functionName = "ShowConstantTbt";
            } else if (FunctionID.ON_ENCODED_SYNC_P_DATA.toString().equals(functionName)
                    || FunctionID.ON_SYNC_P_DATA.toString().equals(functionName)) {
                //This will create an OnSystemRequest instance, but the function id in the RPC
                //will remain FunctionID.ON_ENCODED_SYNC_P_DATA or FunctionID.ON_SYNC_P_DATA.
                //This is desired behavior.
                functionName = FunctionID.ON_SYSTEM_REQUEST.toString();
            } else if (FunctionID.ENCODED_SYNC_P_DATA.toString().equals(functionName)
                    || FunctionID.SYNC_P_DATA.toString().equals(functionName)) {
                //This will create an SystemRequest instance, but the function id in the RPC
                //will remain FunctionID.ENCODED_SYNC_P_DATA or FunctionID.SYNC_P_DATA.
                //This is desired behavior.
                functionName = FunctionID.SYSTEM_REQUEST.toString();
            }

            rpcClassName.append(RPC_PACKAGE);
            rpcClassName.append(functionName);

            if (rpcHashTable.containsKey(RPCMessage.KEY_RESPONSE)
                    && !GENERIC_RESPONSE_STRING.equals(functionName)) {
                rpcClassName.append(RESPONSE_KEY);
            }

            DebugTool.logInfo(TAG, " Attempting to create " + rpcClassName.toString());
            try {
                Class rpcClass = Class.forName(rpcClassName.toString());
                if (rpcClass != null) {
                    java.lang.reflect.Constructor rpcConstructor = rpcClass.getConstructor(Hashtable.class);
                    if (rpcConstructor != null) {
                        return (RPCMessage) rpcConstructor.newInstance(rpcHashTable);
                    }
                } else {
                    DebugTool.logError(TAG, " Java class cannot be found for " + rpcClassName.toString());
                }
            } catch (Exception e) {
                DebugTool.logError(TAG, "RPCConverter was unable to process RPC", e);
            }
        } else {
            DebugTool.logError(TAG, " Unable to parse into RPC");
        }

        return null;
    }


}

