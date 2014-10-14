package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequest;

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
	public static final String playTone = "playTone";
	public static final String duration = "duration";
	public static final String alertText1 = "alertText1";
	public static final String alertText2 = "alertText2";
	public static final String alertText3 = "alertText3";
    public static final String progressIndicator = "progressIndicator";
	public static final String ttsChunks = "ttsChunks";
	public static final String softButtons = "softButtons";

	/**
	 * Constructs a new Alert object
	 */    
	public Alert() {
        super("Alert");
    }
	/**
	 * Constructs a new Alert object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */	
    public Alert(Hashtable hash) {
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
        return (String) parameters.get(Alert.alertText1);
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
        if (alertText1 != null) {
            parameters.put(Alert.alertText1, alertText1);
        } else {
            parameters.remove(Alert.alertText1);
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
        return (String) parameters.get(Alert.alertText2);
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
        if (alertText2 != null) {
            parameters.put(Alert.alertText2, alertText2);
        } else {
            parameters.remove(Alert.alertText2);
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
        return (String) parameters.get(Alert.alertText3);
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
        if (alertText3 != null) {
            parameters.put(Alert.alertText3, alertText3);
        } else {
            parameters.remove(Alert.alertText3);
        }
    }
	/**
	 * Gets TTSChunk[], the Array of type TTSChunk which, taken together,
	 * specify what is to be spoken to the user
	 * 
	 * @return Vector -a Vector<TTSChunk> value specify what is to be spoken to
	 *         the user
	 */    
    public Vector<TTSChunk> getTtsChunks() {
        if (parameters.get(Alert.ttsChunks) instanceof Vector<?>) {
            Vector<?> list = (Vector<?>)parameters.get(Alert.ttsChunks);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (Vector<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TTSChunk> newList = new Vector<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable)hashObj));
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
	 *            <p>
	 *            <b>Notes: </b>Array must have a least one element
	 */    
    public void setTtsChunks(Vector<TTSChunk> ttsChunks) {
        if (ttsChunks != null) {
            parameters.put(Alert.ttsChunks, ttsChunks);
        } else {
            parameters.remove(Alert.ttsChunks);
        }
    }
	/**
	 * Gets the duration of the displayed portion of the alert, in milliseconds
	 * 
	 * @return Integer -an Integer value representing the duration of the
	 *         displayed portion of the alert, in milliseconds
	 */    
    public Integer getDuration() {
        return (Integer) parameters.get(Alert.duration);
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
        if (duration != null) {
            parameters.put(Alert.duration, duration);
        } else {
            parameters.remove(Alert.duration);
        }
    }
	/**
	 * Gets a Boolean value representing the alert tone
	 * 
	 * @return Boolean -If TRUE, means an alert tone will be played before the
	 *         TTS (if any) is spoken
	 */    
    public Boolean getPlayTone() {
        return (Boolean) parameters.get(Alert.playTone);
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
        if (playTone != null) {
            parameters.put(Alert.playTone, playTone);
        } else {
            parameters.remove(Alert.playTone);
        }
    }

	/**
	 * Gets the SoftButton Vector object
	 * 
	 * @return Vector<SoftButton> -a Vector<SoftButton> representing the Vector
	 *         object
	 * @since SmartDeviceLink 2.0
	 */
    public Vector<SoftButton> getSoftButtons() {
        if (parameters.get(Alert.softButtons) instanceof Vector<?>) {
            Vector<?> list = (Vector<?>)parameters.get(Alert.softButtons);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof SoftButton) {
	                return (Vector<SoftButton>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<SoftButton> newList = new Vector<SoftButton>();
	                for (Object hashObj : list) {
	                    newList.add(new SoftButton((Hashtable)hashObj));
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
	 *            a Vector<SoftButton> value
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
    
    public void setSoftButtons(Vector<SoftButton> softButtons) {
        if (softButtons != null) {
            parameters.put(Alert.softButtons, softButtons);
        } else {
            parameters.remove(Alert.softButtons);
        }
    }
    public Boolean getProgressIndicator() {
        final Object obj = parameters.get(Alert.progressIndicator);
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        return null;
    }
    public void setProgressIndicator(Boolean progressIndicator) {
        if (progressIndicator != null) {
            parameters.put(Alert.progressIndicator, progressIndicator);
        } else {
            parameters.remove(Alert.progressIndicator);
        }
    }    
}
