/*
 * Copyright (c) 2019, Livio, Inc.
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
package com.livio.BSON;

import com.smartdevicelink.util.DebugTool;
import org.bson.*;

import java.util.*;

public class BsonEncoder {


    public static byte[] encodeToBytes(HashMap<String, Object> map) throws ClassCastException {
        if(map != null) {
            BasicBSONObject bson = new BasicBSONObject();
            bson.putAll(sanitizeMap(map));
            BasicBSONEncoder encoder = new BasicBSONEncoder();

            return encoder.encode(bson);
        }
        DebugTool.logError(null, "Something went wrong encoding the map into BSON bytes");

        return null;
    }

    public static HashMap<String, Object> decodeFromBytes(byte[] bytes) {
        if(bytes != null) {
            BasicBSONDecoder decoder = new BasicBSONDecoder();
            BSONObject object = decoder.readObject(bytes);
            if (object != null) {
                Map<String, Object> map = object.toMap();
                if (map != null) {
                    return sanitizeMap(new HashMap<>(map));
                }
            }
        }
        DebugTool.logError(null, "Something went wrong decoding bytes into BSON");
        return null;
    }

    /**
     * Goes thorugh the map and ensures that all the values included are supported by SDL. If they are not of a supported
     * value it is removed from the map
     * @param map the map to be sanitized
     * @return a sanitized HashMap with non-supported object type removes
     */
    private static HashMap<String, Object> sanitizeMap(HashMap<String, Object> map){
        Set<String> keys = map.keySet();
        Object value;
        for(String key : keys){
            value = map.get(key);

            //First check to see if it value is a valid type used in SDL
            if(isSupportedType(value)){
                continue;
            }else if(value instanceof List){ //Next, check to see if it is a list of values
                List list = (List)value;

                //If the list is empty, there shouldn't be a problem leaving it in the map
                if(list.isEmpty()){
                    continue;
                }

                //Since the list isn't empty, check the first item
                if(isSupportedType(list.get(0))){
                    continue;
                }
            }
            //If the item isn't valid, remove it from the map
            map.remove(key);

        }
        return map;
    }

    /**
     * Checks the value to ensure it is of a supported type
     * @param value  the generic object value
     * @return if the object is an instanceOf one of the supported SDL BSON objects
     */
    private static boolean isSupportedType(Object value){
        return value instanceof Integer
                || value instanceof Long
                || value instanceof Double
                || value instanceof String
                || value instanceof Boolean;
    }

}
