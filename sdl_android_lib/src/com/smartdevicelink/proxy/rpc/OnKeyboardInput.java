package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;


public class OnKeyboardInput extends RPCNotification {
	public static final String KEY_DATA = "data";
	public static final String KEY_EVENT = "event";

	private String data;
	private String event; // represents KeyboardEvent enum
	
    public OnKeyboardInput() {
        super(FunctionID.ON_KEYBOARD_INPUT);
    }

    /**
     * Creates a OnKeyboardInput object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnKeyboardInput(JSONObject jsonObject){
        super(SdlCommand.ON_KEYBOARD_INPUT, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.data = JsonUtils.readStringFromJsonObject(jsonObject, KEY_DATA);
            this.event = JsonUtils.readStringFromJsonObject(jsonObject, KEY_EVENT);
            break;
        }
    }

    public KeyboardEvent getEvent() {
        return KeyboardEvent.valueForJsonName(this.event, sdlVersion);
    }

    public void setEvent(KeyboardEvent event) {
        this.event = (event == null) ? null : event.getJsonName(sdlVersion);
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public String getData() {
        return this.data;
    }

    @Override
    public String toString(){
        String result =  this.getFunctionName() +": " + " data: " + this.getData() + " event:" + this.getEvent().toString();
        return result;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DATA, this.data);
            JsonUtils.addToJsonObject(result, KEY_EVENT, this.event);
            break;
        }
        
        return result;
    }

}
