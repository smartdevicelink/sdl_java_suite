package com.sdl.hellosdlandroid;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.managers.SdlManagerListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.lifecycle.LifecycleConfigurationUpdate;
import com.smartdevicelink.managers.screen.choiceset.ChoiceCell;
import com.smartdevicelink.managers.screen.choiceset.ChoiceSet;
import com.smartdevicelink.managers.screen.choiceset.ChoiceSetSelectionListener;
import com.smartdevicelink.managers.screen.menu.MenuCell;
import com.smartdevicelink.managers.screen.menu.MenuSelectionListener;
import com.smartdevicelink.managers.screen.menu.VoiceCommand;
import com.smartdevicelink.managers.screen.menu.VoiceCommandSelectionListener;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.Speak;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class SdlService extends Service {

	private static final String TAG 					= "SDL Service";

	private static final String APP_NAME 				= "Hello Sdl";
	private static final String APP_NAME_ES 			= "Hola Sdl";
	private static final String APP_NAME_FR 			= "Bonjour Sdl";
	private static final String APP_ID 					= "8678309";

	private static final String ICON_FILENAME 			= "hello_sdl_icon.png";
	private static final String SDL_IMAGE_FILENAME  	= "sdl_full_image.png";

	private static final String WELCOME_SHOW 			= "Welcome to HelloSDL";
	private static final String WELCOME_SPEAK 			= "Welcome to Hello S D L";

	private static final String TEST_COMMAND_NAME 		= "Test Command";

	private static final int FOREGROUND_SERVICE_ID = 111;

	// TCP/IP transport config
	// The default port is 12345
	// The IP is of the machine that is running SDL Core
	private static final int TCP_PORT = 12247;
	private static final String DEV_MACHINE_IP_ADDRESS = "m.sdl.tools";

	// variable to create and call functions of the SyncProxy
	private SdlManager sdlManager = null;
	private List<ChoiceCell> choiceCellList;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			enterForeground();
		}
	}

	// Helper method to let the service enter foreground mode
	@SuppressLint("NewApi")
	public void enterForeground() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(APP_ID, "SdlService", NotificationManager.IMPORTANCE_DEFAULT);
			NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			if (notificationManager != null) {
				notificationManager.createNotificationChannel(channel);
				Notification serviceNotification = new Notification.Builder(this, channel.getId())
						.setContentTitle("Connected through SDL")
						.setSmallIcon(R.drawable.ic_sdl)
						.build();
				startForeground(FOREGROUND_SERVICE_ID, serviceNotification);
			}
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startProxy();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			stopForeground(true);
		}

		if (sdlManager != null) {
			sdlManager.dispose();
		}

		super.onDestroy();
	}

	private void startProxy() {
		// This logic is to select the correct transport and security levels defined in the selected build flavor
		// Build flavors are selected by the "build variants" tab typically located in the bottom left of Android Studio
		// Typically in your app, you will only set one of these.
		if (sdlManager == null) {
			Log.i(TAG, "Starting SDL Proxy");
			// Enable DebugTool for debug build type
			if (BuildConfig.DEBUG){
				DebugTool.enableDebugTool();
			}
			BaseTransportConfig transport = null;
			if (BuildConfig.TRANSPORT.equals("MULTI")) {
				int securityLevel;
				if (BuildConfig.SECURITY.equals("HIGH")) {
					securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_HIGH;
				} else if (BuildConfig.SECURITY.equals("MED")) {
					securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_MED;
				} else if (BuildConfig.SECURITY.equals("LOW")) {
					securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_LOW;
				} else {
					securityLevel = MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF;
				}
				transport = new MultiplexTransportConfig(this, APP_ID, securityLevel);
			} else if (BuildConfig.TRANSPORT.equals("TCP")) {
				transport = new TCPTransportConfig(TCP_PORT, DEV_MACHINE_IP_ADDRESS, true);
			} else if (BuildConfig.TRANSPORT.equals("MULTI_HB")) {
				MultiplexTransportConfig mtc = new MultiplexTransportConfig(this, APP_ID, MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF);
				mtc.setRequiresHighBandwidth(true);
				transport = mtc;
			}

			// The app type to be used
			Vector<AppHMIType> appType = new Vector<>();
			appType.add(AppHMIType.DEFAULT);

			// The manager listener helps you know when certain events that pertain to the SDL Manager happen
			// Here we will listen for ON_HMI_STATUS and ON_COMMAND notifications
			SdlManagerListener listener = new SdlManagerListener() {
				@Override
				public void onStart() {
					// HMI Status Listener
					sdlManager.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, new OnRPCNotificationListener() {
						@Override
						public void onNotified(RPCNotification notification) {
							OnHMIStatus onHMIStatus = (OnHMIStatus)notification;
							if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
								return;
							}
							if (onHMIStatus.getHmiLevel() == HMILevel.HMI_FULL && onHMIStatus.getFirstRun()) {
								setVoiceCommands();
								sendMenus();
								performWelcomeSpeak();
								performWelcomeShow();
								preloadChoices();
							}
						}
					});
				}

				@Override
				public void onDestroy() {
					SdlService.this.stopSelf();
				}

				@Override
				public void onError(String info, Exception e) {
				}

				@Override
				public LifecycleConfigurationUpdate managerShouldUpdateLifecycle(Language language, Language hmiLanguage){
					boolean isNeedUpdate = false;
					String appName = APP_NAME;
					String ttsName = APP_NAME;
					switch (language) {
						case ES_MX:
							isNeedUpdate = true;
							ttsName = APP_NAME_ES;
							break;
						case FR_CA:
							isNeedUpdate = true;
							ttsName = APP_NAME_FR;
							break;
						default:
							break;
					}
					switch (hmiLanguage) {
						case ES_MX:
							isNeedUpdate = true;
							appName = APP_NAME_ES;
							break;
						case FR_CA:
							isNeedUpdate = true;
							appName = APP_NAME_FR;
							break;
						default:
							break;
					}
					if (isNeedUpdate) {
						return new LifecycleConfigurationUpdate(appName,null,TTSChunkFactory.createSimpleTTSChunks(ttsName), null);
					} else {
						return null;
					}

				}
			};

			// Create App Icon, this is set in the SdlManager builder
			SdlArtwork appIcon = new SdlArtwork(ICON_FILENAME, FileType.GRAPHIC_PNG, R.mipmap.ic_launcher, true);

			// The manager builder sets options for your session
			SdlManager.Builder builder = new SdlManager.Builder(this, APP_ID, APP_NAME, listener);
			builder.setAppTypes(appType);
			builder.setTransportType(transport);
			builder.setAppIcon(appIcon);
			sdlManager = builder.build();
			sdlManager.start();
		}
	}

	/**
	 * Send some voice commands
	 */
	private void setVoiceCommands(){

		List<String> list1 = Collections.singletonList("Command One");
		List<String> list2 = Collections.singletonList("Command two");

		VoiceCommand voiceCommand1 = new VoiceCommand(list1, new VoiceCommandSelectionListener() {
			@Override
			public void onVoiceCommandSelected() {
				Log.i(TAG, "Voice Command 1 triggered");
			}
		});

		VoiceCommand voiceCommand2 = new VoiceCommand(list2, new VoiceCommandSelectionListener() {
			@Override
			public void onVoiceCommandSelected() {
				Log.i(TAG, "Voice Command 2 triggered");
			}
		});

		sdlManager.getScreenManager().setVoiceCommands(Arrays.asList(voiceCommand1,voiceCommand2));
	}

	/**
	 *  Add menus for the app on SDL.
	 */
	private void sendMenus(){

		// some arts
		SdlArtwork livio = new SdlArtwork("livio", FileType.GRAPHIC_PNG, R.drawable.sdl, false);

		// some voice commands
		List<String> voice2 = Collections.singletonList("Cell two");

		MenuCell mainCell1 = new MenuCell("Test Cell 1 (speak)", livio, null, new MenuSelectionListener() {
			@Override
			public void onTriggered(TriggerSource trigger) {
				Log.i(TAG, "Test cell 1 triggered. Source: "+ trigger.toString());
				showTest();
			}
		});

		MenuCell mainCell2 = new MenuCell("Test Cell 2", null, voice2, new MenuSelectionListener() {
			@Override
			public void onTriggered(TriggerSource trigger) {
				Log.i(TAG, "Test cell 2 triggered. Source: "+ trigger.toString());
			}
		});

		// SUB MENU

		MenuCell subCell1 = new MenuCell("SubCell 1",null, null, new MenuSelectionListener() {
			@Override
			public void onTriggered(TriggerSource trigger) {
				Log.i(TAG, "Sub cell 1 triggered. Source: "+ trigger.toString());
			}
		});

		MenuCell subCell2 = new MenuCell("SubCell 2",null, null, new MenuSelectionListener() {
			@Override
			public void onTriggered(TriggerSource trigger) {
				Log.i(TAG, "Sub cell 2 triggered. Source: "+ trigger.toString());
			}
		});

		// sub menu parent cell
		MenuCell mainCell3 = new MenuCell("Test Cell 3 (sub menu)", MenuLayout.LIST, null, Arrays.asList(subCell1,subCell2));

		MenuCell mainCell4 = new MenuCell("Show Perform Interaction", null, null, new MenuSelectionListener() {
			@Override
			public void onTriggered(TriggerSource trigger) {
				showPerformInteraction();
			}
		});

		MenuCell mainCell5 = new MenuCell("Clear the menu",null, null, new MenuSelectionListener() {
			@Override
			public void onTriggered(TriggerSource trigger) {
				Log.i(TAG, "Clearing Menu. Source: "+ trigger.toString());
				// Clear this thing
				sdlManager.getScreenManager().setMenu(Collections.<MenuCell>emptyList());
				showAlert("Menu Cleared");
			}
		});

		// Send the entire menu off to be created
		sdlManager.getScreenManager().setMenu(Arrays.asList(mainCell1, mainCell2, mainCell3, mainCell4, mainCell5));
	}

	/**
	 * Will speak a sample welcome message
	 */
	private void performWelcomeSpeak(){
		sdlManager.sendRPC(new Speak(TTSChunkFactory.createSimpleTTSChunks(WELCOME_SPEAK)));
	}

	/**
	 * Use the Screen Manager to set the initial screen text and set the image.
	 * Because we are setting multiple items, we will call beginTransaction() first,
	 * and finish with commit() when we are done.
	 */
	private void performWelcomeShow() {
		sdlManager.getScreenManager().beginTransaction();
		sdlManager.getScreenManager().setTextField1(APP_NAME);
		sdlManager.getScreenManager().setTextField2(WELCOME_SHOW);
		sdlManager.getScreenManager().setPrimaryGraphic(new SdlArtwork(SDL_IMAGE_FILENAME, FileType.GRAPHIC_PNG, R.drawable.sdl, true));
		sdlManager.getScreenManager().commit(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				if (success){
					Log.i(TAG, "welcome show successful");
				}
			}
		});
	}

	/**
	 * Will show a sample test message on screen as well as speak a sample test message
	 */
	private void showTest(){
		sdlManager.getScreenManager().beginTransaction();
		sdlManager.getScreenManager().setTextField1("Test Cell 1 has been selected");
		sdlManager.getScreenManager().setTextField2("");
		sdlManager.getScreenManager().commit(null);

		sdlManager.sendRPC(new Speak(TTSChunkFactory.createSimpleTTSChunks(TEST_COMMAND_NAME)));
	}

	private void showAlert(String text){
		Alert alert = new Alert();
		alert.setAlertText1(text);
		alert.setDuration(5000);
		sdlManager.sendRPC(alert);
	}

	// Choice Set

	private void preloadChoices(){
		ChoiceCell cell1 = new ChoiceCell("Item 1");
		ChoiceCell cell2 = new ChoiceCell("Item 2");
		ChoiceCell cell3 = new ChoiceCell("Item 3");
		choiceCellList = new ArrayList<>(Arrays.asList(cell1,cell2,cell3));
		sdlManager.getScreenManager().preloadChoices(choiceCellList, null);
	}

	private void showPerformInteraction(){
		if (choiceCellList != null) {
			ChoiceSet choiceSet = new ChoiceSet("Choose an Item from the list", choiceCellList, new ChoiceSetSelectionListener() {
				@Override
				public void onChoiceSelected(ChoiceCell choiceCell, TriggerSource triggerSource, int rowIndex) {
					showAlert(choiceCell.getText() + " was selected");
				}

				@Override
				public void onError(String error) {
					Log.e(TAG, "There was an error showing the perform interaction: "+ error);
				}
			});
			sdlManager.getScreenManager().presentChoiceSet(choiceSet, InteractionMode.MANUAL_ONLY);
		}
	}
}
