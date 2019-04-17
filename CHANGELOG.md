# 4.8.0 Release Notes

### Feature
- Renamed repo to `sdl_java_suite`
- Complete restructure of project to include all Java based projects (Android, JavaSE, and JavaEE). This included creating base source sets for all projects.
- [[SDL 0203] Added JavaSE and JavaEE libraries to support embedded, remote, and cloud applications.](https://github.com/smartdevicelink/sdl_android/pull/983)
- [[SDL 0158] Added new `SetCloudAppProperties`, `GetCloudAppProperties`, new vehicle data item -`cloudAppVehicleID`, and retrieval of auth token sent during `StartServiceACK` for RPC service.  ](https://github.com/smartdevicelink/sdl_android/issues/981)
- [[SDL 0167] Add the new App Services feature. This includes app service types Media, Weather, and Navigation](https://github.com/smartdevicelink/sdl_android/issues/810)
- [Add support for `TemplateImages` in `ScreenManager`](https://github.com/smartdevicelink/sdl_android/issues/916)
- [Added ability to access the `RegisterAppInterface` Response message from `SdlManager`](https://github.com/smartdevicelink/sdl_android/issues/928)
- [[SDL 0208]- Allow developers to block old SDL Head Units from Mobile](https://github.com/smartdevicelink/sdl_android/issues/955)
- [SDL 0197- Update `SetMediaClockTimer` Initializers](https://github.com/smartdevicelink/sdl_android/issues/856)
- [[SDL 0196] Add Support for `StaticIcons` to `SDLArtwork`](https://github.com/smartdevicelink/sdl_android/issues/848)

 
### Bug fix
- [Duplicate send for ButtonPress on v5+](https://github.com/smartdevicelink/sdl_android/issues/1007)
- [Fixed issue where `SdlManagerListener.onDestroy()` was not  being called after transport disconnect when bound to older router services.](https://github.com/smartdevicelink/sdl_android/issues/932)
 - [Vastly improve AOA USB connection stability especially during disconnect/reconnect.](https://github.com/smartdevicelink/sdl_android/issues/924)
- [Fix legacy BT disconnect issue that left library listening for BT connection](https://github.com/smartdevicelink/sdl_android/pull/935)
- [Fixed the `ScreenManager` not being able to clear images](https://github.com/smartdevicelink/sdl_android/pull/954)
- [Fixed issue with `ScreenManager` where it would not call completion listener if TextAndGraphicManager was not dirty](https://github.com/smartdevicelink/sdl_android/issues/930)
- [Make `SystemCapabilityManager` query only for queryable capabilities](https://github.com/smartdevicelink/sdl_android/pull/966)
 - Fix potential NPEs in `SdlRouterService`: [1](https://github.com/smartdevicelink/sdl_android/issues/951), [2](https://github.com/smartdevicelink/sdl_android/pull/956), [3](https://github.com/smartdevicelink/sdl_android/pull/957), [4](https://github.com/smartdevicelink/sdl_android/issues/945)
- [Fixed `VirtualDisplayEncoder` from sending unexpected buffer at startup](https://github.com/smartdevicelink/sdl_android/issues/921)
- [Fixed issue where `SdlRouterService` wasn't sending EndSession frame with valid hash ID for protocol version >= 5](https://github.com/smartdevicelink/sdl_android/issues/943)
- [Fixed an issue with the `ScreenManager` submanagers starting at incorrect time](https://github.com/smartdevicelink/sdl_android/pull/938)

### Misc 
- [Remove unnecessary imports](https://github.com/smartdevicelink/sdl_android/issues/923)
- [Updated incorrect JavaDocs for `UpdateTurnList`](https://github.com/smartdevicelink/sdl_android/issues/1017)
- [Show warning if default `SdlRouterService` class is used in `SdlReceiver`. ](https://github.com/smartdevicelink/sdl_android/issues/975)


