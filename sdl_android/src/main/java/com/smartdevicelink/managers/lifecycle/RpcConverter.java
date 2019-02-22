package com.smartdevicelink.managers.lifecycle;

import android.util.Log;
import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Set;

/**
 * Converts a protocol message into an RPC.
 * Built to reduce boiler plate code for each RPC added.
 */
public class RpcConverter {

    private static final String TAG = "RpcConverter";

    private static final String RPC_PACKAGE             = "com.smartdevicelink.proxy.rpc.";
    private static final String RESPONSE_KEY            = "Response";
    private static final String GENERIC_RESPONSE_STRING = FunctionID.GENERIC_RESPONSE.toString();

    /**
     * Extracts the RPC out of the payload of a given protocol message
     * @param message protocolMessage that has the RPC in the payload
     * @param protocolVersion RPC spec version that should be used to create RPC
     * @return the extracted RPC
     */
    public static RPCMessage extractRpc(ProtocolMessage message, Version protocolVersion){
        Hashtable<String, Object> tempTable = convertProtocolMessage(message, protocolVersion);
        if(tempTable != null){
            try{
                return convertTableToRpc(tempTable);
            }catch (Exception e){
                DebugTool.logError("Error converting RPC",e);
            }
        }
        return null;
    }

    static Hashtable<String, Object> convertProtocolMessage(ProtocolMessage message, Version protocolVersion){
        Hashtable<String, Object> hash = new Hashtable<>();
        if (protocolVersion!= null && protocolVersion.getMajor() > 1) {

            Hashtable<String, Object> hashTemp = new Hashtable<>();
            hashTemp.put(RPCMessage.KEY_CORRELATION_ID, message.getCorrID());
            if (message.getJsonSize() > 0) {
                final Hashtable<String, Object> mhash = JsonRPCMarshaller.unmarshall(message.getData());
                if (mhash != null) {
                    hashTemp.put(RPCMessage.KEY_PARAMETERS, mhash);
                }
            }

            String functionName = FunctionID.getFunctionName(message.getFunctionID());

            if (functionName != null) {
                hashTemp.put(RPCMessage.KEY_FUNCTION_NAME, functionName);
            } else {
                DebugTool.logWarning("Dispatch Incoming Message - function name is null unknown RPC.  FunctionId: " + message.getFunctionID());
                return null;
            }

            if (message.getRPCType() == 0x00) {
                hash.put(RPCMessage.KEY_REQUEST, hashTemp);
            } else if (message.getRPCType() == 0x01) {
                hash.put(RPCMessage.KEY_RESPONSE, hashTemp);
            } else if (message.getRPCType() == 0x02) {
                hash.put(RPCMessage.KEY_NOTIFICATION, hashTemp);
            }

            if (message.getBulkData() != null) hash.put(RPCStruct.KEY_BULK_DATA, message.getBulkData());
            if (message.getPayloadProtected()) hash.put(RPCStruct.KEY_PROTECTED, true);

            return hash;
        } else {
            return JsonRPCMarshaller.unmarshall(message.getData());
        }
    }


    static RPCMessage convertTableToRpc(Hashtable<String,Object> rpcHashTable){

        Hashtable<String,Object> params;
        if(rpcHashTable.containsKey((RPCMessage.KEY_RESPONSE))){
            params = (Hashtable)rpcHashTable.get((RPCMessage.KEY_RESPONSE));
        }else if(rpcHashTable.containsKey((RPCMessage.KEY_NOTIFICATION))){
            params = (Hashtable)rpcHashTable.get((RPCMessage.KEY_NOTIFICATION));
        }else if(rpcHashTable.containsKey((RPCMessage.KEY_REQUEST))){
            params = (Hashtable)rpcHashTable.get((RPCMessage.KEY_REQUEST));
        }else{
            DebugTool.logError(TAG + " Corrupted RPC table.");
            return null;
        }

        if(DebugTool.isDebugEnabled()) {
            if (params != null) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    Log.i(TAG, key + "  -  " + params.get(key));
                }
            }
        }

        if(params.containsKey(RPCMessage.KEY_FUNCTION_NAME)){
            StringBuilder rpcClassName = new StringBuilder();
            String functionName = (String)params.get(RPCMessage.KEY_FUNCTION_NAME);
            rpcClassName.append(RPC_PACKAGE);
            rpcClassName.append (functionName);

            if(rpcHashTable.containsKey(RPCMessage.KEY_RESPONSE)
                    && !GENERIC_RESPONSE_STRING.equals(functionName)){
                rpcClassName.append(RESPONSE_KEY);
            }

            DebugTool.logInfo(TAG + " Attempting to create " + rpcClassName.toString());
            try {
                Class rpcClass = Class.forName(rpcClassName.toString());
                if(rpcClass != null){
                    java.lang.reflect.Constructor rpcConstructor =  rpcClass.getConstructor(Hashtable.class);
                    if(rpcConstructor != null){
                        return (RPCMessage)rpcConstructor.newInstance(rpcHashTable);
                    }
                }
            } catch (ClassNotFoundException e) {
            } catch (NoSuchMethodException e) {
            } catch (IllegalAccessException e) {
            } catch (InstantiationException e) {
            } catch (InvocationTargetException e) {
            } catch (ClassCastException e){

            }
        }else{
            DebugTool.logError(TAG + " Unable to parse into RPC");
        }

        return null;
    }


}

