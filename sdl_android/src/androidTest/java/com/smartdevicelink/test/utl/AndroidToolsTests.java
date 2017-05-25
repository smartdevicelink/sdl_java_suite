package com.smartdevicelink.test.utl;

import junit.framework.Assert;
import android.content.ComponentName;
import android.content.res.Resources;
import android.test.AndroidTestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.util.AndroidTools;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class AndroidToolsTests extends AndroidTestCase{
	
	
	public void testIsServiceExportedNormal(){
		
		try{
			AndroidTools.isServiceExported(mContext, new ComponentName(mContext, mContext.getString(R.string.test)));
		}catch(Exception e){
			Assert.fail(mContext.getString(R.string.exception_during_normal_tests) + e.getMessage());
		}
		
	}
	public void testIsServiceExportedNull(){
		
		try{
			AndroidTools.isServiceExported(mContext, null);
			Assert.fail("Processed null data");
		}catch(Exception e){
			
		}
		
	}

}
