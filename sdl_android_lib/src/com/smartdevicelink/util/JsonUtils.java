package com.smartdevicelink.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;
import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonParameters;



public final class JsonUtils {
    
    private JsonUtils(){}
    
    /**
     * Creates a JSON object from raw bytes.
     * 
     * @param data The raw bytes to convert to JSON
     * @return The JSON object created from the raw bytes
     */
    public static JSONObject createJsonObject(byte[] data){
        JSONObject result = null;
        try {
            String jsonStr = new String(data);
            result = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Adds an object into a JSON object with the given key.
     * 
     * @param json The JSON object to add the value into
     * @param key The key of object to put in the JSON object
     * @param value The object to put in the JSON object
     */
    public static void addToJsonObject(JSONObject json, String key, Object value){
        try{
            json.put(key, value);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a JSONArray containing a list of JSON objects.
     * 
     * @param parameterList The list of JsonParameter objects, which will be used to create a JSON object from
     * @param sdlVersion The version of SDL used to create the JSON objects
     * @return The resultant JSON array
     */
    public static JSONArray createJsonArrayOfJsonObjects(List<? extends JsonParameters> parameterList, int sdlVersion){
        JSONArray result = new JSONArray();
        
        for(JsonParameters parameter : parameterList){
            result.put(parameter.getJsonParameters(sdlVersion));
        }
        
        return result;
    }
    
    /**
     * Creates a JSONArray containing a list of JSON names
     * 
     * @param parameterList The list of JsonName objects, which will be used to create a JSON string from
     * @param sdlVersion The version of SDL used to create the JSON strings
     * @return The resultant JSON array
     */
    public static JSONArray createJsonArrayOfJsonNames(List<? extends JsonName> parameterList, int sdlVersion){
        JSONArray result = new JSONArray();
        
        for(JsonName name : parameterList){
            result.put(name.getJsonName(sdlVersion));
        }
        
        return result;
    }
    
    /**
     * Creates a JSONArray of generic objects
     * 
     * @param list The list of objects to add to the JSON array
     * @return The resultant JSON array
     */
    public static <T> JSONArray createJsonArray(List<T> list) {
        JSONArray result = new JSONArray();
        
        for(T str : list){
            result.put(str);
        }
        
        return result;
    }
    
    // these methods are basically to get around the annoying JSONException that is thrown when a key doesn't exist
    // in the JSON object.  this method returns null instead of throwing an exception.
    /**
     * Reads a generic object from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static Object readObjectFromJsonObject(JSONObject json, String key){
        try {
            return json.get(key);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Reads a Boolean object from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static Boolean readBooleanFromJsonObject(JSONObject json, String key){
        try {
            return json.getBoolean(key);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Reads a Double object from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static Double readDoubleFromJsonObject(JSONObject json, String key){
        try {
            return json.getDouble(key);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Reads an Integer object from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static Integer readIntegerFromJsonObject(JSONObject json, String key){
        try {
            return json.getInt(key);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Reads a JSON array object from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static JSONArray readJsonArrayFromJsonObject(JSONObject json, String key){
        try {
            return json.getJSONArray(key);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Reads a list of String objects from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static List<String> readStringListFromJsonObject(JSONObject json, String key){
        JSONArray jsonArray = readJsonArrayFromJsonObject(json, key);
        
        if(jsonArray != null){
            int len = jsonArray.length();
            List<String> result = new ArrayList<String>(len);
            for(int i=0; i<len; i++){
                try {
                    String str = jsonArray.getString(i);
                    result.add(str);
                } catch (JSONException e) {}
            }
            return result;
        }
        
        return null;
    }

    /**
     * Reads a list of Integer objects from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static List<Integer> readIntegerListFromJsonObject(JSONObject json, String key){
        JSONArray jsonArray = readJsonArrayFromJsonObject(json, key);
        
        if(jsonArray != null){
            int len = jsonArray.length();
            List<Integer> result = new ArrayList<Integer>(len);
            for(int i=0; i<len; i++){
                try {
                    Integer str = jsonArray.getInt(i);
                    result.add(str);
                } catch (JSONException e) {}
            }
            return result;
        }
        
        return null;
    }

    /**
     * Reads a list of Double objects from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static List<Double> readDoubleListFromJsonObject(JSONObject json, String key){
        JSONArray jsonArray = readJsonArrayFromJsonObject(json, key);
        
        if(jsonArray != null){
            int len = jsonArray.length();
            List<Double> result = new ArrayList<Double>(len);
            for(int i=0; i<len; i++){
                try {
                    Double str = jsonArray.getDouble(i);
                    result.add(str);
                } catch (JSONException e) {}
            }
            return result;
        }
        
        return null;
    }

    /**
     * Reads a list of JSON objects from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static List<JSONObject> readJsonObjectListFromJsonObject(JSONObject json, String key){
        JSONArray jsonArray = readJsonArrayFromJsonObject(json, key);
        
        if(jsonArray != null){
            int len = jsonArray.length();
            List<JSONObject> result = new ArrayList<JSONObject>(len);
            for(int i=0; i<len; i++){
                try{
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    result.add(jsonObject);
                } catch(JSONException e) {}
            }
            return result;
        }
        
        return null;
    }

    /**
     * Reads a JSON object from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static JSONObject readJsonObjectFromJsonObject(JSONObject json, String key){
        try {
            return json.getJSONObject(key);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Reads a Long object from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static Long readLongFromJsonObject(JSONObject json, String key){
        try {
            return json.getLong(key);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Reads a String object from a JSON object.
     * 
     * @param json The JSON object to read from
     * @param key The key of the object to read
     * @return The resultant object or null if it doesn't exist
     */
    public static String readStringFromJsonObject(JSONObject json, String key){
        try {
            return json.getString(key);
        } catch (JSONException e) {
            return null;
        }
    }
    
    /**
     * This final class contains all interfaces related to interacting with JSON objects.
     *
     * @author Mike Burke
     *
     */
    public static final class JsonInterfaces {

        private JsonInterfaces() {}

        /**
         * Listener to be called when a JSONException occurs in the system.
         *
         * @author Mike Burke
         *
         */
        public interface JsonExceptionListener{
            /**
             * Method to be called when a JSONException occurs in the system.
             * 
             * @param e The exception that occurred
             */
            void onJsonException(JSONException e);
        }
        
        /**
         * Listener to be called when a JSONObject is created.  This listener allows the library
         * to create JSONObjects on a thread separate from the UI thread and inform the listener
         * when the object is successfully created.
         * 
         * <b>NOTE:</b> This interface extends JsonExceptionListener, so any JSONExceptions that occur
         * while creating the JSONObject can be passed back to the listener as well.
         *
         * @author Mike Burke
         *
         */
        public interface JsonObjectListener extends JsonExceptionListener{
            void onJsonObjectCreated(JSONObject jsonObject);
        }
        
        /**
         * Listener to be called when a JSONArray is created.  This listener allows the library
         * to create JSONArrays on a thread separate from the UI thread and inform the listener
         * when the object is successfully created.
         * 
         * <b>NOTE:</b> This interface extends JsonExceptionListener, so any JSONExceptions that occur
         * while creating the JSONArray can be passed back to the listener as well.
         *
         * @author Mike Burke
         *
         */
        public interface JsonArrayListener extends JsonExceptionListener{
            void onJsonArrayCreated(JSONArray jsonArray);
        }
        
        /**
         * This is a very important interface for the design of RPC messages.  Each layer of an RPC message
         * is responsible for creating its own JSONObject based on the variables it holds.  When this interface
         * is implemented, it is strongly recommended to have an associated constructor that takes in a JSONObject
         * parameter and decode the JSON into the variables it holds.
         *
         * @author Mike Burke
         *
         */
        public interface JsonParameters{
            /**
             * When this method is called, the class that implements the JsonParemeters interface should create a
             * JSONObject with key parameter of type String and value parameter of type Object.
             * 
             * @return The JSONObject representing the parameters for the object
             */
            public JSONObject getJsonParameters(int sdlVersion);
        }
        
        /**
         * Represents an object that has a JSON name associated with it.
         * 
         * When implementing this interface, it is highly recommended to provide a static method that will look
         * up the enum value based on the JSON name.
         *
         * @author Mike Burke
         *
         */
        public interface JsonName{
            /**
             * Returns the JSON name associated with the object.
             * 
             * @return The JSON name
             */
            public String getJsonName(int sdlVersion);
        }
    }
}
