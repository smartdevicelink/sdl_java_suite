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
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class ImageFieldTests extends TestCase{

    private static final int            RESOLUTION_HEIGHT    		= 1920;
    private static final int            RESOLUTION_WIDTH     		= 1080;
    private static final int            RESOLUTION_WIDTH_CHANGED	= 840;
    private static final FileType       IMAGE_TYPE_SUPPORTED 		= FileType.GRAPHIC_PNG;
    private static final ImageFieldName IMAGE_FIELD_NAME     		= ImageFieldName.choiceImage;

    private ImageField                  msg;

    private ImageResolution             imageResolution;

    @Override
    public void setUp(){
        msg = new ImageField();

        imageResolution = new ImageResolution();
        imageResolution.setResolutionHeight(RESOLUTION_HEIGHT);
        imageResolution.setResolutionWidth(RESOLUTION_WIDTH);
        msg.setImageResolution(imageResolution);
        List<FileType> list = new ArrayList<FileType>();
        list.add(IMAGE_TYPE_SUPPORTED);
        msg.setImageTypeSupported(list);
        msg.setName(IMAGE_FIELD_NAME);
    }

    public void testImageResolution(){
        ImageResolution copy = msg.getImageResolution();
        assertTrue("Input value didn't match expected value.", Validator.validateImageResolution(imageResolution, copy));
    }
    
    public void testGetImageResolution() {
    	ImageResolution copy1 = msg.getImageResolution();
    	copy1.setResolutionWidth(RESOLUTION_WIDTH_CHANGED);
    	ImageResolution copy2 = msg.getImageResolution();
    	
    	assertNotSame("Image resolution was not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateImageResolution(copy1, copy2));
    }
    
    public void testSetImageResolution() {
    	ImageResolution copy1 = msg.getImageResolution();
    	msg.setImageResolution(copy1);
    	copy1.setResolutionWidth(RESOLUTION_WIDTH_CHANGED);
    	ImageResolution copy2 = msg.getImageResolution();
    	
    	assertNotSame("Image resolution was not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateImageResolution(copy1, copy2));
    }

    public void testImageTypeSupported(){
        FileType copy = msg.getImageTypeSupported().get(0);
        
        assertEquals("FileType doesn't match expected FileType.", IMAGE_TYPE_SUPPORTED, copy);
    }

    public void testName(){
        ImageFieldName copy = msg.getName();
        assertEquals("Input value didn't match expected value.", IMAGE_FIELD_NAME, copy);
    }

    public void testJson(){
        JSONObject reference = new JSONObject();
        JSONObject imageResolutionJson = new JSONObject();

        try{
            imageResolutionJson.put(ImageResolution.KEY_RESOLUTION_HEIGHT, RESOLUTION_HEIGHT);
            imageResolutionJson.put(ImageResolution.KEY_RESOLUTION_WIDTH, RESOLUTION_WIDTH);

            reference.put(ImageField.KEY_IMAGE_RESOLUTION, imageResolutionJson);
            JSONArray imageTypesArray = new JSONArray();
            imageTypesArray.put(IMAGE_TYPE_SUPPORTED);
            reference.put(ImageField.KEY_IMAGE_TYPE_SUPPORTED, imageTypesArray);
            reference.put(ImageField.KEY_NAME, IMAGE_FIELD_NAME);
            
            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();

                if(key.equals(ImageField.KEY_IMAGE_RESOLUTION)){
                	JSONObject objectEquals = JsonUtils.readJsonObjectFromJsonObject(reference, key);
                	JSONObject testEquals = JsonUtils.readJsonObjectFromJsonObject(underTest, key);
                	Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(objectEquals);
                	Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                	
                    assertTrue("Image resolution value didn't match expected value.",
                            Validator.validateImageResolution( new ImageResolution(hashReference), new ImageResolution(hashTest)));
                }
                else if(key.equals(ImageField.KEY_IMAGE_TYPE_SUPPORTED)) {
					JSONArray imageTypeArrayReference = JsonUtils.readJsonArrayFromJsonObject(reference, key);
					JSONArray imageTypeArrayTest = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
					List<FileType> imageTypeListReference = new ArrayList<FileType>();
					List<FileType> imageTypeListTest = new ArrayList<FileType>();					
					
					assertEquals("Reference and test array sizes not equal", imageTypeArrayReference.length(), imageTypeArrayTest.length());
					
				    for (int index = 0 ; index < imageTypeArrayReference.length(); index++) {
				    	imageTypeListReference.add( (FileType)imageTypeArrayReference.get(index) );
				    	imageTypeListTest.add( (FileType)imageTypeArrayTest.get(index) );
				    }
				    
					assertTrue("Image type test list does not match reference list", 
							imageTypeListReference.containsAll(imageTypeListTest) && imageTypeListTest.containsAll(imageTypeListReference));
				    
                }
                else{
                    assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
                            JsonUtils.readObjectFromJsonObject(reference, key),
                            JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        ImageField msg = new ImageField();
        assertNotNull("Null object creation failed.", msg);

        assertNull("Image resolution wasn't set, but getter method returned an object.", msg.getImageResolution());
        assertNull("Image type supported wasn't set, but getter method returned an object.",
                msg.getImageTypeSupported());
        assertNull("Image name wasn't set, but getter method returned an object.", msg.getName());
    }
}
