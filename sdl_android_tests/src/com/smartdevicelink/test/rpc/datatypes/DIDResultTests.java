package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.DIDResult;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.test.utils.JsonUtils;

public class DIDResultTests extends TestCase{

    public static final VehicleDataResultCode RESULT_CODE = VehicleDataResultCode.SUCCESS;
    public static final String                DATA        = "aglghelgb3l2389hgal3tb34l";

    private DIDResult                         msg;

    @Override
    public void setUp(){
        msg = new DIDResult();

        msg.setData(DATA);
        msg.setResultCode(RESULT_CODE);
    }

    public void testData(){
        String copy = msg.getData();
        assertEquals("Input value didn't match expected value.", DATA, copy);
    }

    public void testResultCode(){
        VehicleDataResultCode copy = msg.getResultCode();
        assertEquals("Input value didn't match expected value.", RESULT_CODE, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(DIDResult.KEY_DATA, DATA);
            reference.put(DIDResult.KEY_RESULT_CODE, RESULT_CODE);

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
                        JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        DIDResult msg = new DIDResult();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Data wasn't set, but getter method returned an object.", msg.getData());
        assertNull("Result code wasn't set, but getter method returned an object.", msg.getResultCode());
    }
  //TODO: remove testCopy()?
    /*
    public void testCopy(){
        DIDResult copy = new DIDResult(msg);

        assertNotSame("Object was not copied.", copy, msg);

        String error = "Object data was not copied correctly.";
        assertEquals(error, copy.getData(), msg.getData());
        assertEquals(error, copy.getResultCode(), msg.getResultCode());
    }
    */
}
