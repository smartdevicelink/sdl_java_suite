# 4.0.1 Release Notes

### API New Features & Breaking Changes
  * Added ability to retrieve HMICapabilities and SystemSoftwareVersion from the proxy object.

### Enhancements
  * Added RPC request callbacks. Developers can now set a listener object into any RPC request and recieve the response through it.
  * Added RPC notification listeners. Developers can now designate a listener for each notifcation type.
  * Added a correlation id generator. Id's can be generated through this class to avoid having to keep track between multiple classes.

### Bug Fixes
  * Removed unused imports
