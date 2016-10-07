# 4.1.0 Release Notes

### API New Features & Breaking Changes
* Added multiplexing transport that gives the ability to share a single bluetooth RCOMM channel with many clients.
* Added external security framework that allows OEM's to provide custom security libraries.
* ProxyALM constructors have changed and old signatures been deprecated.
* Changed TouchEvent method names to be more intuitive.

### Enhancements
* Multiplexing will now be the default mode of transport

### Bug Fixes
* Fixed issue with sending a stop session with an incorrect hash id
* Fixed the library throwing away hybrid packets
* Fixed http onSystemRequests to actually function correctly
* Fixed a class cast exception in the BTTransport class
