package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Provides information to the user using either TTS, the Display or both and
 * can include a system-generated alert tone
 * 
 * <ul>
 * <li>The displayed portion of the Alert, if any, will persist until the
 * specified timeout has elapsed, or the Alert is preempted</li>
 * <li>An Alert will preempt (abort) any SmartDeviceLink Operation that is in-progress,
 * except an already-in-progress Alert</li>
 * <li>An Alert cannot be preempted by any SmartDeviceLink Operation</li>
 * <li>An Alert can be preempted by a user action (button push)</li>
 * <li>An Alert will fail if it is issued while another Alert is in progress</li>
 * <li>Although each Alert parameter is optional, in fact each Alert request
 * must supply at least one of the following parameters:
 * <ul>
 * <li>alertText1</li>
 * <li>alertText2</li>
 * <li>alertText3</li>
 * <li>ttsChunks</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * <p><b>HMILevel needs to be FULL or LIMITED.</b></p>
 * <b>If the app has been granted function group Notification the HMILevel can
 * also be BACKGROUND</b>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>alertText1</td>
 * 			<td>String</td>
 * 			<td>Text to be displayed in the first field of the display during the Alert. </td>
 *                 <td>N</td>
 * 			<td>	Length is limited to what is indicated in RegisterAppInterface response.  If omitted, top display line will be cleared. Text is always centered</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>alertText2</td>
 * 			<td>String</td>
 * 			<td>Text to be displayed in the second field of the display during the Alert. </td>
 *                 <td>N</td>
 * 			<td>	Only permitted if HMI supports a second display line.	Length is limited to what is indicated in RegisterAppInterface response. 	If omitted, second display line will be cleared.  </td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>alertText3</td>
 * 			<td>String</td>
 * 			<td>Text to be displayed in the third field of the display during the Alert.</td>
 *                 <td>N</td>
 * 			<td>Array must have a least one element. </td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>ttsChunks</td>
 * 			<td>TTSChunk[]</td>
 * 			<td>Array of type TTSChunk which, taken together, specify what is to be spoken to the user.</td>
 *                 <td>N</td>
 * 			<td>Array must have a least one element. </td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>duration</td>
 * 			<td>Integer</td>
 * 			<td><p>The duration of the displayed portion of the alert, in milliseconds.</p> After this amount of time has passed, the display fields alertText1 and alertText2 will revert to what was displayed in those fields before the alert began.</td>
 *                 <td>N</td>
 * 			<td>Min Value: 3000 Max Value: 10000	<p>If omitted, the default is 5000 milliseconds</p></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>playTone</td>
 * 			<td>Boolean</td>
 * 			<td>Specifies whether the alert tone should be played before the TTS (if any) is spoken.</td>
 *                 <td>N</td>
 * 			<td>If omitted, default is true.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>softButtons</td>
 * 			<td>SoftButton[]</td>
 * 			<td>Specifies the softbuttons, the apps wants to use in this alert.</td>
 *                 <td></td>
 * 			<td>If omitted on supported displays, the alert will not have any SoftButton.ArrayMin: 0; ArrayMax: 4</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>progressIndicator</td>
 * 			<td>Boolean</td>
 * 			<td>If supported on the given platform, the alert GUI will include some sort of animation indicating that loading of a feature is progressing.  e.g. a spinning wheel or hourglass, etc.</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 1.0
 * 
 *  
 * @see GetVehicleData
 * @see OnVehicleData 
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

	/**
	 * Constructs a new Alert object
	 */    
	public Alert() {
        super(FunctionID.ALERT.toString());
    }
	/**
	 * <p>Constructs a new Alert object indicated by the Hashtable parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */	
    public Alert(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets the text which is displayed in the first field of the display during
	 * the Alert
	 * 
	 * @return String - a String value representing the text which is displayed
	 *         in the first field during the Alert
	 */    
    public String getAlertText1() {
        return (String) parameters.get(KEY_ALERT_TEXT_1);
    }
	/**
	 * Sets the String to be displayed in the first field of the display during
	 * the Alert
	 * 
	 * @param alertText1
	 *            String Value
	 *            
	 *            <p><b>Notes: </b></p>
	 *            <ul>
	 *            <li>Length is limited to what is indicated in <i>
	 *            {@linkplain RegisterAppInterface}</i> response</li>
	 *            <li>If omitted, top display line will be cleared</li>
	 *            <li>Text is always centered</li>
	 *            </ul>
	 */    
    public void setAlertText1(String alertText1) {
        if (alertText1 != null) {
            parameters.put(KEY_ALERT_TEXT_1, alertText1);
        } else {
            parameters.remove(KEY_ALERT_TEXT_1);
        }
    }
	/**
	 * Gets the text which is displayed in the second field of the display
	 * during the Alert
	 * 
	 * @return String -a String value representing the text which is displayed
	 *         in the second field during the Alert
	 */    
    public String getAlertText2() {
        return (String) parameters.get(KEY_ALERT_TEXT_2);
    }
	/**
	 * Sets the String to be displayed in the second field of the display during
	 * the Alert
	 * 
	 * @param alertText2
	 *            String Value
	 *            
	 *            <p><b>Notes: </b></p>
	 *            <ul>
	 *            <li>Only permitted if HMI supports a second display line</li>
	 *            <li>Length is limited to what is indicated in <i>
	 *            {@linkplain RegisterAppInterface}</i> response</li>
	 *            <li>If omitted, second display line will be cleared</li>
	 *            <li>Text is always centered</li>
	 *            </ul>
	 */    
    public void setAlertText2(String alertText2) {
        if (alertText2 != null) {
            parameters.put(KEY_ALERT_TEXT_2, alertText2);
        } else {
            parameters.remove(KEY_ALERT_TEXT_2);
        }
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
        return (String) parameters.get(KEY_ALERT_TEXT_3);
    }

	/**
	 * Sets the String to be displayed in the third field of the display during
	 * the Alert
	 * 
	 * @param alertText3
	 *            String Value
	 *            
	 *           <p> <b>Notes: </b></p>
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
        if (alertText3 != null) {
            parameters.put(KEY_ALERT_TEXT_3, alertText3);
        } else {
            parameters.remove(KEY_ALERT_TEXT_3);
        }
    }
	/**
	 * Gets TTSChunk[], the Array of type TTSChunk which, taken together,
	 * specify what is to be spoken to the user
	 * 
	 * @return List -a List<TTSChunk> value specify what is to be spoken to
	 *         the user
	 */    
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsChunks() {
        if (parameters.get(KEY_TTS_CHUNKS) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_TTS_CHUNKS);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (List<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TTSChunk> newList = new ArrayList<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
	/**
	 * Sets array of type TTSChunk which, taken together, specify what is to be
	 * spoken to the user
	 * 
	 * @param ttsChunks
	 *            
	 *           <p> <b>Notes: </b>Array must have a least one element</p>
	 */    
    public void setTtsChunks(List<TTSChunk> ttsChunks) {
        if (ttsChunks != null) {
            parameters.put(KEY_TTS_CHUNKS, ttsChunks);
        } else {
            parameters.remove(KEY_TTS_CHUNKS);
        }
    }
	/**
	 * Gets the duration of the displayed portion of the alert, in milliseconds
	 * 
	 * @return Integer -an Integer value representing the duration of the
	 *         displayed portion of the alert, in milliseconds
	 */    
    public Integer getDuration() {
        return (Integer) parameters.get(KEY_DURATION);
    }
	/**
	 * <p>Sets the duration of the displayed portion of the alert, in milliseconds.
	 * After this amount of time has passed, the display fields alertText1 and
	 * alertText2 will revert to what was displayed in those fields before the
	 * alert began</p>
	 * 
	 * 
	 * @param duration
	 *            the Integer values representing the duration time, in
	 *            milliseconds
	 *            <p>
	 *            <b>Notes: </b></p>
	 *            <ul>
	 *            <li>Min Value: 3000; Max Value: 10000</li>
	 *            <li>If omitted, the default is 5000 milliseconds</li>
	 *            </ul>
	 */    
    public void setDuration(Integer duration) {
        if (duration != null) {
            parameters.put(KEY_DURATION, duration);
        } else {
            parameters.remove(KEY_DURATION);
        }
    }
	/**
	 * Gets a Boolean value representing the alert tone
	 * 
	 * @return Boolean -If TRUE, means an alert tone will be played before the
	 *         TTS (if any) is spoken
	 */    
    public Boolean getPlayTone() {
        return (Boolean) parameters.get(KEY_PLAY_TONE);
    }
	/**
	 * Sets whether the alert tone should be played before the TTS (if any) is
	 * spoken
	 * 
	 * @param playTone
	 *            a Boolean value which specifies whether the alert tone should
	 *            be played before the TTS (if any) is spoken
	 *            
	 *           <p> <b>Notes: </b>If omitted, default is true</p>
	 */    
    public void setPlayTone(Boolean playTone) {
        if (playTone != null) {
            parameters.put(KEY_PLAY_TONE, playTone);
        } else {
            parameters.remove(KEY_PLAY_TONE);
        }
    }

	/**
	 * Gets the SoftButton List object
	 * 
	 * @return List<SoftButton> -a List<SoftButton> representing the List
	 *         object
	 * @since SmartDeviceLink 2.0
	 */
    @SuppressWarnings("unchecked")
    public List<SoftButton> getSoftButtons() {
        if (parameters.get(KEY_SOFT_BUTTONS) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_SOFT_BUTTONS);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof SoftButton) {
	                return (List<SoftButton>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<SoftButton> newList = new ArrayList<SoftButton>();
	                for (Object hashObj : list) {
	                    newList.add(new SoftButton((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

	/**
	 * Sets the SoftButtons
	 * 
	 * @param softButtons
	 *            a List<SoftButton> value
	 *            <p>
	 *            <b>Notes: </b></p>
	 *            <ul>
	 *            <li>If omitted on supported displays, the alert will not have
	 *            any SoftButton</li>
	 *            <li>ArrayMin: 0</li>
	 *            <li>ArrayMax: 4</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    
    public void setSoftButtons(List<SoftButton> softButtons) {
        if (softButtons != null) {
            parameters.put(KEY_SOFT_BUTTONS, softButtons);
        } else {
            parameters.remove(KEY_SOFT_BUTTONS);
        }
    }
    public Boolean getProgressIndicator() {
        final Object obj = parameters.get(KEY_PROGRESS_INDICATOR);
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        return null;
    }
    public void setProgressIndicator(Boolean progressIndicator) {
        if (progressIndicator != null) {
            parameters.put(KEY_PROGRESS_INDICATOR, progressIndicator);
        } else {
            parameters.remove(KEY_PROGRESS_INDICATOR);
        }
    }    
}
