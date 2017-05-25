package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.FileType;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.FileType}
 */
public class FileTypeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.graphic_bmp_caps);
		FileType enumGraphicBmp = FileType.valueForString(example);
		example = mContext.getString(R.string.graphic_jpeg_caps);
		FileType enumGraphicJpeg = FileType.valueForString(example);
		example = mContext.getString(R.string.graphic_png_caps);
		FileType enumGraphicPng = FileType.valueForString(example);
		example = mContext.getString(R.string.audio_wave_caps);
		FileType enumAudioWave = FileType.valueForString(example);
		example = mContext.getString(R.string.audio_aac_caps);
		FileType enumAudioAac = FileType.valueForString(example);
		example = mContext.getString(R.string.audio_mpthree_caps);
		FileType enumAudioMp3 = FileType.valueForString(example);
		example = mContext.getString(R.string.binary_caps);
		FileType enumBinary = FileType.valueForString(example);
		example = mContext.getString(R.string.json_caps);
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

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    FileType temp = FileType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    FileType temp = FileType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	/**
	 * Verifies the possible enum values of FileType.
	 */
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