package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.FileType;

public class FileTypeTests extends TestCase {

	public void testValidEnums () {	
		String example = "GRAPHIC_BMP";
		FileType enumGraphicBmp = FileType.valueForString(example);
		example = "GRAPHIC_JPEG";
		FileType enumGraphicJpeg = FileType.valueForString(example);
		example = "GRAPHIC_PNG";
		FileType enumGraphicPng = FileType.valueForString(example);
		example = "AUDIO_WAVE";
		FileType enumAudioWave = FileType.valueForString(example);
		example = "AUDIO_AAC";
		FileType enumAudioAac = FileType.valueForString(example);
		example = "AUDIO_MP3";
		FileType enumAudioMp3 = FileType.valueForString(example);
		example = "BINARY";
		FileType enumBinary = FileType.valueForString(example);
		example = "JSON";
		FileType enumJson = FileType.valueForString(example);
		
		assertNotNull("GRAPHIC_BMP returned null", enumGraphicBmp);
		assertNotNull("GRAPHIC_JPEG returned null", enumGraphicJpeg);
		assertNotNull("GRAPHIC_PNG returned null", enumGraphicPng);
		assertNotNull("AUDIO_WAVE returned null", enumAudioWave);
		assertNotNull("AUDIO_AAC returned null", enumAudioAac);
		assertNotNull("AUDIO_MP3 returned null", enumAudioMp3);
		assertNotNull("BINARY returned null", enumBinary);
		assertNotNull("JSON returned null", enumJson);
	}
	
	public void testInvalidEnum () {
		String example = "uSer_ExiT";
		try {
			FileType.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			FileType.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<FileType> enumValueList = Arrays.asList(FileType.values());

		List<FileType> enumTestList = new ArrayList<FileType>();
		enumTestList.add(FileType.GRAPHIC_BMP);
		enumTestList.add(FileType.GRAPHIC_JPEG);
		enumTestList.add(FileType.GRAPHIC_PNG);
		enumTestList.add(FileType.AUDIO_WAVE);
		enumTestList.add(FileType.AUDIO_AAC);
		enumTestList.add(FileType.AUDIO_MP3);		
		enumTestList.add(FileType.BINARY);
		enumTestList.add(FileType.JSON);		

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
