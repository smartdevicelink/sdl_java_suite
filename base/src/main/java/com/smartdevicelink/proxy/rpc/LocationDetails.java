package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

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
        return (Coordinate) getObject(Coordinate.class, KEY_COORDINATE);
    }
    public void setCoordinate(Coordinate coordinate) {
        setValue(KEY_COORDINATE, coordinate);
    }

    public String getLocationName() {
        return getString(KEY_LOCATION_NAME);
    }

    public void setLocationName(String locationName) {
        setValue(KEY_LOCATION_NAME, locationName);
    }

    @SuppressWarnings("unchecked")
    public List<String> getAddressLines() {
        return (List<String>) getObject(String.class, KEY_ADDRESS_LINES);
    }

    public void setAddressLines(List<String> addressLines) {
        setValue(KEY_ADDRESS_LINES, addressLines);
    }

    public String getLocationDescription() {
        return getString(KEY_LOCATION_DESCRIPTION);
    }

    public void setLocationDescription(String locationDescription) {
        setValue(KEY_LOCATION_DESCRIPTION, locationDescription);
    }

    public String getPhoneNumber() {
        return getString(KEY_PHONE_NUMBER);
    }

    public void setPhoneNumber(String phoneNumber) {
        setValue(KEY_PHONE_NUMBER, phoneNumber);
    }

    @SuppressWarnings("unchecked")
    public Image getLocationImage() {
        return (Image) getObject(Image.class, KEY_LOCATION_IMAGE);
    }

    public void setLocationImage(Image locationImage) {
        setValue(KEY_LOCATION_IMAGE, locationImage);
    }

    @SuppressWarnings("unchecked")
    public OasisAddress getSearchAddress() {
        return (OasisAddress) getObject(OasisAddress.class, KEY_SEARCH_ADDRESS);
    }
    public void setSearchAddress(OasisAddress searchAddress) {
        setValue(KEY_SEARCH_ADDRESS, searchAddress);
    }
}
