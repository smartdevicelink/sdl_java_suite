/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
     * @param longitudeDegrees degrees of the longitudinal position
     */
    public SendLocation setLongitudeDegrees( Double longitudeDegrees) {
        setParameters(KEY_LON_DEGREES, longitudeDegrees);
        return this;
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
     * @param latitudeDegrees degrees of the latitudinal position
     */
    public SendLocation setLatitudeDegrees( Double latitudeDegrees) {
        setParameters(KEY_LAT_DEGREES, latitudeDegrees);
        return this;
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
    public SendLocation setLocationName( String locationName) {
        setParameters(KEY_LOCATION_NAME, locationName);
        return this;
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
    public SendLocation setLocationDescription( String locationDescription) {
        setParameters(KEY_LOCATION_DESCRIPTION, locationDescription);
        return this;
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
    public SendLocation setPhoneNumber( String phoneNumber) {
        setParameters(KEY_PHONE_NUMBER, phoneNumber);
        return this;
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
    public SendLocation setAddressLines( List<String> addressLines) {
        setParameters(KEY_ADDRESS_LINES, addressLines);
        return this;
    }

    /**
     * Getter for image of the location to send.
     * @return The image of the location to send
     */
    public Image getLocationImage(){
        return (Image) getObject(Image.class, KEY_LOCATION_IMAGE);
    }

    /**
     * Setter for image of the location to send.
     * @param locationImage The image of the location to send
     */
    public SendLocation setLocationImage( Image locationImage) {
        setParameters(KEY_LOCATION_IMAGE, locationImage);
        return this;
    }

	public DeliveryMode getDeliveryMode() {
        return (DeliveryMode) getObject(DeliveryMode.class, KEY_DELIVERY_MODE);
	}

	public SendLocation setDeliveryMode( DeliveryMode deliveryMode) {
        setParameters(KEY_DELIVERY_MODE, deliveryMode);
        return this;
    }

    public DateTime getTimeStamp() {
        return (DateTime) getObject(DateTime.class, KEY_TIME_STAMP);
	}

	public SendLocation setTimeStamp( DateTime timeStamp) {
        setParameters(KEY_TIME_STAMP, timeStamp);
        return this;
    }

    public OasisAddress getAddress() {
        return (OasisAddress) getObject(OasisAddress.class, KEY_ADDRESS);
	}

	public SendLocation setAddress( OasisAddress address) {
        setParameters(KEY_ADDRESS, address);
        return this;
    }
}
