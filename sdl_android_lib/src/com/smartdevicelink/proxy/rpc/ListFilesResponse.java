package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * List Files Response is sent, when ListFiles has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ListFilesResponse extends RPCResponse {
	public static final String filenames = "filenames";
	public static final String spaceAvailable = "spaceAvailable";

	/**
	 * Constructs a new ListFilesResponse object
	 */
    public ListFilesResponse() {
        super("ListFiles");
    }
    public ListFilesResponse(Hashtable hash) {
        super(hash);
    }
    public void setFilenames(List<String> filenames) {
        if (filenames != null) {
            parameters.put(ListFilesResponse.filenames, filenames);
        } else {
        	parameters.remove(ListFilesResponse.filenames);
        }
    }
    public List<String> getFilenames() {
        if (parameters.get(ListFilesResponse.filenames) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(ListFilesResponse.filenames);
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
            parameters.put(ListFilesResponse.spaceAvailable, spaceAvailable);
        } else {
        	parameters.remove(ListFilesResponse.spaceAvailable);
        }
    }
    public Integer getSpaceAvailable() {
        return (Integer) parameters.get(ListFilesResponse.spaceAvailable);
    }
}
