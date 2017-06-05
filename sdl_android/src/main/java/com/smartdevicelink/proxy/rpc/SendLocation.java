package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.DeliveryMode;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;
import java.util.List;


/**
 * Sends a location to the head-unit to display on a map or list.
 *
 * @since SmartDeviceLink 3.0
 *
 */
public class SendLocation extends RPCRequest{

    public static final String KEY_LAT_DEGREES          = "latitudeDegrees";
    public static final String KEY_LON_DEGREES          = "longitudeDegrees";
    public static final String KEY_LOCATION_NAME        = "locationName";
    public static final String KEY_LOCATION_DESCRIPTION = "locationDescription";
    public static final String KEY_PHONE_NUMBER         = "phoneNumber";
    public static final String KEY_ADDRESS_LINES        = "addressLines";
    public static final String KEY_LOCATION_IMAGE       = "locationImage";
    public static final String KEY_DELIVERY_MODE		= "deliveryMode";
    public static final String KEY_TIME_STAMP			= "timeStamp";
    public static final String KEY_ADDRESS		        = "address";

    /**
     * Constructs a new SendLocation object
     */
    public SendLocation(){
        super(FunctionID.SEND_LOCATION.toString());
    }

    /**
     * <p>
     * Constructs a new SendLocation object indicated by the Hashtable parameter
     * </p>
     * 
     * @param hash
     *            The Hashtable to use
     */
    public SendLocation(Hashtable<String, Object> hash){
        super(hash);
    }

    /**
     * Getter for longitude of the location to send.
     * 
     * <p><b>IMPORTANT NOTE:</b> </p><p>A previous version of this method call returned a Float
     * value, however, it has been changed to return a Double.</p> This will compile, 
     * but cause a ClassCastException if your value is not also a Double type.
     * @since SmartDeviceLink v4.0
     * 
     * @return The longitude of the location
     */
    public Double getLongitudeDegrees(){
    	Object value = getParameters(KEY_LON_DEGREES);
    	return SdlDataTypeConverter.objectToDouble(value);
    }

    /**
     * Setter for longitude of the location to send.
     * @param longitudeDegrees
     */
    public void setLongitudeDegrees(Double longitudeDegrees){
        setParameters(KEY_LON_DEGREES, longitudeDegrees);
    }

    /**
     * Getter for latitude of the location to send.
     * 
     * <p><b>IMPORTANT NOTE:</b> </p><p>A previous version of this method call returned a Float
     * value, however, it has been changed to return a Double.</p> This will compile, 
     * but cause a ClassCastException if your value is not also a Double type.
     * @since SmartDeviceLink v4.0
     * 
     * @return The latitude of the location
     */
    public Double getLatitudeDegrees(){    	
    	Object value = getParameters(KEY_LAT_DEGREES);
    	return SdlDataTypeConverter.objectToDouble(value);
    }

    /**
     * Setter for latitude of the location to send.
     * @param latitudeDegrees
     */
    public void setLatitudeDegrees(Double latitudeDegrees){
        setParameters(KEY_LAT_DEGREES, latitudeDegrees);
    }

    /**
     * Getter for name of the location to send.
     * @return The name of the location
     */
    public String getLocationName(){
        return getString(KEY_LOCATION_NAME);
    }

    /**
     * Setter for name of the location to send.
     * @param locationName The name of the location
     */
    public void setLocationName(String locationName){
        setParameters(KEY_LOCATION_NAME, locationName);
    }

    /**
     * Getter for description of the location to send.
     * @return The description of the location to send
     */
    public String getLocationDescription(){
        return getString(KEY_LOCATION_DESCRIPTION);
    }

    /**
     * Setter for description of the location to send.
     * @param locationDescription The description of the location
     */
    public void setLocationDescription(String locationDescription){
        setParameters(KEY_LOCATION_DESCRIPTION, locationDescription);
    }

    /**
     * Getter for phone number of the location to send.
     * @return
     */
    public String getPhoneNumber(){
        return getString(KEY_PHONE_NUMBER);
    }

    /**
     * Setter for phone number of the location to send.
     * @param phoneNumber The phone number of the location
     */
    public void setPhoneNumber(String phoneNumber){
        setParameters(KEY_PHONE_NUMBER, phoneNumber);
    }

    /**
     * Getter for address lines of the location to send.
     * @return The address lines of the location
     */
    @SuppressWarnings("unchecked")
    public List<String> getAddressLines(){
        return (List<String>) getObject(String.class, KEY_ADDRESS_LINES);
    }

    /**
     * Setter for address lines of the location to send.
     * @param addressLines The address lines of the location
     */
    public void setAddressLines(List<String> addressLines){
        setParameters(KEY_ADDRESS_LINES, addressLines);
    }

    /**
     * Getter for image of the location to send.
     * @return The image of the location to send
     */
    @SuppressWarnings("unchecked")
    public Image getLocationImage(){
        return (Image) getObject(Image.class, KEY_LOCATION_IMAGE);
    }

    /**
     * Setter for image of the location to send.
     * @param locationImage The image of the location to send
     */
    public void setLocationImage(Image locationImage){
        setParameters(KEY_LOCATION_IMAGE, locationImage);
    }

	public DeliveryMode getDeliveryMode() {
        return (DeliveryMode) getObject(DeliveryMode.class, KEY_DELIVERY_MODE);
	}

	public void setDeliveryMode(DeliveryMode deliveryMode) {
        setParameters(KEY_DELIVERY_MODE, deliveryMode);
	}

    @SuppressWarnings("unchecked")
	public DateTime getTimeStamp() {
        return (DateTime) getObject(DateTime.class, KEY_TIME_STAMP);
	}

	public void setTimeStamp(DateTime timeStamp) {
        setParameters(KEY_TIME_STAMP, timeStamp);
	}

    @SuppressWarnings("unchecked")
	public OasisAddress getAddress() {
        return (OasisAddress) getObject(OasisAddress.class, KEY_ADDRESS);
	}

	public void setAddress(OasisAddress address) {
        setParameters(KEY_ADDRESS, address);
	}
}
