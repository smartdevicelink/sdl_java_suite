package com.smartdevicelink.test.utl;

import android.content.ComponentName;
import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.util.AndroidTools;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;

@RunWith(AndroidJUnit4.class)
public class AndroidToolsTests {
	
	@Test
	public void testIsServiceExportedNormal(){
		
		try{
			AndroidTools.isServiceExported(getContext(), new ComponentName(getContext(), "test"));
		}catch(Exception e){
			Assert.fail("Exception during normal test: " + e.getMessage());
		}
		
	}
	@Test
	public void testIsServiceExportedNull(){
		
		try{
			AndroidTools.isServiceExported(getContext(), null);
			Assert.fail("Proccessed null data");
		}catch(Exception e){
			
		}
		
	}

}
