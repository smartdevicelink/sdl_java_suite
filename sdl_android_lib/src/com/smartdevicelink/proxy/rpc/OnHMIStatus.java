package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
/**
 * <p>Notifies an application that HMI conditions have changed for the application. This indicates whether the application 
 * can speak phrases, display text, perform interactions, receive button presses and events, stream audio, etc. This 
 * notification will be sent to the application when there has been a change in any one or several of the indicated 
 * states ({@linkplain HMILevel}, {@linkplain AudioStreamingState} or {@linkplain SystemContext}) for the application</p>
 * <p>All three values are, in principle, independent of each other (though there may be some relationships). A value for 
 * one parameter should not be interpreted from the value of another parameter.</p>
 * <p>There are no guarantees about the timeliness or latency of the OnHMIStatus notification. Therefore, for example, 
 * information such as {@linkplain AudioStreamingState} may not indicate that the audio stream became inaudible to the user 
 * exactly when the OnHMIStatus notification was received.</p>
 * 
 * <p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>hmiLevel</td>
 * <td>{@linkplain HMILevel}</td>
 * <td>The current HMI Level in effect for the application.</td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>audioStreamingState</td>
 * <td>{@linkplain AudioStreamingState}</td>
 * <td>Current state of audio streaming for the application. 
 * When this parameter has a value of NOT_AUDIBLE, 
 * the application must stop streaming audio to SDL. 
 * Informs app whether any currently streaming audio is 
 * audible to user (AUDIBLE) or not (NOT_AUDIBLE). A 
 * value of NOT_AUDIBLE means that either the 
 * application's audio will not be audible to the user, or 
 * that the application's audio should not be audible to 
 * the user (i.e. some other application on the mobile 
 * device may be streaming audio and the application's 
 * audio would be blended with that other audio). </td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>systemContext</td>
 * <td>{@linkplain SystemContext}</td>
 * <td>Indicates that a user-initiated interaction is in-progress 
 * (VRSESSION or MENU), or not (MAIN)</td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table>
 * </p>
 * @since SmartDeviceLink 1.0
 * @see RegisterAppInterface 
 */
public class OnHMIStatus extends RPCNotification {
	public static final String KEY_AUDIO_STREAMING_STATE = "audioStreamingState";
	public static final String KEY_SYSTEM_CONTEXT = "systemContext";
	public static final String KEY_HMI_LEVEL = "hmiLevel";

    private Boolean firstRun;
	
	/**
	*Constructs a newly allocated OnHMIStatus object
	*/ 	
    public OnHMIStatus() {
        super(FunctionID.ON_HMI_STATUS.toString());
    }
    /**
    *<p>Constructs a newly allocated OnHMIStatus object indicated by the Hashtable parameter</p>
    *@param hash The Hashtable to use
    */    
    public OnHMIStatus(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * <p>Get HMILevel in effect for the application</p>
     * @return {@linkplain HMILevel} the current HMI Level in effect for the application
     */    
    public HMILevel getHmiLevel() {
        Object obj = parameters.get(KEY_HMI_LEVEL);
        if (obj instanceof HMILevel) {
            return (HMILevel) obj;
        } else if (obj instanceof String) {
            return HMILevel.valueForString((String) obj);
        }
        return null;
    }
    /**
     * <p>Set the HMILevel of OnHMIStatus</p>
     * @param hmiLevel the HMILevel to set
     */    
    public void setHmiLevel( HMILevel hmiLevel ) {
        if (hmiLevel != null) {
            parameters.put(KEY_HMI_LEVEL, hmiLevel );
        } else {
            parameters.remove(KEY_HMI_LEVEL);
        }
    }
    /**
     * <p>Get current state of audio streaming for the application</p>
     * @return {@linkplain AudioStreamingState} Returns current state of audio streaming for the application
     */    
    public AudioStreamingState getAudioStreamingState() {
        Object obj = parameters.get(KEY_AUDIO_STREAMING_STATE);
        if (obj instanceof AudioStreamingState) {
            return (AudioStreamingState) obj;
        } else if (obj instanceof String) {
            return AudioStreamingState.valueForString((String) obj);
        }
        return null;
    }
    /**
     * <p>Set the audio streaming state</p>
     * @param audioStreamingState the state of audio streaming of the application
     */    
    public void setAudioStreamingState( AudioStreamingState audioStreamingState ) {
        if (audioStreamingState != null) {
            parameters.put(KEY_AUDIO_STREAMING_STATE, audioStreamingState );
        } else {
            parameters.remove(KEY_AUDIO_STREAMING_STATE);
        }
    }
    /**
     * <p>Get the System Context</p>
     * @return {@linkplain SystemContext} whether a user-initiated interaction is in-progress (VRSESSION or MENU), or not (MAIN).
     */    
    public SystemContext getSystemContext() {
        Object obj = parameters.get(KEY_SYSTEM_CONTEXT);
        if (obj instanceof SystemContext) {
            return (SystemContext) obj;
        } else if (obj instanceof String) {
            return SystemContext.valueForString((String) obj);
        }
        return null;
    }
    /**
     * <p>Set the System Context of OnHMIStatus</p>
     * @param systemContext Indicates that a user-initiated interaction is in-progress 
     * (VRSESSION or MENU), or not (MAIN)
     */    
    public void setSystemContext( SystemContext systemContext ) {
        if (systemContext != null) {
            parameters.put(KEY_SYSTEM_CONTEXT, systemContext );
        } else {
            parameters.remove(KEY_SYSTEM_CONTEXT);
        }
    }
    /**
     * <p>Query whether it's the first run</p>
     * @return boolean whether it's the first run
     */    
    public Boolean getFirstRun() {
    	return this.firstRun;
    }
    /**
     * <p>Set the firstRun value</p>
     * @param firstRun True if it is the first run, False or not
     */    
    public void setFirstRun(Boolean firstRun) {
    	this.firstRun = firstRun;
    }
}
