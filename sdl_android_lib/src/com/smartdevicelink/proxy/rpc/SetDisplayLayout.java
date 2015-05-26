package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Used to set an alternate display layout. If not sent, default screen for
 * given platform will be shown
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetDisplayLayout extends RPCRequest {
	public static final String KEY_DISPLAY_LAYOUT = "displayLayout";
	/**
	 * Constructs a new SetDisplayLayout object
	 */
    public SetDisplayLayout() {
        super(FunctionID.SET_DISPLAY_LAYOUT.toString());
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
