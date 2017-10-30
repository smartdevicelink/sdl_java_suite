# 4.4.0 Release Notes

### API New Features & Breaking Changes
- Now uses compile version 26 to handle breaking changes in Android Oreo
- `MOBILE_PROJECTION` added as an app type
- Gesture cancellation was added as a touch even type
- More languages were added to the `Language` enum
- `SystemCapaibilityQuery` was added with a new `SystemCapabilityManager` that can retrieve capabilities
- `VideoStreamingCapabilities` were added. Includes supported codecs, resolution, etc
- Added constructed payloads using BSON
- SDL Remote Control functionality was added. Supports radio and climate controls.
- Added `MetadataType` to `Show` lines
- Spatial data for video streaming apps added with `HapticData`


### Enhancements
- Enhanced video streaming APIs
- Added much more test coverage
- Updated buffer read in sizes and streaming packetizers to use TLS max record size
- Consolidated all references to `sdl.router.startservice` string into single constant
- Refactored RPC classes to consolidate redundant code for retrieving items from underlying data structures
- `MultiplexBluetoothTransport` is no longer a singleton
- Improved inline documentation
- h.264 streaming now includes SPS/PPS NAL units with every I frame to match iOS library
- Real-time Transport Protocol (RTP) video streaming is now supported
- Correlation IDs are now set automatically. Can be retrieved or overwritten by developer.
- Introduced new video streaming callback and deprecated used of pipped streams
- Added an internal interface for common functions between different managers

### Bug Fixes
- Fixed potential out of bounds exception in `BinaryFrameHeader` 
- Fixed issues with unit tests and TravisCI
- Fixed potential NPE in `SdlProxyBase`method, `performBaseCommon()` 
- Fixed potential NPE in `MultiplexTransport` constructor 
- Fixed potential NPE in `SdlRouterService`method, `handleMessaage()` for `AltTransportHandler` 
- Fixed potential NPE in `SdlRouterService`method, `writeBytesToTransport()`
- Removed hardcoded `androidDebuggable = "true"` from manifest
- Added fixes to be compatible with Android Oreo (Does not supported target API level 26 yet)
- Fixed issue where intent from router service was delayed
- Apps now trust themselves as router service hosts
- Removed ambiguous validation call in `SdlBroadcastReceiver` and unused intent extra
- Cleared all warnings from `SdlProxyBase`

### Meta
- Added integration that will deploy to Bintray
- Issue and pull request templates were added for GitHub
- Codecov integration was added
- Updated README links
