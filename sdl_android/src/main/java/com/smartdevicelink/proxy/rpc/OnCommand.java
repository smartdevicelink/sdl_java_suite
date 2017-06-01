package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

import java.util.Hashtable;

/**
 * This is called when a command was selected via VR after pressing the PTT button, or selected from the menu after 
 * pressing the MENU button. <p>
 * <b>Note: </b>Sequence of OnHMIStatus and OnCommand notifications for user-initiated interactions is indeterminate.
 * <p></p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel: 
 * <ul><li>FULL,LIMITED</li></ul>
 * AudioStreamingState: 
 * <ul><li>Any</li></ul>
 * SystemContext: 
 * <ul><li>Any</li></ul>
 * </ul>
 * <p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>Notes</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>cmdID</td>
 * <td>Integer</td>
 * <td>The cmdID of the command the user selected. This is the cmdID value provided by the application in the AddCommand operation that created the command.</td>
 * <td></td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>triggerSource</td>
 * <td>{@linkplain TriggerSource}</td>
 * <td>Indicates whether command was selected via VR or via a menu selection (using the OKbutton).</td>
 * <td></td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table>
 * </p>
 * @since SmartDeviceLink 1.0
 * @see AddCommand
 * @see DeleteCommand
 * @see DeleteSubMenu
 */
public class OnCommand extends RPCNotification {
	public static final String KEY_CMD_ID = "cmdID";
	public static final String KEY_TRIGGER_SOURCE = "triggerSource";
	/**
	*Constructs a newly allocated OnCommand object
	*/    
    public OnCommand() {
        super(FunctionID.ON_COMMAND.toString());
    }
    /**
    *<p>Constructs a newly allocated OnCommand object indicated by the Hashtable parameter</p>
    *@param hash The Hashtable to use
    */    
    public OnCommand(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * <p>Returns an <i>Integer</i> object representing the Command ID</p>
     * @return Integer an integer representation of this object
     */    
    public Integer getCmdID() {
        return getInteger( KEY_CMD_ID );
    }
    /**
     * <p>Sets a Command ID</p>    
     * @param cmdID an integer object representing a Command ID
     */    
    public void setCmdID( Integer cmdID ) {
        setParameter(KEY_CMD_ID, cmdID);
    }
    /**
     * <p>Returns a <I>TriggerSource</I> object which will be shown in the HMI</p>    
     * @return TriggerSource a TriggerSource object
     */    
    public TriggerSource getTriggerSource() {
        return (TriggerSource) getObject(TriggerSource.class, KEY_TRIGGER_SOURCE);
    }
    /**
     * <p>Sets TriggerSource</p>
     * <p>Indicates whether command was selected via VR or via a menu selection (using the OK button).</p>    
     * @param triggerSource a TriggerSource object
     */    
    public void setTriggerSource( TriggerSource triggerSource ) {
        setParameter(KEY_TRIGGER_SOURCE, triggerSource);
    }
}
