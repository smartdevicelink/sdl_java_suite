package com.smartdevicelink.test.json.rpc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.test.JsonUtils;

public class JsonFileReader {
	private static final String PATH = "src/com/smartdevicelink/test/json/rpc/";
	private static final String EXT = ".json";
	
	public static JSONObject readId (String id, String type) {
		String fileName = id + EXT;
		File file = new File(PATH + fileName);

		JSONObject commandJsonWithType = null;

		try {
			JSONObject commandJson = null;
			//get the file which holds the desired object
			FileInputStream fileStream = new FileInputStream(file);
			BufferedInputStream bufferStream = new BufferedInputStream(fileStream);
			byte[] buffer = new byte[(int)file.length()];
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
