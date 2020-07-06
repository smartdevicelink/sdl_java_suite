package com.smartdevicelink.transport;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

@RunWith(AndroidJUnit4.class)
public class LocalRouterServiceTests {

	private static final int TEST_WITH_CONSTRUCTOR 	= 0;
	private static final int TEST_WITH_CREATOR 		= 1;
	
	
	@Test
	public void testLocalRouterServiceParcel(){
		Parcel p =  Parcel.obtain();
		p.writeInt(4);
		p.writeLong(System.currentTimeMillis());
		p.writeParcelable(new Intent(), 0);
		p.writeParcelable(new ComponentName(getContext(), "test"), 0);
		p.setDataPosition(0);
		
		SdlRouterService.LocalRouterService local = new SdlRouterService.LocalRouterService(p);
		
		assertNotNull(local);
		assertEquals(local.version,4);
		p.recycle();
		
	}

	@Test
	public void testLocalRouterServiceParcelCreator(){
		Parcel p =  Parcel.obtain();
		p.writeInt(4);
		p.writeLong(System.currentTimeMillis());
		p.writeParcelable(new Intent(), 0);
		p.writeParcelable(new ComponentName(getContext(), "test"), 0);
		p.setDataPosition(0);
		
		SdlRouterService.LocalRouterService local = SdlRouterService.LocalRouterService.CREATOR.createFromParcel(p);
		
		assertNotNull(local);
		assertEquals(local.version,4);
		p.recycle();
		
	}

	public SdlRouterService.LocalRouterService getLocalRouterService(int testWith, Parcel p){
		if(testWith == TEST_WITH_CONSTRUCTOR){
			return new SdlRouterService.LocalRouterService(p);
		}else{
			return SdlRouterService.LocalRouterService.CREATOR.createFromParcel(p);
		}
	}
	
	public void corruptParcel(int testWith){
		Parcel p =  Parcel.obtain();
		p.writeInt(4);
		p.writeLong(System.currentTimeMillis());
		p.writeParcelable(new ComponentName(getContext(), "test"), 0);
		p.writeParcelable(new Intent(), 0);
		p.setDataPosition(0);
		
		SdlRouterService.LocalRouterService local = getLocalRouterService(testWith, p);

		assertNotNull(local);
		assertNull(local.launchIntent);
		assertNull(local.name);
		
		p.recycle();
		//---------------------------------------------------------------------------
		
		p =  Parcel.obtain();
		p.writeInt(4);
		p.writeLong(System.currentTimeMillis());
		p.writeParcelable(null,0);
		p.writeParcelable(null,0);
		p.setDataPosition(0);
		
		local = getLocalRouterService(testWith, p);

		assertNotNull(local);
		assertNull(local.launchIntent);
		assertNull(local.name);
		
		p.recycle();
		//---------------------------------------------------------------------------
		
		p =  Parcel.obtain();
		p.writeInt(4);
		p.writeLong(System.currentTimeMillis());
		p.setDataPosition(0);
		
		local = getLocalRouterService(testWith, p);

		assertNotNull(local);
		assertNull(local.launchIntent);
		assertNull(local.name);
		
		p.recycle();
		
		//---------------------------------------------------------------------------
		local = null;
		p = null;
		
		p =  Parcel.obtain();
		p.writeInt(4);
		p.writeLong(System.currentTimeMillis());
		int space = p.dataSize();
		p.writeParcelable(new Intent(), 0);
		p.writeParcelable(new ComponentName(getContext(), "test"), 0);
		p.setDataPosition(0);
		
		byte[] raw = p.marshall();
		for(;space<raw.length;space++){
			raw[space] = 0x00;
		}
		p.recycle();
		p = Parcel.obtain();
		p.unmarshall(raw, 0, raw.length);
		p.setDataPosition(0);
		
		
		local = getLocalRouterService(testWith, p);

		assertNotNull(local);
		assertNull(local.launchIntent);
		assertNull(local.name);
		
		p.recycle();
		
	}

	@Test
	public void testLocalRouterServiceCorruptParcel(){
		corruptParcel(TEST_WITH_CONSTRUCTOR);
	}

	@Test
	public void testLocalRouterServiceCorruptParcelCreator(){
		corruptParcel(TEST_WITH_CREATOR);
	}
	

}
