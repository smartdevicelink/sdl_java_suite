package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.util.JsonUtils;

/**
 * Slider Response is sent, when Slider has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SliderResponse extends RPCResponse {
	public static final String KEY_SLIDER_POSITION = "sliderPosition";

	private Integer sliderPosition;
	
	/**
	 * Constructs a new SliderResponse object
	 */
    public SliderResponse() {
        super(FunctionID.SLIDER);
    }
    
    /**
     * Creates a SliderResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SliderResponse(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.sliderPosition = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_SLIDER_POSITION);
            break;
        }
    }
    
    /**
     * Sets an Initial position of slider control
     * @param sliderPosition
     */
    public void setSliderPosition(Integer sliderPosition) {
    	this.sliderPosition = sliderPosition;
    }
    
    /**
     * Gets an Initial position of slider control
     * @return Integer
     */
    public Integer getSliderPosition() {
    	return this.sliderPosition;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_SLIDER_POSITION, this.sliderPosition);
            break;
        }
        
        return result;
    }
}
