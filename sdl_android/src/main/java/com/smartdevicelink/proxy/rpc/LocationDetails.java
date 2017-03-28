package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;
import java.util.List;

public class LocationDetails extends RPCStruct{
    public static final String KEY_COORDINATE = "coordinate";
    public static final String KEY_LOCATION_NAME = "locationName";
    public static final String KEY_ADDRESS_LINES = "addressLines";
    public static final String KEY_LOCATION_DESCRIPTION = "locationDescription";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_LOCATION_IMAGE = "locationImage";
    public static final String KEY_SEARCH_ADDRESS = "searchAddress";

    public LocationDetails() {
    }

    public LocationDetails(Hashtable<String, Object> hash) {
        super(hash);
    }

    @SuppressWarnings("unchecked")
    public Coordinate getCoordinate() {
        Object obj = store.get(KEY_COORDINATE);
        if (obj instanceof Coordinate) {
            return (Coordinate) obj;
        } else if (obj instanceof Hashtable) {
            try {
                return new Coordinate((Hashtable<String, Object>) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_COORDINATE, e);
            }
        }
        return null;
    }
    public void setCoordinate(Coordinate coordinate) {
        if (coordinate != null) {
            store.put(KEY_COORDINATE, coordinate);
        } else {
            store.remove(KEY_COORDINATE);
        }
    }

    public String getLocationName() {
        return (String) store.get(KEY_LOCATION_NAME);
    }

    public void setLocationName(String locationName) {
        if (locationName != null) {
            store.put(KEY_LOCATION_NAME, locationName);
        } else {
            store.remove(KEY_LOCATION_NAME);
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getAddressLines() {
        final Object listObj = store.get(KEY_ADDRESS_LINES);
        if (listObj instanceof List<?>) {
            List<?> list = (List<?>) listObj;
            if (list.size() > 0) {
                Object obj = list.get(0);
                if (obj instanceof String) {
                    return (List<String>) list;
                }
            }
        }
        return null;
    }

    public void setAddressLines(List<String> addressLines) {
        if (addressLines != null) {
            store.put(KEY_ADDRESS_LINES, addressLines);
        } else {
            store.remove(KEY_ADDRESS_LINES);
        }
    }

    public String getLocationDescription() {
        return (String) store.get(KEY_LOCATION_DESCRIPTION);
    }

    public void setLocationDescription(String locationDescription) {
        if (locationDescription != null) {
            store.put(KEY_LOCATION_DESCRIPTION, locationDescription);
        } else {
            store.remove(KEY_LOCATION_DESCRIPTION);
        }
    }

    public String getPhoneNumber() {
        return (String) store.get(KEY_PHONE_NUMBER);
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null) {
            store.put(KEY_PHONE_NUMBER, phoneNumber);
        } else {
            store.remove(KEY_PHONE_NUMBER);
        }
    }

    @SuppressWarnings("unchecked")
    public Image getLocationImage() {
        Object obj = store.get(KEY_LOCATION_IMAGE);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
            try {
                return new Image((Hashtable<String, Object>) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_LOCATION_IMAGE, e);
            }
        }
        return null;
    }

    public void setLocationImage(Image locationImage) {
        if (locationImage != null) {
            store.put(KEY_LOCATION_IMAGE, locationImage);
        } else {
            store.remove(KEY_LOCATION_IMAGE);
        }
    }

    @SuppressWarnings("unchecked")
    public OasisAddress getSearchAddress() {
        Object obj = store.get(KEY_SEARCH_ADDRESS);
        if (obj instanceof OasisAddress) {
            return (OasisAddress) obj;
        } else if (obj instanceof Hashtable) {
            try {
                return new OasisAddress((Hashtable<String, Object>) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_SEARCH_ADDRESS, e);
            }
        }
        return null;
    }
    public void setSearchAddress(OasisAddress searchAddress) {
        if (searchAddress != null) {
            store.put(KEY_SEARCH_ADDRESS, searchAddress);
        } else {
            store.remove(KEY_SEARCH_ADDRESS);
        }
    }
}
