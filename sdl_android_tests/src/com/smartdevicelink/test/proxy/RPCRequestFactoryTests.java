package com.smartdevicelink.test.proxy;

import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequestFactory;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.RegisterAppInterface;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SetAppIcon;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SystemRequest;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.test.utils.Validator;

import junit.framework.TestCase;

public class RPCRequestFactoryTests extends TestCase {
	
	private final String MSG = "Response value did not match tested value.";

	public void testBuildSystemRequest () {
		
		String         testData;
		Integer        testInt;
		SystemRequest  testBSR;
		Vector<String> testVData;
		
		// Test -- buildSystemRequest(String data, Integer correlationID)
		testData = "test";
		testInt  = 0;
		testBSR  = RPCRequestFactory.buildSystemRequest(testData, testInt);
		assertEquals(MSG, testData.getBytes(), testBSR.getBulkData());
		assertEquals(MSG, testInt, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequest(testData, null);
		assertNull(MSG, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequest(null, testInt);
		assertNull(MSG, testBSR);
				
		// Test -- buildSystemRequestLegacy(Vector<String> data, Integer correlationID)
		testVData = new Vector<String>();
		testVData.add("Test A");
		testVData.add("Test B");
		testVData.add("Test C");
		testBSR   = RPCRequestFactory.buildSystemRequestLegacy(testVData, testInt);
		assertEquals(MSG, testVData, new Vector<String>(testBSR.getLegacyData()));
		assertEquals(MSG, testInt, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequestLegacy(testVData, null);
		assertNull(MSG, testBSR.getCorrelationID());
		
		testBSR  = RPCRequestFactory.buildSystemRequestLegacy(null, testInt);
		assertNull(MSG, testBSR);
		
		// Issue #166 -- Null values within the Vector<String> parameter.
		// TODO: Once resolved, add the following test.
		//testVData = new Vector<String>();
		//testVData.add("Test A");
		//testVData.add("Test B");
		//testVData.add(null);
		//testBSR   = RPCRequestFactory.buildSystemRequestLegacy(testVData, testInt);
		//assertNull(MSG, testBSR);		
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
		assertEquals(MSG, testCommandID, testBAC.getCmdID());
		assertEquals(MSG, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(MSG, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(MSG, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(MSG, testVrCommands, testBAC.getVrCommands());
		assertEquals(MSG, Validator.validateImage(testImage, testBAC.getCmdIcon()));
		assertEquals(MSG, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null, null);
		assertNull(MSG, testBAC.getCmdID());
		assertNull(MSG, testBAC.getMenuParams().getMenuName());
		assertNull(MSG, testBAC.getMenuParams().getParentID());
		assertNull(MSG, testBAC.getMenuParams().getPosition());
		assertNull(MSG, testBAC.getVrCommands());
		assertNull(MSG, testBAC.getCmdIcon());
		assertNull(MSG, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position, Vector<String> vrCommands, String IconValue, ImageType IconType, Integer correlationID)
		testIconValue = "icon";
		testIconType  = ImageType.STATIC;
		testImage     = new Image();
		testImage.setValue(testIconValue);
		testImage.setImageType(testIconType);
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testParentID, testPosition, testVrCommands, testIconValue, testIconType, testCorrelationID);
		assertEquals(MSG, testCommandID, testBAC.getCmdID());
		assertEquals(MSG, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(MSG, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(MSG, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(MSG, testVrCommands, testBAC.getVrCommands());
		assertEquals(MSG, testCorrelationID, testBAC.getCorrelationID());		
		assertEquals(MSG, Validator.validateImage(testImage, testBAC.getCmdIcon()));
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null, null, null);
		assertNull(MSG, testBAC.getCmdID());
		assertNull(MSG, testBAC.getMenuParams().getMenuName());
		assertNull(MSG, testBAC.getMenuParams().getParentID());
		assertNull(MSG, testBAC.getMenuParams().getPosition());
		assertNull(MSG, testBAC.getVrCommands());
		assertNull(MSG, testBAC.getCmdIcon().getValue());
		assertNull(MSG, testBAC.getCmdIcon().getImageType());
		assertNull(MSG, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Integer parentID, Integer position, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testParentID, testPosition, testVrCommands, testCorrelationID);
		assertEquals(MSG, testCommandID, testBAC.getCmdID());
		assertEquals(MSG, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(MSG, testParentID, testBAC.getMenuParams().getParentID());
		assertEquals(MSG, testPosition, testBAC.getMenuParams().getPosition());
		assertEquals(MSG, testVrCommands, testBAC.getVrCommands());
		assertEquals(MSG, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null, null, null);
		assertNull(MSG, testBAC.getCmdID());
		assertNull(MSG, testBAC.getMenuParams().getMenuName());
		assertNull(MSG, testBAC.getMenuParams().getParentID());
		assertNull(MSG, testBAC.getMenuParams().getPosition());
		assertNull(MSG, testBAC.getVrCommands());
		assertNull(MSG, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, String menuText, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testMenuText, testVrCommands, testCorrelationID);
		assertEquals(MSG, testCommandID, testBAC.getCmdID());
		assertEquals(MSG, testMenuText, testBAC.getMenuParams().getMenuName());
		assertEquals(MSG, testVrCommands, testBAC.getVrCommands());
		assertEquals(MSG, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null, null);
		assertNull(MSG, testBAC.getCmdID());
		assertNull(MSG, testBAC.getMenuParams().getMenuName());
		assertNull(MSG, testBAC.getVrCommands());
		assertNull(MSG, testBAC.getCorrelationID());
		
		// Test -- buildAddCommand(Integer commandID, Vector<String> vrCommands, Integer correlationID)
		testBAC = RPCRequestFactory.buildAddCommand(testCommandID, testVrCommands, testCorrelationID);
		assertEquals(MSG, testCommandID, testBAC.getCmdID());
		assertEquals(MSG, testVrCommands, testBAC.getVrCommands());
		assertEquals(MSG, testCorrelationID, testBAC.getCorrelationID());
		
		testBAC = RPCRequestFactory.buildAddCommand(null, null, null);
		assertNull(MSG, testBAC.getCmdID());
		assertNull(MSG, testBAC.getVrCommands());
		assertNull(MSG, testBAC.getCorrelationID());
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
		assertEquals(MSG, testMenuID, testBASM.getMenuID());
		assertEquals(MSG, testMenuName, testBASM.getMenuName());
		assertEquals(MSG, testPosition, testBASM.getPosition());
		assertEquals(MSG, testCorrelationID, testBASM.getCorrelationID());
		
		testBASM = RPCRequestFactory.buildAddSubMenu(null, null, null, null);
		assertNull(MSG, testBASM.getMenuID());
		assertNull(MSG, testBASM.getMenuName());
		assertNull(MSG, testBASM.getPosition());
		assertNull(MSG, testBASM.getCorrelationID());
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
		assertTrue(MSG, Validator.validateTtsChunks(TTSChunkFactory.createSimpleTTSChunks(testTTSText), testAlert.getTtsChunks()));
		// ^ Calls another build method.
		
		// Test -- buildAlert(String alertText1, String alertText2, String alertText3, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testAlertText1 = "test 1";
		testAlertText2 = "test 2";
		testAlertText3 = "test 3";
		testDuration   = 1;	
		// ^ Calls another build method.
		
		// Test -- buildAlert(String ttsText, String alertText1, String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testAlert = RPCRequestFactory.buildAlert(testTTSText, testAlertText1, testAlertText2, testAlertText3, testPlayTone, testDuration, testSoftButtons, testCorrelationID);
		assertTrue(MSG, Validator.validateTtsChunks(TTSChunkFactory.createSimpleTTSChunks(testTTSText), testAlert.getTtsChunks()));
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> chunks, Boolean playTone, Vector<SoftButton> softButtons, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildAlert(Vector<TTSChunk> ttsChunks, String alertText1, String alertText2, String alertText3, Boolean playTone, Integer duration, Vector<SoftButton> softButtons, Integer correlationID)
		testTtsChunks = TTSChunkFactory.createSimpleTTSChunks(testTTSText);
		testAlert = RPCRequestFactory.buildAlert(testTtsChunks, testAlertText1, testAlertText2, testAlertText3, testPlayTone, testDuration, testSoftButtons, testCorrelationID);
		assertTrue(MSG, Validator.validateTtsChunks(testTtsChunks, testAlert.getTtsChunks()));
		assertEquals(MSG, testAlertText1, testAlert.getAlertText1());
		assertEquals(MSG, testAlertText2, testAlert.getAlertText2());
		assertEquals(MSG, testAlertText3, testAlert.getAlertText3());
		assertEquals(MSG, testPlayTone, testAlert.getPlayTone());
		assertEquals(MSG, testDuration, testAlert.getDuration());
		assertTrue(MSG, Validator.validateSoftButtons(testSoftButtons, testAlert.getSoftButtons()));
		assertEquals(MSG, testCorrelationID, testAlert.getCorrelationID());
		
		testAlert = RPCRequestFactory.buildAlert((Vector<TTSChunk>) null, null, null, null, null, null, null, null);
		assertNull(MSG, testAlert.getTtsChunks());
		assertNull(MSG, testAlert.getAlertText1());
		assertNull(MSG, testAlert.getAlertText2());
		assertNull(MSG, testAlert.getAlertText3());
		assertNull(MSG, testAlert.getPlayTone());
		assertNull(MSG, testAlert.getDuration());
		assertNull(MSG, testAlert.getSoftButtons());
		assertNull(MSG, testAlert.getCorrelationID());
		
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
		assertTrue(MSG, Validator.validateTtsChunks(testTtsChunks, testAlert.getTtsChunks()));
		assertEquals(MSG, testAlertText1, testAlert.getAlertText1());
		assertEquals(MSG, testAlertText2, testAlert.getAlertText2());
		assertEquals(MSG, testPlayTone, testAlert.getPlayTone());
		assertEquals(MSG, testDuration, testAlert.getDuration());
		assertEquals(MSG, testCorrelationID, testAlert.getCorrelationID());
		
		testAlert = RPCRequestFactory.buildAlert((Vector<TTSChunk>) null, null, null, null, null, null);
		assertNull(MSG, testAlert.getTtsChunks());
		assertNull(MSG, testAlert.getAlertText1());
		assertNull(MSG, testAlert.getAlertText2());
		assertNull(MSG, testAlert.getPlayTone());
		assertNull(MSG, testAlert.getDuration());
		assertNull(MSG, testAlert.getCorrelationID());
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
		assertEquals(MSG, testChoiceSet, testBCICS.getChoiceSet());
		assertEquals(MSG, testICSID, testBCICS.getInteractionChoiceSetID());
		assertEquals(MSG, testCorrelationID, testBCICS.getCorrelationID());
		
		testBCICS = RPCRequestFactory.buildCreateInteractionChoiceSet(null, null, null);
		assertNull(MSG, testBCICS.getChoiceSet());
		assertNull(MSG, testBCICS.getInteractionChoiceSetID());
		assertNull(MSG, testBCICS.getCorrelationID());
	}
	
	public void testBuildDeleteCommand () {
		
		Integer testCID, testCorrelationID;
		DeleteCommand testDC;
		
		// Test -- buildDeleteCommand(Integer commandID, Integer correlationID)
		testCID = 0;
		testCorrelationID = 1;
		testDC = RPCRequestFactory.buildDeleteCommand(testCID, testCorrelationID);
		assertEquals(MSG, testCID, testDC.getCmdID());
		assertEquals(MSG, testCorrelationID, testDC.getCorrelationID());
		
		testDC = RPCRequestFactory.buildDeleteCommand(null, null);
		assertNull(MSG, testDC.getCmdID());
		assertNull(MSG, testDC.getCorrelationID());
		
	}
	
	public void testBuildDeleteFile () {
		
		Integer testCorrelationID;
		String testFileName;
		DeleteFile testDF;
		
		// Test --buildDeleteFile(String sdlFileName, Integer correlationID)
		testCorrelationID = 0;
		testFileName = "test";
		testDF = RPCRequestFactory.buildDeleteFile(testFileName, testCorrelationID);
		assertEquals(MSG, testCorrelationID, testDF.getCorrelationID());
		assertEquals(MSG, testFileName, testDF.getSdlFileName());
		
		testDF = RPCRequestFactory.buildDeleteFile(null, null);
		assertNull(MSG, testDF.getCorrelationID());
		assertNull(MSG, testDF.getSdlFileName());
		
	}
	
	public void testBuildDeleteInteractionChoiceSet () {
		
		Integer testICSID, testCorrelationID;
		DeleteInteractionChoiceSet testDICS;
		
		// Test -- buildDeleteInteractionChoiceSet(Integer interactionChoiceSetID, Integer correlationID)
		testICSID = 0;
		testCorrelationID = 1;
		testDICS = RPCRequestFactory.buildDeleteInteractionChoiceSet(testICSID, testCorrelationID);
		assertEquals(MSG, testICSID, testDICS.getInteractionChoiceSetID());
		assertEquals(MSG, testCorrelationID, testDICS.getCorrelationID());
		
		testDICS = RPCRequestFactory.buildDeleteInteractionChoiceSet(null, null);
		assertNull(MSG, testDICS.getInteractionChoiceSetID());
		assertNull(MSG, testDICS.getCorrelationID());
	}
	
	public void testBuildDeleteSubMenu () {
		
		Integer testMenuID, testCorrelationID;
		DeleteSubMenu testDSM;
		
		// Test -- buildDeleteSubMenu(Integer menuID, Integer correlationID)
		testMenuID = 0;
		testCorrelationID = 1;
		testDSM = RPCRequestFactory.buildDeleteSubMenu(testMenuID, testCorrelationID);
		assertEquals(MSG, testMenuID, testDSM.getMenuID());
		assertEquals(MSG, testCorrelationID, testDSM.getCorrelationID());
		
		testDSM = RPCRequestFactory.buildDeleteSubMenu(null, null);
		assertNull(MSG, testDSM.getMenuID());
		assertNull(MSG, testDSM.getCorrelationID());
	}
	
	public void testBuildListFiles () {
		
		Integer testCorrelationID = 0;
		ListFiles testLF;
		
		// Test -- buildListFiles(Integer correlationID)
		testLF = RPCRequestFactory.buildListFiles(testCorrelationID);
		assertEquals(MSG, testCorrelationID, testLF.getCorrelationID());
				
		testLF = RPCRequestFactory.buildListFiles(null);
		assertNull(MSG, testLF.getCorrelationID());
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
		VrHelpItem item = new VrHelpItem();
		item.setText("test 1");
		testVrHelpItems = new Vector<VrHelpItem>();
		testVrHelpItems.add(item);
		testCSIDs = new Vector<Integer>();
		testCSIDs.add(0);
		testCSIDs.add(1);		
		testPI = RPCRequestFactory.buildPerformInteraction(testInitChunks, testDisplayText, testCSIDs, testHelpChunks, testTimeoutChunks, testIM, testTimeout, testVrHelpItems, testCorrelationID);
		assertTrue(MSG, Validator.validateTtsChunks(testInitChunks, testPI.getInitialPrompt()));
		assertEquals(MSG, testDisplayText, testPI.getInitialText());
		assertTrue(MSG, Validator.validateIntegerList(testCSIDs, testPI.getInteractionChoiceSetIDList()));
		assertTrue(MSG, Validator.validateTtsChunks(testHelpChunks, testPI.getHelpPrompt()));
		assertTrue(MSG, Validator.validateTtsChunks(testTimeoutChunks, testPI.getTimeoutPrompt()));
		assertEquals(MSG, testIM, testPI.getInteractionMode());
		assertEquals(MSG, testTimeout, testPI.getTimeout());
		assertTrue(MSG, Validator.validateVrHelpItems(testVrHelpItems, testPI.getVrHelp()));
		assertEquals(MSG, testCorrelationID, testPI.getCorrelationID());
				
		testPI = RPCRequestFactory.buildPerformInteraction((Vector<TTSChunk>) null, null, null, null, null, null, null, null, null);
		assertNull(MSG, testPI.getInitialPrompt());
		assertNull(MSG, testPI.getInitialText());
		assertNull(MSG, testPI.getInteractionChoiceSetIDList());
		assertNull(MSG, testPI.getHelpPrompt());
		assertNull(MSG, testPI.getTimeoutPrompt());
		assertNull(MSG, testPI.getInteractionMode());
		assertNull(MSG, testPI.getTimeout());
		assertNull(MSG, testPI.getVrHelp());
		assertNull(MSG, testPI.getCorrelationID());
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, Vector<VrHelpItem> vrHelp, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(Vector<TTSChunk> initChunks, String displayText, Vector<Integer> interactionChoiceSetIDList, Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		testPI = RPCRequestFactory.buildPerformInteraction(testInitChunks, testDisplayText, testCSIDs, testHelpChunks, testTimeoutChunks, testIM, testTimeout, testCorrelationID);
		assertTrue(MSG, Validator.validateTtsChunks(testInitChunks, testPI.getInitialPrompt()));
		assertEquals(MSG, testDisplayText, testPI.getInitialText());
		assertTrue(MSG, Validator.validateIntegerList(testCSIDs, testPI.getInteractionChoiceSetIDList()));
		assertTrue(MSG, Validator.validateTtsChunks(testHelpChunks, testPI.getHelpPrompt()));
		assertTrue(MSG, Validator.validateTtsChunks(testTimeoutChunks, testPI.getTimeoutPrompt()));
		assertEquals(MSG, testIM, testPI.getInteractionMode());
		assertEquals(MSG, testTimeout, testPI.getTimeout());
		assertEquals(MSG, testCorrelationID, testPI.getCorrelationID());
		
		testPI = RPCRequestFactory.buildPerformInteraction((Vector<TTSChunk>) null, null, null, null, null, null, null, null);
		assertNull(MSG, testPI.getInitialPrompt());
		assertNull(MSG, testPI.getInitialText());
		assertNull(MSG, testPI.getInteractionChoiceSetIDList());
		assertNull(MSG, testPI.getHelpPrompt());
		assertNull(MSG, testPI.getTimeoutPrompt());
		assertNull(MSG, testPI.getInteractionMode());
		assertNull(MSG, testPI.getTimeout());
		assertNull(MSG, testPI.getCorrelationID());
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, String helpPrompt, String timeoutPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Integer interactionChoiceSetID, Integer correlationID)
		// ^ Calls another build method.
		
		// Test -- buildPerformInteraction(Vector<TTSChunk> initChunks, String displayText, Vector<Integer> interactionChoiceSetIDList, Vector<TTSChunk> helpChunks, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		testPI = RPCRequestFactory.buildPerformInteraction(testInitChunks, testDisplayText, testCSIDs, testHelpChunks, testIM, testTimeout, testCorrelationID);
		assertTrue(MSG, Validator.validateTtsChunks(testInitChunks, testPI.getInitialPrompt()));
		assertEquals(MSG, testDisplayText, testPI.getInitialText());
		assertTrue(MSG, Validator.validateIntegerList(testCSIDs, testPI.getInteractionChoiceSetIDList()));
		assertTrue(MSG, Validator.validateTtsChunks(testHelpChunks, testPI.getHelpPrompt()));
		assertEquals(MSG, testIM, testPI.getInteractionMode());
		assertEquals(MSG, testTimeout, testPI.getTimeout());
		assertEquals(MSG, testCorrelationID, testPI.getCorrelationID());
		
		testPI = RPCRequestFactory.buildPerformInteraction((Vector<TTSChunk>) null, null, null, null, null, null, null);
		assertNull(MSG, testPI.getInitialPrompt());
		assertNull(MSG, testPI.getInitialText());
		assertNull(MSG, testPI.getInteractionChoiceSetIDList());
		assertNull(MSG, testPI.getHelpPrompt());
		assertNull(MSG, testPI.getInteractionMode());
		assertNull(MSG, testPI.getTimeout());
		assertNull(MSG, testPI.getCorrelationID());
		
		// Test -- buildPerformInteraction(String initPrompt, String displayText, Vector<Integer> interactionChoiceSetIDList, String helpPrompt, InteractionMode interactionMode, Integer timeout, Integer correlationID)
		// ^ Calls another build method.
	}
	
	public void testBuildPutFiles () {
		
		String testFileName = "test";
		Boolean testPFile = true, testSystemFile = true;
		Integer testCorrelationID = 0, testOffset = 1, testLength = 2;
		FileType testFileType = FileType.BINARY;		
		byte[] testFileData = {(byte) 0x00, (byte) 0x01, (byte) 0x02 };
		PutFile testPF;
		
		// Test -- buildPutFile(String sdlFileName, FileType fileType, Boolean persistentFile, byte[] fileData, Integer correlationID)
		testPF = RPCRequestFactory.buildPutFile(testFileName, testFileType, testPFile, testFileData, testCorrelationID);
		assertEquals(MSG, testFileName, testPF.getSdlFileName());
		assertEquals(MSG, testFileType, testPF.getFileType());
		assertEquals(MSG, testPFile, testPF.getPersistentFile());
		assertTrue(MSG, Validator.validateBulkData(testFileData, testPF.getFileData()));
		assertEquals(MSG, testCorrelationID, testPF.getCorrelationID());
		
		testPF = RPCRequestFactory.buildPutFile(null, null, null, null, null);
		assertNull(MSG, testPF.getSdlFileName());
		assertNull(MSG, testPF.getFileType());
		assertNull(MSG, testPF.getPersistentFile());
		assertNull(MSG, testPF.getFileData());
		assertNull(MSG, testPF.getCorrelationID());
		
		// Test -- buildPutFile(String sdlFileName, Integer iOffset, Integer iLength)
		testPF = RPCRequestFactory.buildPutFile(testFileName, testOffset, testLength);
		assertEquals(MSG, testFileName, testPF.getSdlFileName());
		assertEquals(MSG, testOffset, testPF.getOffset());
		assertEquals(MSG, testLength, testPF.getLength());
		
		testPF = RPCRequestFactory.buildPutFile(null, null, null);
		assertNull(MSG, testPF.getSdlFileName());
		assertNull(MSG, testPF.getOffset());
		assertNull(MSG, testPF.getLength());
		
		// Test -- buildPutFile(String syncFileName, Integer iOffset, Integer iLength, FileType fileType, Boolean bPersistentFile, Boolean bSystemFile)
		testPF = RPCRequestFactory.buildPutFile(testFileName, testOffset, testLength, testFileType, testPFile, testSystemFile);
		assertEquals(MSG, testFileName, testPF.getSdlFileName());
		assertEquals(MSG, testOffset, testPF.getOffset());
		assertEquals(MSG, testLength, testPF.getLength());
		assertEquals(MSG, testFileType, testPF.getPersistentFile());
		assertEquals(MSG, testSystemFile, testPF.getSystemFile());
		
		testPF = RPCRequestFactory.buildPutFile(null, null, null, null, null, null);
		assertNull(MSG, testPF.getSdlFileName());
		assertNull(MSG, testPF.getOffset());
		assertNull(MSG, testPF.getLength());
		assertNull(MSG, testPF.getFileType());
		assertNull(MSG, testPF.getPersistentFile());
		assertNull(MSG, testPF.getSystemFile());		
	}
	
	public void testBuildRegisterAppInterface () {
		
		SdlMsgVersion testSMV = new SdlMsgVersion();
		testSMV.setMajorVersion(Integer.valueOf(SdlMsgVersion.KEY_MAJOR_VERSION));
		testSMV.setMinorVersion(Integer.valueOf(SdlMsgVersion.KEY_MINOR_VERSION));
		String testAppName = "test", testNGN = "ngn", testAppID = "id";
		Vector<TTSChunk> testTTSName = TTSChunkFactory.createSimpleTTSChunks("name");
		Vector<String> testSynonyms = new Vector<String>();
		testSynonyms.add("examine");
		Boolean testIMA = false;
		Integer testCorrelationID = 0;
		Language testLang = Language.EN_US, testHMILang = Language.EN_GB;
		Vector<AppHMIType> testHMIType = new Vector<AppHMIType>();
		testHMIType.add(AppHMIType.DEFAULT);
		RegisterAppInterface testRAI;
		
		// Test -- buildRegisterAppInterface(String appName, String appID)
		// ^ Calls another build method.
		
		// Test -- buildRegisterAppInterface(String appName, Boolean isMediaApp, String appID)
		// ^ Calls another build method.
		
		// Test -- buildRegisterAppInterface(SdlMsgVersion sdlMsgVersion, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp,  Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID, Integer correlationID)
		testRAI = RPCRequestFactory.buildRegisterAppInterface(testSMV, testAppName, testTTSName, testNGN, testSynonyms, testIMA, testLang, testHMILang, testHMIType, testAppID, testCorrelationID);
		assertTrue(MSG, Validator.validateSdlMsgVersion(testSMV, testRAI.getSdlMsgVersion()));
		assertEquals(MSG, testAppName, testRAI.getAppName());
		assertTrue(MSG, Validator.validateTtsChunks(testTTSName, testRAI.getTtsName()));
		assertEquals(MSG, testNGN, testRAI.getNgnMediaScreenAppName());
		assertTrue(MSG, Validator.validateStringList(testSynonyms, testRAI.getVrSynonyms()));
		assertEquals(MSG, testIMA, testRAI.getIsMediaApplication());
		assertEquals(MSG, testLang, testRAI.getLanguageDesired());
		assertEquals(MSG, testHMILang, testRAI.getHmiDisplayLanguageDesired());
		assertEquals(MSG, AppHMIType.DEFAULT, testRAI.getAppHMIType().get(0));
		assertEquals(MSG, testAppID, testRAI.getAppID());
		assertEquals(MSG, testCorrelationID, testRAI.getCorrelationID());
		
		testRAI = RPCRequestFactory.buildRegisterAppInterface(null, null, null, null, null, null, null, null, null, null, null);
		assertEquals(MSG, (Integer) 1, testRAI.getCorrelationID());
		assertEquals(MSG, testSMV, testRAI.getSdlMsgVersion());
		assertNull(MSG, testRAI.getAppName());
		assertNull(MSG, testRAI.getTtsName());
		assertNull(MSG, testRAI.getNgnMediaScreenAppName());
		assertNotNull(MSG, testRAI.getVrSynonyms());
		assertNull(MSG, testRAI.getIsMediaApplication());
		assertNotNull(MSG, testRAI.getLanguageDesired());
		assertNull(MSG, testRAI.getHmiDisplayLanguageDesired());
		assertNull(MSG, testRAI.getAppHMIType());
		assertNull(MSG, testRAI.getAppID());
	}
	
	public void testBuildSetAppIcon () {
		
		String testFileName = "test";
		Integer testCorrelationID = 0;
		SetAppIcon testSAI;
		
		// Test -- buildSetAppIcon(String sdlFileName, Integer correlationID)
		testSAI = RPCRequestFactory.buildSetAppIcon(testFileName, testCorrelationID);
		assertEquals(MSG, testFileName, testSAI.getSdlFileName());
		assertEquals(MSG, testCorrelationID, testSAI.getCorrelationID());
		
		testSAI = RPCRequestFactory.buildSetAppIcon(null, null);
		assertNull(MSG, testSAI.getSdlFileName());
		assertNull(MSG, testSAI.getCorrelationID());
		
	}
	
	public void testBuildSetGlobalProperties () {
		
		// Test -- buildSetGlobalProperties(String helpPrompt, String timeoutPrompt, Integer correlationID)
		
		// Test -- buildSetGlobalProperties(Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, Integer correlationID)
		
		// Test -- buildSetGlobalProperties(String helpPrompt, String timeoutPrompt, String vrHelpTitle, Vector<VrHelpItem> vrHelp, Integer correlationID)
		
		// Test -- buildSetGlobalProperties(Vector<TTSChunk> helpChunks, Vector<TTSChunk> timeoutChunks, String vrHelpTitle, Vector<VrHelpItem> vrHelp, Integer correlationID)
				
	}
	
	public void testBuildSetMediaClockTimer () {
		
		// Test -- buildSetMediaClockTimer(Integer hours, Integer minutes, Integer seconds, UpdateMode updateMode, Integer correlationID)
	
		// Test -- buildSetMediaClockTimer(UpdateMode updateMode, Integer correlationID)
	}
	
	public void testBuildShow () {
		
		// Test -- buildShow(String mainText1, String mainText2, String mainText3, String mainText4, String statusBar, String mediaClock, String mediaTrack, Image graphic, Vector<SoftButton> softButtons, Vector <String> customPresets, TextAlignment alignment, Integer correlationID)
		
		// Test -- buildShow(String mainText1, String mainText2, String mainText3, String mainText4, TextAlignment alignment, Integer correlationID)
		
		// Test -- buildShow(String mainText1, String mainText2, String statusBar, String mediaClock, String mediaTrack, TextAlignment alignment, Integer correlationID)
	
		// Test -- buildShow(String mainText1, String mainText2, TextAlignment alignment, Integer correlationID)
		
	}
	
	public void testBuildSpeak () {
		
		// Test -- buildSpeak(String ttsText, Integer correlationID)
		
		// Test -- buildSpeak(Vector<TTSChunk> ttsChunks, Integer correlationID)
	}
	
	public void testBuildSubscribeButton () {
		
		// Test -- buildSubscribeButton(ButtonName buttonName, Integer correlationID)
		
	}
	
	public void testBuildUnregisterAppInterface () {
		
		// Test -- buildUnregisterAppInterface(Integer correlationID)
	}
	
	public void testBuildUnsubscribeButton () {
		
		// Test -- buildUnsubscribeButton(ButtonName buttonName, Integer correlationID)
		
	}
	
	public void testBuildSubscribeVehicleData () {
		
		// Test -- BuildSubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID) 
		
		// Test -- BuildUnsubscribeVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID) 
		
		// Test -- BuildGetVehicleData(boolean gps, boolean speed, boolean rpm, boolean fuelLevel, boolean fuelLevel_State, boolean instantFuelConsumption, boolean externalTemperature, boolean vin, boolean prndl, boolean tirePressure, boolean odometer, boolean beltStatus, boolean bodyInformation, boolean deviceStatus, boolean driverBraking, Integer correlationID)
		
	}
	
	public void testBuildScrollableMessage () {
		
		// Test -- BuildScrollableMessage(String scrollableMessageBody, Integer timeout, Vector<SoftButton> softButtons, Integer correlationID)	
		
	}
	
	public void testBuildSlider () {
		
		// Test -- BuildSlider(Integer numTicks, Integer position, String sliderHeader, Vector<String> sliderFooter, Integer timeout, Integer correlationID)
		
	}
	
	public void testBuildChangeRegistration () {
		
		// Test -- BuildChangeRegistration(Language language, Language hmiDisplayLanguage, Integer correlationID)
		
	}
	
	public void testBuildSetDisplayLayout () {
		
		// Test -- BuildSetDisplayLayout(String displayLayout, Integer correlationID)
		
	}
	
	public void testBuildPerformAudioPassThru () {
		
		// Test -- BuildPerformAudioPassThru(String ttsText, String audioPassThruDisplayText1, String audioPassThruDisplayText2, SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample, AudioType audioType, Boolean muteAudio, Integer correlationID)

		// Test -- BuildPerformAudioPassThru(Vector<TTSChunk> initialPrompt, String audioPassThruDisplayText1, String audioPassThruDisplayText2, SamplingRate samplingRate, Integer maxDuration, BitsPerSample bitsPerSample, AudioType audioType, Boolean muteAudio, Integer correlationID)

	}
	
	public void testBuildEndAudioPassThru () {
		
		// Test -- BuildEndAudioPassThru(Integer correlationID)
	}	
}