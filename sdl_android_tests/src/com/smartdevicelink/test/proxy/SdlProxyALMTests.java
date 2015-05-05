//package com.smartdevicelink.test.proxy;
//
//import java.util.Vector;
//
//import junit.framework.TestCase;
//
//import com.smartdevicelink.exception.SdlException;
//import com.smartdevicelink.proxy.SdlProxyALM;
//import com.smartdevicelink.proxy.SdlProxyConfigurationResources;
//import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
//import com.smartdevicelink.proxy.rpc.AddCommandResponse;
//import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
//import com.smartdevicelink.proxy.rpc.AlertResponse;
//import com.smartdevicelink.proxy.rpc.ChangeRegistrationResponse;
//import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSetResponse;
//import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
//import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
//import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSetResponse;
//import com.smartdevicelink.proxy.rpc.DeleteSubMenuResponse;
//import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
//import com.smartdevicelink.proxy.rpc.EndAudioPassThruResponse;
//import com.smartdevicelink.proxy.rpc.GenericResponse;
//import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
//import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
//import com.smartdevicelink.proxy.rpc.ListFilesResponse;
//import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
//import com.smartdevicelink.proxy.rpc.OnButtonEvent;
//import com.smartdevicelink.proxy.rpc.OnButtonPress;
//import com.smartdevicelink.proxy.rpc.OnCommand;
//import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
//import com.smartdevicelink.proxy.rpc.OnHMIStatus;
//import com.smartdevicelink.proxy.rpc.OnHashChange;
//import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
//import com.smartdevicelink.proxy.rpc.OnLanguageChange;
//import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
//import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
//import com.smartdevicelink.proxy.rpc.OnSystemRequest;
//import com.smartdevicelink.proxy.rpc.OnTBTClientState;
//import com.smartdevicelink.proxy.rpc.OnTouchEvent;
//import com.smartdevicelink.proxy.rpc.OnVehicleData;
//import com.smartdevicelink.proxy.rpc.PerformAudioPassThruResponse;
//import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
//import com.smartdevicelink.proxy.rpc.PutFileResponse;
//import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
//import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
//import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
//import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
//import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
//import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
//import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
//import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
//import com.smartdevicelink.proxy.rpc.ShowResponse;
//import com.smartdevicelink.proxy.rpc.SliderResponse;
//import com.smartdevicelink.proxy.rpc.SpeakResponse;
//import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
//import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
//import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
//import com.smartdevicelink.proxy.rpc.TTSChunk;
//import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
//import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
//import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
//import com.smartdevicelink.proxy.rpc.enums.Language;
//import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
//import com.smartdevicelink.transport.BTTransportConfig;
//import com.smartdevicelink.transport.BaseTransportConfig;
//import com.smartdevicelink.transport.TransportType;
//
//public class SdlProxyALMTests extends TestCase {
//	
//	/* Issue - Constructor Test #
//	 * = = = = = = = =
//	 * No get method for IProxyListenerALM listener     - 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25
//	 * No get method for Boolean IsMediaApp             - 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25
//	 * No get method for List<String> VrSynonyms        - 2,3,4,5,6,8,9,10,11,12,17,18,19,20,21,22,23,24,25
//	 * No get method for Boolean autoActivateId         - 2,3,4,5,6,8,9,10,11,12,17,18,19,20,21,22,23,24,25
//	 * No get method for SdlProxyConfigurationResources - 3,5,6,9,11,12,17,18,19,20,21,22,23,24,25
//	 * No get method for boolean CallbackToUIThread     - 4,5,6,10,11,12,13,16,17,18,19,20,21,22,23,24,25
//	 * No get method for boolean PreRegister            - 6,12,13,16,17,18,19,20,21,22,23,24,25
//	 * No get method for BaseTransportConfig            - 7,8,9,10,11,12,15,18,20,22,23,24,25
//	 * No get method for Service appService             - 17,18,23,25
//	 * No get method for List<TTSChunk> ttsNames        - 17,18,19,20,21,22,23,24,25
//	 * No get method for List<AppHMIType> appType       - 21,22,23,24,25
//	 * No get method for String getHashId               - 24,25
//	 * 
//	 * Cannot test the assignment of these values through the constructors.
//	 * - -
//	 * Cannot test RegisterAppInterface return values, they are dependent on the
//	 * applications using library.
//	 * - -
//	 * Cannot mock a service object to test the constructors that require an
//	 * instance of the service that the users must provide.
//	 * Test # 17,18,23, and 25 have been commented out due to this issue.
//	 * - -
//	 * Cannot test anything because of bluetooth data.
//	 **/
//	
//	Vector<AppHMIType> testAppType = new Vector<AppHMIType>();	
//	Vector<TTSChunk> testTtsName = new Vector<TTSChunk>();
//	TransportType testTransportType = TransportType.USB;
//	BaseTransportConfig testTransportConfig = new BTTransportConfig();
//	boolean testCallbackToUIThread = false, testPreRegister = true;
//	SdlProxyConfigurationResources testConfigResources = new SdlProxyConfigurationResources();
//	SdlMsgVersion testMessageVersion = new SdlMsgVersion();
//	String testAppName = "appName", testAppId = "appId", testMediaName = "ngn", testAutoActivateId = "auto", testHashId = "hash";
//	Boolean testIsMediaApp = true;
//	Language testLanguageDesired = Language.EN_US, testHmiDisplayLanguage = Language.EN_GB;
//	IProxyListenerALM testListener = new MockListener();	
//	SdlProxyALM testALM = null;
//		
//	public void testConstructors () {
//		
//		Vector<String> testVrSynonyms = new Vector<String>();
//		testVrSynonyms.add("synonym");
//		
//		// Test 1 -- SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp, Language languageDesired, Language hmiDisplayLanguageDesired, String appID) throws SdlException
//		try {			
//			testALM = new SdlProxyALM(testListener, testAppName, testIsMediaApp, testLanguageDesired, testHmiDisplayLanguage, testAppId);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try { // Note: listener and IsMediaApp cannot be null
//			testALM = new SdlProxyALM(testListener, null, testIsMediaApp, null, null, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp were null");
//		}
//		
//		// Test 2 -- SdlProxyALM(IProxyListenerALM listener, String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testAppName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, testIsMediaApp, null, null, null, null, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//				
//		// Test 3 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, testIsMediaApp, null, null, null, null, null);			
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//				
//		// Test 4 -- SdlProxyALM(IProxyListenerALM listener, String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, boolean callbackToUIThread) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testAppName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testCallbackToUIThread);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());		
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, testIsMediaApp, null, null, null, null, null, false);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 5 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, boolean callbackToUIThread) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testCallbackToUIThread);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, testIsMediaApp, null, null, null, null, null, false);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 6 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, testIsMediaApp, null, null, null, null, null, false, true);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 7 -- SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, BaseTransportConfig transportConfig) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testAppName, testIsMediaApp, testLanguageDesired, testHmiDisplayLanguage, testAppId, testTransportConfig);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, testIsMediaApp, null, null, null, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 8 -- SdlProxyALM(IProxyListenerALM listener, String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, TransportType transportType, BaseTransportConfig transportConfig) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testAppName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testTransportType, testTransportConfig);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//			assertEquals("Transport type did not match expected value.", testTransportType, testALM.getCurrentTransportType());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, testIsMediaApp, null, null, null, null, null, null, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//			assertNull("Transport type did not match expected value.", testALM.getCurrentTransportType());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 9 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, BaseTransportConfig transportConfig) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testTransportConfig);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, testIsMediaApp, null, null, null, null, null, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 10 -- SdlProxyALM(IProxyListenerALM listener, String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, boolean callbackToUIThread, BaseTransportConfig transportConfig) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testAppName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testCallbackToUIThread, testTransportConfig);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, testIsMediaApp, null, null, null, null, null, null, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 11 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, boolean callbackToUIThread, BaseTransportConfig transportConfig) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testCallbackToUIThread, testTransportConfig);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, testIsMediaApp, null, null, null, null, null, true, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 12 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister, BaseTransportConfig transportConfig) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister, testTransportConfig);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, testIsMediaApp, null, null, null, null, null, true, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 13 -- SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp,Language languageDesired, Language hmiDisplayLanguageDesired, String appID, boolean callbackToUIThread, boolean preRegister) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testAppName, testIsMediaApp, testLanguageDesired, testHmiDisplayLanguage, testAppId, testCallbackToUIThread, testPreRegister);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, testIsMediaApp, null, null, null, false, true);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 14 -- SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp,String appID) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testAppName, testIsMediaApp, testAppId);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, testIsMediaApp, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 15 -- SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp,String appID, BaseTransportConfig transportConfig) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testAppName, testIsMediaApp, testAppId, testTransportConfig);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, testIsMediaApp, null, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 16 -- SdlProxyALM(IProxyListenerALM listener, String appName, Boolean isMediaApp,String appID, boolean callbackToUIThread, boolean preRegister) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testAppName, testIsMediaApp, testAppId, testCallbackToUIThread, testPreRegister);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, testIsMediaApp, null, false, true);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//// Test 17 -- SdlProxyALM(Service appService, IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException
////		try {
////			testALM = new SdlProxyALM(testAppService, testListener, testConfigResources, testAppName, testTtsName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister);
////			assertNotNull("SdlProxyALM should not be null", testALM);
////			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
////			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
////			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
////			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
////			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
////			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
////		} catch (SdlException e) {
////			fail("SdlException was thrown");
////		} catch (IllegalArgumentException e) {
////			fail("IProxyListenerALM or IsMediaApp was null");
////		}
////		
////		try {			
////			testALM = new SdlProxyALM(null, testListener, null, null, null, null, null, testIsMediaApp, null, null, null, null, null, false, true);	
////			assertNull("App name did not match expected value.", testALM.getAppName());
////			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
////			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
////			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
////			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
////			assertNull("App id did not match expected value.", testALM.getAppID());
////		} catch (SdlException e) {
////			fail("SdlException was thrown");
////		} catch (IllegalArgumentException e) {
////			fail("IProxyListenerALM or IsMediaApp was null");
////		}
//		
//// Test 18 -- SdlProxyALM(Service appService, IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister, BaseTransportConfig transportConfig) throws SdlException
////		try {
////			testALM = new SdlProxyALM(testAppService, testListener, testConfigResources, testAppName, testTtsName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister, testTransportConfig);
////			assertNotNull("SdlProxyALM should not be null", testALM);
////			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
////			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
////			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
////			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
////			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
////			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
////		} catch (SdlException e) {
////			fail("SdlException was thrown");
////		} catch (IllegalArgumentException e) {
////			fail("IProxyListenerALM or IsMediaApp was null");
////		}
////		
////		try {			
////			testALM = new SdlProxyALM(null, testListener, null, null, null, null, null, testIsMediaApp, null, null, null, null, null, false, true, null);	
////			assertNull("App name did not match expected value.", testALM.getAppName());
////			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
////			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
////			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
////			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
////			assertNull("App id did not match expected value.", testALM.getAppID());
////		} catch (SdlException e) {
////			fail("SdlException was thrown");
////		} catch (IllegalArgumentException e) {
////			fail("IProxyListenerALM or IsMediaApp was null");
////		}
//		
//		// Test 19 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp,  SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testTtsName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, null, testIsMediaApp, null, null, null, null, null, false, true);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 20 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister, BaseTransportConfig transportConfig) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testTtsName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister, testTransportConfig);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, null, testIsMediaApp, null, null, null, null, null, false, true, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 21 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testTtsName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppType, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, null, testIsMediaApp, null, null, null, null, null, null, false, true);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		// Test 22 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister, BaseTransportConfig transportConfig) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testTtsName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppType, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister, testTransportConfig);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, null, testIsMediaApp, null, null, null, null, null, null, false, true, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//// Test 23 -- SdlProxyALM(Service appService, IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister, BaseTransportConfig transportConfig) throws SdlException
////		try {
////			testALM = new SdlProxyALM(testAppService, testListener, testConfigResources, testAppName, testTtsName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppType, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister, testTransportConfig);
////			assertNotNull("SdlProxyALM should not be null", testALM);
////			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
////			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
////			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
////			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
////			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
////			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
////		} catch (SdlException e) {
////			fail("SdlException was thrown");
////		} catch (IllegalArgumentException e) {
////			fail("IProxyListenerALM or IsMediaApp was null");
////		}
////		
////		try {			
////			testALM = new SdlProxyALM(null, testListener, null, null, null, null, null, testIsMediaApp, null, null, null, null, null, null, false, true, null);	
////			assertNull("App name did not match expected value.", testALM.getAppName());
////			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
////			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
////			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
////			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
////			assertNull("App id did not match expected value.", testALM.getAppID());
////		} catch (SdlException e) {
////			fail("SdlException was thrown");
////		} catch (IllegalArgumentException e) {
////			fail("IProxyListenerALM or IsMediaApp was null");
////		}
//		
//		// Test 24 -- SdlProxyALM(IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister, String sHashID, BaseTransportConfig transportConfig) throws SdlException
//		try {
//			testALM = new SdlProxyALM(testListener, testConfigResources, testAppName, testTtsName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppType, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister, testHashId,testTransportConfig);
//			assertNotNull("SdlProxyALM should not be null", testALM);
//			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
//			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
//			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
//			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
//			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
//			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//		try {			
//			testALM = new SdlProxyALM(testListener, null, null, null, null, null, testIsMediaApp, null, null, null, null, null, null, false, true, null, null);	
//			assertNull("App name did not match expected value.", testALM.getAppName());
//			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
//			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
//			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
//			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
//			assertNull("App id did not match expected value.", testALM.getAppID());
//		} catch (SdlException e) {
//			fail("SdlException was thrown");
//		} catch (IllegalArgumentException e) {
//			fail("IProxyListenerALM or IsMediaApp was null");
//		}
//		
//// Test 25 -- SdlProxyALM(Service appService, IProxyListenerALM listener, SdlProxyConfigurationResources sdlProxyConfigurationResources, String appName, Vector<TTSChunk> ttsName, String ngnMediaScreenAppName, Vector<String> vrSynonyms, Boolean isMediaApp, SdlMsgVersion sdlMsgVersion, Language languageDesired, Language hmiDisplayLanguageDesired, Vector<AppHMIType> appType, String appID, String autoActivateID, boolean callbackToUIThread, boolean preRegister, String sHashID, BaseTransportConfig transportConfig) throws SdlException
////		try {
////			testALM = new SdlProxyALM(testAppService,testListener, testConfigResources, testAppName, testTtsName, testMediaName, testVrSynonyms, testIsMediaApp, testMessageVersion, testLanguageDesired, testHmiDisplayLanguage, testAppType, testAppId, testAutoActivateId, testCallbackToUIThread, testPreRegister, testHashId,testTransportConfig);
////			assertNotNull("SdlProxyALM should not be null", testALM);
////			assertEquals("App name did not match expected value.", testAppName, testALM.getAppName());
////			assertEquals("Media app name did not match expected value.", testMediaName, testALM.getNgnAppName());
////			assertEquals("Sdl message version did not match expected values.", testMessageVersion, testALM.getSdlMsgVersion());
////			assertEquals("Language did not match expected value.", testLanguageDesired, testALM.getSdlLanguage());
////			assertEquals("Language did not match expected value.", testHmiDisplayLanguage, testALM.getHmiDisplayLanguage());
////			assertEquals("App id did not match expected value.", testAppId, testALM.getAppID());
////		} catch (SdlException e) {
////			fail("SdlException was thrown");
////		} catch (IllegalArgumentException e) {
////			fail("IProxyListenerALM or IsMediaApp was null");
////		}
////		
////		try {			
////			testALM = new SdlProxyALM(null, testListener, null, null, null, null, null, testIsMediaApp, null, null, null, null, null, null, false, true, null, null);	
////			assertNull("App name did not match expected value.", testALM.getAppName());
////			assertNull("Media app name did not match expected value.", testALM.getNgnAppName());
////			assertNull("Sdl message version did not match expected values.", testALM.getSdlMsgVersion());
////			assertNull("Language did not match expected value.", testALM.getSdlLanguage());
////			assertNull("Language did not match expected value.", testALM.getHmiDisplayLanguage());
////			assertNull("App id did not match expected value.", testALM.getAppID());
////		} catch (SdlException e) {
////			fail("SdlException was thrown");
////		} catch (IllegalArgumentException e) {
////			fail("IProxyListenerALM or IsMediaApp was null");
////		}
//	}	
//}
//
//class MockListener implements IProxyListenerALM {
//	
//	IProxyListenerALM listener = null;
//	public MockListener (IProxyListenerALM listener) {
//		this.listener = listener;
//	}
//	
//	public MockListener () {
//		listener = null;
//	}
//
//	@Override public void onOnHMIStatus(OnHMIStatus notification) {}
//	@Override public void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason) {}
//	@Override public void onError(String info, Exception e) {}
//	@Override public void onGenericResponse(GenericResponse response) {}
//	@Override public void onOnCommand(OnCommand notification) {}
//	@Override public void onAddCommandResponse(AddCommandResponse response) {}
//	@Override public void onAddSubMenuResponse(AddSubMenuResponse response) {}
//	@Override public void onCreateInteractionChoiceSetResponse(CreateInteractionChoiceSetResponse response) {}
//	@Override public void onAlertResponse(AlertResponse response) {}
//	@Override public void onDeleteCommandResponse(DeleteCommandResponse response) {}
//	@Override public void onDeleteInteractionChoiceSetResponse(DeleteInteractionChoiceSetResponse response) {}
//	@Override public void onDeleteSubMenuResponse(DeleteSubMenuResponse response) {}
//	@Override public void onPerformInteractionResponse(PerformInteractionResponse response) {}
//	@Override public void onResetGlobalPropertiesResponse(ResetGlobalPropertiesResponse response) {}
//	@Override public void onSetGlobalPropertiesResponse(SetGlobalPropertiesResponse response) {}
//	@Override public void onSetMediaClockTimerResponse(SetMediaClockTimerResponse response) {}
//	@Override public void onShowResponse(ShowResponse response) {}
//	@Override public void onSpeakResponse(SpeakResponse response) {}
//	@Override public void onOnButtonEvent(OnButtonEvent notification) {}
//	@Override public void onOnButtonPress(OnButtonPress notification) {} 
//	@Override public void onSubscribeButtonResponse(SubscribeButtonResponse response) {} 
//	@Override public void onUnsubscribeButtonResponse(UnsubscribeButtonResponse response) {} 
//	@Override public void onOnPermissionsChange(OnPermissionsChange notification) {}
//	@Override public void onSubscribeVehicleDataResponse(SubscribeVehicleDataResponse response) {}
//	@Override public void onUnsubscribeVehicleDataResponse(UnsubscribeVehicleDataResponse response) {}
//	@Override public void onGetVehicleDataResponse(GetVehicleDataResponse response) {}
//	@Override public void onOnVehicleData(OnVehicleData notification) {}
//	@Override public void onPerformAudioPassThruResponse(PerformAudioPassThruResponse response) {}
//	@Override public void onEndAudioPassThruResponse(EndAudioPassThruResponse response) {}
//	@Override public void onOnAudioPassThru(OnAudioPassThru notification) {}
//	@Override public void onPutFileResponse(PutFileResponse response) {}
//	@Override public void onDeleteFileResponse(DeleteFileResponse response) {}
//	@Override public void onListFilesResponse(ListFilesResponse response) {}
//	@Override public void onSetAppIconResponse(SetAppIconResponse response) {}
//	@Override public void onScrollableMessageResponse(ScrollableMessageResponse response) {}
//	@Override public void onChangeRegistrationResponse(ChangeRegistrationResponse response) {}
//	@Override public void onSetDisplayLayoutResponse(SetDisplayLayoutResponse response) {}
//	@Override public void onOnLanguageChange(OnLanguageChange notification) {}
//	@Override public void onOnHashChange(OnHashChange notification) {}
//	@Override public void onSliderResponse(SliderResponse response) {}
//	@Override public void onOnDriverDistraction(OnDriverDistraction notification) {}
//	@Override public void onOnTBTClientState(OnTBTClientState notification) {}
//	@Override public void onOnSystemRequest(OnSystemRequest notification) {}
//	@Override public void onSystemRequestResponse(SystemRequestResponse response) {}
//	@Override public void onOnKeyboardInput(OnKeyboardInput notification) {}
//	@Override public void onOnTouchEvent(OnTouchEvent notification) {}
//	@Override public void onDiagnosticMessageResponse(DiagnosticMessageResponse response) {}
//	@Override public void onReadDIDResponse(ReadDIDResponse response) {}
//	@Override public void onGetDTCsResponse(GetDTCsResponse response) {}
//	@Override public void onOnLockScreenNotification(OnLockScreenStatus notification) {}	
//}