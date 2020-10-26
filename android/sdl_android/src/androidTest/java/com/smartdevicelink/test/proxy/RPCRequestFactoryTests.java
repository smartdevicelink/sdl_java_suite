package com.smartdevicelink.test.proxy;

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
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import java.util.Vector;

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
		assertNotNull(TestValues.NOT_NULL, testBSR.getBulkData());
		assertEquals(TestValues.MATCH, testInt, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequest(testData, null);
		assertNotNull(TestValues.NULL, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequest(null, testInt);
		assertNull(TestValues.NULL, testBSR);
				
		// Test -- buildSystemRequestLegacy(Vector<String> data, Integer correlationID)
		testVData = new Vector<String>();
		testVData.add("Test A");
		testVData.add("Test B");
		testVData.add("Test C");
		testBSR   = RPCRequestFactory.buildSystemRequestLegacy(testVData, testInt);
		assertEquals(TestValues.MATCH, testVData, new Vector<String>(testBSR.getLegacyData()));
		assertEquals(TestValues.MATCH, testInt, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequestLegacy(testVData, null);
		assertNotNull(TestValues.NOT_NULL, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequestLegacy(null, testInt);
		assertNull(TestValues.NULL, testBSR);
		
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
		assertEquals(TestValues.MATCH, testCommandID, testBAC.getCmdID());
		assertEquals(TestValues.MATCH, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(TestValues.MATCH, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(TestValues.MATCH, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(TestValues.MATCH, testVrCommands, testBAC.getVrCommands());
		assertTrue(TestValues.TRUE, Validator.validateImage(testImage, testBAC.getCmdIcon()));
		assertEquals(TestValues.MATCH, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null, null);
		assertNull(TestValues.NULL, testBAC.getCmdID());
		assertNull(TestValues.NULL, testBAC.getMenuParams());
		assertNull(TestValues.NULL, testBAC.getVrCommands());
		assertNull(TestValues.NULL, testBAC.getCmdIcon());
		assertNotNull(TestValues.NOT_NULL, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position, Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationID)
		testIconValue = "icon";
		testIconType  = ImageType.STATIC;
		testImage     = new Image();
		testImage.setValue(testIconValue);
		testImage.setImageType(testIconType);
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testParentID, testPosition, testVrCommands, testIconValue, testIconType, testCorrelationID);
		assertEquals(TestValues.MATCH, testCommandID, testBAC.getCmdID());
		assertEquals(TestValues.MATCH, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(TestValues.MATCH, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(TestValues.MATCH, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(TestValues.MATCH, testVrCommands, testBAC.getVrCommands());
		assertEquals(TestValues.MATCH, testCorrelationID, testBAC.getCorrelationID());
		assertTrue(TestValues.TRUE, Validator.validateImage(testImage, testBAC.getCmdIcon()));
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null, null, null);
		assertNull(TestValues.NULL, testBAC.getCmdID());
		assertNull(TestValues.NULL, testBAC.getMenuParams());
		assertNull(TestValues.NULL, testBAC.getVrCommands());
		assertNull(TestValues.NULL, testBAC.getCmdIcon());
		assertNotNull(TestValues.NOT_NULL, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testParentID, testPosition, testVrCommands, testCorrelationID);
		assertEquals(TestValues.MATCH, testCommandID, testBAC.getCmdID());
		assertEquals(TestValues.MATCH, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(TestValues.MATCH, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(TestValues.MATCH, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(TestValues.MATCH, testVrCommands, testBAC.getVrCommands());
		assertEquals(TestValues.MATCH, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null);
		assertNull(TestValues.NULL, testBAC.getCmdID());
		assertNull(TestValues.NULL, testBAC.getMenuParams());
		assertNull(TestValues.NULL, testBAC.getVrCommands());
		assertNotNull(TestValues.NOT_NULL, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testVrCommands, testCorrelationID);
		assertEquals(TestValues.MATCH, testCommandID, testBAC.getCmdID());
		assertEquals(TestValues.MATCH, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(TestValues.MATCH, testVrCommands, testBAC.getVrCommands());
		assertEquals(TestValues.MATCH, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null);
		assertNull(TestValues.NULL, testBAC.getCmdID());
		assertNull(TestValues.NULL, testBAC.getMenuParams());
		assertNull(TestValues.NULL, testBAC.getVrCommands());
		assertNotNull(TestValues.NOT_NULL, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testVrCommands, testCorrelationID);
		assertEquals(TestValues.MATCH, testCommandID, testBAC.getCmdID());
		assertEquals(TestValues.MATCH, testVrCommands, testBAC.getVrCommands());
		assertEquals(TestValues.MATCH, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null);
		assertNull(TestValues.NULL, testBAC.getCmdID());
		assertNull(TestValues.NULL, testBAC.getVrCommands());
		assertNotNull(TestValues.NOT_NULL, testBAC.getCorrelationID());
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
		assertEquals(TestValues.MATCH, testMenuID, testBASM.getMenuID());
		assertEquals(TestValues.MATCH, testMenuName, testBASM.getMenuName());
		assertEquals(TestValues.MATCH, testPosition, testBASM.getPosition());
		assertEquals(TestValues.MATCH, testCorrelationID, testBASM.getCorrelationID());
		
		testBASM = RPCRequestFactory.buildAddSubMenu(null, null, null, null);
		assertNull(TestValues.NULL, testBASM.getMenuID());
		assertNull(TestValues.NULL, testBASM.getMenuName());
		assertNull(TestValues.NULL, testBASM.getPosition());
		assertNotNull(TestValues.NOT_NULL, testBASM.getCorrelationID());
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
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TTSChunkFactory.createSimpleTTSChunks(testTTSText), testAlert.getTtsChunks()));
		// ^ Calls another build method.
		
		// Test -- buildAlert(String alertText1, String alertText2, String alertText3, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testAlertText1 = "test 1";
		testAlertText2 = "test 2";
		testAlertText3 = "test 3";
		testDuration   = 1;	
		// ^ Calls another build method.
		
		// Test -- buildAlert(String ttsText, String alertText1, String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testAlert = RPCRequestFactory.buildAlert(testTTSText, testAlertText1, testAlertText2, testAlertText3, testPlayTone, testDuration, testSoftButtons, testCorrelationID);
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TTSChunkFactory.createSimpleTTSChunks(testTTSText), testAlert.getTtsChunks()));
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> chunks, Boolean playTone, Vector<SoftButton> softButtons, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> ttsChunks, String alertText1, String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testTtsChunks = TTSChunkFactory.createSimpleTTSChunks(testTTSText);
		testAlert = RPCRequestFactory.buildAlert(testTtsChunks, testAlertText1, testAlertText2, testAlertText3, testPlayTone, testDuration, testSoftButtons, testCorrelationID);
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testTtsChunks, testAlert.getTtsChunks()));
		assertEquals(TestValues.MATCH, testAlertText1, testAlert.getAlertText1());
		assertEquals(TestValues.MATCH, testAlertText2, testAlert.getAlertText2());
		assertEquals(TestValues.MATCH, testAlertText3, testAlert.getAlertText3());
		assertEquals(TestValues.MATCH, testPlayTone, testAlert.getPlayTone());
		assertEquals(TestValues.MATCH, testDuration, testAlert.getDuration());
		assertTrue(TestValues.TRUE, Validator.validateSoftButtons(testSoftButtons, testAlert.getSoftButtons()));
		assertEquals(TestValues.MATCH, testCorrelationID, testAlert.getCorrelationID());
		
		testAlert = RPCRequestFactory.buildAlert((Vector<TTSChunk>) null, null, null, null, null, null, null, null);
		assertNull(TestValues.NULL, testAlert.getTtsChunks());
		assertNull(TestValues.NULL, testAlert.getAlertText1());
		assertNull(TestValues.NULL, testAlert.getAlertText2());
		assertNull(TestValues.NULL, testAlert.getAlertText3());
		assertNull(TestValues.NULL, testAlert.getPlayTone());
		assertNull(TestValues.NULL, testAlert.getDuration());
		assertNull(TestValues.NULL, testAlert.getSoftButtons());
		assertNotNull(TestValues.NOT_NULL, testAlert.getCorrelationID());
		
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
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testTtsChunks, testAlert.getTtsChunks()));
		assertEquals(TestValues.MATCH, testAlertText1, testAlert.getAlertText1());
		assertEquals(TestValues.MATCH, testAlertText2, testAlert.getAlertText2());
		assertEquals(TestValues.MATCH, testPlayTone, testAlert.getPlayTone());
		assertEquals(TestValues.MATCH, testDuration, testAlert.getDuration());
		assertEquals(TestValues.MATCH, testCorrelationID, testAlert.getCorrelationID());
		
		testAlert = RPCRequestFactory.buildAlert((Vector<TTSChunk>) null, null, null, null, null, null);
		assertNull(TestValues.NULL, testAlert.getTtsChunks());
		assertNull(TestValues.NULL, testAlert.getAlertText1());
		assertNull(TestValues.NULL, testAlert.getAlertText2());
		assertNull(TestValues.NULL, testAlert.getPlayTone());
		assertNull(TestValues.NULL, testAlert.getDuration());
		assertNotNull(TestValues.NOT_NULL, testAlert.getCorrelationID());
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
		assertEquals(TestValues.MATCH, testChoiceSet, testBCICS.getChoiceSet());
		assertEquals(TestValues.MATCH, testICSID, testBCICS.getInteractionChoiceSetID());
		assertEquals(TestValues.MATCH, testCorrelationID, testBCICS.getCorrelationID());
		
		testBCICS = RPCRequestFactory.buildCreateInteractionChoiceSet(null, null, null);
		assertNull(TestValues.NULL, testBCICS.getChoiceSet());
		assertNull(TestValues.NULL, testBCICS.getInteractionChoiceSetID());
		assertNotNull(TestValues.NOT_NULL, testBCICS.getCorrelationID());
	}
	
	public void testBuildDeleteCommand () {
		
		Integer testCID, testCorrelationID;
		DeleteCommand testDC;
		
		// Test -- buildDeleteCommand(Integer commandID, Integer correlationID)
		testCID = 0;
		testCorrelationID = 1;
		testDC = RPCRequestFactory.buildDeleteCommand(testCID, testCorrelationID);
		assertEquals(TestValues.MATCH, testCID, testDC.getCmdID());
		assertEquals(TestValues.MATCH, testCorrelationID, testDC.getCorrelationID());
		
		testDC = RPCRequestFactory.buildDeleteCommand(null, null);
		assertNull(TestValues.NULL, testDC.getCmdID());
		assertNotNull(TestValues.NOT_NULL, testDC.getCorrelationID());
		
	}
	
	public void testBuildDeleteFile () {
		
		Integer testCorrelationID;
		String testFileName;
		DeleteFile testDF;
		
		// Test --buildDeleteFile(String sdlFileName, Integer correlationID)
		testCorrelationID = 0;
		testFileName = "test";
		testDF = RPCRequestFactory.buildDeleteFile(testFileName, testCorrelationID);
		assertEquals(TestValues.MATCH, testCorrelationID, testDF.getCorrelationID());
		assertEquals(TestValues.MATCH, testFileName, testDF.getSdlFileName());
		
		testDF = RPCRequestFactory.buildDeleteFile(null, null);
		assertNotNull(TestValues.NOT_NULL, testDF.getCorrelationID());
		assertNull(TestValues.NULL, testDF.getSdlFileName());
		
	}
	
	public void testBuildDeleteInteractionChoiceSet () {
		
		Integer testICSID, testCorrelationID;
		DeleteInteractionChoiceSet testDICS;
		
		// Test -- buildDeleteInteractionChoiceSet(Integer interactionChoiceSetID, Integer correlationID)
		testICSID = 0;
		testCorrelationID = 1;
		testDICS = RPCRequestFactory.buildDeleteInteractionChoiceSet(testICSID, testCorrelationID);
		assertEquals(TestValues.MATCH, testICSID, testDICS.getInteractionChoiceSetID());
		assertEquals(TestValues.MATCH, testCorrelationID, testDICS.getCorrelationID());
		
		testDICS = RPCRequestFactory.buildDeleteInteractionChoiceSet(null, null);
		assertNull(TestValues.NULL, testDICS.getInteractionChoiceSetID());
		assertNotNull(TestValues.NOT_NULL, testDICS.getCorrelationID());
	}
	
	public void testBuildDeleteSubMenu () {
		
		Integer testMenuID, testCorrelationID;
		DeleteSubMenu testDSM;
		
		// Test -- buildDeleteSubMenu(Integer menuID, Integer correlationID)
		testMenuID = 0;
		testCorrelationID = 1;
		testDSM = RPCRequestFactory.buildDeleteSubMenu(testMenuID, testCorrelationID);
		assertEquals(TestValues.MATCH, testMenuID, testDSM.getMenuID());
		assertEquals(TestValues.MATCH, testCorrelationID, testDSM.getCorrelationID());
		
		testDSM = RPCRequestFactory.buildDeleteSubMenu(null, null);
		assertNull(TestValues.NULL, testDSM.getMenuID());
		assertNotNull(TestValues.NOT_NULL, testDSM.getCorrelationID());
	}
	
	public void testBuildListFiles () {
		
		Integer testCorrelationID = 0;
		ListFiles testLF;
		
		// Test -- buildListFiles(Integer correlationID)
		testLF = RPCRequestFactory.buildListFiles(testCorrelationID);
		assertEquals(TestValues.MATCH, testCorrelationID, testLF.getCorrelationID());
				
		testLF = RPCRequestFactory.buildListFiles(null);
		assertNotNull(TestValues.NOT_NULL, testLF.getCorrelationID());
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
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testInitChunks, testPI.getInitialPrompt()));
		assertEquals(TestValues.MATCH, testDisplayText, testPI.getInitialText());
		assertTrue(TestValues.TRUE, Validator.validateIntegerList(testCSIDs, testPI.getInteractionChoiceSetIDList()));
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testHelpChunks, testPI.getHelpPrompt()));
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testTimeoutChunks, testPI.getTimeoutPrompt()));
		assertEquals(TestValues.MATCH, testIM, testPI.getInteractionMode());
		assertEquals(TestValues.MATCH, testTimeout, testPI.getTimeout());
		assertTrue(TestValues.TRUE, Validator.validateVrHelpItems(testVrHelpItems, testPI.getVrHelp()));
		assertEquals(TestValues.MATCH, testCorrelationID, testPI.getCorrelationID());
				
		testPI = RPCRequestFactory.buildPerformInteraction((Vector<TTSChunk>) null, null, null, null, null, null, null, null, null);
		assertNull(TestValues.NULL, testPI.getInitialPrompt());
		assertNull(TestValues.NULL, testPI.getInitialText());
		assertNull(TestValues.NULL, testPI.getInteractionChoiceSetIDList());
		assertNull(TestValues.NULL, testPI.getHelpPrompt());
		assertNull(TestValues.NULL, testPI.getTimeoutPrompt());
		assertNull(TestValues.NULL, testPI.getInteractionMode());
		assertNull(TestValues.NULL, testPI.getTimeout());
		assertNull(TestValues.NULL, testPI.getVrHelp());
		assertNotNull(TestValues.NOT_NULL, testPI.getCorrelationID());
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(Vector<TTSChunk> initChunks, String displayText, Vector<Integer> interactionChoiceSetIDList, Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		testPI = RPCRequestFactory.buildPerformInteraction(testInitChunks, testDisplayText, testCSIDs, testHelpChunks, testTimeoutChunks, testIM, testTimeout, testCorrelationID);
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testInitChunks, testPI.getInitialPrompt()));
		assertEquals(TestValues.MATCH, testDisplayText, testPI.getInitialText());
		assertTrue(TestValues.TRUE, Validator.validateIntegerList(testCSIDs, testPI.getInteractionChoiceSetIDList()));
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testHelpChunks, testPI.getHelpPrompt()));
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testTimeoutChunks, testPI.getTimeoutPrompt()));
		assertEquals(TestValues.MATCH, testIM, testPI.getInteractionMode());
		assertEquals(TestValues.MATCH, testTimeout, testPI.getTimeout());
		assertEquals(TestValues.MATCH, testCorrelationID, testPI.getCorrelationID());
		
		testPI = RPCRequestFactory.buildPerformInteraction((Vector<TTSChunk>) null, null, null, null, null, null, null, null);
		assertNull(TestValues.NULL, testPI.getInitialPrompt());
		assertNull(TestValues.NULL, testPI.getInitialText());
		assertNull(TestValues.NULL, testPI.getInteractionChoiceSetIDList());
		assertNull(TestValues.NULL, testPI.getHelpPrompt());
		assertNull(TestValues.NULL, testPI.getTimeoutPrompt());
		assertNull(TestValues.NULL, testPI.getInteractionMode());
		assertNull(TestValues.NULL, testPI.getTimeout());
		assertNotNull(TestValues.NOT_NULL, testPI.getCorrelationID());
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(Vector<TTSChunk> initChunks, String displayText, Vector<Integer> interactionChoiceSetIDList, Vector<TTSChunk> helpChunks, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		testPI = RPCRequestFactory.buildPerformInteraction(testInitChunks, testDisplayText, testCSIDs, testHelpChunks, testIM, testTimeout, testCorrelationID);
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testInitChunks, testPI.getInitialPrompt()));
		assertEquals(TestValues.MATCH, testDisplayText, testPI.getInitialText());
		assertTrue(TestValues.TRUE, Validator.validateIntegerList(testCSIDs, testPI.getInteractionChoiceSetIDList()));
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testHelpChunks, testPI.getHelpPrompt()));
		assertEquals(TestValues.MATCH, testIM, testPI.getInteractionMode());
		assertEquals(TestValues.MATCH, testTimeout, testPI.getTimeout());
		assertEquals(TestValues.MATCH, testCorrelationID, testPI.getCorrelationID());
		
		testPI = RPCRequestFactory.buildPerformInteraction((Vector<TTSChunk>) null, null, null, null, null, null, null);
		assertNull(TestValues.NULL, testPI.getInitialPrompt());
		assertNull(TestValues.NULL, testPI.getInitialText());
		assertNull(TestValues.NULL, testPI.getInteractionChoiceSetIDList());
		assertNull(TestValues.NULL, testPI.getHelpPrompt());
		assertNull(TestValues.NULL, testPI.getInteractionMode());
		assertNull(TestValues.NULL, testPI.getTimeout());
		assertNotNull(TestValues.NOT_NULL, testPI.getCorrelationID());
		
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
		assertEquals(TestValues.MATCH, testFileName, testPF.getSdlFileName());
		assertEquals(TestValues.MATCH, testFileType, testPF.getFileType());
		assertEquals(TestValues.MATCH, testPFile, testPF.getPersistentFile());
		assertTrue(TestValues.TRUE, Validator.validateBulkData(testFileData, testPF.getFileData()));
		assertEquals(TestValues.MATCH, testCorrelationID, testPF.getCorrelationID());
		
		testPF = RPCRequestFactory.buildPutFile(null, null, null, null, null);
		assertNull(TestValues.NULL, testPF.getSdlFileName());
		assertNull(TestValues.NULL, testPF.getFileType());
		assertNull(TestValues.NULL, testPF.getPersistentFile());
		assertNull(TestValues.NULL, testPF.getFileData());
		assertNotNull(TestValues.NOT_NULL, testPF.getCorrelationID());
		
		// Test -- buildPutFile(String sdlFileName, Integer iOffset, Integer iLength)
		testPF = RPCRequestFactory.buildPutFile(testFileName, testOffset, testLength);
		assertEquals(TestValues.MATCH, testFileName, testPF.getSdlFileName());
		assertEquals(TestValues.MATCH, testOffset, testPF.getOffset());
		assertEquals(TestValues.MATCH, testLength, testPF.getLength());
		
		testPF = RPCRequestFactory.buildPutFile(NullValues.STRING, NullValues.INTEGER, NullValues.INTEGER);
		assertNull(TestValues.NULL, testPF.getSdlFileName());
		assertNull(TestValues.NULL, testPF.getOffset());
		assertNull(TestValues.NULL, testPF.getLength());
		
		// Test -- buildPutFile(String syncFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile)
		testPF = RPCRequestFactory.buildPutFile(testFileName, testOffset, testLength, testFileType, testPFile, testSystemFile);
		assertEquals(TestValues.MATCH, testFileName, testPF.getSdlFileName());
		assertEquals(TestValues.MATCH, testOffset, testPF.getOffset());
		assertEquals(TestValues.MATCH, testLength, testPF.getLength());
		assertTrue(TestValues.TRUE, testPF.getPersistentFile());
		assertEquals(TestValues.MATCH, testSystemFile, testPF.getSystemFile());
		
		testPF = RPCRequestFactory.buildPutFile(NullValues.STRING, NullValues.INTEGER, NullValues.INTEGER, null, NullValues.BOOLEAN, NullValues.BOOLEAN);
		assertNull(TestValues.NULL, testPF.getSdlFileName());
		assertNull(TestValues.NULL, testPF.getOffset());
		assertNull(TestValues.NULL, testPF.getLength());
		assertNull(TestValues.NULL, testPF.getFileType());
		assertNull(TestValues.NULL, testPF.getPersistentFile());
		assertNull(TestValues.NULL, testPF.getSystemFile());
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
		assertTrue(TestValues.TRUE, Validator.validateSdlMsgVersion(testSMV, testRAI.getSdlMsgVersion()));
		assertEquals(TestValues.MATCH, testAppName, testRAI.getAppName());
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testTTSName, testRAI.getTtsName()));
		assertEquals(TestValues.MATCH, testNGN, testRAI.getNgnMediaScreenAppName());
		assertTrue(TestValues.TRUE, Validator.validateStringList(testSynonyms, testRAI.getVrSynonyms()));
		assertEquals(TestValues.MATCH, testIMA, testRAI.getIsMediaApplication());
		assertEquals(TestValues.MATCH, testLang, testRAI.getLanguageDesired());
		assertEquals(TestValues.MATCH, testHMILang, testRAI.getHmiDisplayLanguageDesired());
		assertEquals(TestValues.MATCH, AppHMIType.DEFAULT, testRAI.getAppHMIType().get(0));
		assertEquals(TestValues.MATCH, testAppID, testRAI.getAppID());
		assertEquals(TestValues.MATCH, testCorrelationID, testRAI.getCorrelationID());
		assertEquals(TestValues.MATCH, testDI, testRAI.getDeviceInfo());

		
		testRAI = RPCRequestFactory.buildRegisterAppInterface(null, null, null, null, null, null, null, null, null, null, null,null);
		assertEquals(TestValues.MATCH, (Integer) 1, testRAI.getCorrelationID());
		assertEquals(TestValues.MATCH, testSMV.getMajorVersion(), testRAI.getSdlMsgVersion().getMajorVersion());
		assertEquals(TestValues.MATCH, testSMV.getMinorVersion(), testRAI.getSdlMsgVersion().getMinorVersion());
		assertNull(TestValues.NULL, testRAI.getAppName());
		assertNull(TestValues.NULL, testRAI.getTtsName());
		assertNull(TestValues.NULL, testRAI.getNgnMediaScreenAppName());
		assertNull(TestValues.NULL, testRAI.getVrSynonyms().get(0));
		assertNull(TestValues.NULL, testRAI.getIsMediaApplication());
		assertNotNull(TestValues.NOT_NULL, testRAI.getLanguageDesired());
		assertNotNull(TestValues.NOT_NULL, testRAI.getHmiDisplayLanguageDesired());
		assertNull(TestValues.NULL, testRAI.getAppHMIType());
		assertNull(TestValues.NULL, testRAI.getAppID());
		assertNull(TestValues.NULL, testRAI.getDeviceInfo());
	}
	
	public void testBuildSetAppIcon () {
		
		String testFileName = "test";
		Integer testCorrelationID = 0;
		SetAppIcon testSAI;
		
		// Test -- buildSetAppIcon(String sdlFileName, Integer correlationID)
		testSAI = RPCRequestFactory.buildSetAppIcon(testFileName, testCorrelationID);
		assertEquals(TestValues.MATCH, testFileName, testSAI.getSdlFileName());
		assertEquals(TestValues.MATCH, testCorrelationID, testSAI.getCorrelationID());
		
		testSAI = RPCRequestFactory.buildSetAppIcon(null, null);
		assertNull(TestValues.NULL, testSAI.getSdlFileName());
		assertNotNull(TestValues.NOT_NULL, testSAI.getCorrelationID());
		
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
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testHelpChunks, testSBP.getHelpPrompt()));
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testTimeoutChunks, testSBP.getTimeoutPrompt()));
		assertEquals(TestValues.MATCH, testCorrelationID, testSBP.getCorrelationID());
		
		testSBP = RPCRequestFactory.buildSetGlobalProperties((Vector<TTSChunk>) null, null, null);
		assertNull(TestValues.NULL, testSBP.getHelpPrompt());
		assertNull(TestValues.NULL, testSBP.getTimeoutPrompt());
		assertNotNull(TestValues.NOT_NULL, testSBP.getCorrelationID());
		
		// Test -- buildSetGlobalProperties(String helpPrompt, String timeoutPrompt, String vrHelpTitle, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildSetGlobalProperties(Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, String vrHelpTitle, Vector<VrHelpItem> vrHelp, Integer correlationID)
		testSBP = RPCRequestFactory.buildSetGlobalProperties(testHelpChunks, testTimeoutChunks, testHelpTitle, testVrHelp, testCorrelationID);
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testHelpChunks, testSBP.getHelpPrompt()));
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testTimeoutChunks, testSBP.getTimeoutPrompt()));
		assertEquals(TestValues.MATCH, testHelpTitle, testSBP.getVrHelpTitle());
		assertTrue(TestValues.TRUE, Validator.validateVrHelpItems(testVrHelp, testSBP.getVrHelp()));
		assertEquals(TestValues.MATCH, testCorrelationID, testSBP.getCorrelationID());
		
		testSBP = RPCRequestFactory.buildSetGlobalProperties((Vector<TTSChunk>) null, null, null, null, null);
		assertNull(TestValues.NULL, testSBP.getHelpPrompt());
		assertNull(TestValues.NULL, testSBP.getTimeoutPrompt());
		assertNull(TestValues.NULL, testSBP.getVrHelpTitle());
		assertNull(TestValues.NULL, testSBP.getVrHelp());
		assertNotNull(TestValues.NOT_NULL, testSBP.getCorrelationID());
	}
	
	public void testBuildSetMediaClockTimer () {
		
		Integer hours = 0, minutes = 0, seconds = 0, testCorrelationID = 0;
		UpdateMode testMode = UpdateMode.COUNTUP;
		SetMediaClockTimer testSMCT;
		
		// Test -- buildSetMediaClockTimer(Integer hours, Integer minutes, Integer seconds, UpdateMode updateMode, Integer correlationID)
		testSMCT = RPCRequestFactory.buildSetMediaClockTimer(hours, minutes, seconds, testMode, testCorrelationID);
		assertEquals(TestValues.MATCH, hours, testSMCT.getStartTime().getHours());
		assertEquals(TestValues.MATCH, minutes, testSMCT.getStartTime().getMinutes());
		assertEquals(TestValues.MATCH, seconds, testSMCT.getStartTime().getSeconds());
		assertEquals(TestValues.MATCH, testMode, testSMCT.getUpdateMode());
		assertEquals(TestValues.MATCH, testCorrelationID, testSMCT.getCorrelationID());
		
		testSMCT = RPCRequestFactory.buildSetMediaClockTimer(null, null, null, null, null);
		assertNull(TestValues.NULL, testSMCT.getStartTime());
		assertNull(TestValues.NULL, testSMCT.getUpdateMode());
		assertNotNull(TestValues.NOT_NULL, testSMCT.getCorrelationID());
		
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
		assertEquals(TestValues.MATCH, testText1, testShow.getMainField1());
		assertEquals(TestValues.MATCH, testText2, testShow.getMainField2());
		assertEquals(TestValues.MATCH, testText3, testShow.getMainField3());
		assertEquals(TestValues.MATCH, testText4, testShow.getMainField4());
		assertEquals(TestValues.MATCH, testStatusBar, testShow.getStatusBar());
		assertEquals(TestValues.MATCH, testMediaClock, testShow.getMediaClock());
		assertEquals(TestValues.MATCH, testMediaTrack, testShow.getMediaTrack());
		assertTrue(TestValues.TRUE, Validator.validateImage(testGraphic, testShow.getGraphic()));
		assertTrue(TestValues.TRUE, Validator.validateSoftButtons(testSoftButtons, testShow.getSoftButtons()));
		assertTrue(TestValues.TRUE, Validator.validateStringList(testCustomPresets, testShow.getCustomPresets()));
		assertEquals(TestValues.MATCH, testAlignment, testShow.getAlignment());
		assertEquals(TestValues.MATCH, testCorrelationID, testShow.getCorrelationID());
		
		testShow = RPCRequestFactory.buildShow(null, null, null, null, null, null, null, null, null, null, null, null);
		assertNull(TestValues.NULL, testShow.getMainField1());
		assertNull(TestValues.NULL, testShow.getMainField2());
		assertNull(TestValues.NULL, testShow.getMainField3());
		assertNull(TestValues.NULL, testShow.getMainField4());
		assertNull(TestValues.NULL, testShow.getStatusBar());
		assertNull(TestValues.NULL, testShow.getMediaClock());
		assertNull(TestValues.NULL, testShow.getMediaTrack());
		assertNull(TestValues.NULL, testShow.getGraphic());
		assertNull(TestValues.NULL, testShow.getSoftButtons());
		assertNull(TestValues.NULL, testShow.getCustomPresets());
		assertNull(TestValues.NULL, testShow.getAlignment());
		assertNotNull(TestValues.NOT_NULL, testShow.getCorrelationID());
		// Test -- buildShow(String mainText1, String mainText2, String mainText3, String mainText4, TextAlignment alignment, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildShow(String mainText1, String mainText2, String statusBar, String mediaClock, String mediaTrack, TextAlignment alignment, Integer correlationID)
		testShow = RPCRequestFactory.buildShow(testText1, testText2, testStatusBar, testMediaClock, testMediaTrack, testAlignment, testCorrelationID);
		assertEquals(TestValues.MATCH, testText1, testShow.getMainField1());
		assertEquals(TestValues.MATCH, testText2, testShow.getMainField2());
		assertEquals(TestValues.MATCH, testStatusBar, testShow.getStatusBar());
		assertEquals(TestValues.MATCH, testMediaClock, testShow.getMediaClock());
		assertEquals(TestValues.MATCH, testMediaTrack, testShow.getMediaTrack());
		assertEquals(TestValues.MATCH, testAlignment, testShow.getAlignment());
		assertEquals(TestValues.MATCH, testCorrelationID, testShow.getCorrelationID());
		
		testShow = RPCRequestFactory.buildShow(null, null, null, null, null, null, null);
		assertNull(TestValues.NULL, testShow.getMainField1());
		assertNull(TestValues.NULL, testShow.getMainField2());
		assertNull(TestValues.NULL, testShow.getStatusBar());
		assertNull(TestValues.NULL, testShow.getMediaClock());
		assertNull(TestValues.NULL, testShow.getMediaTrack());
		assertNull(TestValues.NULL, testShow.getAlignment());
		assertNotNull(TestValues.NOT_NULL, testShow.getCorrelationID());
		
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
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testTTSChunks, testSpeak.getTtsChunks()));
		assertEquals(TestValues.MATCH, testCorrelationID, testSpeak.getCorrelationID());
		
		testSpeak = RPCRequestFactory.buildSpeak((String) null, null);
		assertNull(TestValues.NULL, testSpeak.getTtsChunks());
		assertNotNull(TestValues.NOT_NULL, testSpeak.getCorrelationID());
		
		// Test -- buildSpeak(Vector<TTSChunk> ttsChunks, Integer correlationID)
		testSpeak = RPCRequestFactory.buildSpeak(testTTSChunks, testCorrelationID);
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testTTSChunks, testSpeak.getTtsChunks()));
		assertEquals(TestValues.MATCH, testCorrelationID, testSpeak.getCorrelationID());
		
		testSpeak = RPCRequestFactory.buildSpeak((Vector<TTSChunk>) null, null);
		assertNull(TestValues.NULL, testSpeak.getTtsChunks());
		assertNotNull(TestValues.NOT_NULL, testSpeak.getCorrelationID());
	}
	
	public void testBuildSubscribeButton () {
		
		ButtonName testButtonName = ButtonName.CUSTOM_BUTTON;
		Integer testCorrelationID = 0;
		SubscribeButton testSB;
		
		// Test -- buildSubscribeButton(ButtonName buttonName, Integer correlationID)
		testSB = RPCRequestFactory.buildSubscribeButton(testButtonName, testCorrelationID);
		assertEquals(TestValues.MATCH, testButtonName, testSB.getButtonName());
		assertEquals(TestValues.MATCH, testCorrelationID, testSB.getCorrelationID());
		
		testSB = RPCRequestFactory.buildSubscribeButton(null, null);
		assertNull(TestValues.NULL, testSB.getButtonName());
		assertNotNull(TestValues.NOT_NULL, testSB.getCorrelationID());
		
	}
	
	public void testBuildUnregisterAppInterface () {
		
		Integer testCorrelationID = 0;
		UnregisterAppInterface testUAI;
		
		// Test -- buildUnregisterAppInterface(Integer correlationID)
		testUAI = RPCRequestFactory.buildUnregisterAppInterface(testCorrelationID);
		assertEquals(TestValues.MATCH, testCorrelationID, testUAI.getCorrelationID());
		
		testUAI = RPCRequestFactory.buildUnregisterAppInterface(null);
		assertNotNull(TestValues.NOT_NULL, testUAI.getCorrelationID());
	}
	
	public void testBuildUnsubscribeButton () {
		
		ButtonName testButtonName = ButtonName.CUSTOM_BUTTON;
		Integer testCorrelationID = 0;
		UnsubscribeButton testUB;
		
		// Test -- buildUnsubscribeButton(ButtonName buttonName, Integer correlationID)
		testUB = RPCRequestFactory.buildUnsubscribeButton(testButtonName, testCorrelationID);
		assertEquals(TestValues.MATCH, testButtonName, testUB.getButtonName());
		assertEquals(TestValues.MATCH, testCorrelationID, testUB.getCorrelationID());
		
		testUB = RPCRequestFactory.buildUnsubscribeButton(null, null);
		assertNull(TestValues.NULL, testUB.getButtonName());
		assertNotNull(TestValues.NOT_NULL, testUB.getCorrelationID());
		
	}
	
	public void testBuildSubscribeVehicleData () {
		
		boolean testGPS = true, testSpeed = true, testRPM = true, testFuelLevel = true, testFuelLevelState = true, testInstantFuelConsumption = true, testExternalTemperature = true, testPRNDL = true, testTirePressure = true, testOdometer = true, testBeltStatus = true, testBodyInformation = true, testDeviceStatus = true, testDriverBraking = true;
		Integer testCorrelationID = 0;
		SubscribeVehicleData testSVD;
		
		// Test -- BuildSubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID) 
		testSVD = RPCRequestFactory.BuildSubscribeVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, testCorrelationID);	
		assertTrue(TestValues.TRUE, testSVD.getGps());
		assertTrue(TestValues.TRUE, testSVD.getSpeed());
		assertTrue(TestValues.TRUE, testSVD.getRpm());
		assertTrue(TestValues.TRUE, testSVD.getFuelLevel());
		assertTrue(TestValues.TRUE, testSVD.getFuelLevelState());
		assertTrue(TestValues.TRUE, testSVD.getInstantFuelConsumption());
		assertTrue(TestValues.TRUE, testSVD.getExternalTemperature());
		assertTrue(TestValues.TRUE, testSVD.getPrndl());
		assertTrue(TestValues.TRUE, testSVD.getTirePressure());
		assertTrue(TestValues.TRUE, testSVD.getOdometer());
		assertTrue(TestValues.TRUE, testSVD.getBeltStatus());
		assertTrue(TestValues.TRUE, testSVD.getBodyInformation());
		assertTrue(TestValues.TRUE, testSVD.getDeviceStatus());
		assertTrue(TestValues.TRUE, testSVD.getDriverBraking());
		assertEquals(TestValues.MATCH, testCorrelationID, testSVD.getCorrelationID());
		
		testSVD = RPCRequestFactory.BuildSubscribeVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, null);
		assertNotNull(TestValues.NULL, testSVD.getCorrelationID());
	}
	
	public void testBuildUnsubscribeVehicleData () {
		
		boolean testGPS = true, testSpeed = true, testRPM = true, testFuelLevel = true, testFuelLevelState = true, testInstantFuelConsumption = true, testExternalTemperature = true, testPRNDL = true, testTirePressure = true, testOdometer = true, testBeltStatus = true, testBodyInformation = true, testDeviceStatus = true, testDriverBraking = true;
		Integer testCorrelationID = 0;
		UnsubscribeVehicleData testUVD;
		
		// Test -- BuildUnsubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID) 
		testUVD = RPCRequestFactory.BuildUnsubscribeVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, testCorrelationID);	
		assertTrue(TestValues.TRUE, testUVD.getGps());
		assertTrue(TestValues.TRUE, testUVD.getSpeed());
		assertTrue(TestValues.TRUE, testUVD.getRpm());
		assertTrue(TestValues.TRUE, testUVD.getFuelLevel());
		assertTrue(TestValues.TRUE, testUVD.getFuelLevelState());
		assertTrue(TestValues.TRUE, testUVD.getInstantFuelConsumption());
		assertTrue(TestValues.TRUE, testUVD.getExternalTemperature());
		assertTrue(TestValues.TRUE, testUVD.getPrndl());
		assertTrue(TestValues.TRUE, testUVD.getTirePressure());
		assertTrue(TestValues.TRUE, testUVD.getOdometer());
		assertTrue(TestValues.TRUE, testUVD.getBeltStatus());
		assertTrue(TestValues.TRUE, testUVD.getBodyInformation());
		assertTrue(TestValues.TRUE, testUVD.getDeviceStatus());
		assertTrue(TestValues.TRUE, testUVD.getDriverBraking());
		assertEquals(TestValues.MATCH, testCorrelationID, testUVD.getCorrelationID());
		
		testUVD = RPCRequestFactory.BuildUnsubscribeVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, null);
		assertNotNull(TestValues.NULL, testUVD.getCorrelationID());
	}
	
	public void testBuildGetVehicleData () {
		
		boolean testGPS = true, testSpeed = true, testRPM = true, testFuelLevel = true, testFuelLevelState = true, testInstantFuelConsumption = true, testExternalTemperature = true, testVIN = true, testPRNDL = true, testTirePressure = true, testOdometer = true, testBeltStatus = true, testBodyInformation = true, testDeviceStatus = true, testDriverBraking = true;
		Integer testCorrelationID = 0;
		GetVehicleData testGVD;
		
		// Test -- BuildGetVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean vin, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID)
		testGVD = RPCRequestFactory.BuildGetVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testVIN, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, testCorrelationID);	
		assertTrue(TestValues.TRUE, testGVD.getGps());
		assertTrue(TestValues.TRUE, testGVD.getSpeed());
		assertTrue(TestValues.TRUE, testGVD.getRpm());
		assertTrue(TestValues.TRUE, testGVD.getFuelLevel());
		assertTrue(TestValues.TRUE, testGVD.getFuelLevelState());
		assertTrue(TestValues.TRUE, testGVD.getInstantFuelConsumption());
		assertTrue(TestValues.TRUE, testGVD.getExternalTemperature());
		assertTrue(TestValues.TRUE, testGVD.getPrndl());
		assertTrue(TestValues.TRUE, testGVD.getTirePressure());
		assertTrue(TestValues.TRUE, testGVD.getOdometer());
		assertTrue(TestValues.TRUE, testGVD.getBeltStatus());
		assertTrue(TestValues.TRUE, testGVD.getBodyInformation());
		assertTrue(TestValues.TRUE, testGVD.getDeviceStatus());
		assertTrue(TestValues.TRUE, testGVD.getDriverBraking());
		assertTrue(TestValues.TRUE, testGVD.getVin());
		assertEquals(TestValues.MATCH, testCorrelationID, testGVD.getCorrelationID());
		
		testGVD = RPCRequestFactory.BuildGetVehicleData(testGPS, testSpeed, testRPM, testFuelLevel, testFuelLevelState, testInstantFuelConsumption, testExternalTemperature, testVIN, testPRNDL, testTirePressure, testOdometer, testBeltStatus, testBodyInformation, testDeviceStatus, testDriverBraking, null);
		assertNotNull(TestValues.NULL, testGVD.getCorrelationID());
	}
	
	public void testBuildScrollableMessage () {
		
		String testSMB = "test";
		Integer testTimeout = 1, testCorrelationID = 0;
		Vector<SoftButton> testSoftButtons = new Vector<SoftButton>();
		testSoftButtons.add(new SoftButton());
		ScrollableMessage testSM;
		
		// Test -- BuildScrollableMessage(String scrollableMessageBody, Integer timeout, Vector<SoftButton> softButtons, Integer correlationID)	
		testSM = RPCRequestFactory.BuildScrollableMessage(testSMB, testTimeout, testSoftButtons, testCorrelationID);
		assertEquals(TestValues.MATCH, testSMB, testSM.getScrollableMessageBody());
		assertEquals(TestValues.MATCH, testTimeout, testSM.getTimeout());
		assertTrue(TestValues.TRUE, Validator.validateSoftButtons(testSoftButtons, testSM.getSoftButtons()));
		assertEquals(TestValues.MATCH, testCorrelationID, testSM.getCorrelationID());
		
		testSM = RPCRequestFactory.BuildScrollableMessage(null, null, null, null);
		assertNull(TestValues.NULL, testSM.getScrollableMessageBody());
		assertNull(TestValues.NULL, testSM.getTimeout());
		assertNull(TestValues.NULL, testSM.getSoftButtons());
		assertNotNull(TestValues.NOT_NULL, testSM.getCorrelationID());
	}
	
	public void testBuildSlider () {
		
		Integer testTicks = 1, testPosition = 2, testTimeout = 3, testCorrelationID = 0;
		String testHeader = "header";
		Vector<String> testFooter = new Vector<String>();
		testFooter.add("footer");
		Slider testSlider;
		
		// Test -- BuildSlider(Integer numTicks, Integer position, String sliderHeader, Vector<String> sliderFooter, Integer timeout, Integer correlationID)
		testSlider = RPCRequestFactory.BuildSlider(testTicks, testPosition, testHeader, testFooter, testTimeout, testCorrelationID);
		assertEquals(TestValues.MATCH, testTicks, testSlider.getNumTicks());
		assertEquals(TestValues.MATCH, testPosition, testSlider.getPosition());
		assertEquals(TestValues.MATCH, testHeader, testSlider.getSliderHeader());
		assertTrue(TestValues.TRUE, Validator.validateStringList(testFooter, testSlider.getSliderFooter()));
		assertEquals(TestValues.MATCH, testCorrelationID, testSlider.getCorrelationID());
		
		testSlider = RPCRequestFactory.BuildSlider(null, null, null, null, null, null);
		assertNull(TestValues.NULL, testSlider.getNumTicks());
		assertNull(TestValues.NULL, testSlider.getPosition());
		assertNull(TestValues.NULL, testSlider.getSliderHeader());
		assertNull(TestValues.NULL, testSlider.getSliderFooter());
		assertNull(TestValues.NULL, testSlider.getTimeout());
		assertNotNull(TestValues.NOT_NULL, testSlider.getCorrelationID());
	}
	
	public void testBuildChangeRegistration () {
		
		Language testLang = Language.EN_US, testHMILang = Language.EN_AU;
		Integer testCorrelationID = 0;
		ChangeRegistration testCR;
		
		// Test -- BuildChangeRegistration(Language language, Language hmiDisplayLanguage, Integer correlationID)
		testCR = RPCRequestFactory.BuildChangeRegistration(testLang, testHMILang, testCorrelationID);
		assertEquals(TestValues.MATCH, testLang, testCR.getLanguage());
		assertEquals(TestValues.MATCH, testHMILang, testCR.getHmiDisplayLanguage());
		assertEquals(TestValues.MATCH, testCorrelationID, testCR.getCorrelationID());
		
		testCR = RPCRequestFactory.BuildChangeRegistration(null, null, null);
		assertNull(TestValues.NULL, testCR.getLanguage());
		assertNull(TestValues.NULL, testCR.getHmiDisplayLanguage());
		assertNotNull(TestValues.NOT_NULL, testCR.getCorrelationID());
	}
	
	public void testBuildSetDisplayLayout () {
		
		String testDL = "layout";
		Integer testCorrelationID = 0;
		SetDisplayLayout testSDL;
		
		// Test -- BuildSetDisplayLayout(String displayLayout, Integer correlationID)
		testSDL = RPCRequestFactory.BuildSetDisplayLayout(testDL, testCorrelationID);
		assertEquals(TestValues.MATCH, testDL, testSDL.getDisplayLayout());
		assertEquals(TestValues.MATCH, testCorrelationID, testSDL.getCorrelationID());
		
		testSDL = RPCRequestFactory.BuildSetDisplayLayout(null, null);
		assertNull(TestValues.NULL, testSDL.getDisplayLayout());
		assertNotNull(TestValues.NOT_NULL, testSDL.getCorrelationID());
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
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(testInitialPrompt, testPAPT.getInitialPrompt()));
		assertEquals(TestValues.MATCH, testAPTDT1, testPAPT.getAudioPassThruDisplayText1());
		assertEquals(TestValues.MATCH, testAPTDT2, testPAPT.getAudioPassThruDisplayText2());
		assertEquals(TestValues.MATCH, testSR, testPAPT.getSamplingRate());
		assertEquals(TestValues.MATCH, testMaxDuration, testPAPT.getMaxDuration());
		assertEquals(TestValues.MATCH, testBits, testPAPT.getBitsPerSample());
		assertEquals(TestValues.MATCH, testAT, testPAPT.getAudioType());
		assertEquals(TestValues.MATCH, testMute, testPAPT.getMuteAudio());
		assertEquals(TestValues.MATCH, testCorrelationID, testPAPT.getCorrelationID());
		
		testPAPT = RPCRequestFactory.BuildPerformAudioPassThru((Vector<TTSChunk>) null, null, null, null, null, null, null, null, null);
		assertNull(TestValues.NULL, testPAPT.getInitialPrompt());
		assertNull(TestValues.NULL, testPAPT.getAudioPassThruDisplayText1());
		assertNull(TestValues.NULL, testPAPT.getAudioPassThruDisplayText2());
		assertNull(TestValues.NULL, testPAPT.getSamplingRate());
		assertNull(TestValues.NULL, testPAPT.getMaxDuration());
		assertNull(TestValues.NULL, testPAPT.getBitsPerSample());
		assertNull(TestValues.NULL, testPAPT.getAudioType());
		assertNull(TestValues.NULL, testPAPT.getMuteAudio());
		assertNotNull(TestValues.NOT_NULL, testPAPT.getCorrelationID());
	}
	
	public void testBuildEndAudioPassThru () {
		
		Integer testCorrelationID = 0;
		EndAudioPassThru testEAPT;
		
		// Test -- BuildEndAudioPassThru(Integer correlationID)
		testEAPT = RPCRequestFactory.BuildEndAudioPassThru(testCorrelationID);
		assertEquals(TestValues.MATCH, testCorrelationID, testEAPT.getCorrelationID());
		
		testEAPT = RPCRequestFactory.BuildEndAudioPassThru(null);
		assertNotNull(TestValues.NOT_NULL, testEAPT.getCorrelationID());
	}	
}