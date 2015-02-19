package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

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

    private Double latitudeDegrees, longitudeDegrees;
    private String locationName, locationDescription, phoneNumber;
    private List<String> addressLines;
    private Image locationImage;
    
    /**
     * Constructs a new SendLocation object
     */
    public SendLocation(){
        super(FunctionID.SEND_LOCATION);
    }

    /**
     * Creates an SendLocation object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SendLocation(JSONObject jsonObject) {
        super(SdlCommand.SEND_LOCATION, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.latitudeDegrees = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_LAT_DEGREES);
            this.longitudeDegrees = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_LON_DEGREES);
            this.locationName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_LOCATION_NAME);
            this.locationDescription = JsonUtils.readStringFromJsonObject(jsonObject, KEY_LOCATION_DESCRIPTION);
            this.phoneNumber = JsonUtils.readStringFromJsonObject(jsonObject, KEY_PHONE_NUMBER);
            this.addressLines = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_ADDRESS_LINES);
            
            JSONObject imageObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_LOCATION_IMAGE);
            if(imageObj != null){
                this.locationImage = new Image(imageObj);
            }
            break;
        }
    }

    /**
     * Getter for longitude of the location to send.
     * @return The longitude of the location
     */
    public Double getLongitudeDegrees(){
        return this.longitudeDegrees;
    }

    /**
     * Setter for longitude of the location to send.
     * @param longitudeDegrees
     */
    public void setLongitudeDegrees(Double longitudeDegrees){
        this.longitudeDegrees = longitudeDegrees;
    }

    /**
     * Getter for latitude of the location to send.
     * @return The latitude of the location
     */
    public Double getLatitudeDegrees(){
        return this.latitudeDegrees;
    }

    /**
     * Setter for latitude of the location to send.
     * @param latitudeDegrees
     */
    public void setLatitudeDegrees(Double latitudeDegrees){
        this.latitudeDegrees = latitudeDegrees;
    }

    /**
     * Getter for name of the location to send.
     * @return The name of the location
     */
    public String getLocationName(){
        return this.locationName;
    }

    /**
     * Setter for name of the location to send.
     * @param locationName The name of the location
     */
    public void setLocationName(String locationName){
        this.locationName = locationName;
    }

    /**
     * Getter for description of the location to send.
     * @return The description of the location to send
     */
    public String getLocationDescription(){
        return this.locationDescription;
    }

    /**
     * Setter for description of the location to send.
     * @param locationDescription The description of the location
     */
    public void setLocationDescription(String locationDescription){
        this.locationDescription = locationDescription;
    }

    /**
     * Getter for phone number of the location to send.
     * @return
     */
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    /**
     * Setter for phone number of the location to send.
     * @param phoneNumber The phone number of the location
     */
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for address lines of the location to send.
     * @return The address lines of the location
     */
    public List<String> getAddressLines(){
        return this.addressLines;
    }

    /**
     * Setter for address lines of the location to send.
     * @param addressLines The address lines of the location
     */
    public void setAddressLines(List<String> addressLines){
        this.addressLines = addressLines;
    }

    /**
     * Getter for image of the location to send.
     * @return The image of the location to send
     */
    public Image getLocationImage(){
        return this.locationImage;
    }

    /**
     * Setter for image of the location to send.
     * @param locationImage The image of the location to send
     */
    public void setLocationImage(Image locationImage){
        this.locationImage = locationImage;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_LAT_DEGREES, this.latitudeDegrees);
            JsonUtils.addToJsonObject(result, KEY_LON_DEGREES, this.longitudeDegrees);
            JsonUtils.addToJsonObject(result, KEY_LOCATION_NAME, this.locationName);
            JsonUtils.addToJsonObject(result, KEY_LOCATION_DESCRIPTION, this.locationDescription);
            JsonUtils.addToJsonObject(result, KEY_PHONE_NUMBER, this.phoneNumber);
            JsonUtils.addToJsonObject(result, KEY_ADDRESS_LINES, (this.addressLines == null) ? null :
                JsonUtils.createJsonArray(this.addressLines));
            JsonUtils.addToJsonObject(result, KEY_LOCATION_IMAGE, (this.locationImage == null) ? null :
                this.locationImage.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( addressLines == null ) ? 0 : addressLines.hashCode() );
        result = prime * result + ( ( latitudeDegrees == null ) ? 0 : latitudeDegrees.hashCode() );
        result = prime * result + ( ( locationDescription == null ) ? 0 : locationDescription.hashCode() );
        result = prime * result + ( ( locationImage == null ) ? 0 : locationImage.hashCode() );
        result = prime * result + ( ( locationName == null ) ? 0 : locationName.hashCode() );
        result = prime * result + ( ( longitudeDegrees == null ) ? 0 : longitudeDegrees.hashCode() );
        result = prime * result + ( ( phoneNumber == null ) ? 0 : phoneNumber.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        SendLocation other = (SendLocation) obj;
        if(addressLines == null){
            if(other.addressLines != null){
                return false;
            }
        }
        else if(!addressLines.equals(other.addressLines)){
            return false;
        }
        if(latitudeDegrees == null){
            if(other.latitudeDegrees != null){
                return false;
            }
        }
        else if(!latitudeDegrees.equals(other.latitudeDegrees)){
            return false;
        }
        if(locationDescription == null){
            if(other.locationDescription != null){
                return false;
            }
        }
        else if(!locationDescription.equals(other.locationDescription)){
            return false;
        }
        if(locationImage == null){
            if(other.locationImage != null){
                return false;
            }
        }
        else if(!locationImage.equals(other.locationImage)){
            return false;
        }
        if(locationName == null){
            if(other.locationName != null){
                return false;
            }
        }
        else if(!locationName.equals(other.locationName)){
            return false;
        }
        if(longitudeDegrees == null){
            if(other.longitudeDegrees != null){
                return false;
            }
        }
        else if(!longitudeDegrees.equals(other.longitudeDegrees)){
            return false;
        }
        if(phoneNumber == null){
            if(other.phoneNumber != null){
                return false;
            }
        }
        else if(!phoneNumber.equals(other.phoneNumber)){
            return false;
        }
        return true;
    }
}
