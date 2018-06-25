# 4.6.0 Release Notes

### API New Features & Breaking Changes
- `RPCRequestFactory` has been deprecated. Please use the desired RPC's constructor instead.
- The Android Annotations Library has been added to the project to better help and inform developers about the SDK.

### Enhancements
- The router service foreground lifecycle is improved. The notification should no longer be seen when connecting to non-SDL devices.
- The SDL notification now links to a webpage to explain what the notification is and how to hide it.
- The required `intent-filter` entires for the `SdlBroadcastReceiver` has been reduced. It is now only listening for the SDL custom intent, ACL connect, and USB connection if using AOA.
- RPC classes now contain constructors with the required parameters for that RPC.
- Moved project to newer version of Gradle. Updated configurations including from `compile` to `api` and `implementation`.
- `SdlProxyBuilder` was cleaned up to remove redundant variables between the `SdlProxyBuilder.Builder` object and the `SdlProxyBuilder` class.

### Bug Fixes
- Fixed touch issues with the video streaming feature. A new module was added to handle touch events much more aligned with Android native views.
- Fixed JavaDoc issue in `UnregisterAppInterface`.
- Fixed JavaDoc issue in `AddCommand`.
- Added tags to the string resource xml file to ignore translation
- Temporary fix to the TCP transport to catch `NetworkOnTheMainThread` exceptions when the connection is closing.
- Fix issue where the `SdlBroadcastReceiver` was attempting to send implicit intents to ping the `SdlRouterService`. They are now explicit. 
- Fix a potential NPE in the `SdlBroadcastReceiver` while an app is only using USB and does not include an instance of an `SdlRouterService`. 
- Removed reflection usage in bluetooth transports when operating on systems that are newer than Android Oreo in anticipation of Android P.
- Fix an issue where the `SdlBroadcastReceiver` would throw a false positive regarding whether or not an app had included the correct `intent-filter` in their `SdlRouterService` manifest declaration.