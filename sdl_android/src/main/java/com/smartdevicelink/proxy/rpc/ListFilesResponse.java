package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.util.Version;

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
	private static final Integer MAX_VALUE = 2000000000;

	/**
	 * Constructs a new ListFilesResponse object
	 */
    public ListFilesResponse() {
        super(FunctionID.LIST_FILES.toString());
    }
    public ListFilesResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * @deprecated use {@link ListFilesResponse#ListFilesResponse(Boolean, Result)} instead <br>
     *
     * Constructs a new ListFilesResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    @Deprecated
    public ListFilesResponse(@NonNull Boolean success, @NonNull Result resultCode, @NonNull Integer spaceAvailable) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
        setSpaceAvailable(spaceAvailable);
    }

    /**
     * Constructs a new ListFilesResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public ListFilesResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }

    /**
     * SpaceAvailable became optional as of RPC Spec 5.0. If a system that expected the value to
     * always have a value connects to such a system, it could return null. Check to see if there
     * is a value, and if not, set it to MAX_VALUE as defined by the RPC Spec
     *
     * @param rpcVersion the rpc spec version that has been negotiated. If value is null the
     *                   the max value of RPC spec version this library supports should be used.
     * @param formatParams if true, the format method will be called on subsequent params
     */
    @Override
    public void format(Version rpcVersion, boolean formatParams){
        if (rpcVersion == null || rpcVersion.getMajor() >= 5){
            if (getSpaceAvailable() == null){
                setSpaceAvailable(MAX_VALUE);
            }
        }
        super.format(rpcVersion, formatParams);
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
