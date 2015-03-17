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

	public static Long objectToLong(Object obj) {
    	if (obj == null) {
    		return null;
    	}
    	Long L = null;
    	if (obj instanceof Integer) {
    		int i = ((Integer) obj).intValue();
    		long l = (long) i;
    		L = Long.valueOf(l);
    	} else if (obj instanceof Long){
			L = (Long) obj;
		}
    	
    	return L;
    }
}
