package com.smartdevicelink.test;

import android.content.Context;
import android.content.SharedPreferences;

import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.transport.utl.VehicleTypeHolder;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VehicleTypeHolderTests {
    Context mMockContext;
    VehicleType mMockVehicleType;
    String mAddress;

    @Before
    public void mockVariables() throws JSONException {
        mMockContext = mock(Context.class);
        mMockVehicleType = new VehicleType();
        mMockVehicleType.setMake("Ford");
        mMockVehicleType.setTrim("GT");
        mMockVehicleType.setModel("Mustang");
        mMockVehicleType.setModelYear("2019");
        mAddress = "1234";

        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        when(editor.commit()).thenReturn(true);

        SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        when(mMockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        when(sharedPrefs.edit()).thenReturn(editor);
        when(sharedPrefs.getString(mAddress, null)).thenReturn(mMockVehicleType.serializeJSON().toString());
    }

    @Test
    public void testVehicleTypeSave() {

        VehicleTypeHolder.saveVehicleType(mMockContext, mMockVehicleType, mAddress);
        VehicleType type = new VehicleType(VehicleTypeHolder.getVehicleTypeFromPrefs(mMockContext, mAddress));

        Assert.assertEquals(type.getMake(), mMockVehicleType.getMake());
        Assert.assertEquals(type.getModel(), mMockVehicleType.getModel());
        Assert.assertEquals(type.getModelYear(), mMockVehicleType.getModelYear());
        Assert.assertEquals(type.getTrim(), mMockVehicleType.getTrim());
    }
}
