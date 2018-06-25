package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

/**
 * Delete File Response is sent, when DeleteFile has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class DeleteFileResponse extends RPCResponse {
	public static final String KEY_SPACE_AVAILABLE = "spaceAvailable";
	/** Constructs a new DeleteFileResponse object
	 * 
	 */

    public DeleteFileResponse() {
        super(FunctionID.DELETE_FILE.toString());
    }
    public DeleteFileResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new DeleteFileResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     * @param spaceAvailable  the total local space available on the module for the registered app.
     */
    public DeleteFileResponse(@NonNull Boolean success, @NonNull Result resultCode, @NonNull Integer spaceAvailable) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
        setSpaceAvailable(spaceAvailable);
    }
    public void setSpaceAvailable(@NonNull Integer spaceAvailable) {
        setParameters(KEY_SPACE_AVAILABLE, spaceAvailable);
    }
    public Integer getSpaceAvailable() {
        return getInteger(KEY_SPACE_AVAILABLE);
    }

}
