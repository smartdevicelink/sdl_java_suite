package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
/**
 * Various information about connecting device.
 * 
 * 
 * 
 * <p><b>Parameter List</b></p>
 * 
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>hardware</td>
 * 			<td>String</td>
 * 			<td>Device model</td>
 *                 <td>N</td>
 *                 <td>Max Length: 500</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>firmwareRev</td>
 * 			<td>String</td>
 * 			<td>Device firmware revision</td>
 *                 <td>N</td>
 *                 <td>Max Length: 500</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>os</td>
 * 			<td>String</td>
 * 			<td>Device OS version</td>
 *                 <td>N</td>
 *                 <td>Max Length: 500</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>osVersion</td>
 * 			<td>String</td>
 * 			<td>Device OS version</td>
 *                 <td>N</td>
 *                 <td>Max Length: 500</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>carrier</td>
 * 			<td>String</td>
 * 			<td>Device mobile carrier (if applicable)</td>
 *                 <td>N</td>
 *                 <td>Max Length: 500</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>maxNumberRFCOMMPorts</td>
 * 			<td>Integer</td>
 * 			<td>Omitted if connected not via BT.</td>
 *                 <td>N</td>
 *                 <td>Min Value: 0; Max Value: 100</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 *
 */
public class DeviceInfo extends RPCStruct{
    public static final String KEY_HARDWARE = "hardware";
    public static final String KEY_FIRMWARE_REV = "firmwareRev";
    public static final String KEY_OS = "os";
    public static final String KEY_OS_VERSION = "osVersion";
    public static final String KEY_CARRIER = "carrier";
    public static final String KEY_MAX_NUMBER_RFCOMM_PORTS = "maxNumberRFCOMMPorts";
    public static final String DEVICE_OS = "Android";
    
	/** Constructs a new DeviceInfo object indicated by the Hashtable
	 * parameter
	 * @param hash
	 * 
	 * The hash table to use
	 * 
	 *
	 */

	public DeviceInfo() { }
 
	public DeviceInfo(Hashtable<String, Object> hash) {
        super(hash);
    }

   public void setHardware(String hardware) {
        setValue(KEY_HARDWARE, hardware);
   }

   public String getHardware() {
       return getString(KEY_HARDWARE);
   }
    
   public void setFirmwareRev(String firmwareRev) {
       setValue(KEY_FIRMWARE_REV, firmwareRev);
  }

  public String getFirmwareRev() {
      return getString(KEY_FIRMWARE_REV);
  }

  public void setOs(String os) {
      setValue(KEY_OS, os);
  }

 public String getOs() {
     return getString(KEY_OS);
 }  

 public void setOsVersion(String osVersion) {
     setValue(KEY_OS_VERSION, osVersion);
}

public String getOsVersion() {
    return getString(KEY_OS_VERSION);
}  
    
public void setCarrier(String carrier) {
    setValue(KEY_CARRIER, carrier);
}

public String getCarrier() {
   return getString(KEY_CARRIER);
} 

public Integer getMaxNumberRFCOMMPorts() {
    return getInteger( KEY_MAX_NUMBER_RFCOMM_PORTS );
}

public void setMaxNumberRFCOMMPorts( Integer maxNumberRFCOMMPorts ) {
    setValue(KEY_MAX_NUMBER_RFCOMM_PORTS, maxNumberRFCOMMPorts);
}

}
