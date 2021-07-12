# 5.2.0 Release Notes

## Summary:
||Version|
|--|--|
| **Protocol** | 5.4.0
| **RPC** | 7.1.0
| **Tested Targeting** | Android 30


## Bug Fixes / Enhancements:

- [MenuManager sending secondary image with menuCells when menuCommandSecondaryImage is not supported.](https://github.com/smartdevicelink/sdl_java_suite/issues/1688)

- [IllegalArgumentException when starting video stream with custom RPC MTU](https://github.com/smartdevicelink/sdl_java_suite/issues/1667)

- [Send voiceCommand with duplicate strings](https://github.com/smartdevicelink/sdl_java_suite/issues/1664)

- [Two voiceCommands contains the same string](https://github.com/smartdevicelink/sdl_java_suite/issues/1677)

- [java.lang.NegativeArraySizeException Crash at SdlPsm.java line 241 com.smartdevicelink.transport.SdlPsm.transitionOnInput](https://github.com/smartdevicelink/sdl_java_suite/issues/1678)

- [Exception handling variances](https://github.com/smartdevicelink/sdl_java_suite/issues/1687)

- [Allow SdlDeviceListener to start after BT connection](https://github.com/smartdevicelink/sdl_java_suite/pull/1685)

- [voiceCommand that contains no string should be removed](https://github.com/smartdevicelink/sdl_java_suite/issues/1675)

- [Image returned as "not uploaded" in certain circumstances when it's already uploaded, leading to the image being unusable](https://github.com/smartdevicelink/sdl_java_suite/issues/1692)

- [Primary Graphic not sent to SDL Core for Media Template ](https://github.com/smartdevicelink/sdl_java_suite/issues/1690)

- [Race condition leads to NPE in TransportManager](https://github.com/smartdevicelink/sdl_java_suite/issues/1703)

- [Avoid deleting and setting identical voice commands](https://github.com/smartdevicelink/sdl_java_suite/issues/1676)

- [Sdl disconnection is not notified to the app](https://github.com/smartdevicelink/sdl_java_suite/issues/1697)

- [PredefinedLayout.NON_MEDIA not found in templatesAvailable](https://github.com/smartdevicelink/sdl_java_suite/issues/1705)

- [Lockscreen should show again after dismissal if a DD notification is received where DismissalEnabled is false](https://github.com/smartdevicelink/sdl_java_suite/issues/1695)

- [Choice Cells and Menu Cells do not take which properties are available into account for uniqueness](https://github.com/smartdevicelink/sdl_java_suite/issues/1682)

- [BSON library should be updated to the latest version (1.2.5)](https://github.com/smartdevicelink/sdl_java_suite/issues/1712)





