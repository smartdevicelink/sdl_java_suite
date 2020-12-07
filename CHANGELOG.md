# 5.0.0 Release Notes

## Summary:
||Version|
|--|--|
| **Protocol** | 5.3.0
| **RPC** | 7.0.0
| **Tested Targeting** | Android 29

## Features:

- [[SDL 0242] Subtle Alert Style](https://github.com/smartdevicelink/sdl_java_suite/issues/1120)

- [[SDL 0278] Screen Manager Template Management](https://github.com/smartdevicelink/sdl_java_suite/issues/1327)

- [[SDL 0148] Template Improvements: Additional SubMenus](https://github.com/smartdevicelink/sdl_java_suite/issues/716)

- [[SDL 0268] Main Menu Updating and Pagination](https://github.com/smartdevicelink/sdl_java_suite/issues/1254)

- [[SDL 0152] Driver Distraction Improvements: Command List Limitations](https://github.com/smartdevicelink/sdl_java_suite/issues/729)

- [[SDL 0311] Make RPC Setters Chainable](https://github.com/smartdevicelink/sdl_java_suite/issues/1421)

- [[SDL 0308] Add a Reason Parameter to All Protocol NAKs](https://github.com/smartdevicelink/sdl_java_suite/issues/1379)

- [[SDL 0253] New vehicle data StabilityControlsStatus](https://github.com/smartdevicelink/sdl_java_suite/issues/1204)

- [[SDL 0266] New vehicle data GearStatus](https://github.com/smartdevicelink/sdl_java_suite/issues/1256)

- [[SDL 0261] New vehicle data WindowStatus](https://github.com/smartdevicelink/sdl_java_suite/issues/1243)

- [[SDL 0257] New vehicle data HandsOffSteering](https://github.com/smartdevicelink/sdl_java_suite/issues/1224)

- [[SDL 0256] Refactor Fuel Information Related Vehicle Data](https://github.com/smartdevicelink/sdl_java_suite/issues/1223)

- [[SDL 0273] WebEngine Projection mode](https://github.com/smartdevicelink/sdl_java_suite/issues/1375)

- [[SDL 0202] Supported Character Sets](https://github.com/smartdevicelink/sdl_java_suite/issues/950)

## Enhancements:

- [[SDL 0286] Java Suite Cleanup](https://github.com/smartdevicelink/sdl_java_suite/issues/1306)

- [Android X support](https://github.com/smartdevicelink/sdl_java_suite/issues/1094)

- [RPC Generator should use @deprecated annotation in JavaDocs when applicable](https://github.com/smartdevicelink/sdl_java_suite/issues/1448)

- [RPC Generator needs to be updated to generate correct Android X imports](https://github.com/smartdevicelink/sdl_java_suite/issues/1444)

- [onError should be removed from OnRPCResponseListener](https://github.com/smartdevicelink/sdl_java_suite/issues/1455)

- [SystemCapabilityManager & HapticInterfaceManager should be moved to the managers package](https://github.com/smartdevicelink/sdl_java_suite/issues/1432)

- [[SDL 0193] Update SDL-Android minimum SDK](https://github.com/smartdevicelink/sdl_java_suite/issues/835)

- [Refactor Text & Graphic Manager](https://github.com/smartdevicelink/sdl_java_suite/issues/1464)

- [Refactor session and protocol interfaces](https://github.com/smartdevicelink/sdl_java_suite/pull/1430)

- [Remove SdlProxy classes](https://github.com/smartdevicelink/sdl_java_suite/pull/1471)

- [Remove FrameData Class](https://github.com/smartdevicelink/sdl_java_suite/pull/1466)

- [Two StreamPacketizer classes](https://github.com/smartdevicelink/sdl_java_suite/issues/1272)

- [SdlFile in javaSE should have a constructor that takes URI as file source](https://github.com/smartdevicelink/sdl_java_suite/issues/1469)

- [The default value for overwrite property in SdlFile should be false to align with iOS ](https://github.com/smartdevicelink/sdl_java_suite/issues/1451)

- [Deprecated RPC APIs that don't exist in the spec should be removed ](https://github.com/smartdevicelink/sdl_java_suite/issues/1446)

- [[SDL 0200] - Removing URL Parameter Max Length](https://github.com/smartdevicelink/sdl_java_suite/issues/906)

- [Integration validator](https://github.com/smartdevicelink/sdl_java_suite/pull/1436)

- [Gradle dependencies needs to be updates](https://github.com/smartdevicelink/sdl_java_suite/issues/1459)

- [BSON library should be updated to the latest version (1.2.2)](https://github.com/smartdevicelink/sdl_java_suite/issues/1542)

- [Update Project Documentation in README](https://github.com/smartdevicelink/sdl_java_suite/issues/1534)

- [Feature/lockscreen ui testing](https://github.com/smartdevicelink/sdl_java_suite/pull/1527)

## Bug Fixes:

- [LockScreen behavior is incorrect for Display Always and Dismissible](https://github.com/smartdevicelink/sdl_java_suite/issues/1515)

- [NPE with SoftButtonCapabilities using Core](https://github.com/smartdevicelink/sdl_java_suite/issues/1499)

- [NPE Enum doesn't exist](https://github.com/smartdevicelink/sdl_java_suite/issues/1495)

- [NullPointerException While updating Image/Text after layout Change.](https://github.com/smartdevicelink/sdl_java_suite/issues/1465)

- [javaSE and javaEE links in the readme file don't reference the latest version of the library ](https://github.com/smartdevicelink/sdl_java_suite/issues/1449)

- [ScreenManager doesn't work when app register on backup transport](https://github.com/smartdevicelink/sdl_java_suite/issues/1518)

- [Difficult to import sdl_android as source library](https://github.com/smartdevicelink/sdl_java_suite/issues/1048)

- [Some cloud apps fail to download icons ](https://github.com/smartdevicelink/sdl_java_suite/issues/1513)

- [ANRs (Application Not Responding) occur when switching from App1 to App2 on HU.](https://github.com/smartdevicelink/sdl_java_suite/issues/1398)

- [Lists with initial values of null are not returned properly in RPCs](https://github.com/smartdevicelink/sdl_java_suite/issues/1473)

- [ClassCastException when calling RPC getter method returning Float type](https://github.com/smartdevicelink/sdl_java_suite/issues/1407)

- [TransportManager.exitLegacyMode () : NullPointerException ](https://github.com/smartdevicelink/sdl_java_suite/issues/1412)

- [RPC Generator is not generating the corresponding Javadoc for min & max attributes ](https://github.com/smartdevicelink/sdl_java_suite/issues/1438)

- [RPC Generator is generating enum values with wrong capitalization](https://github.com/smartdevicelink/sdl_java_suite/issues/1425)

- [RPC generator strips text after @TODO](https://github.com/smartdevicelink/sdl_java_suite/issues/1506)

- [Some RPCs in the code do not exactly match the spec](https://github.com/smartdevicelink/sdl_java_suite/issues/1545)

- [Fix issue in setting MediaTrack in the T&G manager](https://github.com/smartdevicelink/sdl_java_suite/pull/1544)

- [There are a few deprecated functions that don't link to the correct function](https://github.com/smartdevicelink/sdl_java_suite/issues/1536)

- [Some cloud apps fail to download icons](https://github.com/smartdevicelink/sdl_java_suite/issues/1513)
 
- [Fix inconsistency with HMICapabilities API names](https://github.com/smartdevicelink/sdl_java_suite/pull/1508)

- [LockScreen behavior is incorrect for Display Always and Dismissible](https://github.com/smartdevicelink/sdl_java_suite/issues/1515)

