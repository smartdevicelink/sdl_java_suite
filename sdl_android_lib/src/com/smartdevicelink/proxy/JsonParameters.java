package com.smartdevicelink.proxy;

import org.json.JSONException;
import org.json.JSONObject;


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
    public JSONObject getJsonParameters(int sdlVersion) throws JSONException;
}
