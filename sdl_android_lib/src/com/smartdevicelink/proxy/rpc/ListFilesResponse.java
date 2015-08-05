package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * List Files Response is sent, when ListFiles has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ListFilesResponse extends RPCResponse {
	public static final String KEY_FILENAMES = "filenames";
	public static final String KEY_SPACE_AVAILABLE = "spaceAvailable";

	/**
	 * Constructs a new ListFilesResponse object
	 */
    public ListFilesResponse() {
        super(FunctionID.LIST_FILES.toString());
    }
    public ListFilesResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setFilenames(List<String> filenames) {
        if (filenames != null) {
            parameters.put(KEY_FILENAMES, filenames);
        } else {
        	parameters.remove(KEY_FILENAMES);
        }
    }
    @SuppressWarnings("unchecked")
    public List<String> getFilenames() {
        if (parameters.get(KEY_FILENAMES) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_FILENAMES);
        	if (list != null && list.size()>0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
        			return (List<String>) list;
        		}
        	}
        }
    	return null;
    }
    public void setSpaceAvailable(Integer spaceAvailable) {
        if (spaceAvailable != null) {
            parameters.put(KEY_SPACE_AVAILABLE, spaceAvailable);
        } else {
        	parameters.remove(KEY_SPACE_AVAILABLE);
        }
    }
    public Integer getSpaceAvailable() {
        return (Integer) parameters.get(KEY_SPACE_AVAILABLE);
    }
}
