package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.util.DebugTool;
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
	private Boolean firstRun;
	/**
	*Constructs a newly allocated OnHMIStatus object
	*/ 	
    public OnHMIStatus() {
        super("OnHMIStatus");
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
        Object obj = parameters.get(Names.hmiLevel);
        if (obj instanceof HMILevel) {
            return (HMILevel) obj;
        } else if (obj instanceof String) {
            HMILevel theCode = null;
            try {
                theCode = HMILevel.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.hmiLevel, e);
            }
            return theCode;
        }
        return null;
    }
    /**
     * <p>Set the HMILevel of OnHMIStatus</p>
     * @param hmiLevel the HMILevel to set
     */    
    public void setHmiLevel( HMILevel hmiLevel ) {
        if (hmiLevel != null) {
            parameters.put(Names.hmiLevel, hmiLevel );
        }
    }
    /**
     * <p>Get current state of audio streaming for the application</p>
     * @return {@linkplain AudioStreamingState} Returns current state of audio streaming for the application
     */    
    public AudioStreamingState getAudioStreamingState() {
        Object obj = parameters.get(Names.audioStreamingState);
        if (obj instanceof AudioStreamingState) {
            return (AudioStreamingState) obj;
        } else if (obj instanceof String) {
            AudioStreamingState theCode = null;
            try {
                theCode = AudioStreamingState.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.audioStreamingState, e);
            }
            return theCode;
        }
        return null;
    }
    /**
     * <p>Set the audio streaming state</p>
     * @param audioStreamingState the state of audio streaming of the application
     */    
    public void setAudioStreamingState( AudioStreamingState audioStreamingState ) {
        if (audioStreamingState != null) {
            parameters.put(Names.audioStreamingState, audioStreamingState );
        }
    }
    /**
     * <p>Get the System Context</p>
     * @return {@linkplain SystemContext} whether a user-initiated interaction is in-progress (VRSESSION or MENU), or not (MAIN).
     */    
    public SystemContext getSystemContext() {
        Object obj = parameters.get(Names.systemContext);
        if (obj instanceof SystemContext) {
            return (SystemContext) obj;
        } else if (obj instanceof String) {
            SystemContext theCode = null;
            try {
                theCode = SystemContext.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.systemContext, e);
            }
            return theCode;
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
            parameters.put(Names.systemContext, systemContext );
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
