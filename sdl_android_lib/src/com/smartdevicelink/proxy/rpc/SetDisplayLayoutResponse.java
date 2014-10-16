package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Set Display Layout Response is sent, when SetDisplayLayout has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetDisplayLayoutResponse extends RPCResponse {
	public static final String buttonCapabilities = "buttonCapabilities";
	public static final String displayCapabilities = "displayCapabilities";
    public static final String softButtonCapabilities = "softButtonCapabilities";
    public static final String presetBankCapabilities = "presetBankCapabilities";

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
        Object obj = parameters.get(SetDisplayLayoutResponse.displayCapabilities);
        if (obj instanceof DisplayCapabilities) {
        	return (DisplayCapabilities)obj;
        } else if (obj instanceof Hashtable) {
        	return new DisplayCapabilities((Hashtable)obj);
        }
        return null;
    }

    public void setDisplayCapabilities(DisplayCapabilities displayCapabilities) {
        if (displayCapabilities != null) {
            parameters.put(SetDisplayLayoutResponse.displayCapabilities, displayCapabilities);
        }
    }

    public List<ButtonCapabilities> getButtonCapabilities() {
        if (parameters.get(SetDisplayLayoutResponse.buttonCapabilities) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(SetDisplayLayoutResponse.buttonCapabilities);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof ButtonCapabilities) {
	                return (List<ButtonCapabilities>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<ButtonCapabilities> newList = new ArrayList<ButtonCapabilities>();
	                for (Object hashObj : list) {
	                    newList.add(new ButtonCapabilities((Hashtable)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

    public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
        if (buttonCapabilities != null) {
            parameters.put(SetDisplayLayoutResponse.buttonCapabilities, buttonCapabilities);
        }
    }

    public List<SoftButtonCapabilities> getSoftButtonCapabilities() {
        if (parameters.get(SetDisplayLayoutResponse.softButtonCapabilities) instanceof List<?>) {
	    	List<?> list = (List<?>)parameters.get(SetDisplayLayoutResponse.softButtonCapabilities);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof SoftButtonCapabilities) {
	                return (List<SoftButtonCapabilities>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<SoftButtonCapabilities> newList = new ArrayList<SoftButtonCapabilities>();
	                for (Object hashObj : list) {
	                    newList.add(new SoftButtonCapabilities((Hashtable)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

    public void setSoftButtonCapabilities(List<SoftButtonCapabilities> softButtonCapabilities) {
        if (softButtonCapabilities != null) {
            parameters.put(SetDisplayLayoutResponse.softButtonCapabilities, softButtonCapabilities);
        }
    }

    public PresetBankCapabilities getPresetBankCapabilities() {
        Object obj = parameters.get(SetDisplayLayoutResponse.presetBankCapabilities);
        if (obj instanceof PresetBankCapabilities) {
        	return (PresetBankCapabilities)obj;
        } else if (obj instanceof Hashtable) {
        	return new PresetBankCapabilities((Hashtable)obj);
        }
        return null;
    }

    public void setPresetBankCapabilities(PresetBankCapabilities presetBankCapabilities) {
        if (presetBankCapabilities != null) {
            parameters.put(SetDisplayLayoutResponse.presetBankCapabilities, presetBankCapabilities);
        }
    }
    
}
