package com.smartdevicelink.util;

import java.util.concurrent.atomic.AtomicInteger;

public class CorrelationIdGenerator {
	
	private static final int CORRELATION_ID_START = 0;
	
	private static final AtomicInteger sNextCorrelationId = new AtomicInteger(CORRELATION_ID_START);
	
		public static int generateId() {
		    for (;;) {
		        final int result = sNextCorrelationId.get();
		        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
		        int newValue = result + 1;
		        
		        if (newValue > 0x00FFFFFF){
		        	newValue = CORRELATION_ID_START; // Roll over to 0.
		        }
		        if (sNextCorrelationId.compareAndSet(result, newValue)) {
		            return result;
		        }
		    }
		}
}
