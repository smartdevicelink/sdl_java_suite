package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.util.DebugTool;

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
 * <td>Int32</td>
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
	public static final String cmdID = "cmdID";
	public static final String triggerSource = "triggerSource";
	/**
	*Constructs a newly allocated OnCommand object
	*/    
    public OnCommand() {
        super("OnCommand");
    }
    /**
    *<p>Constructs a newly allocated OnCommand object indicated by the Hashtable parameter</p>
    *@param hash The Hashtable to use
    */    
    public OnCommand(Hashtable hash) {
        super(hash);
    }
    /**
     * <p>Returns an <i>Integer</i> object representing the Command ID</p>
     * @return Integer an integer representation of this object
     */    
    public Integer getCmdID() {
        return (Integer) parameters.get( OnCommand.cmdID );
    }
    /**
     * <p>Sets a Command ID</p>    
     * @param cmdID an integer object representing a Command ID
     */    
    public void setCmdID( Integer cmdID ) {
        if (cmdID != null) {
            parameters.put(OnCommand.cmdID, cmdID );
        }
    }
    /**
     * <p>Returns a <I>TriggerSource</I> object which will be shown in the HMI</p>    
     * @return TriggerSource a TriggerSource object
     */    
    public TriggerSource getTriggerSource() {
        Object obj = parameters.get(OnCommand.triggerSource);
        if (obj instanceof TriggerSource) {
            return (TriggerSource) obj;
        } else if (obj instanceof String) {
            TriggerSource theCode = null;
            try {
                theCode = TriggerSource.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnCommand.triggerSource, e);
            }
            return theCode;
        }
        return null;
    }
    /**
     * <p>Sets TriggerSource<br/>
     * Indicates whether command was selected via VR or via a menu selection (using the OK button).</p>    
     * @param triggerSource a TriggerSource object
     */    
    public void setTriggerSource( TriggerSource triggerSource ) {
        if (triggerSource != null) {
            parameters.put(OnCommand.triggerSource, triggerSource );
        }
    }
}
