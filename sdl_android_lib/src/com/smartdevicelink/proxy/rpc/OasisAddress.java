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

	/** 
	* OASIS Address - A standard based address class that has been established by The Organization for the Advancement of Structured Information Standards (OASIS). 
	* Oasis is a global nonprofit consortium that works on the development, convergence, and adoption of standards for security,
	* Internet of Things, energy, content technologies, emergency management, and other areas. 
	*
	*/
    public OasisAddress() {
    }

    public OasisAddress(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
	* Gets the localized Name of the country associated with the OasisAddress class.
	* 
	* @return String - The localized Name of the country associated with the OasisAddress class.
	* 
    */
    public String getCountryName() {
        return (String) store.get(KEY_COUNTRY_NAME);
    }

	/**
 	* Sets the localized Name of the country associated with the OasisAddress class.
	* 
	* @param countryName
	* The localized Name of the country associated with the OasisAddress class.
	* 
	*/
    public void setCountryName(String countryName) {
        if (countryName != null) {
            store.put(KEY_COUNTRY_NAME, countryName);
        } else {
            store.remove(KEY_COUNTRY_NAME);
        }
    }

    /**
	* Gets the country code in ISO 3166-2 format associated with the OasisAddress class.
	* 
	* @return String - The country code in ISO 3166-2 format associated with the OasisAddress class.
	* 
    */
    public String getCountryCode() {
        return (String) store.get(KEY_COUNTRY_CODE);
    }

	/**
 	* Sets the country code in ISO 3166-2 format associated with the OasisAddress class.
	* 
	* @param countryCode
	* The country code in ISO 3166-2 format associated with the OasisAddress class.
	* 
	*/ 
    public void setCountryCode(String countryCode) {
        if (countryCode != null) {
            store.put(KEY_COUNTRY_CODE, countryCode);
        } else {
            store.remove(KEY_COUNTRY_CODE);
        }
    }

    /**
	* Gets the Postal Code associated with the OasisAddress class.
	* 
	* @return String - The Postal Code associated with the OasisAddress class.
	* 
    */
    public String getPostalCode() {
        return (String) store.get(KEY_POSTAL_CODE);
    }

	/**
 	* Sets the Postal Code associated with the OasisAddress class.
	* 
	* @param postalCode
	* The Postal Code associated with the OasisAddress class.
	* 
	*/ 
    public void setPostalCode(String postalCode) {
        if (postalCode != null) {
            store.put(KEY_POSTAL_CODE, postalCode);
        } else {
            store.remove(KEY_POSTAL_CODE);
        }
    }

    /**
	* Gets the Administrative Area associated with the OasisAddress class. A portion of the country - Administrative Area's can include details of the top-level area division in the country, such as state, district, province, island, region, etc.
	* 
	* @return String - The Administrative Area associated with the OasisAddress class.
	* 
    */
    public String getAdministrativeArea() {
        return (String) store.get(KEY_ADMINISTRATIVE_AREA);
    }

	/**
 	* Sets the Administrative Area associated with the OasisAddress class.   A portion of the country - Administrative Area can include details of the top-level area division in the country, such as state, district, province, island, region, etc.
	* 
	* @param administrativeArea
	* The Administrative Area associated with the OasisAddress class.
	* 
	*/ 
    public void setAdministrativeArea(String administrativeArea) {
        if (administrativeArea != null) {
            store.put(KEY_ADMINISTRATIVE_AREA, administrativeArea);
        } else {
            store.remove(KEY_ADMINISTRATIVE_AREA);
        }
    }

    /**
	* Gets the SubAdministrative Area associated with the OasisAddress class. A portion of the administrativeArea - The next level down division of the area. E.g. state / county, province / reservation.
	* 
	* @return String - The SubAdministrative Area associated with the OasisAddress class.
	* 
    */
    public String getSubAdministrativeArea() {
        return (String) store.get(KEY_SUB_ADMINISTRATIVE_AREA);
    }

	/**
 	* Sets the SubAdministrative Area associated with the OasisAddress class.   A portion of the administrativeArea - The next level down division of the area. E.g. state / county, province / reservation.
	* 
	* @param subAdministrativeArea
	* The SubAdministrative Area associated with the OasisAddress class.
	* 
	*/ 
    public void setSubAdministrativeArea(String subAdministrativeArea) {
        if (subAdministrativeArea != null) {
            store.put(KEY_SUB_ADMINISTRATIVE_AREA, subAdministrativeArea);
        } else {
            store.remove(KEY_SUB_ADMINISTRATIVE_AREA);
        }
    }

    /**
	* Gets the Locality associated with the OasisAddress class. - A hypernym for city/village
	* 
	* @return String -  The Locality associated with the OasisAddress class.
	* 
    */
    public String getLocality() {
        return (String) store.get(KEY_LOCALITY);
    }

	/**
 	* Sets the Locality associated with the OasisAddress class. - A hypernym for city/village.
	* 
	* @param locality
	* The Locality associated with the OasisAddress class.
	* 
	*/
    public void setLocality(String locality) {
        if (locality != null) {
            store.put(KEY_LOCALITY, locality);
        } else {
            store.remove(KEY_LOCALITY);
        }
    }

    /**
	* Gets the Sub-Locality associated with the OasisAddress class. - Hypernym for district.
	* 
	* @return String -  The Sub-Locality associated with the OasisAddress class.
	* 
    */
    public String getSubLocality() {
        return (String) store.get(KEY_SUB_LOCALITY);
    }

	/**
 	* Sets the Sub-Locality associated with the OasisAddress class.   A hypernym for district.
	* 
	* @param subLocality
	* The Sub-Locality associated with the OasisAddress class.
	* 
	*/ 
    public void setSubLocality(String subLocality) {
        if (subLocality != null) {
            store.put(KEY_SUB_LOCALITY, subLocality);
        } else {
            store.remove(KEY_SUB_LOCALITY);
        }
    }

    /**
	* Gets the Thoroughfare associated with the OasisAddress class. - A hypernym for street, road etc.
	* 
	* @return String -  The Thoroughfare associated with the OasisAddress class.
	* 
    */
    public String getThoroughfare() {
        return (String) store.get(KEY_THOROUGH_FARE);
    }

	/**
 	* Sets the Thoroughfare associated with the OasisAddress class.   A hypernym for street, road etc.
	* 
	* @param thoroughFare
	* The Thoroughfare associated with the OasisAddress class.
	* 
	*/ 
    public void setThoroughfare(String thoroughFare) {
        if (thoroughFare != null) {
            store.put(KEY_THOROUGH_FARE, thoroughFare);
        } else {
            store.remove(KEY_THOROUGH_FARE);
        }
    }
    
    /**
	* Gets the Sub-Thoroughfare associated with the OasisAddress class. - A Portion of thoroughfare (e.g. house number).
	* 
	* @return String -  The Sub-Thoroughfare associated with the OasisAddress class.
    */
    public String getSubThoroughfare() {
        return (String) store.get(KEY_SUB_THOROUGH_FARE);
    }
    
	/**
 	* Sets the Sub-Thoroughfare associated with the OasisAddress class. - A Portion of thoroughfare (e.g. house number).
	* 
	* @param subThoroughfare
	* The Sub-Thoroughfare associated with the OasisAddress class.
	* 
	*/ 
    public void setSubThoroughfare(String subThoroughfare) {
        if (subThoroughfare != null) {
            store.put(KEY_SUB_THOROUGH_FARE, subThoroughfare);
        } else {
            store.remove(KEY_SUB_THOROUGH_FARE);
        }
    }
}
