package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;

/**
 * Describes the type of vehicle the mobile phone is connected with.
 * <p><b>Parameter List</b></p>
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
public class VehicleType extends RPCStruct {
	public static final String KEY_MAKE = "make";
	public static final String KEY_MODEL = "model";
	public static final String KEY_MODEL_YEAR = "modelYear";
	public static final String KEY_TRIM = "trim";

	/**
	 * Constructs a newly allocated VehicleType object
	 */
    public VehicleType() { }
    
    /**
     * Constructs a newly allocated VehicleType object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public VehicleType(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    /**
     * get the make of the vehicle
     * @return the make of the vehicle
     */
    public String getMake() {
        return (String) store.get(KEY_MAKE);
    }
    
    /**
     * set the make of the vehicle
     *@param make the make of the vehicle
     */
    public void setMake(String make) {
        if (make != null) {
            store.put(KEY_MAKE, make);
        } else {
        	store.remove(KEY_MAKE);
        }
    }
    
    /**
     * get the model of the vehicle
     * @return the model of the vehicle
     */
    public String getModel() {
        return (String) store.get(KEY_MODEL);
    }
    
    /**
     * set the model of the vehicle
     * @param model the model of the vehicle
     */
    public void setModel(String model) {
        if (model != null) {
            store.put(KEY_MODEL, model);
        } else {
        	store.remove(KEY_MODEL);
        }
    }
    
    /**
     * get the model year of the vehicle
     * @return the model year of the vehicle
     */
    public String getModelYear() {
        return (String) store.get(KEY_MODEL_YEAR);
    }
    
    /**
     * set the model year of the vehicle
     * @param modelYear the model year of the vehicle
     */
    public void setModelYear(String modelYear) {
        if (modelYear != null) {
            store.put(KEY_MODEL_YEAR, modelYear);
        } else {
        	store.remove(KEY_MODEL_YEAR);
        }
    }
    
    /**
     * get the trim of the vehicle
     * @return the trim of the vehicle
     */
    public String getTrim() {
        return (String) store.get(KEY_TRIM);
    }
    
    /**
     * set the trim of the vehicle
     * @param trim the trim of the vehicle
     */
    public void setTrim(String trim) {
        if (trim != null) {
            store.put(KEY_TRIM, trim);
        } else {
        	store.remove(KEY_TRIM);
        }
    }
}
