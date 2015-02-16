package com.smartdevicelink.util;

public class SdlDataTypeConverter {
	
	public static Double objectToDouble(Object obj) {
    	if (obj == null) {
    		return null;
    	}
		Double D = null;		
    	if (obj instanceof Integer) {
    		int i = ((Integer) obj).intValue();
    		double d = (double) i;
    		D = Double.valueOf(d);
    	} else if (obj instanceof Double){
			D = (Double) obj;
		}
    	
    	return D;
    }

}
