package com.smartdevicelink.managers.screen;

import android.content.Context;
import android.net.Uri;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.livio.taskmaster.Task;
import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.lifecycle.OnSystemCapabilityListener;
import com.smartdevicelink.managers.lifecycle.SystemCapabilityManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.TemplateConfiguration;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.PredefinedLayout;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link com.smartdevicelink.managers.screen.TextAndGraphicManager}
 */
@RunWith(AndroidJUnit4.class)
public class TextAndGraphicManagerTests {

	// SETUP / HELPERS
	private TextAndGraphicManager textAndGraphicManager;
	private SdlArtwork testArtwork1, testArtwork2;
	private TemplateConfiguration configuration1, configuration2;

	@Before
	public void setUp() throws Exception{
		Context mTestContext = getInstrumentation().getContext();

		// mock things
		ISdl internalInterface = mock(ISdl.class);
		FileManager fileManager = mock(FileManager.class);
		SoftButtonManager softButtonManager = mock(SoftButtonManager.class);

		testArtwork1 = new SdlArtwork();
		testArtwork1.setName("testFile");
		Uri uri = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
		testArtwork1.setUri(uri);
		testArtwork1.setType(FileType.GRAPHIC_PNG);

		testArtwork2 = new SdlArtwork();
		testArtwork2.setName("testFile2");
		Uri uri2 = Uri.parse("android.resource://" + mTestContext.getPackageName() + "/drawable/ic_sdl");
		testArtwork2.setUri(uri2);
		testArtwork2.setType(FileType.GRAPHIC_PNG);

		configuration1 = new TemplateConfiguration(PredefinedLayout.GRAPHIC_WITH_TEXT.toString());
		configuration2 = new TemplateConfiguration(PredefinedLayout.DOUBLE_GRAPHIC_WITH_SOFTBUTTONS.toString());

		Taskmaster taskmaster = new Taskmaster.Builder().build();
		taskmaster.start();
		when(internalInterface.getTaskmaster()).thenReturn(taskmaster);

		Answer<Void> onHMIStatusAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				OnRPCNotificationListener onHMIStatusListener = (OnRPCNotificationListener) args[1];
				OnHMIStatus onHMIStatusFakeNotification = new OnHMIStatus();
				onHMIStatusFakeNotification.setHmiLevel(HMILevel.HMI_FULL);
				onHMIStatusListener.onNotified(onHMIStatusFakeNotification);
				return null;
			}
		};
		doAnswer(onHMIStatusAnswer).when(internalInterface).addOnRPCNotificationListener(eq(FunctionID.ON_HMI_STATUS), any(OnRPCNotificationListener.class));

		Answer<Void> onSystemCapabilityAnswer = new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				OnSystemCapabilityListener onSystemCapabilityListener = (OnSystemCapabilityListener) args[1];
				WindowCapability windowCapability = getWindowCapability(4);
				DisplayCapability displayCapability = new DisplayCapability();
				displayCapability.setWindowCapabilities(Collections.singletonList(windowCapability));
				List<DisplayCapability> capabilities = Collections.singletonList(displayCapability);
				onSystemCapabilityListener.onCapabilityRetrieved(capabilities);
				return null;
			}
		};
		SystemCapabilityManager systemCapabilityManager = mock(SystemCapabilityManager.class);
		doAnswer(onSystemCapabilityAnswer).when(systemCapabilityManager).addOnSystemCapabilityListener(eq(SystemCapabilityType.DISPLAYS), any(OnSystemCapabilityListener.class));
		doReturn(systemCapabilityManager).when(internalInterface).getSystemCapabilityManager();

		textAndGraphicManager = new TextAndGraphicManager(internalInterface, fileManager, softButtonManager);
	}


	private WindowCapability getWindowCapability(int numberOfMainFields){

		TextField mainField1 = new TextField();
		mainField1.setName(TextFieldName.mainField1);
		TextField mainField2 = new TextField();
		mainField2.setName(TextFieldName.mainField2);
		TextField mainField3 = new TextField();
		mainField3.setName(TextFieldName.mainField3);
		TextField mainField4 = new TextField();
		mainField4.setName(TextFieldName.mainField4);

		List<TextField> textFieldList = new ArrayList<>();

		textFieldList.add(mainField1);
		textFieldList.add(mainField2);
		textFieldList.add(mainField3);
		textFieldList.add(mainField4);

		List<TextField> returnList = new ArrayList<>();

		if (numberOfMainFields > 0){
			for (int i = 0; i < numberOfMainFields; i++) {
				returnList.add(textFieldList.get(i));
			}
		}

		WindowCapability windowCapability = new WindowCapability();
		windowCapability.setTextFields(returnList);

		return windowCapability;
	}

	/**
	 * Used to simulate WindowCapability having no capabilities set
	 * @return windowCapability that has no capabilities set
	 */
	private WindowCapability getNullVarWindowCapability() {

		WindowCapability windowCapability = new WindowCapability();
		return windowCapability;
	}

	@Test
	public void testInstantiation(){
		assertNull(textAndGraphicManager.getTextField1());
		assertNull(textAndGraphicManager.getTextField2());
		assertNull(textAndGraphicManager.getTextField3());
		assertNull(textAndGraphicManager.getTextField4());
		assertNull(textAndGraphicManager.getTitle());
		assertNull(textAndGraphicManager.getMediaTrackTextField());
		assertNull(textAndGraphicManager.getPrimaryGraphic());
		assertNull(textAndGraphicManager.getSecondaryGraphic());
		assertEquals(textAndGraphicManager.getTextAlignment(), TextAlignment.CENTERED);
		assertNull(textAndGraphicManager.getTextField1Type());
		assertNull(textAndGraphicManager.getTextField2Type());
		assertNull(textAndGraphicManager.getTextField3Type());
		assertNull(textAndGraphicManager.getTextField4Type());
		assertNotNull(textAndGraphicManager.currentScreenData);
		assertNotNull(textAndGraphicManager.defaultMainWindowCapability);
		assertEquals(textAndGraphicManager.currentHMILevel, HMILevel.HMI_FULL);
		assertFalse(textAndGraphicManager.isDirty);
		assertEquals(textAndGraphicManager.getState(), BaseSubManager.SETTING_UP);
		assertNotNull(textAndGraphicManager.getBlankArtwork());
	}

	/**
	 * Test getting number of lines available to be set based off of windowCapability
	 */
	@Test
	public void testGetMainLines(){

		// We want to test that the looping works. By default, it will return 4 if display cap is null
		textAndGraphicManager.defaultMainWindowCapability = getNullVarWindowCapability();

		// Null test
		assertEquals(0, ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(textAndGraphicManager.defaultMainWindowCapability));

		// The tests.java class has an example of this, but we must build it to do what
		// we need it to do. Build display cap w/ 3 main fields and test that it returns 3
		textAndGraphicManager.defaultMainWindowCapability = getWindowCapability(3);
		assertEquals(ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(textAndGraphicManager.defaultMainWindowCapability), 3);
	}

	@Test
	public void testMediaTrackTextField() {

		String songTitle = "Wild For The Night";
		textAndGraphicManager.setMediaTrackTextField(songTitle);
		assertEquals(textAndGraphicManager.getMediaTrackTextField(), songTitle);
	}

	@Test
	public void testTemplateTitle() {

		String title = "template title";
		textAndGraphicManager.setTitle(title);
		assertEquals(textAndGraphicManager.getTitle(), title);
	}

	@Test
	public void testAlignment() {

		textAndGraphicManager.setTextAlignment(TextAlignment.LEFT_ALIGNED);
		assertEquals(textAndGraphicManager.getTextAlignment(), TextAlignment.LEFT_ALIGNED);
	}

	// TEST IMAGES

	@Test
	public void testSetPrimaryGraphic() {
		textAndGraphicManager.setPrimaryGraphic(testArtwork1);
		assertEquals(textAndGraphicManager.getPrimaryGraphic(), testArtwork1);
	}

	@Test
	public void testSetSecondaryGraphic() {
		textAndGraphicManager.setSecondaryGraphic(testArtwork1);
		assertEquals(textAndGraphicManager.getSecondaryGraphic(), testArtwork1);
	}

	// TEST DISPOSE

	@Test
	public void testDispose() {
		textAndGraphicManager.dispose();

		assertNull(textAndGraphicManager.getTextField1());
		assertNull(textAndGraphicManager.getTextField2());
		assertNull(textAndGraphicManager.getTextField3());
		assertNull(textAndGraphicManager.getTextField4());
		assertNull(textAndGraphicManager.getMediaTrackTextField());
		assertNull(textAndGraphicManager.getPrimaryGraphic());
		assertNull(textAndGraphicManager.getSecondaryGraphic());
		assertNull(textAndGraphicManager.getTextAlignment());
		assertNull(textAndGraphicManager.getTextField1Type());
		assertNull(textAndGraphicManager.getTextField2Type());
		assertNull(textAndGraphicManager.getTextField3Type());
		assertNull(textAndGraphicManager.getTextField4Type());
		assertNull(textAndGraphicManager.getTitle());
		assertNotNull(textAndGraphicManager.getBlankArtwork());
		assertNull(textAndGraphicManager.currentScreenData);
		assertNull(textAndGraphicManager.defaultMainWindowCapability);
		assertFalse(textAndGraphicManager.isDirty);
		assertEquals(textAndGraphicManager.getState(), BaseSubManager.SHUTDOWN);
	}

	@Test
	public void testOperationManagement() {
		textAndGraphicManager.isDirty = true;
		textAndGraphicManager.updateOperation = null;
		textAndGraphicManager.update(null);
		assertEquals(textAndGraphicManager.transactionQueue.getTasksAsList().size(), 1);

		textAndGraphicManager.transactionQueue.clear();
		textAndGraphicManager.updateOperation = null;

		assertEquals(textAndGraphicManager.transactionQueue.getTasksAsList().size(), 0);

		textAndGraphicManager.isDirty = true;
		textAndGraphicManager.update(null);

		assertEquals(textAndGraphicManager.transactionQueue.getTasksAsList().size(), 1);

		assertTrue(textAndGraphicManager.transactionQueue.getTasksAsList().get(0).getState() == Task.READY);
	}

	@Test
	public void testHasData() {
		assertFalse(textAndGraphicManager.hasData());

		textAndGraphicManager.setTextField1("HI");
		assertTrue(textAndGraphicManager.hasData());

		textAndGraphicManager.setTextField1(null);
		textAndGraphicManager.setPrimaryGraphic(testArtwork1);
		assertTrue(textAndGraphicManager.hasData());
	}

	@Test
	public void resetFieldsToCurrentScreenDataTest() {
		textAndGraphicManager.setTextField1("textField1");
		textAndGraphicManager.setTextField2("textField2");
		textAndGraphicManager.setTextField3("textField3");
		textAndGraphicManager.setTextField4("textField4");
		textAndGraphicManager.setTextField1Type(MetadataType.MEDIA_TITLE);
		textAndGraphicManager.setTextField2Type(MetadataType.MEDIA_TITLE);
		textAndGraphicManager.setTextField3Type(MetadataType.MEDIA_TITLE);
		textAndGraphicManager.setTextField4Type(MetadataType.MEDIA_TITLE);
		textAndGraphicManager.setMediaTrackTextField("mediaTrackTextField");
		textAndGraphicManager.setTitle("title");
		textAndGraphicManager.setPrimaryGraphic(testArtwork1);
		textAndGraphicManager.setSecondaryGraphic(testArtwork2);
		textAndGraphicManager.changeLayout(configuration1, null);
		textAndGraphicManager.currentScreenData = textAndGraphicManager.currentState();

		assertTrue(textAndGraphicManager.currentScreenData.getTextField1().equals(textAndGraphicManager.getTextField1()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField2().equals(textAndGraphicManager.getTextField2()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField3().equals(textAndGraphicManager.getTextField3()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField4().equals(textAndGraphicManager.getTextField4()));
		assertTrue(textAndGraphicManager.currentScreenData.getTitle().equals(textAndGraphicManager.getTitle()));
		assertTrue(textAndGraphicManager.currentScreenData.getMediaTrackTextField().equals(textAndGraphicManager.getMediaTrackTextField()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField1Type().toString().equals(textAndGraphicManager.getTextField1Type().toString()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField2Type().toString().equals(textAndGraphicManager.getTextField2Type().toString()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField3Type().toString().equals(textAndGraphicManager.getTextField3Type().toString()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField4Type().toString().equals(textAndGraphicManager.getTextField4Type().toString()));
		assertTrue(textAndGraphicManager.currentScreenData.getPrimaryGraphic().getName().equals(textAndGraphicManager.getPrimaryGraphic().getName()));
		assertTrue(textAndGraphicManager.currentScreenData.getSecondaryGraphic().getName().equals(textAndGraphicManager.getSecondaryGraphic().getName()));
		assertTrue(textAndGraphicManager.currentScreenData.getTemplateConfiguration().getStore().equals(textAndGraphicManager.getTemplateConfiguration().getStore()));

		textAndGraphicManager.setTextField1("BadData");
		textAndGraphicManager.setTextField2("BadData");
		textAndGraphicManager.setTextField3("BadData");
		textAndGraphicManager.setTextField4("BadData");
		textAndGraphicManager.setTextField1Type(MetadataType.HUMIDITY);
		textAndGraphicManager.setTextField2Type(MetadataType.HUMIDITY);
		textAndGraphicManager.setTextField3Type(MetadataType.HUMIDITY);
		textAndGraphicManager.setTextField4Type(MetadataType.HUMIDITY);
		textAndGraphicManager.setMediaTrackTextField("BadData");
		textAndGraphicManager.setTitle("BadData");
		textAndGraphicManager.setPrimaryGraphic(testArtwork2);
		textAndGraphicManager.setSecondaryGraphic(testArtwork1);
		textAndGraphicManager.changeLayout(configuration2, null);

		assertFalse(textAndGraphicManager.currentScreenData.getTextField1().equals(textAndGraphicManager.getTextField1()));
		assertFalse(textAndGraphicManager.currentScreenData.getTextField2().equals(textAndGraphicManager.getTextField2()));
		assertFalse(textAndGraphicManager.currentScreenData.getTextField3().equals(textAndGraphicManager.getTextField3()));
		assertFalse(textAndGraphicManager.currentScreenData.getTextField4().equals(textAndGraphicManager.getTextField4()));
		assertFalse(textAndGraphicManager.currentScreenData.getTitle().equals(textAndGraphicManager.getTitle()));
		assertFalse(textAndGraphicManager.currentScreenData.getMediaTrackTextField().equals(textAndGraphicManager.getMediaTrackTextField()));
		assertFalse(textAndGraphicManager.currentScreenData.getTextField1Type().toString().equals(textAndGraphicManager.getTextField1Type().toString()));
		assertFalse(textAndGraphicManager.currentScreenData.getTextField2Type().toString().equals(textAndGraphicManager.getTextField2Type().toString()));
		assertFalse(textAndGraphicManager.currentScreenData.getTextField3Type().toString().equals(textAndGraphicManager.getTextField3Type().toString()));
		assertFalse(textAndGraphicManager.currentScreenData.getTextField4Type().toString().equals(textAndGraphicManager.getTextField4Type().toString()));
		assertFalse(textAndGraphicManager.currentScreenData.getPrimaryGraphic().getName().equals(textAndGraphicManager.getPrimaryGraphic().getName()));
		assertFalse(textAndGraphicManager.currentScreenData.getSecondaryGraphic().getName().equals(textAndGraphicManager.getSecondaryGraphic().getName()));
		assertFalse(textAndGraphicManager.currentScreenData.getTemplateConfiguration().getStore().equals(textAndGraphicManager.getTemplateConfiguration().getStore()));

		textAndGraphicManager.resetFieldsToCurrentScreenData();

		assertTrue(textAndGraphicManager.currentScreenData.getTextField1().equals(textAndGraphicManager.getTextField1()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField2().equals(textAndGraphicManager.getTextField2()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField3().equals(textAndGraphicManager.getTextField3()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField4().equals(textAndGraphicManager.getTextField4()));
		assertTrue(textAndGraphicManager.currentScreenData.getTitle().equals(textAndGraphicManager.getTitle()));
		assertTrue(textAndGraphicManager.currentScreenData.getMediaTrackTextField().equals(textAndGraphicManager.getMediaTrackTextField()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField1Type().toString().equals(textAndGraphicManager.getTextField1Type().toString()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField2Type().toString().equals(textAndGraphicManager.getTextField2Type().toString()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField3Type().toString().equals(textAndGraphicManager.getTextField3Type().toString()));
		assertTrue(textAndGraphicManager.currentScreenData.getTextField4Type().toString().equals(textAndGraphicManager.getTextField4Type().toString()));
		assertTrue(textAndGraphicManager.currentScreenData.getPrimaryGraphic().getName().equals(textAndGraphicManager.getPrimaryGraphic().getName()));
		assertTrue(textAndGraphicManager.currentScreenData.getSecondaryGraphic().getName().equals(textAndGraphicManager.getSecondaryGraphic().getName()));
		assertTrue(textAndGraphicManager.currentScreenData.getTemplateConfiguration().getStore().equals(textAndGraphicManager.getTemplateConfiguration().getStore()));
	}
}
