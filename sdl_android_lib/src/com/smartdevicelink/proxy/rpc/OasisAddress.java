package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class OasisAddress extends RPCStruct{
    public static final String KEY_COUNTRY_NAME = "countryName";
    public static final String KEY_COUNTRY_CODE = "countryCode";
    public static final String KEY_POSTAL_CODE = "postalCode";
    public static final String KEY_ADMINISTRATIVE_AREA = "administrativeArea";
    public static final String KEY_SUB_ADMINISTRATIVE_AREA = "subAdministrativeArea";
    public static final String KEY_LOCALITY = "locality";
    public static final String KEY_SUB_LOCALITY = "subLocality";
    public static final String KEY_THOROUGH_FARE = "thoroughfare";
    public static final String KEY_SUB_THOROUGH_FARE = "subThoroughfare";

    public OasisAddress() {
    }

    public OasisAddress(Hashtable<String, Object> hash) {
        super(hash);
    }

    public String getCountryName() {
        return (String) store.get(KEY_COUNTRY_NAME);
    }

    public void setCountryName(String countryName) {
        if (countryName != null) {
            store.put(KEY_COUNTRY_NAME, countryName);
        } else {
            store.remove(KEY_COUNTRY_NAME);
        }
    }

    public String getCountryCode() {
        return (String) store.get(KEY_COUNTRY_CODE);
    }

    public void setCountryCode(String countryCode) {
        if (countryCode != null) {
            store.put(KEY_COUNTRY_CODE, countryCode);
        } else {
            store.remove(KEY_COUNTRY_CODE);
        }
    }

    public String getPostalCode() {
        return (String) store.get(KEY_POSTAL_CODE);
    }

    public void setPostalCode(String postalCode) {
        if (postalCode != null) {
            store.put(KEY_POSTAL_CODE, postalCode);
        } else {
            store.remove(KEY_POSTAL_CODE);
        }
    }

    public String getAdministrativeArea() {
        return (String) store.get(KEY_ADMINISTRATIVE_AREA);
    }

    public void setAdministrativeArea(String administrativeArea) {
        if (administrativeArea != null) {
            store.put(KEY_ADMINISTRATIVE_AREA, administrativeArea);
        } else {
            store.remove(KEY_ADMINISTRATIVE_AREA);
        }
    }

    public String getSubAdministrativeArea() {
        return (String) store.get(KEY_SUB_ADMINISTRATIVE_AREA);
    }

    public void setSubAdministrativeArea(String subAdministrativeArea) {
        if (subAdministrativeArea != null) {
            store.put(KEY_SUB_ADMINISTRATIVE_AREA, subAdministrativeArea);
        } else {
            store.remove(KEY_SUB_ADMINISTRATIVE_AREA);
        }
    }

    public String getLocality() {
        return (String) store.get(KEY_LOCALITY);
    }

    public void setLocality(String locality) {
        if (locality != null) {
            store.put(KEY_LOCALITY, locality);
        } else {
            store.remove(KEY_LOCALITY);
        }
    }

    public String getSubLocality() {
        return (String) store.get(KEY_SUB_LOCALITY);
    }

    public void setSubLocality(String subLocality) {
        if (subLocality != null) {
            store.put(KEY_SUB_LOCALITY, subLocality);
        } else {
            store.remove(KEY_SUB_LOCALITY);
        }
    }

    public String getThoroughfare() {
        return (String) store.get(KEY_THOROUGH_FARE);
    }

    public void setThoroughfare(String thoroughFare) {
        if (thoroughFare != null) {
            store.put(KEY_THOROUGH_FARE, thoroughFare);
        } else {
            store.remove(KEY_THOROUGH_FARE);
        }
    }

    public String getSubThoroughfare() {
        return (String) store.get(KEY_SUB_THOROUGH_FARE);
    }

    public void setSubThoroughfare(String subThoroughfare) {
        if (subThoroughfare != null) {
            store.put(KEY_SUB_THOROUGH_FARE, subThoroughfare);
        } else {
            store.remove(KEY_SUB_THOROUGH_FARE);
        }
    }
}
