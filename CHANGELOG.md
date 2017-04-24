# 4.3.0 Release Notes

### API New Features & Breaking Changes
* The library has now moved to Android Studio as its default IDE. All files and folders have been moved to align with proper Android Studio project structure.
* The target API level is now `19` instead of `18`. Apps will also need to target this version or higher to continue using this library.
* `Java 7` is now used to compile the library and its features can be used moving forward. 

### Enhancements
* Multiplexing bluetooth, legacy bluetooth, and TCP transport has been improved in performance by switching to buffer reads vs single byte read from transport.
* Trusted router service checking feature is now adjustable by developers. 
* More unit tests were created.
* Router service will now check to make sure the app that propagated it has permissions to use bluetooth. 
* Packet streaming classes will now use the agreed upon MTU instead of the hardcoded 1024 when the stream is not encrypted.
* App IDs sent between the client apps and the router service are now Strings instead of Longs to support longer IDs.

### Bug Fixes
* Fixed issue with AOA transport not clearing old accessory reference after disconnect
* Fixed missing setting of error state in multiplexing bluetooth transport
* Fixed potential OOMs when corrupted packets are recieved in:
 *  `SdlPsm`
 *  `WiProProtocol`
 *  `BinaryFrameHeader`
* Fixed possible NPEs in:
 * `SdlRouterService` when checking for correct process
 * `MultiplexingBluetoothTransport` during reads and writes
 * `SdlConnection` during session registration
 * `SdlProxyBase` when clearing RPC response and notification listeners during close 
 * `TransportBroker` when sending a message to router service
 * `SdlBroadcastReceiver` during check for running router service
 * `HttpRequestTask` that happens when a server can't be reached
 * `SdlSecurityBase` when a security lib would become initialized after the base has been reset. 
* Added synchronization to a cancel call in the `MultiplexTransport`
* Refactor code in `SdlBroadcastReceiver` to protect against a potential SecurityException
* Added try/catch around bluetooth system calls that can fail in Android classes
* Added try/catch when attempting to build `LocalRouterService` object from parcel when parcel could be corrupt
* Fixed version checking flow in the router service to be cleaner and correctly synched

