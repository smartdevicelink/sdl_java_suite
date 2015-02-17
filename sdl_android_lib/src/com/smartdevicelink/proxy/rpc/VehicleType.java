package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

/**
 * Describes the type of vehicle the mobile phone is connected with.
 * <p><b>Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>make</td>
 * 			<td>String</td>
 * 			<td>Make of the vehicle
 *				 <ul>
 *					<li>Maxlength = 500</li>
 *				 </ul> 
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>Model</td>
 * 			<td>String</td>
 * 			<td>Model of the vehicle, e.g. Fiesta
 *				 <ul>
 *					<li>Maxlength = 500</li>
 *				 </ul> 
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>modelYear</td>
 * 			<td>String</td>
 * 			<td>Model Year of the vehicle, e.g. 2013
 *				 <ul>
 *					<li>Maxlength = 500</li>
 *				 </ul> 
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>trim</td>
 * 			<td>String</td>
 * 			<td>Trim of the vehicle, e.g. SE
 *				 <ul>
 *					<li>Maxlength = 500</li>
 *				 </ul> 
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class VehicleType extends RPCObject {
	public static final String KEY_MAKE = "make";
	public static final String KEY_MODEL = "model";
	public static final String KEY_MODEL_YEAR = "modelYear";
	public static final String KEY_TRIM = "trim";

	private String make, model, modelYear, trim;
	
	/**
	 * Constructs a newly allocated VehicleType object
	 */
    public VehicleType() { }
    
    /**
     * Creates a VehicleType object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public VehicleType(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.make = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MAKE);
            this.model = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MODEL);
            this.modelYear = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MODEL_YEAR);
            this.trim = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TRIM);
            break;
        }
    }
    
    /**
     * get the make of the vehicle
     * @return the make of the vehicle
     */
    public String getMake() {
        return this.make;
    }
    
    /**
     * set the make of the vehicle
     *@param make the make of the vehicle
     */
    public void setMake(String make) {
        this.make = make;
    }
    
    /**
     * get the model of the vehicle
     * @return the model of the vehicle
     */
    public String getModel() {
        return this.model;
    }
    
    /**
     * set the model of the vehicle
     * @param model the model of the vehicle
     */
    public void setModel(String model) {
        this.model = model;
    }
    
    /**
     * get the model year of the vehicle
     * @return the model year of the vehicle
     */
    public String getModelYear() {
        return this.modelYear;
    }
    
    /**
     * set the model year of the vehicle
     * @param modelYear the model year of the vehicle
     */
    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }
    
    /**
     * get the trim of the vehicle
     * @return the trim of the vehicle
     */
    public String getTrim() {
        return this.trim;
    }
    
    /**
     * set the trim of the vehicle
     * @param trim the trim of the vehicle
     */
    public void setTrim(String trim) {
        this.trim = trim;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_MAKE, this.make);
            JsonUtils.addToJsonObject(result, KEY_MODEL, this.model);
            JsonUtils.addToJsonObject(result, KEY_MODEL_YEAR, this.modelYear);
            JsonUtils.addToJsonObject(result, KEY_TRIM, this.trim);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((modelYear == null) ? 0 : modelYear.hashCode());
		result = prime * result + ((trim == null) ? 0 : trim.hashCode());
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
		VehicleType other = (VehicleType) obj;
		if (make == null) {
			if (other.make != null) { 
				return false;
			}
		} 
		else if (!make.equals(other.make)) { 
			return false;
		}
		if (model == null) {
			if (other.model != null) { 
				return false;
			}
		} 
		else if (!model.equals(other.model)) { 
			return false;
		}
		if (modelYear == null) {
			if (other.modelYear != null) { 
				return false;
			}
		} 
		else if (!modelYear.equals(other.modelYear)) { 
			return false;
		}
		if (trim == null) {
			if (other.trim != null) { 
				return false;
			}
		} 
		else if (!trim.equals(other.trim)) { 
			return false;
		}
		return true;
	}
}
