# 4.12.0 Release Notes

### Summary:
||Version|
|--|--|
| **Protocol** | 5.2.0
| **RPC** | 6.0.0
| **Tested Targeting** | Android 29

## Features
- [[SDL 0230] SPP resource management for Android](https://github.com/smartdevicelink/sdl_java_suite/issues/1132)

- [[SDL 0279] Screen Manager Subscribe Buttons](https://github.com/smartdevicelink/sdl_java_suite/issues/1280)

- [[SDL 0289] Support for Set Language Separately](https://github.com/smartdevicelink/sdl_java_suite/issues/1313)

- [Add a way to set the resumption hash in the managers layer](https://github.com/smartdevicelink/sdl_java_suite/issues/1400)

- [Change lock screen logo and ensure correct contrast](https://github.com/smartdevicelink/sdl_java_suite/issues/1342)


## Enhancements
- [[SDL 0306] Use Taskmaster To Handle Queuing Operations in Managers](https://github.com/smartdevicelink/sdl_java_suite/issues/1368)

- [[SDL 0301] SDL Device Listener](https://github.com/smartdevicelink/sdl_java_suite/issues/1348)

- [Sdl Android should use the new LifecycleManager](https://github.com/smartdevicelink/sdl_java_suite/issues/1365)

- [Deprecate onError for OnRPCResponseListener](https://github.com/smartdevicelink/sdl_java_suite/pull/1404)

- [Add ForegroundServiceType Parameter to HelloSdl Project](https://github.com/smartdevicelink/sdl_java_suite/issues/1374)

- [Update javadocs for PermissionManager.addListener](https://github.com/smartdevicelink/sdl_java_suite/pull/1364)

- [PermissionManager Should send callback when subscribing to listener](https://github.com/smartdevicelink/sdl_java_suite/issues/1353)

- [Move textFields / imageFields == null checking from ScreenManager to SystemCapabilityManager](https://github.com/smartdevicelink/sdl_java_suite/issues/1335)

- [Change Http to Https](https://github.com/smartdevicelink/sdl_java_suite/issues/1333)

- [Move src/java/main/android folder](https://github.com/smartdevicelink/sdl_java_suite/issues/1377)

- [Classes that need to be removed in next major release should be deprecated ](https://github.com/smartdevicelink/sdl_java_suite/issues/1362)

- [OnSdlChoiceChosen contains duplicate class definitions](https://github.com/smartdevicelink/sdl_java_suite/issues/14)

- [The name of setter and getter in OnButtonPress are ambiguous](https://github.com/smartdevicelink/sdl_java_suite/issues/547)


## Bug Fixes

- [presentChoiceSet failed with "INVALID_ID, null"](https://github.com/smartdevicelink/sdl_java_suite/issues/1336)

- [ Android app will not receive the notification of HU when the language is switched on the HU side.](https://github.com/smartdevicelink/sdl_java_suite/issues/1372)

- [If an empty array is set for voice commands on the SDLMenuCell the AddCommand is rejected](https://github.com/smartdevicelink/sdl_java_suite/issues/1341)

- [Disconnecting a cloud app doesn't free the port](https://github.com/smartdevicelink/sdl_java_suite/issues/1339)