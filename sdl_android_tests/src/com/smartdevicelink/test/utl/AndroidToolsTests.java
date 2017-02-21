package com.smartdevicelink.test.utl;

import junit.framework.Assert;
import android.content.ComponentName;
import android.test.AndroidTestCase;

import com.smartdevicelink.util.AndroidTools;

public class AndroidToolsTests extends AndroidTestCase{
	
	
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
