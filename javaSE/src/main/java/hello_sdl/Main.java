package hello_sdl;

import android.util.Log;
import com.smartdevicelink.managers.SdlManager;
import com.smartdevicelink.managers.SdlManagerListener;
import com.smartdevicelink.managers.lifecycle.LifecycleManager;
import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.rpc.*;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.transport.WebSocketServerConfig;
import com.smartdevicelink.util.Version;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {

    private static final String TAG = "hello_sdl.Main";

    public static void main(String[] args) {
       // testRAIString();
        startSdl();
    }

    public static void testRAI(){


        SdlMsgVersion sdlMsgVersion = new SdlMsgVersion();
        sdlMsgVersion.setMajorVersion(5);
        sdlMsgVersion.setMinorVersion(0);
        sdlMsgVersion.setPatchVersion(0);

        RegisterAppInterface rai = new RegisterAppInterface(sdlMsgVersion,
                "AppName",false, null,
                null,"78srtv78vt789vs29");

        rai.setCorrelationID(65529);

        rai.format(new Version(5,0,0),true);

        byte[] msgBytes = JsonRPCMarshaller.marshall(rai, (byte)4);
    }

    public static void testRAIString(){

       String rawRai = "{\r\n  \"request\":{\r\n    \"name\":\"RegisterAppInterface\",\r\n    \"correlationID\":141,\r\n    \"parameters\":{\r\n      \"ttsName\":[\r\n        {\r\n          \"text\":\"Phrase 1\",\r\n          \"type\":\"TEXT\"\r\n        },\r\n        {\r\n          \"text\":\"Phrase 2\",\r\n          \"type\":\"TEXT\"\r\n        }\r\n      ],\r\n      \"hmiDisplayLanguageDesired\":\"EN-US\",\r\n      \"appHMIType\":[\r\n        \"SOCIAL\",\r\n        \"MEDIA\"\r\n      ],\r\n      \"appID\":\"t4weGRSWY\",\r\n      \"languageDesired\":\"PT-BR\",\r\n      \"deviceInfo\":{\r\n        \"hardware\":\"My Hardware\",\r\n        \"firmwareRev\":\"My Firmware Revision\",\r\n        \"os\":\"Windows\",\r\n        \"osVersion\":\"95\",\r\n        \"carrier\":\"nobody\",\r\n        \"maxNumberRFCOMMPorts\":2\r\n      },\r\n      \"appName\":\"Dumb app\",\r\n      \"ngnMediaScreenAppName\":\"DA\",\r\n      \"isMediaApplication\":true,\r\n      \"vrSynonyms\":[\r\n        \"dumb\",\r\n        \"really dumb app\"\r\n      ],\r\n      \"syncMsgVersion\":{\r\n        \"majorVersion\":3,\r\n        \"minorVersion\":64\r\n      },\r\n      \"hashID\":\"y534htz\"\r\n    }\r\n  },\r\n  \"response\":{\r\n    \"name\":\"RegisterAppInterfaceResponse\",\r\n    \"correlationID\":142,\r\n    \"parameters\":{\r\n      \"vehicleType\":{\r\n        \"make\":\"Chrysler\",\r\n        \"model\":\"Crossfire\",\r\n        \"modelYear\":\"1820\",\r\n        \"trim\":\"Gold\"\r\n      },\r\n      \"speechCapabilities\":[\r\n        \"SAPI_PHONEMES\",\r\n        \"TEXT\",\r\n        \"PRE_RECORDED\"\r\n      ],\r\n      \"vrCapabilities\":[\r\n        \"Text\"\r\n      ],\r\n      \"audioPassThruCapabilities\":[\r\n        {\r\n          \"samplingRate\":\"16KHZ\",\r\n          \"audioType\":\"PCM\",\r\n          \"bitsPerSample\":\"16_BIT\"\r\n        },\r\n        {\r\n          \"samplingRate\":\"44KHZ\",\r\n          \"audioType\":\"PCM\",\r\n          \"bitsPerSample\":\"8_BIT\"\r\n        }\r\n      ],\r\n      \"hmiZoneCapabilities\":[\r\n        \"FRONT\",\r\n        \"BACK\"\r\n      ],\r\n      \"prerecordedSpeech\":[\r\n        \"HELP_JINGLE\",\r\n        \"LISTEN_JINGLE\",\r\n        \"NEGATIVE_JINGLE\"\r\n      ],\r\n      \"supportedDiagModes\":[\r\n        324,\r\n        2356,\r\n        865,\r\n        211,\r\n        8098\r\n      ],\r\n      \"syncMsgVersion\":{\r\n        \"majorVersion\":3,\r\n        \"minorVersion\":64\r\n      },\r\n      \"language\":\"EN-US\",\r\n      \"buttonCapabilities\":[\r\n        {\r\n          \"name\":\"SEEKRIGHT\",\r\n          \"shortPressAvailable\":true,\r\n          \"longPressAvailable\":false,\r\n          \"upDownAvailable\":true\r\n        },\r\n        {\r\n          \"name\":\"TUNEDOWN\",\r\n          \"shortPressAvailable\":false,\r\n          \"longPressAvailable\":true,\r\n          \"upDownAvailable\":false\r\n        }\r\n      ],\r\n      \"displayCapabilities\":{\r\n        \"displayType\":\"TYPE2\",\r\n        \"mediaClockFormats\":[\r\n          \"CLOCKTEXT3\",\r\n          \"CLOCK1\"\r\n        ],\r\n        \"textFields\":[\r\n          {\r\n            \"width\":480,\r\n            \"characterSet\":\"TYPE5SET\",\r\n            \"rows\":360,\r\n            \"name\":\"alertText2\"\r\n          },\r\n          {\r\n            \"width\":1980,\r\n            \"characterSet\":\"CID2SET\",\r\n            \"rows\":960,\r\n            \"name\":\"scrollableMessageBody\"\r\n          },\r\n        ],\r\n        \"imageFields\":[\r\n          {\r\n            \"imageTypeSupported\":[\r\n              \"GRAPHIC_JPEG\",\r\n              \"AUDIO_AAC\"\r\n            ],\r\n            \"imageResolution\":{\r\n              \"resolutionWidth\":640,\r\n              \"resolutionHeight\":480\r\n            },\r\n            \"name\":\"menuIcon\"\r\n          },\r\n          {\r\n            \"imageTypeSupported\":[\r\n              \"BINARY\",\r\n              \"AUDIO_WAVE\"\r\n            ],\r\n            \"imageResolution\":{\r\n              \"resolutionWidth\":320,\r\n              \"resolutionHeight\":240\r\n            },\r\n            \"name\":\"graphic\"\r\n          }\r\n        ],\r\n        \"graphicSupported\":true,\r\n        \"screenParams\":{\r\n          \"resolution\":{\r\n            \"resolutionWidth\":1200,\r\n            \"resolutionHeight\":800\r\n          },\r\n          \"touchEventAvailable\":{\r\n            \"pressAvailable\":true,\r\n            \"multiTouchAvailable\":false,\r\n            \"doublePressAvailable\":true\r\n          }\r\n        },\r\n        \"templatesAvailable\":[\r\n          \"Template 1\",\r\n          \"Template 2\",\r\n          \"Template 3\"\r\n        ],\r\n        \"numCustomPresetsAvailable\":5\r\n      },\r\n      \"hmiDisplayLanguage\":\"ES-ES\",\r\n      \"softButtonCapabilities\":[\r\n        {\r\n          \"imageSupported\":false,\r\n          \"shortPressAvailable\":true,\r\n          \"longPressAvailable\":false,\r\n          \"upDownAvailable\":true\r\n        },\r\n        {\r\n          \"imageSupported\":true,\r\n          \"shortPressAvailable\":false,\r\n          \"longPressAvailable\":true,\r\n          \"upDownAvailable\":false\r\n        }\r\n      ],\r\n      \"presetBankCapabilities\":{\r\n        \"OnScreenPresetsAvailable\":false\r\n      },\r\n      \"bulkData\":[\r\n        0,\r\n        1,\r\n        2\r\n      ]\r\n    }\r\n  }\r\n}";
        System.out.print(rawRai);
        System.out.print("\n");

        try {
            JSONObject jsonObject = new JSONObject(rawRai);
            if(jsonObject != null){
                System.out.print("The package was accepted");

            }else{
                System.out.print("The system is down");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static void attemptSdlManager(){
        SdlManager.Builder builder = new SdlManager.Builder("234523452345234", "JavaChip", new SdlManagerListener() {
            @Override
            public void onStart(SdlManager manager) {
                Log.i(TAG, "OnStart");
                manager.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, new OnRPCNotificationListener() {
                    @Override
                    public void onNotified(RPCNotification notification) {
                        Log.i(TAG, "on notified");
                        OnHMIStatus hmiStatus = (OnHMIStatus)notification;
                        if(HMILevel.HMI_FULL.equals(hmiStatus.getHmiLevel())) {
                            if (hmiStatus.getFirstRun()) {
                                //TOD DO a show
                                Show show = new Show();
                                show.setMainField1("There's snake in my boots");
                                show.setMainField2("YEET THAT SUCKER!");
                                manager.sendRPC(show);
                                Log.i(TAG, "Attempting sending show");


                            }
                        }
                    }
                });
            }

            @Override
            public void onDestroy(SdlManager manager) {
                Log.i(TAG, "onDestroy");

            }

            @Override
            public void onError(SdlManager manager, String info, Exception e) {
                Log.i(TAG, "OnError");
            }
        });
        //FIXME have to add websocket setting
       SdlManager manager =  builder.build();
       manager.start();


    }

    public static void startSdl(){
        System.out.println("Hello World!");

        Thread thread = new Thread(new Runnable() {
            boolean end = false;

            @Override
            public void run() {
        LifecycleManager.AppConfig config = new LifecycleManager.AppConfig();
        config.setAppID("234523452345234");
        config.setAppName("JavaChip");

        WebSocketServerConfig serverConfig = new WebSocketServerConfig(5679,0);
        LifecycleManager lifer = new LifecycleManager(config, serverConfig, new LifecycleManager.LifecycleListener() {
            @Override
            public void onProxyConnected(LifecycleManager lifeCycleManager) {
                System.out.print("On proxy CONNECTED");

                lifeCycleManager.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, new OnRPCNotificationListener() {
                    @Override
                    public void onNotified(RPCNotification notification) {
                        Log.i(TAG, "on notified");
                        OnHMIStatus hmiStatus = (OnHMIStatus)notification;

                        if(HMILevel.HMI_FULL.equals(hmiStatus.getHmiLevel())) {
                            if (true || hmiStatus.getFirstRun()) {
                                //TOD DO a show
                                Show show = new Show();
                                show.setMainField1("There's snake in my boots");
                                show.setMainField2("YEET THAT SUCKER!");
                                lifeCycleManager.sendRPC(show);


                            }
                        }
                    }
                });
            }

            @Override
            public void onProxyClosed(LifecycleManager lifeCycleManager, String info, Exception e, SdlDisconnectedReason reason) {
                System.out.print("On proxy CLOSED");
                end = true;
            }

            @Override
            public void onServiceEnded(LifecycleManager lifeCycleManager, OnServiceEnded serviceEnded) {
                System.out.print("On service ENDED");

            }

            @Override
            public void onServiceNACKed(LifecycleManager lifeCycleManager, OnServiceNACKed serviceNACKed) {
                System.out.print("On service NAKed");

            }

            @Override
            public void onError(LifecycleManager lifeCycleManager, String info, Exception e) {
                System.out.print("OnError " + info);

            }
        });
        lifer.start();
        while(true || !end){

        }
            }

        });

        thread.start();

    }
}
