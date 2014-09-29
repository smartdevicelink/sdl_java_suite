package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.util.DebugTool;

/**
 * Used to set an alternate display layout. If not sent, default screen for
 * given platform will be shown
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetDisplayLayout extends RPCRequest {

	/**
	 * Constructs a new SetDisplayLayout object
	 */
    public SetDisplayLayout() {
        super("SetDisplayLayout");
    }

	/**
	 * Constructs a new SetDisplayLayout object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetDisplayLayout(Hashtable hash) {
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
            parameters.put(Names.displayLayout, displayLayout);
        } else {
        	parameters.remove(Names.displayLayout);
        }
    }

	/**
	 * Gets a display layout.
	 */
    public String getDisplayLayout() {
    	return (String) parameters.get(Names.displayLayout);
    }
}
