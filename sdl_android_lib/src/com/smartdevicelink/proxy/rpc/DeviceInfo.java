package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;

public class DeviceInfo extends RPCStruct{

	public DeviceInfo() { }
 
	public DeviceInfo(Hashtable<String, Object> hash) {
        super(hash);
    }
	
   public void setHardware(String hardware) {
        if (hardware != null) {
        	store.put(Names.hardware, hardware);
        } else {
        	store.remove(Names.hardware);
        }
   }

   public String getHardware() {
       return (String) store.get(Names.hardware);
   }
    
   public void setFirmwareRev(String firmwareRev) {
       if (firmwareRev != null) {
       	store.put(Names.firmwareRev, firmwareRev);
       } else {
       	store.remove(Names.firmwareRev);
       }
  }

  public String getFirmwareRev() {
      return (String) store.get(Names.firmwareRev);
  }

  public void setOs(String os) {
      if (os != null) {
      	store.put(Names.os, os);
      } else {
      	store.remove(Names.os);
      }
  }

 public String getOs() {
     return (String) store.get(Names.os);
 }  

 public void setOsVersion(String osVersion) {
     if (osVersion != null) {
     	store.put(Names.osVersion, osVersion);
     } else {
     	store.remove(Names.osVersion);
     }
}

public String getOsVersion() {
    return (String) store.get(Names.osVersion);
}  
    
public void setCarrier(String carrier) {
    if (carrier != null) {
    	store.put(Names.carrier, carrier);
    } else {
    	store.remove(Names.carrier);
    }
}

public String getCarrier() {
   return (String) store.get(Names.carrier);
} 

public Integer getMaxNumberRFCOMMPorts() {
    return (Integer) store.get( Names.maxNumberRFCOMMPorts );
}

public void setMaxNumberRFCOMMPorts( Integer maxNumberRFCOMMPorts ) {
    if (maxNumberRFCOMMPorts != null) {
        store.put(Names.maxNumberRFCOMMPorts, maxNumberRFCOMMPorts );
    }
    else {
    	store.remove(Names.maxNumberRFCOMMPorts);
    }    
}

}
