package com.smartdevicelink.test.utl;

import android.content.ComponentName;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.util.AndroidTools;

import junit.framework.Assert;

public class AndroidToolsTests extends AndroidTestCase2 {
	
	
	public void testIsServiceExportedNormal(){
		
		try{
			AndroidTools.isServiceExported(mContext, new ComponentName(mContext, "test"));
		}catch(Exception e){
			Assert.fail("Exception during normal test: " + e.getMessage());
		}
		
	}
	public void testIsServiceExportedNull(){
		
		try{
			AndroidTools.isServiceExported(mContext, null);
			Assert.fail("Proccessed null data");
		}catch(Exception e){
			
		}
		
	}

}
