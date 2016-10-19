package com.smartdevicelink.test.proxy;

import java.util.Vector;

import junit.framework.TestCase;
import android.telephony.TelephonyManager;

import com.smartdevicelink.proxy.RPCRequestFactory;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.ChangeRegistration;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.DeviceInfo;
import com.smartdevicelink.proxy.rpc.EndAudioPassThru;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThru;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.RegisterAppInterface;
import com.smartdevicelink.proxy.rpc.ScrollableMessage;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SetAppIcon;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimer;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.Slider;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.Speak;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleData;
import com.smartdevicelink.proxy.rpc.SystemRequest;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.UnregisterAppInterface;
import com.smartdevicelink.proxy.rpc.UnsubscribeButton;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleData;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;
import com.smartdevicelink.test.NullValues;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.RPCRequestFactory}
 */
public class RPCRequestFactoryTests extends TestCase {

	public void testBuildSystemRequest () {
		
		String         testData;
		Integer        testInt;
		SystemRequest  testBSR;
		Vector<String> testVData;
		
		// Test -- buildSystemRequest(String data, Integer correlationID)
		testData = "test";
		testInt  = 0;
		testBSR  = RPCRequestFactory.buildSystemRequest(testData, testInt);
		assertNotNull(Test.NOT_NULL, testBSR.getBulkData());
		assertEquals(Test.MATCH, testInt, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequest(testData, null);
		assertNull(Test.NULL, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequest(null, testInt);
		assertNull(Test.NULL, testBSR);
				
		// Test -- buildSystemRequestLegacy(Vector<String> data, Integer correlationID)
		testVData = new Vector<String>();
		testVData.add("Test A");
		testVData.add("Test B");
		testVData.add("Test C");
		testBSR   = RPCRequestFactory.buildSystemRequestLegacy(testVData, testInt);
		assertEquals(Test.MATCH, testVData, new Vector<String>(testBSR.getLegacyData()));
		assertEquals(Test.MATCH, testInt, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequestLegacy(testVData, null);
		assertNull(Test.NULL, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequestLegacy(null, testInt);
		assertNull(Test.NULL, testBSR);
		
		// Issue #166 -- Null values within the Vector<String> parameter.
		// TODO: Once resolved, add the following test.
		//testVData = new Vector<String>();
		//testVData.add("Test A");
		//testVData.add("Test B");
		//testVData.add(null);
		//testBSR   = RPCRequestFactory.buildSystemRequestLegacy(testVData, testInt);
		//assertNull(Test.NULL, testBSR);		
	}
	
	public void testBuildAddCommand () {
		
		Image          testImage;
		String         testMenuText, testIconValue;		
		Integer        testCommandID, testParentID, testPosition, testCorrelationID;
		ImageType      testIconType;
		AddCommand     testBAC;
		Vector<String> testVrCommands;
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position,Vector<String> vrCommands, Image cmdIcon, Integer correlationID)
		testImage         = new Image();
		testMenuText      = "menu";
		testPosition      = 1;
		testParentID      = 2;
		testCommandID     = 3;
		testCorrelationID = 4;
		testVrCommands    = new Vector<String>();
		testImage.setImageType(ImageType.STATIC);
		testImage.setValue("image");
		testVrCommands.add("Test A");
		testVrCommands.add("Test B");
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testParentID, testPosition, testVrCommands, testImage, testCorrelationID);
		assertEquals(Test.MATCH, testCommandID, testBAC.getCmdID());
		assertEquals(Test.MATCH, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(Test.MATCH, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(Test.MATCH, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(Test.MATCH, testVrCommands, testBAC.getVrCommands());
		assertTrue(Test.TRUE, Validator.validateImage(testImage, testBAC.getCmdIcon()));
		assertEquals(Test.MATCH, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null, null);
		assertNull(Test.NULL, testBAC.getCmdID());
		assertNull(Test.NULL, testBAC.getMenuParams());
		assertNull(Test.NULL, testBAC.getVrCommands());
		assertNull(Test.NULL, testBAC.getCmdIcon());
		assertNull(Test.NULL, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position, Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationID)
		testIconValue = "icon";
		testIconType  = ImageType.STATIC;
		testImage     = new Image();
		testImage.setValue(testIconValue);
		testImage.setImageType(testIconType);
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testParentID, testPosition, testVrCommands, testIconValue, testIconType, testCorrelationID);
		assertEquals(Test.MATCH, testCommandID, testBAC.getCmdID());
		assertEquals(Test.MATCH, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(Test.MATCH, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(Test.MATCH, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(Test.MATCH, testVrCommands, testBAC.getVrCommands());
		assertEquals(Test.MATCH, testCorrelationID, testBAC.getCorrelationID());		
		assertTrue(Test.TRUE, Validator.validateImage(testImage, testBAC.getCmdIcon()));
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null, null, null);
		assertNull(Test.NULL, testBAC.getCmdID());
		assertNull(Test.NULL, testBAC.getMenuParams());
		assertNull(Test.NULL, testBAC.getVrCommands());
		assertNull(Test.NULL, testBAC.getCmdIcon());
		assertNull(Test.NULL, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testParentID, testPosition, testVrCommands, testCorrelationID);
		assertEquals(Test.MATCH, testCommandID, testBAC.getCmdID());
		assertEquals(Test.MATCH, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(Test.MATCH, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(Test.MATCH, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(Test.MATCH, testVrCommands, testBAC.getVrCommands());
		assertEquals(Test.MATCH, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null);
		assertNull(Test.NULL, testBAC.getCmdID());
		assertNull(Test.NULL, testBAC.getMenuParams());
		assertNull(Test.NULL, testBAC.getVrCommands());
		assertNull(Test.NULL, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testVrCommands, testCorrelationID);
		assertEquals(Test.MATCH, testCommandID, testBAC.getCmdID());
		assertEquals(Test.MATCH, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(Test.MATCH, testVrCommands, testBAC.getVrCommands());
		assertEquals(Test.MATCH, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null);
		assertNull(Test.NULL, testBAC.getCmdID());
		assertNull(Test.NULL, testBAC.getMenuParams());
		assertNull(Test.NULL, testBAC.getVrCommands());
		assertNull(Test.NULL, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testVrCommands, testCorrelationID);
		assertEquals(Test.MATCH, testCommandID, testBAC.getCmdID());
		assertEquals(Test.MATCH, testVrCommands, testBAC.getVrCommands());
		assertEquals(Test.MATCH, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null);
		assertNull(Test.NULL, testBAC.getCmdID());
		assertNull(Test.NULL, testBAC.getVrCommands());
		assertNull(Test.NULL, testBAC.getCorrelationID());
	}
	
	public void testBuildAddSubMenu () {
		
		Integer    testMenuID, testCorrelationID, testPosition;
		String     testMenuName;
		AddSubMenu testBASM;
		
		// Test -- buildAddSubMenu(Integer menuID, String menuName, Integer correlationID)
		// ^ Calls another build method.
		// Test -- buildAddSubMenu(Integer menuID, String menuName, Integer position, Integer correlationID)
		testMenuID        = 0;
		testMenuName      = "name";
		testPosition      = 1;
		testCorrelationID = 2;
		testBASM = RPCRequestFactory.buildAddSubMenu(testMenuID, testMenuName, testPosition, testCorrelationID);
		assertEquals(Test.MATCH, testMenuID, testBASM.getMenuID());
		assertEquals(Test.MATCH, testMenuName, testBASM.getMenuName());
		assertEquals(Test.MATCH, testPosition, testBASM.getPosition());
		assertEquals(Test.MATCH, testCorrelationID, testBASM.getCorrelationID());
		
		testBASM = RPCRequestFactory.buildAddSubMenu(null, null, null, null);
		assertNull(Test.NULL, testBASM.getMenuID());
		assertNull(Test.NULL, testBASM.getMenuName());
		assertNull(Test.NULL, testBASM.getPosition());
		assertNull(Test.NULL, testBASM.getCorrelationID());
	}
	
	public void testBuildAlert () {
		
		Alert              testAlert;
		String             testTTSText, testAlertText1, testAlertText2, testAlertText3;
		Integer            testCorrelationID, testDuration;
		Boolean            testPlayTone;
		Vector<SoftButton> testSoftButtons;
		Vector<TTSChunk>   testTtsChunks;
				
		// Test -- buildAlert(String ttsText, Boolean playTone, Vector<SoftButton> softButtons, Integer correlationID)
		testTTSText       = "simple test";
		testCorrelationID = 0;
		testPlayTone      = true;
		testSoftButtons   = new Vector<SoftButton>();
		SoftButton test1  = new SoftButton();
		test1.setText("test 1");
		SoftButton test2  = new SoftButton();
		test2.setText("test 2");
		testSoftButtons.add(test1);
		testSoftButtons.add(test2);		
		testAlert = RPCRequestFactory.buildAlert(testTTSText, testPlayTone, testSoftButtons, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(TTSChunkFactory.createSimpleTTSChunks(testTTSText), testAlert.getTtsChunks()));
		// ^ Calls another build method.
		
		// Test -- buildAlert(String alertText1, String alertText2, String alertText3, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testAlertText1 = "test 1";
		testAlertText2 = "test 2";
		testAlertText3 = "test 3";
		testDuration   = 1;	
		// ^ Calls another build method.
		
		// Test -- buildAlert(String ttsText, String alertText1, String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testAlert = RPCRequestFactory.buildAlert(testTTSText, testAlertText1, testAlertText2, testAlertText3, testPlayTone, testDuration, testSoftButtons, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(TTSChunkFactory.createSimpleTTSChunks(testTTSText), testAlert.getTtsChunks()));
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> chunks, Boolean playTone, Vector<SoftButton> softButtons, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> ttsChunks, String alertText1, String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testTtsChunks = TTSChunkFactory.createSimpleTTSChunks(testTTSText);
		testAlert = RPCRequestFactory.buildAlert(testTtsChunks, testAlertText1, testAlertText2, testAlertText3, testPlayTone, testDuration, testSoftButtons, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testTtsChunks, testAlert.getTtsChunks()));
		assertEquals(Test.MATCH, testAlertText1, testAlert.getAlertText1());
		assertEquals(Test.MATCH, testAlertText2, testAlert.getAlertText2());
		assertEquals(Test.MATCH, testAlertText3, testAlert.getAlertText3());
		assertEquals(Test.MATCH, testPlayTone, testAlert.getPlayTone());
		assertEquals(Test.MATCH, testDuration, testAlert.getDuration());
		assertTrue(Test.TRUE, Validator.validateSoftButtons(testSoftButtons, testAlert.getSoftButtons()));
		assertEquals(Test.MATCH, testCorrelationID, testAlert.getCorrelationID());
		
		testAlert = RPCRequestFactory.buildAlert((Vector<TTSChunk>) null, null, null, null, null, null, null, null);
		assertNull(Test.NULL, testAlert.getTtsChunks());
		assertNull(Test.NULL, testAlert.getAlertText1());
		assertNull(Test.NULL, testAlert.getAlertText2());
		assertNull(Test.NULL, testAlert.getAlertText3());
		assertNull(Test.NULL, testAlert.getPlayTone());
		assertNull(Test.NULL, testAlert.getDuration());
		assertNull(Test.NULL, testAlert.getSoftButtons());
		assertNull(Test.NULL, testAlert.getCorrelationID());
		
		// Test -- buildAlert(String ttsText, Boolean playTone, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(String alertText1, String alertText2, Integer duration, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(String ttsText, String alertText1, String alertText2, Boolean playTone, Integer duration, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> chunks, Boolean playTone, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> ttsChunks, String alertText1, String alertText2, Boolean playTone, Integer duration, Integer correlationID)
		testAlert = RPCRequestFactory.buildAlert(testTtsChunks, testAlertText1, testAlertText2, testPlayTone, testDuration, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testTtsChunks, testAlert.getTtsChunks()));
		assertEquals(Test.MATCH, testAlertText1, testAlert.getAlertText1());
		assertEquals(Test.MATCH, testAlertText2, testAlert.getAlertText2());
		assertEquals(Test.MATCH, testPlayTone, testAlert.getPlayTone());
		assertEquals(Test.MATCH, testDuration, testAlert.getDuration());
		assertEquals(Test.MATCH, testCorrelationID, testAlert.getCorrelationID());
		
		testAlert = RPCRequestFactory.buildAlert((Vector<TTSChunk>) null, null, null, null, null, null);
		assertNull(Test.NULL, testAlert.getTtsChunks());
		assertNull(Test.NULL, testAlert.getAlertText1());
		assertNull(Test.NULL, testAlert.getAlertText2());
		assertNull(Test.NULL, testAlert.getPlayTone());
		assertNull(Test.NULL, testAlert.getDuration());
		assertNull(Test.NULL, testAlert.getCorrelationID());
	}
	
	public void testBuildCreateInteractionChoiceSet () {
		
		Integer testICSID, testCorrelationID;
		Vector<Choice> testChoiceSet;
		CreateInteractionChoiceSet testBCICS;
		
		// Test --buildCreateInteractionChoiceSet(Vector<Choice> choiceSet, Integer interactionChoiceSetID, Integer correlationID)
		testICSID = 0;
		testCorrelationID = 1;
		testChoiceSet = new Vector<Choice>();
		Choice testChoice = new Choice();
		testChoiceSet.add(testChoice);
		testBCICS = RPCRequestFactory.buildCreateInteractionChoiceSet(testChoiceSet, testICSID, testCorrelationID);
		assertEquals(Test.MATCH, testChoiceSet, testBCICS.getChoiceSet());
		assertEquals(Test.MATCH, testICSID, testBCICS.getInteractionChoiceSetID());
		assertEquals(Test.MATCH, testCorrelationID, testBCICS.getCorrelationID());
		
		testBCICS = RPCRequestFactory.buildCreateInteractionChoiceSet(null, null, null);
		assertNull(Test.NULL, testBCICS.getChoiceSet());
		assertNull(Test.NULL, testBCICS.getInteractionChoiceSetID());
		assertNull(Test.NULL, testBCICS.getCorrelationID());
	}
	
	public void testBuildDeleteCommand () {
		
		Integer testCID, testCorrelationID;
		DeleteCommand testDC;
		
		// Test -- buildDeleteCommand(Integer commandID, Integer correlationID)
		testCID = 0;
		testCorrelationID = 1;
		testDC = RPCRequestFactory.buildDeleteCommand(testCID, testCorrelationID);
		assertEquals(Test.MATCH, testCID, testDC.getCmdID());
		assertEquals(Test.MATCH, testCorrelationID, testDC.getCorrelationID());
		
		testDC = RPCRequestFactory.buildDeleteCommand(null, null);
		assertNull(Test.NULL, testDC.getCmdID());
		assertNull(Test.NULL, testDC.getCorrelationID());
		
	}
	
	public void testBuildDeleteFile () {
		
		Integer testCorrelationID;
		String testFileName;
		DeleteFile testDF;
		
		// Test --buildDeleteFile(String sdlFileName, Integer correlationID)
		testCorrelationID = 0;
		testFileName = "test";
		testDF = RPCRequestFactory.buildDeleteFile(testFileName, testCorrelationID);
		assertEquals(Test.MATCH, testCorrelationID, testDF.getCorrelationID());
		assertEquals(Test.MATCH, testFileName, testDF.getSdlFileName());
		
		testDF = RPCRequestFactory.buildDeleteFile(null, null);
		assertNull(Test.NULL, testDF.getCorrelationID());
		assertNull(Test.NULL, testDF.getSdlFileName());
		
	}
	
	public void testBuildDeleteInteractionChoiceSet () {
		
		Integer testICSID, testCorrelationID;
		DeleteInteractionChoiceSet testDICS;
		
		// Test -- buildDeleteInteractionChoiceSet(Integer interactionChoiceSetID, Integer correlationID)
		testICSID = 0;
		testCorrelationID = 1;
		testDICS = RPCRequestFactory.buildDeleteInteractionChoiceSet(testICSID, testCorrelationID);
		assertEquals(Test.MATCH, testICSID, testDICS.getInteractionChoiceSetID());
		assertEquals(Test.MATCH, testCorrelationID, testDICS.getCorrelationID());
		
		testDICS = RPCRequestFactory.buildDeleteInteractionChoiceSet(null, null);
		assertNull(Test.NULL, testDICS.getInteractionChoiceSetID());
		assertNull(Test.NULL, testDICS.getCorrelationID());
	}
	
	public void testBuildDeleteSubMenu () {
		
		Integer testMenuID, testCorrelationID;
		DeleteSubMenu testDSM;
		
		// Test -- buildDeleteSubMenu(Integer menuID, Integer correlationID)
		testMenuID = 0;
		testCorrelationID = 1;
		testDSM = RPCRequestFactory.buildDeleteSubMenu(testMenuID, testCorrelationID);
		assertEquals(Test.MATCH, testMenuID, testDSM.getMenuID());
		assertEquals(Test.MATCH, testCorrelationID, testDSM.getCorrelationID());
		
		testDSM = RPCRequestFactory.buildDeleteSubMenu(null, null);
		assertNull(Test.NULL, testDSM.getMenuID());
		assertNull(Test.NULL, testDSM.getCorrelationID());
	}
	
	public void testBuildListFiles () {
		
		Integer testCorrelationID = 0;
		ListFiles testLF;
		
		// Test -- buildListFiles(Integer correlationID)
		testLF = RPCRequestFactory.buildListFiles(testCorrelationID);
		assertEquals(Test.MATCH, testCorrelationID, testLF.getCorrelationID());
				
		testLF = RPCRequestFactory.buildListFiles(null);
		assertNull(Test.NULL, testLF.getCorrelationID());
	}
	
	@SuppressWarnings("deprecation")
	public void testBuildPerformInteraction () {
		
		String testDisplayText = "test";
		Integer testTimeout = 1, testCorrelationID = 0;
		InteractionMode testIM = InteractionMode.BOTH;
		Vector<TTSChunk> testInitChunks, testHelpChunks, testTimeoutChunks;
		Vector<Integer> testCSIDs;
		Vector<VrHelpItem> testVrHelpItems;
		PerformInteraction testPI;
		
		// Test -- buildPerformInteraction(Vector<TTSChunk> initChunks, String displayText, Vector<Integer> interactionChoiceSetIDList, Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp, Integer correlationID)
		testInitChunks    = TTSChunkFactory.createSimpleTTSChunks("init chunks");
		testHelpChunks    = TTSChunkFactory.createSimpleTTSChunks("help items");
		testTimeoutChunks = TTSChunkFactory.createSimpleTTSChunks("timeout");
		testVrHelpItems = new Vector<VrHelpItem>();
		VrHelpItem testItem = new VrHelpItem();
		testItem.setPosition(0);
		testItem.setText("text");
		Image image = new Image();
		image.setValue("value");
		image.setImageType(ImageType.DYNAMIC);
		testItem.setImage(image);
		testVrHelpItems.add(testItem);
		testCSIDs = new Vector<Integer>();
		testCSIDs.add(0);
		testCSIDs.add(1);		
		testPI = RPCRequestFactory.buildPerformInteraction(testInitChunks, testDisplayText, testCSIDs, testHelpChunks, testTimeoutChunks, testIM, testTimeout, testVrHelpItems, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testInitChunks, testPI.getInitialPrompt()));
		assertEquals(Test.MATCH, testDisplayText, testPI.getInitialText());
		assertTrue(Test.TRUE, Validator.validateIntegerList(testCSIDs, testPI.getInteractionChoiceSetIDList()));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testHelpChunks, testPI.getHelpPrompt()));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testTimeoutChunks, testPI.getTimeoutPrompt()));
		assertEquals(Test.MATCH, testIM, testPI.getInteractionMode());
		assertEquals(Test.MATCH, testTimeout, testPI.getTimeout());
		assertTrue(Test.TRUE, Validator.validateVrHelpItems(testVrHelpItems, testPI.getVrHelp()));
		assertEquals(Test.MATCH, testCorrelationID, testPI.getCorrelationID());
				
		testPI = RPCRequestFactory.buildPerformInteraction((Vector<TTSChunk>) null, null, null, null, null, null, null, null, null);
		assertNull(Test.NULL, testPI.getInitialPrompt());
		assertNull(Test.NULL, testPI.getInitialText());
		assertNull(Test.NULL, testPI.getInteractionChoiceSetIDList());
		assertNull(Test.NULL, testPI.getHelpPrompt());
		assertNull(Test.NULL, testPI.getTimeoutPrompt());
		assertNull(Test.NULL, testPI.getInteractionMode());
		assertNull(Test.NULL, testPI.getTimeout());
		assertNull(Test.NULL, testPI.getVrHelp());
		assertNull(Test.NULL, testPI.getCorrelationID());
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(Vector<TTSChunk> initChunks, String displayText, Vector<Integer> interactionChoiceSetIDList, Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		testPI = RPCRequestFactory.buildPerformInteraction(testInitChunks, testDisplayText, testCSIDs, testHelpChunks, testTimeoutChunks, testIM, testTimeout, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testInitChunks, testPI.getInitialPrompt()));
		assertEquals(Test.MATCH, testDisplayText, testPI.getInitialText());
		assertTrue(Test.TRUE, Validator.validateIntegerList(testCSIDs, testPI.getInteractionChoiceSetIDList()));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testHelpChunks, testPI.getHelpPrompt()));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testTimeoutChunks, testPI.getTimeoutPrompt()));
		assertEquals(Test.MATCH, testIM, testPI.getInteractionMode());
		assertEquals(Test.MATCH, testTimeout, testPI.getTimeout());
		assertEquals(Test.MATCH, testCorrelationID, testPI.getCorrelationID());
		
		testPI = RPCRequestFactory.buildPerformInteraction((Vector<TTSChunk>) null, null, null, null, null, null, null, null);
		assertNull(Test.NULL, testPI.getInitialPrompt());
		assertNull(Test.NULL, testPI.getInitialText());
		assertNull(Test.NULL, testPI.getInteractionChoiceSetIDList());
		assertNull(Test.NULL, testPI.getHelpPrompt());
		assertNull(Test.NULL, testPI.getTimeoutPrompt());
		assertNull(Test.NULL, testPI.getInteractionMode());
		assertNull(Test.NULL, testPI.getTimeout());
		assertNull(Test.NULL, testPI.getCorrelationID());
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(Vector<TTSChunk> initChunks, String displayText, Vector<Integer> interactionChoiceSetIDList, Vector<TTSChunk> helpChunks, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		testPI = RPCRequestFactory.buildPerformInteraction(testInitChunks, testDisplayText, testCSIDs, testHelpChunks, testIM, testTimeout, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testInitChunks, testPI.getInitialPrompt()));
		assertEquals(Test.MATCH, testDisplayText, testPI.getInitialText());
		assertTrue(Test.TRUE, Validator.validateIntegerList(testCSIDs, testPI.getInteractionChoiceSetIDList()));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testHelpChunks, testPI.getHelpPrompt()));
		assertEquals(Test.MATCH, testIM, testPI.getInteractionMode());
		assertEquals(Test.MATCH, testTimeout, testPI.getTimeout());
		assertEquals(Test.MATCH, testCorrelationID, testPI.getCorrelationID());
		
		testPI = RPCRequestFactory.buildPerformInteraction((Vector<TTSChunk>) null, null, null, null, null, null, null);
		assertNull(Test.NULL, testPI.getInitialPrompt());
		assertNull(Test.NULL, testPI.getInitialText());
		assertNull(Test.NULL, testPI.getInteractionChoiceSetIDList());
		assertNull(Test.NULL, testPI.getHelpPrompt());
		assertNull(Test.NULL, testPI.getInteractionMode());
		assertNull(Test.NULL, testPI.getTimeout());
		assertNull(Test.NULL, testPI.getCorrelationID());
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		// ^ Calls another build method.
	}
	
	public void testBuildPutFiles () {
		
		String testFileName = "test";
		Boolean testPFile = true, testSystemFile = true;
		Integer testCorrelationID = 0;
		Long testOffset = 1L, testLength = 2L;
		FileType testFileType = FileType.BINARY;		
		byte[] testFileData = {(byte) 0x00, (byte) 0x01, (byte) 0x02 };
		PutFile testPF;
		
		// Test -- buildPutFile(String sdlFileName, FileType fileType, Boolean persistentFile, byte[] fileData, Integer correlationID)
		testPF = RPCRequestFactory.buildPutFile(testFileName, testFileType, testPFile, testFileData, testCorrelationID);
		assertEquals(Test.MATCH, testFileName, testPF.getSdlFileName());
		assertEquals(Test.MATCH, testFileType, testPF.getFileType());
		assertEquals(Test.MATCH, testPFile, testPF.getPersistentFile());
		assertTrue(Test.TRUE, Validator.validateBulkData(testFileData, testPF.getFileData()));
		assertEquals(Test.MATCH, testCorrelationID, testPF.getCorrelationID());
		
		testPF = RPCRequestFactory.buildPutFile(null, null, null, null, null);
		assertNull(Test.NULL, testPF.getSdlFileName());
		assertNull(Test.NULL, testPF.getFileType());
		assertNull(Test.NULL, testPF.getPersistentFile());
		assertNull(Test.NULL, testPF.getFileData());
		assertNull(Test.NULL, testPF.getCorrelationID());
		
		// Test -- buildPutFile(String sdlFileName, Integer iOffset, Integer iLength)
		testPF = RPCRequestFactory.buildPutFile(testFileName, testOffset, testLength);
		assertEquals(Test.MATCH, testFileName, testPF.getSdlFileName());
		assertEquals(Test.MATCH, testOffset, testPF.getOffset());
		assertEquals(Test.MATCH, testLength, testPF.getLength());
		
		testPF = RPCRequestFactory.buildPutFile(NullValues.STRING, NullValues.INTEGER, NullValues.INTEGER);
		assertNull(Test.NULL, testPF.getSdlFileName());
		assertNull(Test.NULL, testPF.getOffset());
		assertNull(Test.NULL, testPF.getLength());
		
		// Test -- buildPutFile(String syncFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile)
		testPF = RPCRequestFactory.buildPutFile(testFileName, testOffset, testLength, testFileType, testPFile, testSystemFile);
		assertEquals(Test.MATCH, testFileName, testPF.getSdlFileName());
		assertEquals(Test.MATCH, testOffset, testPF.getOffset());
		assertEquals(Test.MATCH, testLength, testPF.getLength());
		assertTrue(Test.TRUE, testPF.getPersistentFile());
		assertEquals(Test.MATCH, testSystemFile, testPF.getSystemFile());
		
		testPF = RPCRequestFactory.buildPutFile(NullValues.STRING, NullValues.INTEGER, NullValues.INTEGER, null, NullValues.BOOLEAN, NullValues.BOOLEAN);
		assertNull(Test.NULL, testPF.getSdlFileName());
		assertNull(Test.NULL, testPF.getOffset());
		assertNull(Test.NULL, testPF.getLength());
		assertNull(Test.NULL, testPF.getFileType());
		assertNull(Test.NULL, testPF.getPersistentFile());
		assertNull(Test.NULL, testPF.getSystemFile());		
	}
	
	public void testBuildRegisterAppInterface () {
		
		SdlMsgVersion testSMV = new SdlMsgVersion();
		testSMV.setMajorVersion(1);
		testSMV.setMinorVersion(0);
		String testAppName = "test", testNGN = "ngn", testAppID = "id";
		Vector<TTSChunk> testTTSName = TTSChunkFactory.createSimpleTTSChunks("name");
		Vector<String> testSynonyms = new Vector<String>();
		testSynonyms.add("examine");
		Boolean testIMA = false;
		Integer testCorrelationID = 0;
		Language testLang = Language.EN_US, testHMILang = Language.EN_GB;
		Vector<AppHMIType> testHMIType = new Vector<AppHMIType>();
		testHMIType.add(AppHMIType.DEFAULT);
		DeviceInfo testDI = RPCRequestFactory.BuildDeviceInfo(null);
		RegisterAppInterface testRAI;
		
		// Test -- buildRegisterAppInterface(String appName, String appID)
		// ^ Calls another build method.
		
		// Test -- buildRegisterAppInterface(String appName, Boolean isMediaApp, String appID)
		// ^ Calls another build method.
		
		// Test -- buildRegisterAppInterface(SdlMsgVersion sdlMsgVersion, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp,  Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID, Integer correlationID)
		testRAI = RPCRequestFactory.buildRegisterAppInterface(testSMV, testAppName, testTTSName, testNGN, testSynonyms, testIMA, testLang, testHMILang, testHMIType, testAppID, testCorrelationID,testDI);
		assertTrue(Test.TRUE, Validator.validateSdlMsgVersion(testSMV, testRAI.getSdlMsgVersion()));
		assertEquals(Test.MATCH, testAppName, testRAI.getAppName());
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testTTSName, testRAI.getTtsName()));
		assertEquals(Test.MATCH, testNGN, testRAI.getNgnMediaScreenAppName());
		assertTrue(Test.TRUE, Validator.validateStringList(testSynonyms, testRAI.getVrSynonyms()));
		assertEquals(Test.MATCH, testIMA, testRAI.getIsMediaApplication());
		assertEquals(Test.MATCH, testLang, testRAI.getLanguageDesired());
		assertEquals(Test.MATCH, testHMILang, testRAI.getHmiDisplayLanguageDesired());
		assertEquals(Test.MATCH, AppHMIType.DEFAULT, testRAI.getAppHMIType().get(0));
		assertEquals(Test.MATCH, testAppID, testRAI.getAppID());
		assertEquals(Test.MATCH, testCorrelationID, testRAI.getCorrelationID());
		assertEquals(Test.MATCH, testDI, testRAI.getDeviceInfo());

		
		testRAI = RPCRequestFactory.buildRegisterAppInterface(null, null, null, null, null, null, null, null, null, null, null,null);
		assertEquals(Test.MATCH, (Integer) 1, testRAI.getCorrelationID());
		assertEquals(Test.MATCH, testSMV.getMajorVersion(), testRAI.getSdlMsgVersion().getMajorVersion());
		assertEquals(Test.MATCH, testSMV.getMinorVersion(), testRAI.getSdlMsgVersion().getMinorVersion());
		assertNull(Test.NULL, testRAI.getAppName());
		assertNull(Test.NULL, testRAI.getTtsName());
		assertNull(Test.NULL, testRAI.getNgnMediaScreenAppName());
		assertNull(Test.NULL, testRAI.getVrSynonyms());
		assertNull(Test.NULL, testRAI.getIsMediaApplication());
		assertNotNull(Test.NOT_NULL, testRAI.getLanguageDesired());
		assertNotNull(Test.NOT_NULL, testRAI.getHmiDisplayLanguageDesired());
		assertNull(Test.NULL, testRAI.getAppHMIType());
		assertNull(Test.NULL, testRAI.getAppID());
		assertNull(Test.NULL, testRAI.getDeviceInfo());
	}
	
	public void testBuildSetAppIcon () {
		
		String testFileName = "test";
		Integer testCorrelationID = 0;
		SetAppIcon testSAI;
		
		// Test -- buildSetAppIcon(String sdlFileName, Integer correlationID)
		testSAI = RPCRequestFactory.buildSetAppIcon(testFileName, testCorrelationID);
		assertEquals(Test.MATCH, testFileName, testSAI.getSdlFileName());
		assertEquals(Test.MATCH, testCorrelationID, testSAI.getCorrelationID());
		
		testSAI = RPCRequestFactory.buildSetAppIcon(null, null);
		assertNull(Test.NULL, testSAI.getSdlFileName());
		assertNull(Test.NULL, testSAI.getCorrelationID());
		
	}
	
	public void testBuildSetGlobalProperties () {
		
		Vector<TTSChunk> testHelpChunks = TTSChunkFactory.createSimpleTTSChunks("test"),
				testTimeoutChunks = TTSChunkFactory.createSimpleTTSChunks("timeout");
		Vector<VrHelpItem> testVrHelp = new Vector<VrHelpItem>();
		VrHelpItem testItem = new VrHelpItem();
		testItem.setPosition(0);
		testItem.setText("text");
		Image image = new Image();
		image.setValue("value");
		image.setImageType(ImageType.DYNAMIC);
		testItem.setImage(image);
		testVrHelp.add(testItem);
		Integer testCorrelationID = 0;
		String testHelpTitle = "help";
		SetGlobalProperties testSBP;
		
		// Test -- buildSetGlobalProperties(String helpPrompt, String timeoutPrompt, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildSetGlobalProperties(Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, Integer correlationID)
		testSBP = RPCRequestFactory.buildSetGlobalProperties(testHelpChunks, testTimeoutChunks, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testHelpChunks, testSBP.getHelpPrompt()));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testTimeoutChunks, testSBP.getTimeoutPrompt()));
		assertEquals(Test.MATCH, testCorrelationID, testSBP.getCorrelationID());
		
		testSBP = RPCRequestFactory.buildSetGlobalProperties((Vector<TTSChunk>) null, null, null);
		assertNull(Test.NULL, testSBP.getHelpPrompt());
		assertNull(Test.NULL, testSBP.getTimeoutPrompt());
		assertNull(Test.NULL, testSBP.getCorrelationID());
		
		// Test -- buildSetGlobalProperties(String helpPrompt, String timeoutPrompt, String vrHelpTitle, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildSetGlobalProperties(Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, String vrHelpTitle, Vector<VrHelpItem> vrHelp, Integer correlationID)
		testSBP = RPCRequestFactory.buildSetGlobalProperties(testHelpChunks, testTimeoutChunks, testHelpTitle, testVrHelp, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testHelpChunks, testSBP.getHelpPrompt()));
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testTimeoutChunks, testSBP.getTimeoutPrompt()));
		assertEquals(Test.MATCH, testHelpTitle, testSBP.getVrHelpTitle());
		assertTrue(Test.TRUE, Validator.validateVrHelpItems(testVrHelp, testSBP.getVrHelp()));
		assertEquals(Test.MATCH, testCorrelationID, testSBP.getCorrelationID());
		
		testSBP = RPCRequestFactory.buildSetGlobalProperties((Vector<TTSChunk>) null, null, null, null, null);
		assertNull(Test.NULL, testSBP.getHelpPrompt());
		assertNull(Test.NULL, testSBP.getTimeoutPrompt());
		assertNull(Test.NULL, testSBP.getVrHelpTitle());
		assertNull(Test.NULL, testSBP.getVrHelp());
		assertNull(Test.NULL, testSBP.getCorrelationID());
	}
	
	public void testBuildSetMediaClockTimer () {
		
		Integer hours = 0, minutes = 0, seconds = 0, testCorrelationID = 0;
		UpdateMode testMode = UpdateMode.COUNTUP;
		SetMediaClockTimer testSMCT;
		
		// Test -- buildSetMediaClockTimer(Integer hours, Integer minutes, Integer seconds, UpdateMode updateMode, Integer correlationID)
		testSMCT = RPCRequestFactory.buildSetMediaClockTimer(hours, minutes, seconds, testMode, testCorrelationID);
		assertEquals(Test.MATCH, hours, testSMCT.getStartTime().getHours());
		assertEquals(Test.MATCH, minutes, testSMCT.getStartTime().getMinutes());
		assertEquals(Test.MATCH, seconds, testSMCT.getStartTime().getSeconds());
		assertEquals(Test.MATCH, testMode, testSMCT.getUpdateMode());
		assertEquals(Test.MATCH, testCorrelationID, testSMCT.getCorrelationID());
		
		testSMCT = RPCRequestFactory.buildSetMediaClockTimer(null, null, null, null, null);
		assertNull(Test.NULL, testSMCT.getStartTime());
		assertNull(Test.NULL, testSMCT.getUpdateMode());
		assertNull(Test.NULL, testSMCT.getCorrelationID());
		
		// Test -- buildSetMediaClockTimer(UpdateMode updateMode, Integer correlationID)
		// ^ Calls another build method.
	}
	
	@SuppressWarnings("deprecation")
	public void testBuildShow () {
		
		Image testGraphic = new Image();
		testGraphic.setImageType(ImageType.STATIC);
		testGraphic.setValue("test");
		String testText1 = "test1", testText2 = "test2", testText3 = "test3", testText4 = "test4", testStatusBar = "status", testMediaClock = "media", testMediaTrack = "track";
		Vector<SoftButton> testSoftButtons = new Vector<SoftButton>();
		testSoftButtons.add(new SoftButton());
		Vector<String> testCustomPresets = new Vector<String>();
		testCustomPresets.add("Test");
		TextAlignment testAlignment = TextAlignment.CENTERED;
		Integer testCorrelationID = 0;
		Show testShow;
		
		// Test -- buildShow(String mainText1, String mainText2, String mainText3, String mainText4, String statusBar, String mediaClock, String mediaTrack, Image graphic, Vector<SoftButton> softButtons, Vector <String> customPresets, TextAlignment alignment, Integer correlationID)
		testShow = RPCRequestFactory.buildShow(testText1, testText2, testText3, testText4, testStatusBar, testMediaClock, testMediaTrack, testGraphic, testSoftButtons, testCustomPresets, testAlignment, testCorrelationID);
		assertEquals(Test.MATCH, testText1, testShow.getMainField1());
		assertEquals(Test.MATCH, testText2, testShow.getMainField2());
		assertEquals(Test.MATCH, testText3, testShow.getMainField3());
		assertEquals(Test.MATCH, testText4, testShow.getMainField4());
		assertEquals(Test.MATCH, testStatusBar, testShow.getStatusBar());
		assertEquals(Test.MATCH, testMediaClock, testShow.getMediaClock());
		assertEquals(Test.MATCH, testMediaTrack, testShow.getMediaTrack());
		assertTrue(Test.TRUE, Validator.validateImage(testGraphic, testShow.getGraphic()));
		assertTrue(Test.TRUE, Validator.validateSoftButtons(testSoftButtons, testShow.getSoftButtons()));
		assertTrue(Test.TRUE, Validator.validateStringList(testCustomPresets, testShow.getCustomPresets()));
		assertEquals(Test.MATCH, testAlignment, testShow.getAlignment());
		assertEquals(Test.MATCH, testCorrelationID, testShow.getCorrelationID());
		
		testShow = RPCRequestFactory.buildShow(null, null, null, null, null, null, null, null, null, null, null, null);
		assertNull(Test.NULL, testShow.getMainField1());
		assertNull(Test.NULL, testShow.getMainField2());
		assertNull(Test.NULL, testShow.getMainField3());
		assertNull(Test.NULL, testShow.getMainField4());
		assertNull(Test.NULL, testShow.getStatusBar());
		assertNull(Test.NULL, testShow.getMediaClock());
		assertNull(Test.NULL, testShow.getMediaTrack());
		assertNull(Test.NULL, testShow.getGraphic());
		assertNull(Test.NULL, testShow.getSoftButtons());
		assertNull(Test.NULL, testShow.getCustomPresets());
		assertNull(Test.NULL, testShow.getAlignment());
		assertNull(Test.NULL, testShow.getCorrelationID());
		// Test -- buildShow(String mainText1, String mainText2, String mainText3, String mainText4, TextAlignment alignment, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildShow(String mainText1, String mainText2, String statusBar, String mediaClock, String mediaTrack, TextAlignment alignment, Integer correlationID)
		testShow = RPCRequestFactory.buildShow(testText1, testText2, testStatusBar, testMediaClock, testMediaTrack, testAlignment, testCorrelationID);
		assertEquals(Test.MATCH, testText1, testShow.getMainField1());
		assertEquals(Test.MATCH, testText2, testShow.getMainField2());
		assertEquals(Test.MATCH, testStatusBar, testShow.getStatusBar());
		assertEquals(Test.MATCH, testMediaClock, testShow.getMediaClock());
		assertEquals(Test.MATCH, testMediaTrack, testShow.getMediaTrack());
		assertEquals(Test.MATCH, testAlignment, testShow.getAlignment());
		assertEquals(Test.MATCH, testCorrelationID, testShow.getCorrelationID());
		
		testShow = RPCRequestFactory.buildShow(null, null, null, null, null, null, null);
		assertNull(Test.NULL, testShow.getMainField1());
		assertNull(Test.NULL, testShow.getMainField2());
		assertNull(Test.NULL, testShow.getStatusBar());
		assertNull(Test.NULL, testShow.getMediaClock());
		assertNull(Test.NULL, testShow.getMediaTrack());
		assertNull(Test.NULL, testShow.getAlignment());
		assertNull(Test.NULL, testShow.getCorrelationID());
		
		// Test -- buildShow(String mainText1, String mainText2, TextAlignment alignment, Integer correlationID)
		// ^ Calls another build method.
	}
	
	public void testBuildSpeak () {
		
		String testTTSText = "test";
		Integer testCorrelationID = 0;
		Vector<TTSChunk> testTTSChunks = TTSChunkFactory.createSimpleTTSChunks(testTTSText);
		Speak testSpeak;
		
		// Test -- buildSpeak(String ttsText, Integer correlationID)
		testSpeak = RPCRequestFactory.buildSpeak(testTTSText, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testTTSChunks, testSpeak.getTtsChunks()));
		assertEquals(Test.MATCH, testCorrelationID, testSpeak.getCorrelationID());
		
		testSpeak = RPCRequestFactory.buildSpeak((String) null, null);
		assertNull(Test.NULL, testSpeak.getTtsChunks());
		assertNull(Test.NULL, testSpeak.getCorrelationID());
		
		// Test -- buildSpeak(Vector<TTSChunk> ttsChunks, Integer correlationID)
		testSpeak = RPCRequestFactory.buildSpeak(testTTSChunks, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testTTSChunks, testSpeak.getTtsChunks()));
		assertEquals(Test.MATCH, testCorrelationID, testSpeak.getCorrelationID());
		
		testSpeak = RPCRequestFactory.buildSpeak((Vector<TTSChunk>) null, null);
		assertNull(Test.NULL, testSpeak.getTtsChunks());
		assertNull(Test.NULL, testSpeak.getCorrelationID());
	}
	
	public void testBuildSubscribeButton () {
		
		ButtonName testButtonName = ButtonName.CUSTOM_BUTTON;
		Integer testCorrelationID = 0;
		SubscribeButton testSB;
		
		// Test -- buildSubscribeButton(ButtonName buttonName, Integer correlationID)
		testSB = RPCRequestFactory.buildSubscribeButton(testButtonName, testCorrelationID);
		assertEquals(Test.MATCH, testButtonName, testSB.getButtonName());
		assertEquals(Test.MATCH, testCorrelationID, testSB.getCorrelationID());
		
		testSB = RPCRequestFactory.buildSubscribeButton(null, null);
		assertNull(Test.NULL, testSB.getButtonName());
		assertNull(Test.NULL, testSB.getCorrelationID());
		
	}
	
	public void testBuildUnregisterAppInterface () {
		
		Integer testCorrelationID = 0;
		UnregisterAppInterface testUAI;
		
		// Test -- buildUnregisterAppInterface(Integer correlationID)
		testUAI = RPCRequestFactory.buildUnregisterAppInterface(testCorrelationID);
		assertEquals(Test.MATCH, testCorrelationID, testUAI.getCorrelationID());
		
		testUAI = RPCRequestFactory.buildUnregisterAppInterface(null);
		assertNull(Test.NULL, testUAI.getCorrelationID());
	}
	
	public void testBuildUnsubscribeButton () {
		
		ButtonName testButtonName = ButtonName.CUSTOM_BUTTON;
		Integer testCorrelationID = 0;
		UnsubscribeButton testUB;
		
		// Test -- buildUnsubscribeButton(ButtonName buttonName, Integer correlationID)
		testUB = RPCRequestFactory.buildUnsubscribeButton(testButtonName, testCorrelationID);
		assertEquals(Test.MATCH, testButtonName, testUB.getButtonName());
		assertEquals(Test.MATCH, testCorrelationID, testUB.getCorrelationID());
		
		testUB = RPCRequestFactory.buildUnsubscribeButton(null, null);
		assertNull(Test.NULL, testUB.getButtonName());
		assertNull(Test.NULL, testUB.getCorrelationID());
		
	}
	
	public void testBuildSubscribeVehicleData () {
		
		boolean testGPS = true, testSpeed = true, testRPM = true, testFuelLevel = true, testFuelLevelState = true, testInstantFuelConsumption = true, testExternalTemperature = true, testPRNDL = true, testTirePressure = true, testOdometer = true, testBeltStatus = true, testBodyInformation = true, testDeviceStatus = true, testDriverBraking = true;
		Integer testCorrelationID = 0;
		SubscribeVehicleData testSVD;
		
		// Test -- BuildSubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID) 
		testSVD = RPCRequestFactory.BuildSubscribeVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, testCorrelationID);	
		assertTrue(Test.TRUE, testSVD.getGps());
		assertTrue(Test.TRUE, testSVD.getSpeed());
		assertTrue(Test.TRUE, testSVD.getRpm());
		assertTrue(Test.TRUE, testSVD.getFuelLevel());
		assertTrue(Test.TRUE, testSVD.getFuelLevelState());
		assertTrue(Test.TRUE, testSVD.getInstantFuelConsumption());
		assertTrue(Test.TRUE, testSVD.getExternalTemperature());
		assertTrue(Test.TRUE, testSVD.getPrndl());
		assertTrue(Test.TRUE, testSVD.getTirePressure());
		assertTrue(Test.TRUE, testSVD.getOdometer());
		assertTrue(Test.TRUE, testSVD.getBeltStatus());
		assertTrue(Test.TRUE, testSVD.getBodyInformation());
		assertTrue(Test.TRUE, testSVD.getDeviceStatus());
		assertTrue(Test.TRUE, testSVD.getDriverBraking());
		assertEquals(Test.MATCH, testCorrelationID, testSVD.getCorrelationID());
		
		testSVD = RPCRequestFactory.BuildSubscribeVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, null);
		assertNull(Test.NULL, testSVD.getCorrelationID());
	}
	
	public void testBuildUnsubscribeVehicleData () {
		
		boolean testGPS = true, testSpeed = true, testRPM = true, testFuelLevel = true, testFuelLevelState = true, testInstantFuelConsumption = true, testExternalTemperature = true, testPRNDL = true, testTirePressure = true, testOdometer = true, testBeltStatus = true, testBodyInformation = true, testDeviceStatus = true, testDriverBraking = true;
		Integer testCorrelationID = 0;
		UnsubscribeVehicleData testUVD;
		
		// Test -- BuildUnsubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID) 
		testUVD = RPCRequestFactory.BuildUnsubscribeVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, testCorrelationID);	
		assertTrue(Test.TRUE, testUVD.getGps());
		assertTrue(Test.TRUE, testUVD.getSpeed());
		assertTrue(Test.TRUE, testUVD.getRpm());
		assertTrue(Test.TRUE, testUVD.getFuelLevel());
		assertTrue(Test.TRUE, testUVD.getFuelLevelState());
		assertTrue(Test.TRUE, testUVD.getInstantFuelConsumption());
		assertTrue(Test.TRUE, testUVD.getExternalTemperature());
		assertTrue(Test.TRUE, testUVD.getPrndl());
		assertTrue(Test.TRUE, testUVD.getTirePressure());
		assertTrue(Test.TRUE, testUVD.getOdometer());
		assertTrue(Test.TRUE, testUVD.getBeltStatus());
		assertTrue(Test.TRUE, testUVD.getBodyInformation());
		assertTrue(Test.TRUE, testUVD.getDeviceStatus());
		assertTrue(Test.TRUE, testUVD.getDriverBraking());
		assertEquals(Test.MATCH, testCorrelationID, testUVD.getCorrelationID());
		
		testUVD = RPCRequestFactory.BuildUnsubscribeVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, null);
		assertNull(Test.NULL, testUVD.getCorrelationID());
	}
	
	public void testBuildGetVehicleData () {
		
		boolean testGPS = true, testSpeed = true, testRPM = true, testFuelLevel = true, testFuelLevelState = true, testInstantFuelConsumption = true, testExternalTemperature = true, testVIN = true, testPRNDL = true, testTirePressure = true, testOdometer = true, testBeltStatus = true, testBodyInformation = true, testDeviceStatus = true, testDriverBraking = true;
		Integer testCorrelationID = 0;
		GetVehicleData testGVD;
		
		// Test -- BuildGetVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean vin, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID)
		testGVD = RPCRequestFactory.BuildGetVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testVIN, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, testCorrelationID);	
		assertTrue(Test.TRUE, testGVD.getGps());
		assertTrue(Test.TRUE, testGVD.getSpeed());
		assertTrue(Test.TRUE, testGVD.getRpm());
		assertTrue(Test.TRUE, testGVD.getFuelLevel());
		assertTrue(Test.TRUE, testGVD.getFuelLevelState());
		assertTrue(Test.TRUE, testGVD.getInstantFuelConsumption());
		assertTrue(Test.TRUE, testGVD.getExternalTemperature());
		assertTrue(Test.TRUE, testGVD.getPrndl());
		assertTrue(Test.TRUE, testGVD.getTirePressure());
		assertTrue(Test.TRUE, testGVD.getOdometer());
		assertTrue(Test.TRUE, testGVD.getBeltStatus());
		assertTrue(Test.TRUE, testGVD.getBodyInformation());
		assertTrue(Test.TRUE, testGVD.getDeviceStatus());
		assertTrue(Test.TRUE, testGVD.getDriverBraking());
		assertTrue(Test.TRUE, testGVD.getVin());
		assertEquals(Test.MATCH, testCorrelationID, testGVD.getCorrelationID());
		
		testGVD = RPCRequestFactory.BuildGetVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testVIN, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, null);
		assertNull(Test.NULL, testGVD.getCorrelationID());	
	}
	
	public void testBuildScrollableMessage () {
		
		String testSMB = "test";
		Integer testTimeout = 1, testCorrelationID = 0;
		Vector<SoftButton> testSoftButtons = new Vector<SoftButton>();
		testSoftButtons.add(new SoftButton());
		ScrollableMessage testSM;
		
		// Test -- BuildScrollableMessage(String scrollableMessageBody, Integer timeout, Vector<SoftButton> softButtons, Integer correlationID)	
		testSM = RPCRequestFactory.BuildScrollableMessage(testSMB, testTimeout, testSoftButtons, testCorrelationID);
		assertEquals(Test.MATCH, testSMB, testSM.getScrollableMessageBody());
		assertEquals(Test.MATCH, testTimeout, testSM.getTimeout());
		assertTrue(Test.TRUE, Validator.validateSoftButtons(testSoftButtons, testSM.getSoftButtons()));
		assertEquals(Test.MATCH, testCorrelationID, testSM.getCorrelationID());
		
		testSM = RPCRequestFactory.BuildScrollableMessage(null, null, null, null);
		assertNull(Test.NULL, testSM.getScrollableMessageBody());
		assertNull(Test.NULL, testSM.getTimeout());
		assertNull(Test.NULL, testSM.getSoftButtons());
		assertNull(Test.NULL, testSM.getCorrelationID());
	}
	
	public void testBuildSlider () {
		
		Integer testTicks = 1, testPosition = 2, testTimeout = 3, testCorrelationID = 0;
		String testHeader = "header";
		Vector<String> testFooter = new Vector<String>();
		testFooter.add("footer");
		Slider testSlider;
		
		// Test -- BuildSlider(Integer numTicks, Integer position, String sliderHeader, Vector<String> sliderFooter, Integer timeout, Integer correlationID)
		testSlider = RPCRequestFactory.BuildSlider(testTicks, testPosition, testHeader, testFooter, testTimeout, testCorrelationID);
		assertEquals(Test.MATCH, testTicks, testSlider.getNumTicks());
		assertEquals(Test.MATCH, testPosition, testSlider.getPosition());
		assertEquals(Test.MATCH, testHeader, testSlider.getSliderHeader());
		assertTrue(Test.TRUE, Validator.validateStringList(testFooter, testSlider.getSliderFooter()));
		assertEquals(Test.MATCH, testCorrelationID, testSlider.getCorrelationID());
		
		testSlider = RPCRequestFactory.BuildSlider(null, null, null, null, null, null);
		assertNull(Test.NULL, testSlider.getNumTicks());
		assertNull(Test.NULL, testSlider.getPosition());
		assertNull(Test.NULL, testSlider.getSliderHeader());
		assertNull(Test.NULL, testSlider.getSliderFooter());
		assertNull(Test.NULL, testSlider.getTimeout());
		assertNull(Test.NULL, testSlider.getCorrelationID());
	}
	
	public void testBuildChangeRegistration () {
		
		Language testLang = Language.EN_US, testHMILang = Language.EN_AU;
		Integer testCorrelationID = 0;
		ChangeRegistration testCR;
		
		// Test -- BuildChangeRegistration(Language language, Language hmiDisplayLanguage, Integer correlationID)
		testCR = RPCRequestFactory.BuildChangeRegistration(testLang, testHMILang, testCorrelationID);
		assertEquals(Test.MATCH, testLang, testCR.getLanguage());
		assertEquals(Test.MATCH, testHMILang, testCR.getHmiDisplayLanguage());
		assertEquals(Test.MATCH, testCorrelationID, testCR.getCorrelationID());
		
		testCR = RPCRequestFactory.BuildChangeRegistration(null, null, null);
		assertNull(Test.NULL, testCR.getLanguage());
		assertNull(Test.NULL, testCR.getHmiDisplayLanguage());
		assertNull(Test.NULL, testCR.getCorrelationID());
	}
	
	public void testBuildSetDisplayLayout () {
		
		String testDL = "layout";
		Integer testCorrelationID = 0;
		SetDisplayLayout testSDL;
		
		// Test -- BuildSetDisplayLayout(String displayLayout, Integer correlationID)
		testSDL = RPCRequestFactory.BuildSetDisplayLayout(testDL, testCorrelationID);
		assertEquals(Test.MATCH, testDL, testSDL.getDisplayLayout());
		assertEquals(Test.MATCH, testCorrelationID, testSDL.getCorrelationID());
		
		testSDL = RPCRequestFactory.BuildSetDisplayLayout(null, null);
		assertNull(Test.NULL, testSDL.getDisplayLayout());
		assertNull(Test.NULL, testSDL.getCorrelationID());
	}
	
	public void testBuildPerformAudioPassThru () {
		
		Vector<TTSChunk> testInitialPrompt = TTSChunkFactory.createSimpleTTSChunks("test");
		String testAPTDT1 = "audio", testAPTDT2 = "pass";
		SamplingRate testSR = SamplingRate._16KHZ;
		Integer testMaxDuration = 1, testCorrelationID = 0;
		BitsPerSample testBits = BitsPerSample._16_BIT;
		AudioType testAT = AudioType.PCM;
		Boolean testMute = false;	
		PerformAudioPassThru testPAPT;
		
		// Test -- BuildPerformAudioPassThru(String ttsText, String audioPassThruDisplayText1, String audioPassThruDisplayText2, SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample, AudioType audioType, Boolean muteAudio, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- BuildPerformAudioPassThru(Vector<TTSChunk> initialPrompt, String audioPassThruDisplayText1, String audioPassThruDisplayText2, SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample, AudioType audioType, Boolean muteAudio, Integer correlationID)
		testPAPT = RPCRequestFactory.BuildPerformAudioPassThru(testInitialPrompt, testAPTDT1, testAPTDT2, testSR, testMaxDuration, testBits, testAT, testMute, testCorrelationID);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(testInitialPrompt, testPAPT.getInitialPrompt()));
		assertEquals(Test.MATCH, testAPTDT1, testPAPT.getAudioPassThruDisplayText1());
		assertEquals(Test.MATCH, testAPTDT2, testPAPT.getAudioPassThruDisplayText2());
		assertEquals(Test.MATCH, testSR, testPAPT.getSamplingRate());
		assertEquals(Test.MATCH, testMaxDuration, testPAPT.getMaxDuration());
		assertEquals(Test.MATCH, testBits, testPAPT.getBitsPerSample());
		assertEquals(Test.MATCH, testAT, testPAPT.getAudioType());
		assertEquals(Test.MATCH, testMute, testPAPT.getMuteAudio());
		assertEquals(Test.MATCH, testCorrelationID, testPAPT.getCorrelationID());
		
		testPAPT = RPCRequestFactory.BuildPerformAudioPassThru((Vector<TTSChunk>) null, null, null, null, null, null, null, null, null);
		assertNull(Test.NULL, testPAPT.getInitialPrompt());
		assertNull(Test.NULL, testPAPT.getAudioPassThruDisplayText1());
		assertNull(Test.NULL, testPAPT.getAudioPassThruDisplayText2());
		assertNull(Test.NULL, testPAPT.getSamplingRate());
		assertNull(Test.NULL, testPAPT.getMaxDuration());
		assertNull(Test.NULL, testPAPT.getBitsPerSample());
		assertNull(Test.NULL, testPAPT.getAudioType());
		assertNull(Test.NULL, testPAPT.getMuteAudio());
		assertNull(Test.NULL, testPAPT.getCorrelationID());
	}
	
	public void testBuildEndAudioPassThru () {
		
		Integer testCorrelationID = 0;
		EndAudioPassThru testEAPT;
		
		// Test -- BuildEndAudioPassThru(Integer correlationID)
		testEAPT = RPCRequestFactory.BuildEndAudioPassThru(testCorrelationID);
		assertEquals(Test.MATCH, testCorrelationID, testEAPT.getCorrelationID());
		
		testEAPT = RPCRequestFactory.BuildEndAudioPassThru(null);
		assertNull(Test.NULL, testEAPT.getCorrelationID());
	}	
}