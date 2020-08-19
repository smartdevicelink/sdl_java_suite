package com.sdl.hellosdlandroid;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.jakewharton.rxrelay2.PublishRelay;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.managers.SdlManagerListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.lifecycle.LifecycleConfigurationUpdate;
import com.smartdevicelink.managers.screen.OnButtonListener;
import com.smartdevicelink.managers.screen.choiceset.ChoiceCell;
import com.smartdevicelink.managers.screen.choiceset.ChoiceSet;
import com.smartdevicelink.managers.screen.choiceset.ChoiceSetSelectionListener;
import com.smartdevicelink.managers.screen.menu.MenuCell;
import com.smartdevicelink.managers.screen.menu.MenuSelectionListener;
import com.smartdevicelink.managers.screen.menu.VoiceCommand;
import com.smartdevicelink.managers.screen.menu.VoiceCommandSelectionListener;
import com.smartdevicelink.managers.video.resolution.AspectRatio;
import com.smartdevicelink.managers.video.resolution.Resolution;
import com.smartdevicelink.managers.video.resolution.VideoStreamingRange;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.Speak;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.streaming.video.SdlRemoteDisplay;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import io.reactivex.functions.Consumer;

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
	private static final int TCP_PORT = 12345;
	private static final String DEV_MACHINE_IP_ADDRESS = "192.168.0.104";

	// variable to create and call functions of the SyncProxy
	private SdlManager sdlManager = null;
	private List<ChoiceCell> choiceCellList;
	Map<FunctionID, OnRPCNotificationListener> onRPCNotificationListenerMap = new HashMap<>();
	public static final PublishRelay<MainActivity.STREAM_ENUM> relay = PublishRelay.create();

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
		String ip = intent.getStringExtra(MainActivity.IP);
		int port = intent.getIntExtra(MainActivity.PORT, 12345);
		startProxy(port, ip);

		relay.doOnNext(new Consumer<MainActivity.STREAM_ENUM>() {
			@Override
			public void accept(MainActivity.STREAM_ENUM stream_enum) throws Exception {
				startStreaming(stream_enum);
			}
		}).subscribe();
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

	private void startStreaming(final MainActivity.STREAM_ENUM value) {
		if (sdlManager.getVideoStreamManager() != null) {
			sdlManager.getVideoStreamManager().start(new CompletionListener() {
				@Override
				public void onComplete(boolean success) {
					if (success) {
						Class myClass;
						if (value.equals(MainActivity.STREAM_ENUM.START_STREAMING)) {
							myClass = MyDisplay.class;
						} else {
							myClass = UIStreamingDisplay.class;
						}
						VideoStreamingRange.Builder builder = new VideoStreamingRange.Builder();
						builder
								.setMaxSupportedResolution(new Resolution(800, 480))
								.setMinSupportedResolution(new Resolution(400, 200))
								.setAspectRatio(new AspectRatio(1., 6.))
								.setMaxScreenDiagonal(20.);
						VideoStreamingRange range = builder.build();
						sdlManager.getVideoStreamManager().startRemoteDisplayStream(getApplicationContext(), myClass, null, false, range);
					} else {
						Log.e(TAG, "Failed to start video streaming manager");
					}
				}
			});
		}
	}

	private void startProxy(int port, String ip) {
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
				transport = new TCPTransportConfig(port, ip, true);
			} else if (BuildConfig.TRANSPORT.equals("MULTI_HB")) {
				MultiplexTransportConfig mtc = new MultiplexTransportConfig(this, APP_ID, MultiplexTransportConfig.FLAG_MULTI_SECURITY_OFF);
				mtc.setRequiresHighBandwidth(true);
				transport = mtc;
			}

			// The app type to be used
			Vector<AppHMIType> appType = new Vector<>();
			appType.add(AppHMIType.NAVIGATION);

			// The manager listener helps you know when certain events that pertain to the SDL Manager happen
			// Here we will listen for ON_HMI_STATUS and ON_COMMAND notifications
			SdlManagerListener listener = new SdlManagerListener() {
				@Override
				public void onStart() {
				}

				@Override
				public void onDestroy() {
					SdlService.this.stopSelf();
				}

				@Override
				public void onError(String info, Exception e) {
				}

				@Override
				public LifecycleConfigurationUpdate managerShouldUpdateLifecycle(Language language) {
					return null;
				}

				@Override
				public LifecycleConfigurationUpdate managerShouldUpdateLifecycle(Language language, Language hmiLanguage) {
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
						return new LifecycleConfigurationUpdate(appName, null, TTSChunkFactory.createSimpleTTSChunks(ttsName), null);
					} else {
						return null;
					}
				}
			};

			// Create App Icon, this is set in the SdlManager builder
			SdlArtwork appIcon = new SdlArtwork(ICON_FILENAME, FileType.GRAPHIC_PNG, R.mipmap.ic_launcher, true);

			onRPCNotificationListenerMap.put(FunctionID.ON_HMI_STATUS, new OnRPCNotificationListener() {
				@Override
				public void onNotified(RPCNotification notification) {
					OnHMIStatus status = (OnHMIStatus) notification;
					if (status != null && status.getHmiLevel() == HMILevel.HMI_NONE) {
						//Stop the stream
						if (sdlManager.getVideoStreamManager() != null && sdlManager.getVideoStreamManager().isStreaming()) {
							Log.d("OnHmiStatus", "stop streaming");
							sdlManager.getVideoStreamManager().stopStreaming(false);
						}
					}
				}
			});
			// The manager builder sets options for your session
			SdlManager.Builder builder = new SdlManager.Builder(this, APP_ID, APP_NAME, listener);
			builder.setAppTypes(appType);
			builder.setTransportType(transport);
			builder.setAppIcon(appIcon);
			builder.setRPCNotificationListeners(onRPCNotificationListenerMap);
			sdlManager = builder.build();
			sdlManager.start();
		}
	}

	public static class UIStreamingDisplay extends SdlRemoteDisplay {
		int clickCounter1 = 0;
		int clickCounter2 = 0;
		public UIStreamingDisplay(Context context, Display display) {
			super(context, display);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.ui_streaming_layout);


			final Button button1 = findViewById(R.id.button1);
			final Button button2 = findViewById(R.id.button2);
			final TextView counter1 = findViewById(R.id.button_one_counter);
			final TextView counter2 = findViewById(R.id.button_two_counter);
            button1.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View view, MotionEvent motionEvent) {

                    counter1.setText("Click!!! " + ++clickCounter1);
					int location [] = new int[2];
                    button1.getLocationInWindow(location);
                    //counter1.append("\nButton size: " + button1.getWidth() + "x" + button1.getHeight());
					//textView.append("\nButton location: " + location[0] + "," + location[1]);
					return false;
				}
			});

            button2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    counter2.setText("Click!!! " + ++clickCounter2);
                    int location [] = new int[2];
                    button2.getLocationInWindow(location);
                    //counter2.append("\nButton size: " + button.getWidth() + "x" + button.getHeight());
                    //textView.append("\nButton location: " + location[0] + "," + location[1]);
                    return false;
                }
            });
		}

		@Override
		public void onViewResized(int width, int height) {
			Toast.makeText(getContext(),
					String.format("Remote view new width and height (%s, %s)", width, height),
					Toast.LENGTH_SHORT
			).show();
		}
	}

	public static class MyDisplay extends SdlRemoteDisplay {
		public MyDisplay(Context context, Display display) {
			super(context, display);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.streaming_layout);


			String videoUri = "android.resource://" + getContext().getPackageName() + "/" + R.raw.sdl;
			final VideoView videoView = findViewById(R.id.videoView);
			videoView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View view, MotionEvent motionEvent) {
					int[] location = new int[2];
					videoView.getLocationInWindow(location);
					Log.i("convertTouch", "View size " + videoView.getWidth() + "x" + videoView.getHeight());
					Log.i("convertTouch", "Location " + location[0] + " " + location[1]);
					Log.i("convertTouch", "Count: " + motionEvent.getPointerCount());
					Log.i("convertTouch", "Click(" + motionEvent.getX() + " " + motionEvent.getY() + " Raw " + motionEvent.getRawX() + " " + motionEvent.getRawY());
					return false;
				}
			});
			videoView.setVideoURI(Uri.parse(videoUri));
			videoView.start();
		}

		@Override
		public void onViewResized(int width, int height) {
			Toast.makeText(getContext(),
					String.format("Remote view new width and height (%s, %s)", width, height),
					Toast.LENGTH_SHORT
			).show();
		}
    }
}
