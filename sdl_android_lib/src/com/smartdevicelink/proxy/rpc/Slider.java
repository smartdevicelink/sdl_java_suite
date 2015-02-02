package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.JsonUtils;

/**
 * Creates a full screen or pop-up overlay (depending on platform) with a single
 * user controlled slider
 * <p>
 * Function Group: Base
 * <p>
 * <b>HMILevel needs to be FULL</b>
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 * 
 */
public class Slider extends RPCRequest {

	public static final String KEY_NUM_TICKS = "numTicks";
	public static final String KEY_SLIDER_HEADER = "sliderHeader";
	public static final String KEY_SLIDER_FOOTER = "sliderFooter";
	public static final String KEY_POSITION = "position";
	public static final String KEY_TIMEOUT = "timeout";
	
	private Integer numTicks, position, timeout;
	private String header;
	private List<String> footers;
	
	/**
	 * Constructs a new Slider object
	 */
    public Slider() {
        super(FunctionID.SLIDER);
    }
    
    /**
     * Creates a Slider object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public Slider(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.numTicks = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_NUM_TICKS);
            this.position = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_POSITION);
            this.timeout = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_TIMEOUT);
            this.header = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SLIDER_HEADER);
            this.footers = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_SLIDER_FOOTER);
            break;
        }
    }

	/**
	 * Sets a number of selectable items on a horizontal axis
	 * 
	 * @param numTicks
	 *            an Integer value representing a number of selectable items on
	 *            a horizontal axis
	 *            <p>
	 *            <b>Notes: </b>Minvalue=2; Maxvalue=26
	 */
    public void setNumTicks(Integer numTicks) {
    	this.numTicks = numTicks;
    }

	/**
	 * Gets a number of selectable items on a horizontal axis
	 * 
	 * @return Integer -an Integer value representing a number of selectable
	 *         items on a horizontal axis
	 */
    public Integer getNumTicks() {
    	return this.numTicks;
    }

	/**
	 * Sets an Initial position of slider control
	 * 
	 * @param position
	 *            an Integer value representing an Initial position of slider
	 *            control
	 *            <p>
	 *            <b>Notes: </b>Minvalue=1; Maxvalue=26
	 */
    public void setPosition(Integer position) {
    	this.position = position;
    }

	/**
	 * Gets an Initial position of slider control
	 * 
	 * @return Integer -an Integer value representing an Initial position of
	 *         slider control
	 */
    public Integer getPosition() {
    	return this.position;
    }

	/**
	 * Sets a text header to display
	 * 
	 * @param sliderHeader
	 *            a String value
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public void setSliderHeader(String sliderHeader) {
    	this.header = sliderHeader;
    }

	/**
	 * Gets a text header to display
	 * 
	 * @return String -a String value representing a text header to display
	 */
    public String getSliderHeader() {
    	return this.header;
    }

	/**
	 * Sets a text footer to display
	 * 
	 * @param sliderFooter
	 *            a List<String> value representing a text footer to display
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500; Minvalue=1; Maxvalue=26
	 */
    public void setSliderFooter(List<String> sliderFooter) {
    	this.footers = sliderFooter;
    }

	/**
	 * Gets a text footer to display
	 * 
	 * @return String -a String value representing a text footer to display
	 */
    public List<String> getSliderFooter() {
        return this.footers;
    }

	/**
	 * Sets an App defined timeout
	 * 
	 * @param timeout
	 *            an Integer value representing an App defined timeout
	 *            <p>
	 *            <b>Notes: </b>Minvalue=0; Maxvalue=65535; Defvalue=10000
	 */
    public void setTimeout(Integer timeout) {
    	this.timeout = timeout;
    }

	/**
	 * Gets an App defined timeout
	 * @return Integer -an Integer value representing an App defined timeout
	 */
    public Integer getTimeout() {
    	return this.timeout;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_NUM_TICKS, this.numTicks);
            JsonUtils.addToJsonObject(result, KEY_POSITION, this.position);
            JsonUtils.addToJsonObject(result, KEY_TIMEOUT, this.timeout);
            JsonUtils.addToJsonObject(result, KEY_SLIDER_HEADER, this.header);
            JsonUtils.addToJsonObject(result, KEY_SLIDER_FOOTER, (this.footers == null) ? null :
                JsonUtils.createJsonArray(this.footers));
            break;
        }
        
        return result;
    }
}
