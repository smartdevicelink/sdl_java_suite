# 4.10.0 Release Notes

## Features

- [[SDL 0054] ChangeRegistration Manager was implemented for the Java Suite](https://github.com/smartdevicelink/sdl_java_suite/issues/1140)
- [[SDL 0116] Open Menu RPC](https://github.com/smartdevicelink/sdl_java_suite/issues/659)
- [[SDL 0115] CloseApplication RPC](https://github.com/smartdevicelink/sdl_java_suite/issues/658)
- [[SDL 0119] SDL Passenger Mode](https://github.com/smartdevicelink/sdl_java_suite/issues/735)
- [[SDL 0173] Read Generic Network Signal Data](https://github.com/smartdevicelink/sdl_java_suite/issues/990)
- [[SDL 0177] Alert icon](https://github.com/smartdevicelink/sdl_java_suite/issues/795)
- [[SDL 0179] Pixel density and Scale](https://github.com/smartdevicelink/sdl_java_suite/issues/804)
- [[SDL 0184] Cancel Interaction RPC](https://github.com/smartdevicelink/sdl_java_suite/issues/840)
- [[SDL 0186] Template Titles](https://github.com/smartdevicelink/sdl_java_suite/issues/818)
- [[SDL 0199]-Adding-GPS-Shift-support](https://github.com/smartdevicelink/sdl_java_suite/issues/875)
- [[SDL 0207] - RPC message protection](https://github.com/smartdevicelink/sdl_java_suite/issues/978)
- [[SDL 0213]- Remote Control - Radio and Climate Parameter Update](https://github.com/smartdevicelink/sdl_java_suite/issues/960)
- [[SDL 0216] Widget support](https://github.com/smartdevicelink/sdl_java_suite/issues/1069)
- [[SDL 0221] Remote Control - Allow Multiple Modules per Module Type](https://github.com/smartdevicelink/sdl_java_suite/issues/1070)
- [[SDL 0223] Add Currently Playing Media Image to MediaServiceData](https://github.com/smartdevicelink/sdl_java_suite/issues/1047)
- [[SDL 0224] Navigation Subscription Buttons](https://github.com/smartdevicelink/sdl_java_suite/issues/1068)
- [[SDL 0225] Update Published App Services](https://github.com/smartdevicelink/sdl_java_suite/issues/1060)
- [[SDL 0231] Add Tiles as an Option for Main Menus](https://github.com/smartdevicelink/sdl_java_suite/issues/1076)
- [[SDL 0243] Manager Update for DisplayCapability](https://github.com/smartdevicelink/sdl_java_suite/issues/1154)
- [[SDL 0246] Add App Services to HMICapabilities](https://github.com/smartdevicelink/sdl_java_suite/issues/1159)
- [Add showInOptionalState to LockScreenConfig along with other missing methods](https://github.com/smartdevicelink/sdl_java_suite/pull/1153)
- [Make TCP use new transport layers](https://github.com/smartdevicelink/sdl_java_suite/pull/1108)
- [FileManager now has getBytesAvailable() ](https://github.com/smartdevicelink/sdl_java_suite/issues/1133)
- [Added isHighlighted & systemAction setters to SoftButtonState](https://github.com/smartdevicelink/sdl_java_suite/issues/1127)
- [SoftButtonObject now let developers set their own ids](https://github.com/smartdevicelink/sdl_java_suite/issues/1126)
- [Added prerecordedSpeech to SystemCapabilityManager](https://github.com/smartdevicelink/sdl_java_suite/issues/1123)



## Bug Fixes

- [Prevent NPE when using Static Icons in the MenuManager](https://github.com/smartdevicelink/sdl_java_suite/issues/1193)
- [iSdlProtocol now gets notified when encrypted session is started](https://github.com/smartdevicelink/sdl_java_suite/issues/1181)
- [Prevent NPE in TransportBrokerImpl.onHardwareDisconnected(SourceFile_309)](https://github.com/smartdevicelink/sdl_java_suite/issues/1082)
- [Prevent NPE in SdlRouterService.java:3220 (handleIncommingClientMessage)](https://github.com/smartdevicelink/sdl_java_suite/issues/1101)
- [Fix isStreaming() method in VSM](https://github.com/smartdevicelink/sdl_java_suite/issues/1164)
- [Fix issues causing the video stream to not resume after stopping](https://github.com/smartdevicelink/sdl_java_suite/issues/1102) 
    - [Also see #1150](https://github.com/smartdevicelink/sdl_java_suite/issues/1150)
- [Fix an issue that caused the video (NAV) service and stream to not be able to start again after the secondary transport was disconnected and reconnected](https://github.com/smartdevicelink/sdl_java_suite/issues/1151)
- [Fix NPE in UsbAccessory.getManufacturer()](https://github.com/smartdevicelink/sdl_java_suite/issues/1086)
- [Prevent a runtime exception when service isn't started in the foreground in time](https://github.com/smartdevicelink/sdl_java_suite/issues/1167)
- [Dispose main underlying layers when SdlManager is disposed](https://github.com/smartdevicelink/sdl_java_suite/pull/1176)
- [Align some RPC response params with RPC spec and make them not mandatory ](https://github.com/smartdevicelink/sdl_java_suite/issues/1180)
- [Fix an issue where static Icons were not working with the ChoiceManager](https://github.com/smartdevicelink/sdl_java_suite/issues/1194)
- [Fix typo issue and register the SdlRouterService Bluetooth broadcast receiver to listen for state changes](https://github.com/smartdevicelink/sdl_java_suite/issues/1190)
- [Register legacy Bluetooth broadcast receiver to listen for state changes](https://github.com/smartdevicelink/sdl_java_suite/pull/1192)
- [Fix issue where video formats aren't supported, but used to start the video service](https://github.com/smartdevicelink/sdl_java_suite/pull/1206)
- [Allow FileManager to start even with certain RPCs aren't allowed, eg ListFiles](https://github.com/smartdevicelink/sdl_java_suite/pull/1201)


## Misc

- [Fix misc code analyzer issues](https://github.com/smartdevicelink/sdl_java_suite/pull/1179)
- [Old proxy classes are now deprecated ](https://github.com/smartdevicelink/sdl_java_suite/issues/1148)
- [Align lockscreen behavior between platforms](https://github.com/smartdevicelink/sdl_java_suite/issues/1125)
