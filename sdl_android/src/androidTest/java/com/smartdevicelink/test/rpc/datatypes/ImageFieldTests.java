package com.smartdevicelink.test.rpc.datatypes;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ImageField}
 */
public class ImageFieldTests extends TestCase{

    private ImageField msg;

    @Override
    public void setUp(){
        msg = new ImageField();

        msg.setImageResolution(Test.GENERAL_IMAGERESOLUTION);
        msg.setImageTypeSupported(Test.GENERAL_FILETYPE_LIST);
        msg.setName(Test.GENERAL_IMAGEFIELDNAME);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        ImageResolution imageRes = msg.getImageResolution();
        List<FileType> imageTypes = msg.getImageTypeSupported();
        ImageFieldName name = msg.getName();
        
        // Valid Tests
        assertTrue(Test.TRUE, Validator.validateImageResolution(Test.GENERAL_IMAGERESOLUTION, imageRes));        
        assertEquals(Test.MATCH, Test.GENERAL_IMAGEFIELDNAME, name);        
        assertTrue(Test.TRUE, Validator.validateFileTypes(Test.GENERAL_FILETYPE_LIST, imageTypes));
                
        // Invalid/Null Tests
        ImageField msg = new ImageField();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getImageResolution());
        assertNull(Test.NULL, msg.getImageTypeSupported());
        assertNull(Test.NULL, msg.getName());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ImageField.KEY_IMAGE_RESOLUTION, Test.JSON_IMAGERESOLUTION);
            reference.put(ImageField.KEY_IMAGE_TYPE_SUPPORTED, JsonUtils.createJsonArray(Test.GENERAL_FILETYPE_LIST));
            reference.put(ImageField.KEY_NAME, Test.GENERAL_IMAGEFIELDNAME);
            
            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                if(key.equals(ImageField.KEY_IMAGE_RESOLUTION)){
                	JSONObject objectEquals = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                	JSONObject testEquals = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                	Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);                	
                    assertTrue(Test.TRUE, Validator.validateImageResolution( new ImageResolution(hashReference), new ImageResolution(hashTest)));
                } else if(key.equals(ImageField.KEY_IMAGE_TYPE_SUPPORTED)) {
					JSONArray imageTypeArrayReference = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					JSONArray imageTypeArrayTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					List<FileType> imageTypeListReference = new ArrayList<FileType>();
					List<FileType> imageTypeListTest = new ArrayList<FileType>();					
					
					assertEquals(Test.MATCH, imageTypeArrayReference.length(), imageTypeArrayTest.length());
					
				    for (int index = 0 ; index < imageTypeArrayReference.length(); index++) {
				    	imageTypeListReference.add( (FileType)imageTypeArrayReference.get(index) );
				    	imageTypeListTest.add( (FileType)imageTypeArrayTest.get(index) );
				    }				    
					assertTrue(Test.TRUE, imageTypeListReference.containsAll(imageTypeListTest) && imageTypeListTest.containsAll(imageTypeListReference));
				} else{
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}