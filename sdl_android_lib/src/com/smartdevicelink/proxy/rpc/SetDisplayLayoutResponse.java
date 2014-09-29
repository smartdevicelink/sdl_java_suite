package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.constants.Names;

/**
 * Set Display Layout Response is sent, when SetDisplayLayout has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetDisplayLayoutResponse extends RPCResponse {

	/**
	 * Constructs a new SetDisplayLayoutResponse object
	 */
    public SetDisplayLayoutResponse() {
        super("SetDisplayLayout");
    }

	/**
	 * Constructs a new SetDisplayLayoutResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetDisplayLayoutResponse(Hashtable hash) {
        super(hash);
    }
    
    public DisplayCapabilities getDisplayCapabilities() {
        Object obj = parameters.get(Names.displayCapabilities);
        if (obj instanceof DisplayCapabilities) {
        	return (DisplayCapabilities)obj;
        } else if (obj instanceof Hashtable) {
        	return new DisplayCapabilities((Hashtable)obj);
        }
        return null;
    }

    public void setDisplayCapabilities(DisplayCapabilities displayCapabilities) {
        if (displayCapabilities != null) {
            parameters.put(Names.displayCapabilities, displayCapabilities);
        }
    }

    public Vector<ButtonCapabilities> getButtonCapabilities() {
        if (parameters.get(Names.buttonCapabilities) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(Names.buttonCapabilities);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof ButtonCapabilities) {
	                return (Vector<ButtonCapabilities>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<ButtonCapabilities> newList = new Vector<ButtonCapabilities>();
	                for (Object hashObj : list) {
	                    newList.add(new ButtonCapabilities((Hashtable)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

    public void setButtonCapabilities(Vector<ButtonCapabilities> buttonCapabilities) {
        if (buttonCapabilities != null) {
            parameters.put(Names.buttonCapabilities, buttonCapabilities);
        }
    }

    public Vector<SoftButtonCapabilities> getSoftButtonCapabilities() {
        if (parameters.get(Names.softButtonCapabilities) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(Names.softButtonCapabilities);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof SoftButtonCapabilities) {
	                return (Vector<SoftButtonCapabilities>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<SoftButtonCapabilities> newList = new Vector<SoftButtonCapabilities>();
	                for (Object hashObj : list) {
	                    newList.add(new SoftButtonCapabilities((Hashtable)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

    public void setSoftButtonCapabilities(Vector<SoftButtonCapabilities> softButtonCapabilities) {
        if (softButtonCapabilities != null) {
            parameters.put(Names.softButtonCapabilities, softButtonCapabilities);
        }
    }

    public PresetBankCapabilities getPresetBankCapabilities() {
        Object obj = parameters.get(Names.presetBankCapabilities);
        if (obj instanceof PresetBankCapabilities) {
        	return (PresetBankCapabilities)obj;
        } else if (obj instanceof Hashtable) {
        	return new PresetBankCapabilities((Hashtable)obj);
        }
        return null;
    }

    public void setPresetBankCapabilities(PresetBankCapabilities presetBankCapabilities) {
        if (presetBankCapabilities != null) {
            parameters.put(Names.presetBankCapabilities, presetBankCapabilities);
        }
    }
    
}