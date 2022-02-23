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

import androidx.annotation.Nullable;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.SdlDataTypeConverter;
import com.smartdevicelink.util.Version;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class RPCStruct implements Cloneable {
    private static final String TAG = "RPCStruct";

    public static final String KEY_BULK_DATA = "bulkData";
    public static final String KEY_PROTECTED = "protected";

    private byte[] _bulkData = null;
    private Boolean protectedPayload = false;

    private boolean formatRequested = false;
    private Version rpcSpecVersion = null;


    protected Hashtable<String, Object> store;

    public boolean getStoreValue(String key) { // for unit testing
        return store.contains(key);
    }

    public Hashtable<String, Object> getStore() { // for unit testing
        return store;
    }

    public RPCStruct() {
        store = new Hashtable<>();
    }

    protected RPCStruct(RPCStruct rpcs) {
        this.store = cloneStore(rpcs);
    }

    public RPCStruct(Hashtable<String, Object> hashtable) {
        store = hashtable;
        //store = (Hashtable<String, Object>) ObjectCopier.copy(hashtable);
    }

    static Hashtable<String, Object> cloneStore(RPCStruct rpcStruct) {
        if (rpcStruct != null && rpcStruct.store != null) {
            return (Hashtable) rpcStruct.store.clone();
        }
        return null;
    }

    public void deserializeJSON(JSONObject jsonObject) throws JSONException {
        store = JsonRPCMarshaller.deserializeJSONObject(jsonObject);

    }

    public JSONObject serializeJSON() throws JSONException {
        return JsonRPCMarshaller.serializeHashtable(store);
    }

    @SuppressWarnings("unchecked")
    public JSONObject serializeJSON(byte protocolVersion) throws JSONException {
        if (protocolVersion > 1) {
            String messageType = getMessageTypeName(store.keySet());
            Hashtable<String, Object> function = (Hashtable<String, Object>) store.get(messageType);
            if (function != null) {
                Hashtable<String, Object> parameters = (Hashtable<String, Object>) function.get(RPCMessage.KEY_PARAMETERS);
                return JsonRPCMarshaller.serializeHashtable(parameters);
            } else {
                return null;
            }

        } else return JsonRPCMarshaller.serializeHashtable(store);
    }

    /**
     * This method should clean the RPC to make sure it is compliant with the spec.
     * <br><br><b> NOTE:</b> Super needs to be called at the END of the method
     *
     * @param rpcVersion   the rpc spec version that has been negotiated. If value is null the
     *                     the max value of RPC spec version this library supports should be used.
     * @param formatParams if true, the format method will be called on subsequent params
     */
    public void format(Version rpcVersion, boolean formatParams) {
        formatRequested = true;
        rpcSpecVersion = rpcVersion;
        //Should override this method when breaking changes are made to the RPC spec
        if (formatParams && store != null) {
            Hashtable<String, Object> parameters;

            if (this instanceof RPCMessage) {
                //If this is a message (request, response, notification) the parameters have to be
                //retrieved from the store object.
                String messageType = getMessageTypeName(store.keySet());
                Hashtable<String, Object> function = (Hashtable<String, Object>) store.get(messageType);
                if (function != null) {
                    parameters = (Hashtable<String, Object>) function.get(RPCMessage.KEY_PARAMETERS);
                } else {
                    parameters = null;
                }
            } else {
                //If this is just an RPC struct the store itself should be used
                parameters = store;
            }

            if (parameters != null) {
                for (Object value : parameters.values()) {
                    internalFormat(rpcVersion, value);
                }
            }
        }
    }

    /**
     * Cycles through parameters in this RPC to ensure they all get formatted
     *
     * @param rpcVersion version of the rpc spec that should be used to format this rpc
     * @param value      the object to investigate if it needs to be formatted
     */
    private void internalFormat(Version rpcVersion, Object value) {
        if (value instanceof RPCStruct) {
            ((RPCStruct) value).format(rpcVersion, true);
        } else if (value instanceof List<?>) {
            List<?> list = (List<?>) value;
            if (list != null && list.size() > 0) {
                for (Object listItem : list) {
                    internalFormat(rpcVersion, listItem);
                }
            }
        }
    }


    public byte[] getBulkData() {
        return this._bulkData;
    }

    public RPCStruct setBulkData(byte[] bulkData) {
        if (bulkData != null) {
            this._bulkData = new byte[bulkData.length];
            System.arraycopy(bulkData, 0, _bulkData, 0, bulkData.length);
        } else {
            this._bulkData = null;
        }
        return this;
    }

    public RPCStruct setPayloadProtected(Boolean bVal) {
        protectedPayload = bVal;
        return this;
    }

    public Boolean isPayloadProtected() {
        return protectedPayload;
    }

    protected String getMessageTypeName(Set<String> keys) {
        for (String key : keys) {
            if (key == null) {
                continue;
            }
            if (key.equals(RPCMessage.KEY_REQUEST) || key.equals(RPCMessage.KEY_RESPONSE) ||
                    key.equals(RPCMessage.KEY_NOTIFICATION)) {
                return key;
            }
        }
        return null;
    }

    protected boolean hasKey(Set<String> keys, String keyName) {
        for (String key : keys) {
            if (key == null) {
                continue;
            }
            if (key.equals(keyName)) {
                return true;
            }
        }
        return false;
    }

    // Generalized Getters and Setters

    public RPCStruct setValue(String key, Object value) {
        if (value != null) {
            store.put(key, value);
        } else {
            store.remove(key);
        }
        return this;
    }

    public Object getValue(String key) {
        return store.get(key);
    }

    public Object getObject(Class tClass, String key) {
        Object obj = store.get(key);
        return formatObject(tClass, obj);
    }

    // Helper methods

    /**
     * @param tClass a Class to cast Objects to
     * @param obj    Object returned from a stored hashtable
     * @return A null object if obj is null or if none of the following is true:
     * a) obj is an instance of tClass
     * b) obj is an instance of String and it tClass has a valid `valueForString` method
     * c) obj is an instance of a Hashtable
     * d) obj is an instance of a List
     */
    protected Object formatObject(Class tClass, Object obj) {
        if (obj == null) {
            return null;
        } else if (tClass.isInstance(obj)) {
            return obj;
        } else if (obj instanceof String) {
            return getValueForString(tClass, (String) obj);
        } else if (obj instanceof Hashtable) {
            try {
                Constructor constructor = tClass.getConstructor(Hashtable.class);
                Object customObject = constructor.newInstance((Hashtable<String, Object>) obj);
                if (formatRequested && customObject instanceof RPCStruct) {
                    ((RPCStruct) customObject).format(rpcSpecVersion, true);
                }

                return customObject;
            } catch (Exception e) {
                DebugTool.logError(TAG, "Error attempting to format an object from a Hashtable", e);
            }
        } else if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            if (list != null && list.size() > 0) {
                Object item = null;
                //Iterate through list to find first non-null object
                for (Object object : list) {
                    if (object != null) {
                        item = object;
                        break;
                    }
                }

                if (item == null) {
                    return list;
                } else if (tClass.isInstance(item)) {
                    return list;
                } else if (item instanceof Hashtable) {
                    List<Object> newList = new ArrayList<>();
                    Object customObject;
                    for (Object hashObj : list) {
                        try {
                            Constructor constructor = tClass.getConstructor(Hashtable.class);
                            customObject = constructor.newInstance((Hashtable<String, Object>) hashObj);
                            if (formatRequested
                                    && customObject != null
                                    && customObject instanceof RPCStruct) {
                                ((RPCStruct) customObject).format(rpcSpecVersion, true);
                            }
                            newList.add(customObject);
                        } catch (Exception e) {
                            DebugTool.logError(TAG, "Error attempting to format object from list of Hashtables", e);
                            return null;
                        }
                    }
                    return newList;
                } else if (item instanceof String) {
                    List<Object> newList = new ArrayList<>();
                    for (Object hashObj : list) {
                        Object toAdd = getValueForString(tClass, (String) hashObj);
                        if (toAdd != null) {
                            newList.add(toAdd);
                        }
                    }
                    return newList;
                }
            } else {
                //If the list is either null or empty it should be returned. It will keep the same
                //behavior as it does today with null lists, but empty ones will now also be returned.
                return list;
            }
        }
        return null;
    }

    /**
     * @param tClass - a Class with a `valueForString(String s)` method that returns an Object for a given String
     * @param s      - a String to be converted to an Object using a `valueForString(String s)` method
     * @return An Object converted using a `valueForString(String s)` method in the Class passed in, or a null object if such method does not exist
     */
    protected Object getValueForString(Class tClass, String s) {
        Method valueForString = null;
        try {
            valueForString = tClass.getDeclaredMethod("valueForString", String.class);
        } catch (NoSuchMethodException e) {
            DebugTool.logError(TAG, "Error attempting to find valueForString method in class", e);
        }
        if (valueForString != null) {
            try {
                return valueForString.invoke(null, (String) s);
            } catch (IllegalAccessException e) {
                DebugTool.logError(TAG, "Illegal access while using reflection to get enum from string", e);
            } catch (InvocationTargetException e) {
                DebugTool.logError(TAG, "Error attempting to use method from reflection to get enum from string", e);
            }
        }
        return null;
    }

    // Common Object Getters
    public String getString(String key) {
        return (String) store.get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) store.get(key);
    }

    public Double getDouble(String key) {
        return SdlDataTypeConverter.objectToDouble(store.get(key));
    }

    public Float getFloat(String key) {
        return SdlDataTypeConverter.objectToFloat(store.get(key));
    }

    public Boolean getBoolean(String key) {
        return (Boolean) store.get(key);
    }

    public Long getLong(String key) {
        Object result = store.get(key);
        if (result instanceof Integer) {
            return ((Integer) result).longValue();
        } else if (result instanceof Long) {
            return (Long) result;
        }
        return null;
    }

    /**
     * Creates a deep copy of the object
     *
     * @return deep copy of the object, null if an exception occurred
     */
    @Override
    public RPCStruct clone() {
        try {
            RPCStruct clone = (RPCStruct) super.clone();
            clone.setPayloadProtected(protectedPayload);
            clone.setBulkData(_bulkData);
            clone.store = (Hashtable) store.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            DebugTool.logError("RPCStruct", "Failed to clone: " + e);
            return null;
        }
    }

    /**
     * Uses the RPCStruct store for RPCStruct objects
     *
     * @param obj - The object to compare
     * @return boolean of whether the objects are the same or not
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        // if this is the same memory address, its the same
        if (this == obj) {
            return true;
        }
        // if this is not an instance of the same class, not the same
        if (obj.getClass() != getClass()) {
            return false;
        }
        // return comparison of store
        return isEqualToRPC((RPCStruct) obj);
    }

    private boolean isEqualToRPC(RPCStruct rpc) {
        return store.equals(rpc.store);
    }

    /**
     * Used to compile hashcode for RPCStruct
     *
     * @return Custom hashcode of RPCStruct
     */
    @Override
    public int hashCode() {
        return store.hashCode();
    }
}
