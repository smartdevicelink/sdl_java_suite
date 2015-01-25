package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.JsonUtils;

/**
 * Provides information to the user using either TTS, the Display or both and
 * can include a system-generated alert tone
 * <p>
 * <ul>
 * <li>The displayed portion of the Alert, if any, will persist until the
 * specified timeout has elapsed, or the Alert is preempted</li>
 * <li>An Alert will preempt (abort) any SmartDeviceLink Operation that is in-progress,
 * except an already-in-progress Alert</li>
 * <li>An Alert cannot be preempted by any SmartDeviceLink Operation</li>
 * <li>An Alert can be preempted by a user action (button push)</li>
 * <li>An Alert will fail if it is issued while another Alert is in progress</li>
 * <li>Although each Alert parameter is optional, in fact each Alert request
 * must supply at least one of the following parameters:<br/>
 * <ul>
 * <li>alertText1</li>
 * <li>alertText2</li>
 * <li>alertText3</li>
 * <li>ttsChunks</li>
 * </ul>
 * </li>
 * </ul>
 * <br/>
 * <b>HMILevel needs to be FULL or LIMITED.</b><br/>
 * <b>If the app has been granted function group Notification the HMILevel can
 * also be BACKGROUND</b><br/>
 * 
 * @since SmartDeviceLink 1.0
 * @see Show
 * @see Speak
 */
public class Alert extends RPCRequest {
	public static final String KEY_PLAY_TONE = "playTone";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_ALERT_TEXT_1 = "alertText1";
	public static final String KEY_ALERT_TEXT_2 = "alertText2";
	public static final String KEY_ALERT_TEXT_3 = "alertText3";
    public static final String KEY_PROGRESS_INDICATOR = "progressIndicator";
	public static final String KEY_TTS_CHUNKS = "ttsChunks";
	public static final String KEY_SOFT_BUTTONS = "softButtons";

	private Integer duration;
	private String alertText1, alertText2, alertText3;
	private Boolean playTone, progressIndicator;
	private List<TTSChunk> ttsChunks;
	private List<SoftButton> softButtons;
	
	/**
	 * Constructs a new Alert object
	 */    
	public Alert() {
        super(FunctionID.ALERT);
    }
	
	/**
	 * Creates an Alert object from a JSON object.
	 * 
	 * @param jsonObject The JSON object to read from
	 * @param sdlVersion The version of SDL represented in the JSON
	 */
    public Alert(JSONObject jsonObject, int sdlVersion){
        super(jsonObject);
        
        switch(sdlVersion){
        default:
            this.duration = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_DURATION);
            
            this.alertText1 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_ALERT_TEXT_1);
            this.alertText2 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_ALERT_TEXT_2);
            this.alertText3 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_ALERT_TEXT_3);
            
            this.playTone = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_PLAY_TONE);
            this.progressIndicator = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_PROGRESS_INDICATOR);
            
            List<JSONObject> ttsChunksArray = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_TTS_CHUNKS);
            if(ttsChunksArray != null){
                this.ttsChunks = new ArrayList<TTSChunk>(ttsChunksArray.size());
                for(JSONObject ttsChunkObj : ttsChunksArray){
                        this.ttsChunks.add(new TTSChunk(ttsChunkObj, sdlVersion));
                }
            }
        
            List<JSONObject> softButtonsArray = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_SOFT_BUTTONS);
            if(softButtonsArray != null){
                this.softButtons = new ArrayList<SoftButton>(softButtonsArray.size());
                for(JSONObject softButtonObj : softButtonsArray){
                        this.ttsChunks.add(new SoftButton(softButtonObj, sdlVersion));
                }
            }
            break;
        }
    }
    
	/**
	 * Gets the text which is displayed in the first field of the display during
	 * the Alert
	 * 
	 * @return String - a String value representing the text which is displayed
	 *         in the first field during the Alert
	 */    
    public String getAlertText1() {
        return this.alertText1;
    }
    
	/**
	 * Sets the String to be displayed in the first field of the display during
	 * the Alert
	 * 
	 * @param alertText1
	 *            String Value
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>Length is limited to what is indicated in <i>
	 *            {@linkplain RegisterAppInterface}</i> response</li>
	 *            <li>If omitted, top display line will be cleared</li>
	 *            <li>Text is always centered</li>
	 *            </ul>
	 */    
    public void setAlertText1(String alertText1) {
        this.alertText1 = alertText1;
    }
    
	/**
	 * Gets the text which is displayed in the second field of the display
	 * during the Alert
	 * 
	 * @return String -a String value representing the text which is displayed
	 *         in the second field during the Alert
	 */    
    public String getAlertText2() {
        return this.alertText2;
    }
    
	/**
	 * Sets the String to be displayed in the second field of the display during
	 * the Alert
	 * 
	 * @param alertText2
	 *            String Value
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>Only permitted if HMI supports a second display line</li>
	 *            <li>Length is limited to what is indicated in <i>
	 *            {@linkplain RegisterAppInterface}</i> response</li>
	 *            <li>If omitted, second display line will be cleared</li>
	 *            <li>Text is always centered</li>
	 *            </ul>
	 */    
    public void setAlertText2(String alertText2) {
        this.alertText2 = alertText2;
    }

	/**
	 * Gets the text which is displayed in the third field of the display during
	 * the Alert
	 * 
	 * @return String -a String value representing the text which is displayed
	 *         in the third field during the Alert
	 * 
	 * @since SmartDeviceLink 2.0
	 */
    public String getAlertText3() {
        return this.alertText3;
    }

	/**
	 * Sets the String to be displayed in the third field of the display during
	 * the Alert
	 * 
	 * @param alertText3
	 *            String Value
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>Only permitted if HMI supports a third display line</li>
	 *            <li>Length is limited to what is indicated in <i>
	 *            {@linkplain RegisterAppInterface}</i> response</li>
	 *            <li>If omitted, third display line will be cleared</li>
	 *            <li>Text is always centered</li>
	 *            </ul>
	 * 
	 * @since SmartDeviceLink 2.0
	 */
    public void setAlertText3(String alertText3) {
        this.alertText3 = alertText3;
    }
    
	/**
	 * Gets TTSChunk[], the Array of type TTSChunk which, taken together,
	 * specify what is to be spoken to the user
	 * 
	 * @return List -a List<TTSChunk> value specify what is to be spoken to
	 *         the user
	 */    
    public List<TTSChunk> getTtsChunks() {
        return this.ttsChunks;
    }
    
	/**
	 * Sets array of type TTSChunk which, taken together, specify what is to be
	 * spoken to the user
	 * 
	 * @param ttsChunks
	 *            <p>
	 *            <b>Notes: </b>Array must have a least one element
	 */    
    public void setTtsChunks(List<TTSChunk> ttsChunks) {
        this.ttsChunks = ttsChunks;
    }
    
	/**
	 * Gets the duration of the displayed portion of the alert, in milliseconds
	 * 
	 * @return Integer -an Integer value representing the duration of the
	 *         displayed portion of the alert, in milliseconds
	 */    
    public Integer getDuration() {
        return this.duration;
    }
    
	/**
	 * Sets the duration of the displayed portion of the alert, in milliseconds.
	 * After this amount of time has passed, the display fields alertText1 and
	 * alertText2 will revert to what was displayed in those fields before the
	 * alert began
	 * <p>
	 * 
	 * @param duration
	 *            the Integer values representing the duration time, in
	 *            milliseconds
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>Min Value: 3000; Max Value: 10000</li>
	 *            <li>If omitted, the default is 5000 milliseconds</li>
	 *            </ul>
	 */    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
	/**
	 * Gets a Boolean value representing the alert tone
	 * 
	 * @return Boolean -If TRUE, means an alert tone will be played before the
	 *         TTS (if any) is spoken
	 */    
    public Boolean getPlayTone() {
        return this.playTone;
    }
    
	/**
	 * Sets whether the alert tone should be played before the TTS (if any) is
	 * spoken
	 * 
	 * @param playTone
	 *            a Boolean value which specifies whether the alert tone should
	 *            be played before the TTS (if any) is spoken
	 *            <p>
	 *            <b>Notes: </b>If omitted, default is true
	 */    
    public void setPlayTone(Boolean playTone) {
        this.playTone = playTone;
    }

	/**
	 * Gets the SoftButton List object
	 * 
	 * @return List<SoftButton> -a List<SoftButton> representing the List
	 *         object
	 * @since SmartDeviceLink 2.0
	 */
    public List<SoftButton> getSoftButtons() {
        return this.softButtons;
    }

	/**
	 * Sets the SoftButtons
	 * 
	 * @param softButtons
	 *            a List<SoftButton> value
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>If omitted on supported displays, the alert will not have
	 *            any SoftButton</li>
	 *            <li>ArrayMin: 0</li>
	 *            <li>ArrayMax: 4</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setSoftButtons(List<SoftButton> softButtons) {
        this.softButtons = softButtons;
    }
    
    public Boolean getProgressIndicator() {
        return this.progressIndicator;
    }
    
    public void setProgressIndicator(Boolean progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_ALERT_TEXT_1, this.alertText1);
            JsonUtils.addToJsonObject(result, KEY_ALERT_TEXT_2, this.alertText2);
            JsonUtils.addToJsonObject(result, KEY_ALERT_TEXT_3, this.alertText3);
            
            JsonUtils.addToJsonObject(result, KEY_PLAY_TONE, this.playTone);
            JsonUtils.addToJsonObject(result, KEY_PROGRESS_INDICATOR, this.progressIndicator);
            
            JsonUtils.addToJsonObject(result, KEY_DURATION, this.duration);
            
            JsonUtils.addToJsonObject(result, KEY_TTS_CHUNKS, (this.ttsChunks == null) ? null : 
                JsonUtils.createJsonArrayOfJsonObjects(this.ttsChunks, sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_SOFT_BUTTONS, (this.softButtons == null) ? null : 
                JsonUtils.createJsonArrayOfJsonObjects(this.softButtons, sdlVersion));
            break;
        }
        
        return result;
    }    
}
