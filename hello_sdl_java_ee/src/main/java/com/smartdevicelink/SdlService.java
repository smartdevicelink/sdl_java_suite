package com.smartdevicelink;

import android.util.Log;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.managers.SdlManagerListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.*;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.util.DebugTool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

public class SdlService {


    private static final String TAG 					= "SDL Service";

    private static final String APP_NAME 				= "Hello Sdl";
    private static final String APP_ID 					= "8678309";

    private static final String ICON_FILENAME 			= "hello_sdl_icon.png";
    private static final String SDL_IMAGE_FILENAME  	= "sdl_full_image.png";

    private static final String WELCOME_SHOW 			= "Welcome to HelloSDL";
    private static final String WELCOME_SPEAK 			= "Welcome to Hello S D L";

    private static final String TEST_COMMAND_NAME 		= "Test Command";
    private static final int TEST_COMMAND_ID 			= 1;

    private static final String IMAGE_DIR =             "assets/images/";



    // variable to create and call functions of the SyncProxy
    private SdlManager sdlManager = null;

    private SdlServiceCallback callback;


    public SdlService(BaseTransportConfig config, SdlServiceCallback callback){
        this.callback = callback;
        buildSdlManager(config);
    }



    public void start() {
        DebugTool.logInfo("SdlService start() ");
        if(sdlManager != null){
            sdlManager.start();
        }
    }

    public void stop() {
        if (sdlManager != null) {
            sdlManager.dispose();
            sdlManager = null;
        }
    }

    private void buildSdlManager(BaseTransportConfig transport) {
        // This logic is to select the correct transport and security levels defined in the selected build flavor
        // Build flavors are selected by the "build variants" tab typically located in the bottom left of Android Studio
        // Typically in your app, you will only set one of these.
        if (sdlManager == null) {
            DebugTool.logInfo("Creating SDL Manager");

            //FIXME add the transport type
            // The app type to be used
            Vector<AppHMIType> appType = new Vector<>();
            appType.add(AppHMIType.MEDIA);

            // The manager listener helps you know when certain events that pertain to the SDL Manager happen
            // Here we will listen for ON_HMI_STATUS and ON_COMMAND notifications
            SdlManagerListener listener = new SdlManagerListener() {
                @Override
                public void onStart(SdlManager sdlManager) {
                    DebugTool.logInfo("SdlManager onStart");
                }

                @Override
                public void onDestroy(SdlManager sdlManager) {
                    DebugTool.logInfo("SdlManager onDestroy ");
                    SdlService.this.sdlManager = null;
                    if(SdlService.this.callback != null){
                        SdlService.this.callback.onEnd();
                    }
                }

                @Override
                public void onError(SdlManager sdlManager, String info, Exception e) {
                }
            };


            HashMap<FunctionID,OnRPCNotificationListener> notificationListenerHashMap = new HashMap<FunctionID,OnRPCNotificationListener>();
            notificationListenerHashMap.put(FunctionID.ON_HMI_STATUS, new OnRPCNotificationListener() {
                @Override
                public void onNotified(RPCNotification notification) {
                    OnHMIStatus status = (OnHMIStatus) notification;
                    if (status.getHmiLevel() == HMILevel.HMI_FULL && ((OnHMIStatus) notification).getFirstRun()) {
                        sendCommands();
                        performWelcomeSpeak();
                        performWelcomeShow();
                    }
                }
            });

            notificationListenerHashMap.put(FunctionID.ON_COMMAND, new OnRPCNotificationListener() {
                @Override
                public void onNotified(RPCNotification notification) {
                    OnCommand command = (OnCommand) notification;
                    Integer id = command.getCmdID();
                    if(id != null){
                        switch(id){
                            case TEST_COMMAND_ID:
                                showTest();
                                break;
                        }
                    }
                }
            });


            // Create App Icon, this is set in the SdlManager builder
            SdlArtwork appIcon = new SdlArtwork(ICON_FILENAME, FileType.GRAPHIC_PNG, IMAGE_DIR+"sdl_s_green.png", true);

            // The manager builder sets options for your session
            SdlManager.Builder builder = new SdlManager.Builder(APP_ID, APP_NAME, listener);
            builder.setAppTypes(appType);
            builder.setTransportType(transport);
            builder.setAppIcon(appIcon);
            builder.setRPCNotificationListeners(notificationListenerHashMap);
            sdlManager = builder.build();
        }
    }

    /**
     *  Add commands for the app on SDL.
     */
    private void sendCommands(){
        AddCommand command = new AddCommand();
        MenuParams params = new MenuParams();
        params.setMenuName(TEST_COMMAND_NAME);
        command.setCmdID(TEST_COMMAND_ID);
        command.setMenuParams(params);
        command.setVrCommands(Collections.singletonList(TEST_COMMAND_NAME));
        sdlManager.sendRPC(command);
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
        sdlManager.getScreenManager().setPrimaryGraphic(new SdlArtwork(SDL_IMAGE_FILENAME, FileType.GRAPHIC_PNG, IMAGE_DIR+"sdl.png", true));
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
        sdlManager.getScreenManager().setTextField1("Command has been selected");
        sdlManager.getScreenManager().setTextField2("");
        sdlManager.getScreenManager().commit(null);

        sdlManager.sendRPC(new Speak(TTSChunkFactory.createSimpleTTSChunks(TEST_COMMAND_NAME)));
    }


    public interface SdlServiceCallback{
        void onEnd();
    }



}
