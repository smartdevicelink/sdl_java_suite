package com.smartdevicelink.test.json.rpc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.smartdevicelink.test.JsonUtils;

public class JsonFileReader {
	private static final String PATH = "json/";
	private static final String EXT = ".json";
	
	public static JSONObject readId (Context context, String id, String type) {
		if(context == null){
			return null;
		}
		String fileName = PATH + id + EXT;
		
		JSONObject commandJsonWithType = null;

		try {
			JSONObject commandJson = null;
			//get the file which holds the desired object
			InputStream fileStream = context.getAssets().open(fileName);
			BufferedInputStream bufferStream = new BufferedInputStream(fileStream);
			byte[] buffer = new byte[(int)fileStream.available()];
			bufferStream.read(buffer);
			fileStream.close();
			commandJson = JsonUtils.createJsonObject(buffer);
			
			//now use the type parameter to get the specific message type
			//it also makes sure to return it with the message type in the top level because that's needed to eventually make this into a class object
			commandJson = JsonUtils.readJsonObjectFromJsonObject(commandJson, type);
			commandJsonWithType = new JSONObject();
			commandJsonWithType = commandJsonWithType.put(type, commandJson);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return commandJsonWithType;

	}

}
