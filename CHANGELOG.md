# 4.5.0 Release Notes

### API New Features & Breaking Changes
- **IMPORTANT:** `SdlRouterService` manifest declarations now require an `intent-filter` and `meta-data` tags. These changes can be found in the documentation.
- Now targeting version 26 to make use of new Android Oreo features.
    - Most broadcast intents are now sent explicitly instead of implicitly 
- Added methods to send a batch of RPCs. This includes chaining RPC messages or sending them all at once

### Enhancements
- Added method calls to retrieve `pcmCapabilities` from the `SystemCapbilityManager`
- `SdlRouterService` had a good deal of refactoring and cleaning up to remove warnings and issues
- Created a new way to retrieve the library version from apps.
- Version checking for `SdlRouterService` is now performed before starting an actual router service.
- General enhancements and stability fixes to the multiplexing transport feature

### Bug Fixes
- Fixed a potential deadlock within the `LegacyBluetoothTransport`
- Fix issue with video streaming not being able to restart after being stopped
- Fixed issue where `OnHMIStatus` was ignored if the level was the same, but the `AudioStreamingState` had changed
- Fixed potential NPE in the `SdlProxyBase` class when a packet was malformed
- Fixed issue that would incorrectly unregister apps from the module through the router service when apps are being force closed and others register
- Fixed issue in `SdlRouterStatusProvider` class where the handler wasn't able to obtain a looper.
- Fixed issue in `ServiceFinder` class where the handler was using a looper than was exiting and therefore not posting the expected runnable
- Fixed an issue that kept the `SdlRouterService` notification icon showing even if there were no current connects
- Fixed an issue where the `SdlRouterService` would start regardless of what bluetooth device it connected.
- `UsbTransport` was given a few fixes around incorrect exiting calls
- Fixed an issue found with some modules where they would return a single `SpeechCapability` instead of a list