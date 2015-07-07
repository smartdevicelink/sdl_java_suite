package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;

public class ECallInfo extends RPCStruct {
    public static final String KEY_E_CALL_NOTIFICATION_STATUS = "eCallNotificationStatus";
    public static final String KEY_AUX_E_CALL_NOTIFICATION_STATUS = "auxECallNotificationStatus";
    public static final String KEY_E_CALL_CONFIRMATION_STATUS = "eCallConfirmationStatus";

    public ECallInfo() { }
    public ECallInfo(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setECallNotificationStatus(VehicleDataNotificationStatus eCallNotificationStatus) {
        if (eCallNotificationStatus != null) {
            store.put(KEY_E_CALL_NOTIFICATION_STATUS, eCallNotificationStatus);
        } else {
        	store.remove(KEY_E_CALL_NOTIFICATION_STATUS);
        }
    }
    public VehicleDataNotificationStatus getECallNotificationStatus() {
        Object obj = store.get(KEY_E_CALL_NOTIFICATION_STATUS);
        if (obj instanceof VehicleDataNotificationStatus) {
            return (VehicleDataNotificationStatus) obj;
        } else if (obj instanceof String) {
        	return VehicleDataNotificationStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setAuxECallNotificationStatus(VehicleDataNotificationStatus auxECallNotificationStatus) {
        if (auxECallNotificationStatus != null) {
            store.put(KEY_AUX_E_CALL_NOTIFICATION_STATUS, auxECallNotificationStatus);
        } else {
        	store.remove(KEY_AUX_E_CALL_NOTIFICATION_STATUS);
        }
    }
    public VehicleDataNotificationStatus getAuxECallNotificationStatus() {
        Object obj = store.get(KEY_AUX_E_CALL_NOTIFICATION_STATUS);
        if (obj instanceof VehicleDataNotificationStatus) {
            return (VehicleDataNotificationStatus) obj;
        } else if (obj instanceof String) {
        	return VehicleDataNotificationStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setECallConfirmationStatus(ECallConfirmationStatus eCallConfirmationStatus) {
        if (eCallConfirmationStatus != null) {
            store.put(KEY_E_CALL_CONFIRMATION_STATUS, eCallConfirmationStatus);
        } else {
        	store.remove(KEY_E_CALL_CONFIRMATION_STATUS);
        }
    }
    public ECallConfirmationStatus getECallConfirmationStatus() {
        Object obj = store.get(KEY_E_CALL_CONFIRMATION_STATUS);
        if (obj instanceof ECallConfirmationStatus) {
            return (ECallConfirmationStatus) obj;
        } else if (obj instanceof String) {
        	return ECallConfirmationStatus.valueForString((String) obj);
        }
        return null;
    }
}
