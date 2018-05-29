package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

public class GetInteriorVehicleDataResponse extends RPCResponse {
	public static final String KEY_MODULE_DATA = "moduleData";
	public static final String KEY_IS_SUBSCRIBED = "isSubscribed";

	/**
	 * Constructs a new GetInteriorVehicleDataResponse object
	 */
    public GetInteriorVehicleDataResponse() {
        super(FunctionID.GET_INTERIOR_VEHICLE_DATA.toString());
    }

	/**
	 * <p>Constructs a new GetInteriorVehicleDataResponse object indicated by the
	 * Hashtable parameter</p>
	 *
	 *
	 * @param hash
	 * The Hashtable to use
	 */
    public GetInteriorVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new GetInteriorVehicleDataResponse object
	 * @param moduleData
	 * @param resultCode whether the request is successfully processed
	 * @param success whether the request is successfully processed

	 */
	public GetInteriorVehicleDataResponse( @NonNull ModuleData moduleData, @NonNull Result resultCode, @NonNull Boolean success) {
		this();
		setModuleData(moduleData);
		setResultCode(resultCode);
		setSuccess(success);
	}

	/**
	 * Gets the moduleData
	 *
	 * @return ModuleData
	 */
	public ModuleData getModuleData() {
		return (ModuleData) getObject(ModuleData.class, KEY_MODULE_DATA);
	}

	/**
	 * Sets the moduleData
	 *
	 * @param moduleData
	 */
	public void setModuleData(ModuleData moduleData) {
		setParameters(KEY_MODULE_DATA, moduleData);
	}

	/**
	 * Sets isSubscribed parameter
	 *
	 * @param isSubscribed
	 * It is a conditional-mandatory parameter: must be returned in case "subscribe" parameter was present in the related request.
	 * If "true" - the "moduleType" from request is successfully subscribed and the head unit will send onInteriorVehicleData notifications for the moduleType.
	 * If "false" - the "moduleType" from request is either unsubscribed or failed to subscribe.
	 * */
	public void setIsSubscribed(Boolean isSubscribed) {
		setParameters(KEY_IS_SUBSCRIBED, isSubscribed);
	}

	/**
	 * Gets isSubscribed parameter
	 *
	 * @return Boolean - It is a conditional-mandatory parameter: must be returned in case "subscribe" parameter was present in the related request.
	 * If "true" - the "moduleType" from request is successfully subscribed and the head unit will send onInteriorVehicleData notifications for the moduleType.
	 * If "false" - the "moduleType" from request is either unsubscribed or failed to subscribe.
	 * */
	public Boolean getIsSubscribed() {
		return getBoolean(KEY_IS_SUBSCRIBED);
	}
}
