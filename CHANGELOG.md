# 4.11.0 Release Notes

### Summary:
||Version|
|--|--|
|**Protocol** |5.2.0
| **RPC** |6.0.0
| **Tested Targeting** |Android 29

## Features

- [[SDL 0234] Proxy Library RPC Generation](https://github.com/smartdevicelink/sdl_java_suite/issues/1090)

- [[SDL 0124] SDLImageUploadManager](https://github.com/smartdevicelink/sdl_java_suite/issues/1326)

- [[SDL 0191] Retry Failed File Uploads](https://github.com/smartdevicelink/sdl_java_suite/issues/830)

- [[SDL 0220] Support for Android custom RouterService](https://github.com/smartdevicelink/sdl_java_suite/issues/1079)

- [Use GitHub Actions ](https://github.com/smartdevicelink/sdl_java_suite/pull/1233)


## Enhancements

- [Align SystemCapabilityManager with iOS](https://github.com/smartdevicelink/sdl_java_suite/issues/1324)

- [SystemCapabilityManager doesn't fully support capability subscriptions ](https://github.com/smartdevicelink/sdl_java_suite/issues/1255)

- [Align Video Streaming State with iOS](https://github.com/smartdevicelink/sdl_java_suite/issues/1331)

- [SoftButtonObject override Equals method](https://github.com/smartdevicelink/sdl_java_suite/issues/1257)

- [Lock Screen Icon is not cached](https://github.com/smartdevicelink/sdl_java_suite/issues/1316)

- [Update License to 2020](https://github.com/smartdevicelink/sdl_java_suite/issues/1264)


## Bug Fixes

- [Inconsistently in ScreenManager behavior if displayCapabilities is null](https://github.com/smartdevicelink/sdl_java_suite/issues/1310)

- [Remove left over code in hellosdl](https://github.com/smartdevicelink/sdl_java_suite/pull/1330)

- [VideoStreaming does not start during HMI Level "LIMITED".](https://github.com/smartdevicelink/sdl_java_suite/issues/1289)

- [Scale Workaround for legacy high-res displays](https://github.com/smartdevicelink/sdl_java_suite/issues/1282)

- [Refactor router service's exitForeground logic](https://github.com/smartdevicelink/sdl_java_suite/pull/1311)

- [No Overwrite property for SdlFiles](https://github.com/smartdevicelink/sdl_java_suite/issues/1302)

- [Deprecate DeviceInfo.DEVICE_OS](https://github.com/smartdevicelink/sdl_java_suite/issues/1290)

- [Missing enums ](https://github.com/smartdevicelink/sdl_java_suite/issues/1283)

- [BaseFileManager UploadFiles handles static icons differently then UploadFile](https://github.com/smartdevicelink/sdl_java_suite/issues/1284)

- [USB transport drops remaining data after PSM FINISHED or ERROR state](https://github.com/smartdevicelink/sdl_java_suite/issues/1279)

- [Delete notification channel when possible](https://github.com/smartdevicelink/sdl_java_suite/pull/1271)

- [Null pointer exception in startStream if hapticSpatialDataSupported is missing](https://github.com/smartdevicelink/sdl_java_suite/issues/1245)

- [Issue in VoiceCommandManager.setVoiceCommands()](https://github.com/smartdevicelink/sdl_java_suite/issues/1293)

- [Video streaming doesn't start again after closing the app using voice commands](https://github.com/smartdevicelink/sdl_java_suite/issues/1248)

- [Potential NPE in TextAndGraphicManager](https://github.com/smartdevicelink/sdl_java_suite/issues/1237)