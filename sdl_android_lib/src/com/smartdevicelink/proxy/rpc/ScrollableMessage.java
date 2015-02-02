package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.JsonUtils;

/**
 * Creates a full screen overlay containing a large block of formatted text that
 * can be scrolled with up to 8 SoftButtons defined
 * <p>
 * Function Group: ScrollableMessage
 * <p>
 * <b>HMILevel needs to be FULL</b>
 * <p>
 */
public class ScrollableMessage extends RPCRequest {
	public static final String KEY_SCROLLABLE_MESSAGE_BODY = "scrollableMessageBody";
	public static final String KEY_TIMEOUT = "timeout";
	public static final String KEY_SOFT_BUTTONS = "softButtons";

	private String body;
	private Integer timeout;
	private List<SoftButton> softButtons;
	
	/**
	 * Constructs a new ScrollableMessage object
	 */
    public ScrollableMessage() {
        super(FunctionID.SCROLLABLE_MESSAGE);
    }
    
    /**
     * Creates a ScrollableMessage object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ScrollableMessage(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.body = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SCROLLABLE_MESSAGE_BODY);
            this.timeout = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_TIMEOUT);
            
            List<JSONObject> softButtonObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_SOFT_BUTTONS);
            if(softButtonObjs != null){
                this.softButtons = new ArrayList<SoftButton>(softButtonObjs.size());
                for(JSONObject softButtonObj : softButtonObjs){
                    this.softButtons.add(new SoftButton(softButtonObj));
                }
            }
            break;
        }
    }

	/**
	 * Sets a Body of text that can include newlines and tabs
	 * 
	 * @param scrollableMessageBody
	 *            a String value representing the Body of text that can include
	 *            newlines and tabs
	 *            <p>
	 *            <b>Notes: </b>Maxlength=500
	 */
    public void setScrollableMessageBody(String scrollableMessageBody) {
        this.body = scrollableMessageBody;
    }

	/**
	 * Gets a Body of text that can include newlines and tabs
	 * 
	 * @return String -a String value
	 */
    public String getScrollableMessageBody() {
        return this.body;
    }

	/**
	 * Sets an App defined timeout. Indicates how long of a timeout from the
	 * last action
	 * 
	 * @param timeout
	 *            an Integer value representing an App defined timeout
	 *            <p>
	 *            <b>Notes</b>:Minval=0; Maxval=65535;Default=30000
	 */
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

	/**
	 * Gets an App defined timeout
	 * 
	 * @return Integer -an Integer value representing an App defined timeout
	 */
    public Integer getTimeout() {
        return this.timeout;
    }

	/**
	 * Sets App defined SoftButtons.If omitted on supported displays, only the
	 * system defined "Close" SoftButton will be displayed
	 * 
	 * @param softButtons
	 *            a List<SoftButton> value representing App defined
	 *            SoftButtons
	 *            <p>
	 *            <b>Notes: </b>Minsize=0, Maxsize=8
	 */
    public void setSoftButtons(List<SoftButton> softButtons) {
        this.softButtons = softButtons;
    }

	/**
	 * Gets App defined soft button
	 * @return List -List<SoftButton> value
	 */
    public List<SoftButton> getSoftButtons() {
        return this.softButtons;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_SCROLLABLE_MESSAGE_BODY, this.body);
            JsonUtils.addToJsonObject(result, KEY_TIMEOUT, this.timeout);
            JsonUtils.addToJsonObject(result, KEY_SOFT_BUTTONS, (this.softButtons == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.softButtons, sdlVersion));
            break;
        }
        
        return result;
    }
}
