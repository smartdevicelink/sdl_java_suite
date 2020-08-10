package com.smartdevicelink.test.utl;

import android.content.ComponentName;
import androidx.test.runner.AndroidJUnit4;

import com.smartdevicelink.util.AndroidTools;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class AndroidToolsTests {
	
	@Test
	public void testIsServiceExportedNormal(){
		
		try{
			AndroidTools.isServiceExported(getTargetContext(), new ComponentName(getTargetContext(), "test"));
		}catch(Exception e){
			Assert.fail("Exception during normal test: " + e.getMessage());
		}
		
	}
	@Test
	public void testIsServiceExportedNull(){
		
		try{
			AndroidTools.isServiceExported(getTargetContext(), null);
			Assert.fail("Proccessed null data");
		}catch(Exception e){
			
		}
		
	}

}
