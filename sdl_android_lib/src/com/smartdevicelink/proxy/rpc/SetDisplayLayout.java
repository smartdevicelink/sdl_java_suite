package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Used to set an alternate display layout. If not sent, default screen for
 * given platform will be shown
 * <p>
 * 
 * <p><b>Parameter List</b>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>displayLayout</td>
 * 			<td>string</td>
 * 			<td>Predefined or dynamically created screen layout.<br> Currently only predefined screen layouts are defined.<br> Predefined layouts include: "ONSCREEN_PRESETS"<br> Custom screen containing app-defined onscreen presets. <br>Currently defined for GEN2.</td>
 *                 <td>Y</td>
 * 			<td>maxlength: 500</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 *  </table>
 *<b>Response </b>
 *<p>
 *<b> Non-default Result Codes: </b><br>
 *	 SUCCESS <br>
 *	 INVALID_DATA<br>
 *	 OUT_OF_MEMORY<br>
 *     TOO_MANY_PENDING_REQUESTS<br>
 *     APPLICATION_NOT_REGISTERED<br>
 *     GENERIC_ERROR<br>
 *     REJECTED<br>
 * @since SmartDeviceLink 2.0
 */
public class SetDisplayLayout extends RPCRequest {
	public static final String KEY_DISPLAY_LAYOUT = "displayLayout";
	/**
	 * Constructs a new SetDisplayLayout object
	 */
    public SetDisplayLayout() {
        super(FunctionID.SET_DISPLAY_LAYOUT);
    }

	/**
	 * Constructs a new SetDisplayLayout object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetDisplayLayout(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets a display layout. Predefined or dynamically created screen layout.
	 * Currently only predefined screen layouts are defined. Predefined layouts
	 * include: "ONSCREEN_PRESETS" Custom screen containing app-defined onscreen
	 * presets. Currently defined for GEN2
	 * 
	 * @param displayLayout
	 *            a String value representing a diaply layout
	 */
    public void setDisplayLayout(String displayLayout) {
        if (displayLayout != null) {
            parameters.put(KEY_DISPLAY_LAYOUT, displayLayout);
        } else {
        	parameters.remove(KEY_DISPLAY_LAYOUT);
        }
    }

	/**
	 * Gets a display layout.
	 */
    public String getDisplayLayout() {
    	return (String) parameters.get(KEY_DISPLAY_LAYOUT);
    }
}
