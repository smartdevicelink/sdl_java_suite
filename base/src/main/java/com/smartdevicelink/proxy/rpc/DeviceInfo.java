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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
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

    public DeviceInfo() { }

    /** Constructs a new DeviceInfo object indicated by the Hashtable
	 * parameter
	 * @param hash The hash table to use to create an instance of this RPC
	 */
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
