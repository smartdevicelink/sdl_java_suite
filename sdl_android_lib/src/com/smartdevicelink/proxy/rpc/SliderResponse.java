package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
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
        super(SdlCommand.SLIDER, jsonObject);
        jsonObject = getParameters(jsonObject);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sliderPosition == null) ? 0 : sliderPosition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		SliderResponse other = (SliderResponse) obj;
		if (sliderPosition == null) {
			if (other.sliderPosition != null) { 
				return false;
			}
		} 
		else if (!sliderPosition.equals(other.sliderPosition)) { 
			return false;
		}
		return true;
	}
}
