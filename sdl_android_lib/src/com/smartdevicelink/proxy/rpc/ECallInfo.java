package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;
import com.smartdevicelink.util.DebugTool;

public class ECallInfo extends RPCStruct {
    public static final String eCallNotificationStatus = "eCallNotificationStatus";
    public static final String auxECallNotificationStatus = "auxECallNotificationStatus";
    public static final String eCallConfirmationStatus = "eCallConfirmationStatus";

    public ECallInfo() { }
    public ECallInfo(Hashtable hash) {
        super(hash);
    }

    public void setECallNotificationStatus(VehicleDataNotificationStatus eCallNotificationStatus) {
        if (eCallNotificationStatus != null) {
            store.put(ECallInfo.eCallNotificationStatus, eCallNotificationStatus);
        } else {
        	store.remove(ECallInfo.eCallNotificationStatus);
        }
    }
    public VehicleDataNotificationStatus getECallNotificationStatus() {
        Object obj = store.get(ECallInfo.eCallNotificationStatus);
        if (obj instanceof VehicleDataNotificationStatus) {
            return (VehicleDataNotificationStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataNotificationStatus theCode = null;
            try {
                theCode = VehicleDataNotificationStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + ECallInfo.eCallNotificationStatus, e);
            }
            return theCode;
        }
        return null;
    }
    public void setAuxECallNotificationStatus(VehicleDataNotificationStatus auxECallNotificationStatus) {
        if (auxECallNotificationStatus != null) {
            store.put(ECallInfo.auxECallNotificationStatus, auxECallNotificationStatus);
        } else {
        	store.remove(ECallInfo.auxECallNotificationStatus);
        }
    }
    public VehicleDataNotificationStatus getAuxECallNotificationStatus() {
        Object obj = store.get(ECallInfo.auxECallNotificationStatus);
        if (obj instanceof VehicleDataNotificationStatus) {
            return (VehicleDataNotificationStatus) obj;
        } else if (obj instanceof String) {
        	VehicleDataNotificationStatus theCode = null;
            try {
                theCode = VehicleDataNotificationStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + ECallInfo.auxECallNotificationStatus, e);
            }
            return theCode;
        }
        return null;
    }
    public void setECallConfirmationStatus(ECallConfirmationStatus eCallConfirmationStatus) {
        if (eCallConfirmationStatus != null) {
            store.put(ECallInfo.eCallConfirmationStatus, eCallConfirmationStatus);
        } else {
        	store.remove(ECallInfo.eCallConfirmationStatus);
        }
    }
    public ECallConfirmationStatus getECallConfirmationStatus() {
        Object obj = store.get(ECallInfo.eCallConfirmationStatus);
        if (obj instanceof ECallConfirmationStatus) {
            return (ECallConfirmationStatus) obj;
        } else if (obj instanceof String) {
        	ECallConfirmationStatus theCode = null;
            try {
                theCode = ECallConfirmationStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + ECallInfo.eCallConfirmationStatus, e);
            }
            return theCode;
        }
        return null;
    }
}
