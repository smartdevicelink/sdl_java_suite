# 4.7.0 Release Notes

## Summary

- Manager APIs - The manager APIs will closely align with the iOS SDL Library managers, with a few exceptions to match the native platform. 
- Transport layer overhaul - The protocol layer and transport layer have been overhauled to properly match the stack in which they should exist. This also sets up three additional features
    - AOA multiplexing - SDL apps can now use the multiplexing transport with the AOA/USB transport. Multiple apps can then register on a single AOA connection to SDL Core.
    - Primary/Secondary transports - apps can now carry their session over multiple transports with the first transport being primary, and a later connected one being a secondary. This means apps can register over bluetooth, then connect over WiFi when necessary (video/audio streaming).
    - All apps should be using `MultiplexingConfig` at this point unless debugging with TCP.
- Color Scheme for templates - App developers now have the ability to set color themes for the templates they use
- New Remote Control modules
- Additional vehicle data added
- `SdlProxyALM` has been deprecated - The `SdlProxyALM` will still function for this release but it has now moved into maintenance mode and no new features will be added. The manager APIs should be used from this point forward.


## New Features

- [Add enum for `predefinedlayout`](https://github.com/smartdevicelink/sdl_android/pull/851)
- [Feature/Listen for responses and capability changes in ISdl](https://github.com/smartdevicelink/sdl_android/pull/828)
- [Add ability for RPCs to be versioned/formatted](https://github.com/smartdevicelink/sdl_android/pull/839)
- [[SDL 0159] Static SDL Icon Names Enum](https://github.com/smartdevicelink/sdl_android/issues/740)

#### Transport

- [[SDL 0141] Supporting simultaneous multiple transports](https://github.com/smartdevicelink/sdl_android/issues/714)
- [[SDL 0194] Android Transport Layer Overhaul](https://github.com/smartdevicelink/sdl_android/issues/841)

#### Manager API

- [[SDL 0171] Android Manager APIs](https://github.com/smartdevicelink/sdl_android/issues/782)
- [[SDL 0113] SDLAudioStreamManager](https://github.com/smartdevicelink/sdl_android/issues/654)

#### RPC Updates

- [Add PLAY_PAUSE to ButtonName enum](https://github.com/smartdevicelink/sdl_android/issues/228)
- [[SDL 0014] Adding Audio File Playback to TTSChunk](https://github.com/smartdevicelink/sdl_android/issues/419)
- [[SDL 0037] Expand Mobile `PutFile` RPC](https://github.com/smartdevicelink/sdl_android/issues/452)
- [[SDL 0041] Provide AppIcon resumption across app registration requests](https://github.com/smartdevicelink/sdl_android/issues/453)
- [[SDL 0062] Template images](https://github.com/smartdevicelink/sdl_android/issues/533)
- [[SDL 0064] Choice-VR optional](https://github.com/smartdevicelink/sdl_android/issues/739)
- [[SDL 0063] Display name parameter](https://github.com/smartdevicelink/sdl_android/issues/534)
- [[SDL 0163] Make spaceAvailable field non-mandatory ](https://github.com/smartdevicelink/sdl_android/issues/860)
- [[SDL 0083] Expandable Design for Proprietary Data Exchange](https://github.com/smartdevicelink/sdl_android/issues/594)
- [[SDL 0085] SubMenu Icon](https://github.com/smartdevicelink/sdl_android/issues/603)
- [[SDL 0109] SetAudioStreamingIndicator RPC](https://github.com/smartdevicelink/sdl_android/issues/710)
- [[SDL 0147] Template Improvements: Color Scheme](https://github.com/smartdevicelink/sdl_android/issues/715)
- [[SDL 0150] Enhancing onHMIStatus with a New Parameter for Video Streaming State](https://github.com/smartdevicelink/sdl_android/issues/734)
- [[SDL 0151] ImageFieldName for SecondaryImage](https://github.com/smartdevicelink/sdl_android/issues/724)
- [[SDL 0153] Support for Short and Full UUID App ID](https://github.com/smartdevicelink/sdl_android/issues/738)

#### Remote Control

- [[SDL 0099] New modules LIGHT, AUDIO, HMI_SETTINGS and parameter SIS Data](https://github.com/smartdevicelink/sdl_android/issues/624)
- [[SDL 0105] New Seat module](https://github.com/smartdevicelink/sdl_android/issues/651)
- [[SDL 0106] OnRCStatus notification](https://github.com/smartdevicelink/sdl_android/issues/657)
- [[SDL 0160] Radio Parameter Update](https://github.com/smartdevicelink/sdl_android/issues/741)
- [[SDL 0165] Lights modules -  More Names and Status Values](https://github.com/smartdevicelink/sdl_android/issues/751)
- [[SDL 0172] Update OnRCStatus with a new allowed parameter](https://github.com/smartdevicelink/sdl_android/issues/783)
- [[SDL 0182] Audio Source AM/FM/XM/DAB](https://github.com/smartdevicelink/sdl_android/issues/809)

#### Vehicle Data

- [[SDL 0072] FuelRange](https://github.com/smartdevicelink/sdl_android/issues/552)
- [[SDL 0082] EngineOilLife](https://github.com/smartdevicelink/sdl_android/issues/593)
- [[SDL 0097] Tire pressure additions](https://github.com/smartdevicelink/sdl_android/issues/613)
- [[SDL 0102] ElectronicParkBrakeStatus](https://github.com/smartdevicelink/sdl_android/issues/632)
- [[SDL 0107] TurnSignal](https://github.com/smartdevicelink/sdl_android/issues/650)
- [[SDL 0175] Updating DOP value range for GPS notification](https://github.com/smartdevicelink/sdl_android/issues/803)


## Bug Fixes

- [SystemCapabilityManager false positive issue](https://github.com/smartdevicelink/sdl_android/issues/844)
- [Optimize video streaming for still graphics](https://github.com/smartdevicelink/sdl_android/issues/806)
- [sdl.router.startservice broadcast is sent twice unexpectedly](https://github.com/smartdevicelink/sdl_android/issues/884)
- [maxBitrate in VIDEO_STREAMING capability is read in wrong unit](https://github.com/smartdevicelink/sdl_android/issues/882)


## Documentation

- [Add third_party file](https://github.com/smartdevicelink/sdl_android/issues/865)

