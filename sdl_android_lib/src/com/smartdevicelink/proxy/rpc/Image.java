package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.util.JsonUtils;

/**
 *Specifies, which image shall be used, e.g. in Alerts or on Softbuttons provided the display supports it.
 *<p><b>Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>value</td>
 * 			<td>String</td>
 * 			<td>Either the static hex icon value or the binary image file name identifier (sent by PutFile).
 * 					<ul>
 *					<li>Min: 0</li>
 *					<li>Max: 65535</li>
 *					</ul>
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>imageType</td>
 * 			<td>ImageType</td>
 * 			<td>Describes, whether it is a static or dynamic image.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class Image extends RPCObject {
	public static final String KEY_VALUE = "value";
	public static final String KEY_IMAGE_TYPE = "imageType";

	private String value;
	private String imageType; // represents ImageType enum
	
	/**
	 * Constructs a newly allocated Image object
	 */
    public Image() { }
    
    /**
     * Creates an Image object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public Image(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.value = JsonUtils.readStringFromJsonObject(jsonObject, KEY_VALUE);
            this.imageType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_IMAGE_TYPE);
            break;
        }
    }
    
    /**
     * set either the static hex icon value or the binary image file name identifier (sent by PutFile)
     * @param value either the static hex icon value or the binary image file name identifier (sent by PutFile)
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * get either the static hex icon value or the binary image file name identifier (sent by PutFile)
     * @return  either the static hex icon value or the binary image file name identifier (sent by PutFile)
     */
    public String getValue() {
        return this.value;
    }
    
    /**
     * set the image type
     * @param imageType whether it is a static or dynamic image
     */
    public void setImageType(ImageType imageType) {
        this.imageType = (imageType == null) ? null : imageType.getJsonName(sdlVersion);
    }
    
    /**
     * get image type
     * @return the image type
     */
    public ImageType getImageType() {
    	return ImageType.valueForJsonName(this.imageType, sdlVersion);
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_VALUE, this.value);
            JsonUtils.addToJsonObject(result, KEY_IMAGE_TYPE, this.imageType);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imageType == null) ? 0 : imageType.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		Image other = (Image) obj;
		if (imageType == null) {
			if (other.imageType != null) { 
				return false;
			}
		} else if (!imageType.equals(other.imageType)) { 
			return false;
		}
		if (value == null) {
			if (other.value != null) { 
				return false;
			}
		} else if (!value.equals(other.value)) { 
			return false;
		}
		return true;
	}
}
