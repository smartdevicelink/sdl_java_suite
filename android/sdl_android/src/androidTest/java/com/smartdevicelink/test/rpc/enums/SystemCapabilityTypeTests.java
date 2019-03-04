package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType}
 */
public class SystemCapabilityTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "NAVIGATION";
		SystemCapabilityType enumNavigation = SystemCapabilityType.valueForString(example);
		example = "PHONE_CALL";
		SystemCapabilityType enumPhoneCall = SystemCapabilityType.valueForString(example);
		example = "VIDEO_STREAMING";
		SystemCapabilityType enumVideoStreaming = SystemCapabilityType.valueForString(example);
		example = "REMOTE_CONTROL";
		SystemCapabilityType enumRemoteControl = SystemCapabilityType.valueForString(example);
		example = "HMI";
		SystemCapabilityType enumHmi = SystemCapabilityType.valueForString(example);
		example = "DISPLAY";
		SystemCapabilityType enumDisplay = SystemCapabilityType.valueForString(example);
		example = "AUDIO_PASSTHROUGH";
		SystemCapabilityType enumAudioPassThrough = SystemCapabilityType.valueForString(example);
		example = "BUTTON";
		SystemCapabilityType enumButton = SystemCapabilityType.valueForString(example);
		example = "HMI_ZONE";
		SystemCapabilityType enumHmiZone = SystemCapabilityType.valueForString(example);
		example = "PRESET_BANK";
		SystemCapabilityType enumPresetBank = SystemCapabilityType.valueForString(example);
		example = "SOFTBUTTON";
		SystemCapabilityType enumSoftButton = SystemCapabilityType.valueForString(example);
		example = "SPEECH";
		SystemCapabilityType enumSpeech = SystemCapabilityType.valueForString(example);
		example = "VOICE_RECOGNITION";
		SystemCapabilityType enumVoiceRecognition = SystemCapabilityType.valueForString(example);
		example = "PCM_STREAMING";
		SystemCapabilityType enumPCM = SystemCapabilityType.valueForString(example);
		example = "APP_SERVICES";
		SystemCapabilityType enumAppServices = SystemCapabilityType.valueForString(example);

		assertNotNull("NAVIGATION returned null", enumNavigation);
		assertNotNull("PHONE_CALL returned null", enumPhoneCall);
		assertNotNull("VIDEO_STREAMING returned null", enumVideoStreaming);
		assertNotNull("REMOTE_CONTROL returned null", enumRemoteControl);
		assertNotNull("HMI returned null", enumHmi);
		assertNotNull("DISPLAY returned null", enumDisplay);
		assertNotNull("AUDIO_PASSTHROUGH returned null", enumAudioPassThrough);
		assertNotNull("BUTTON returned null", enumButton);
		assertNotNull("HMI_ZONE returned null", enumHmiZone);
		assertNotNull("PRESET_BANK returned null", enumPresetBank);
		assertNotNull("SOFTBUTTON returned null", enumSoftButton);
		assertNotNull("SPEECH returned null", enumSpeech);
		assertNotNull("VOICE_RECOGNITION returned null", enumVoiceRecognition);
		assertNotNull("PCM_STREAMING", enumPCM);
		assertNotNull("APP_SERVICES", enumAppServices);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "nAVIGATION";
		try {
			SystemCapabilityType temp = SystemCapabilityType.valueForString(example);
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
			SystemCapabilityType temp = SystemCapabilityType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of SystemCapabilityType.
	 */
	public void testListEnum() {
 		List<SystemCapabilityType> enumValueList = Arrays.asList(SystemCapabilityType.values());

		List<SystemCapabilityType> enumTestList = new ArrayList<SystemCapabilityType>();
		enumTestList.add(SystemCapabilityType.NAVIGATION);
		enumTestList.add(SystemCapabilityType.PHONE_CALL);
		enumTestList.add(SystemCapabilityType.VIDEO_STREAMING);
		enumTestList.add(SystemCapabilityType.REMOTE_CONTROL);
		enumTestList.add(SystemCapabilityType.HMI);
		enumTestList.add(SystemCapabilityType.DISPLAY);
		enumTestList.add(SystemCapabilityType.AUDIO_PASSTHROUGH);
		enumTestList.add(SystemCapabilityType.BUTTON);
		enumTestList.add(SystemCapabilityType.HMI_ZONE);
		enumTestList.add(SystemCapabilityType.PRESET_BANK);
		enumTestList.add(SystemCapabilityType.SOFTBUTTON);
		enumTestList.add(SystemCapabilityType.SPEECH);
		enumTestList.add(SystemCapabilityType.VOICE_RECOGNITION);
		enumTestList.add(SystemCapabilityType.PCM_STREAMING);
		enumTestList.add(SystemCapabilityType.APP_SERVICES);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}