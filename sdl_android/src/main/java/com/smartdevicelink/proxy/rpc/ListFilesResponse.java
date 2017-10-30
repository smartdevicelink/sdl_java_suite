package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;
import java.util.List;

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
        setParameters(KEY_FILENAMES, filenames);
    }
    @SuppressWarnings("unchecked")
    public List<String> getFilenames() {
        return (List<String>) getObject(String.class, KEY_FILENAMES);
    }
    public void setSpaceAvailable(Integer spaceAvailable) {
        setParameters(KEY_SPACE_AVAILABLE, spaceAvailable);
    }
    public Integer getSpaceAvailable() {
        return getInteger(KEY_SPACE_AVAILABLE);
    }
}
