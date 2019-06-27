# 4.9.0 Release Notes

## Feature

- [[SDL 0157] Mobile Choice Set Manager](https://github.com/smartdevicelink/sdl_java_suite/issues/764)
- [[SDL 0155] Mobile Menu Manager](https://github.com/smartdevicelink/sdl_java_suite/issues/730)
- [[SDL 0210]-Mobile Manager Dynamic Menu Cell Updating ](https://github.com/smartdevicelink/sdl_java_suite/issues/961)
- [[SDL 0232] Added Pushing Buffer Support to AudioStreamManager](https://github.com/smartdevicelink/sdl_java_suite/issues/1075)
- [Library will dynamically check if head unit requires VR synonyms for Choice items](https://github.com/smartdevicelink/sdl_java_suite/issues/941)
- [VideoStreamingManager now fully supports multi-touch events](https://github.com/smartdevicelink/sdl_java_suite/issues/972)



## Bug Fixes

- [Symlinks broken on Windows](https://github.com/smartdevicelink/sdl_java_suite/issues/1062) - Windows user must run a gradle task (`buildWindowSymLinks`) in order to create the Windows version of symlinks. 
- [Audio over AOA Issues](https://github.com/smartdevicelink/sdl_java_suite/issues/1056) - Media apps will now wait until an audio output device is ready before connecting over a transport. This can be overwritten by the developer.
- [Fixed LeftRearInflatableBelted using the wrong key value in BeltStatus Class](https://github.com/smartdevicelink/sdl_java_suite/issues/1078)
- [New Router Service and old USB Transport compatibility issues](https://github.com/smartdevicelink/sdl_java_suite/issues/1064) - Fixed an issue that caused apps not close when a legacy AOA app was chosen by the user.
- [Fixed an issue with `sendSequentialRPCs` where it didn't call `onResponse` and stoped on first `onError`](https://github.com/smartdevicelink/sdl_java_suite/issues/1061)

## Misc
- [Updated library to use the newest BSON library](https://github.com/smartdevicelink/sdl_java_suite/pull/1072)
- [Update gradle plugin](https://github.com/smartdevicelink/sdl_java_suite/pull/1077)
