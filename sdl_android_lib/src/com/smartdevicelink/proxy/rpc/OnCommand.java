package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.util.JsonUtils;

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
	public static final String KEY_CMD_ID = "cmdID";
	public static final String KEY_TRIGGER_SOURCE = "triggerSource";
	
	private Integer cmdId;
	private String triggerSource; // represents TriggerSource enum
	
	/**
	*Constructs a newly allocated OnCommand object
	*/    
    public OnCommand() {
        super(FunctionID.ON_COMMAND);
    }
    
    /**
     * Creates an OnCommand object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */    
    public OnCommand(JSONObject jsonObject) {
        super(SdlCommand.ON_COMMAND, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.cmdId = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_CMD_ID);
            this.triggerSource = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TRIGGER_SOURCE);
            break;
        }
    }
    
    /**
     * <p>Returns an <i>Integer</i> object representing the Command ID</p>
     * @return Integer an integer representation of this object
     */    
    public Integer getCmdID() {
        return this.cmdId;
    }
    
    /**
     * <p>Sets a Command ID</p>    
     * @param cmdID an integer object representing a Command ID
     */    
    public void setCmdID( Integer cmdID ) {
        this.cmdId = cmdID;
    }
    
    /**
     * <p>Returns a <I>TriggerSource</I> object which will be shown in the HMI</p>    
     * @return TriggerSource a TriggerSource object
     */    
    public TriggerSource getTriggerSource() {
        return TriggerSource.valueForJsonName(this.triggerSource, sdlVersion);
    }
    
    /**
     * <p>Sets TriggerSource<br/>
     * Indicates whether command was selected via VR or via a menu selection (using the OK button).</p>    
     * @param triggerSource a TriggerSource object
     */    
    public void setTriggerSource( TriggerSource triggerSource ) {
        this.triggerSource = (triggerSource == null) ? null : triggerSource.getJsonName(sdlVersion);
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_CMD_ID, this.cmdId);
            JsonUtils.addToJsonObject(result, KEY_TRIGGER_SOURCE, this.triggerSource);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmdId == null) ? 0 : cmdId.hashCode());
		result = prime * result + ((triggerSource == null) ? 0 : triggerSource.hashCode());
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
		OnCommand other = (OnCommand) obj;
		if (cmdId == null) {
			if (other.cmdId != null) { 
				return false;
			}
		} 
		else if (!cmdId.equals(other.cmdId)) { 
			return false;
		}
		if (triggerSource == null) {
			if (other.triggerSource != null) { 
				return false;
			}
		}
		else if (!triggerSource.equals(other.triggerSource)) { 
			return false;
		}
		return true;
	}
}
