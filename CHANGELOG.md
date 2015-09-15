# 4.0.0 Release Notes

### API New Features & Breaking Changes
* Updated to v4.0 RPCs and enums. 
  * Enums updated:
    * LayoutMode (Add and deprecate)
    * AppInterfaceUnregisteredReason
    * TextFieldName
    * ImageFieldName
    * VehicleDataResultCode
    * KeyboardEvent
    * RequestType
  * RPCs updated
    * TouchEvent - Changed timestamp (ts) to use Long instead of Integers
    * HMICapabilities (new)
    * RegisterAppInterface (response)
    * PutFile (request) - Changed offset and length to use Long instead of Integers
    * DialNumber (new - request, response)
    * OnSystemRequest (notification) (Changed offset and length to use Long instead of Integers)
* Removed unused classes StringEnumer, Base64, and utl/Mime
* Removed unused methods including these public/protected methods:
  * `com/smartDeviceLink/streaming/AbstractPacketizer` 
    *     printBuffer(byte[],int,int)
  * `com/smartDeviceLink/trace/Mime`
    *     base64Decode(String)
  * `com/smartDeviceLink/trace/SdlTrace`
    *     setTracingEnable(Boolean) 
* Moved TransportType enum to new package which will contain all new enums for the transport package, `com/smartDeviceLink/transport/enums`
* Removed unused enums
  * GearShiftAdviceStatus
  * LightSwitchStatus
  * MaintenanceModeStatus
  * MessageType
  * PermissionStatus
  * TirePressureTellTale
  * VehicleDataActiveStatus
* Video/Audio streaming are now enabled. *[(See spec for more detail)](https://github.com/smartdevicelink/protocol_spec)*
* Changed the USB metadata information to use SDL as the manufacturer and Core as the model


### Enhancements
* Modified generics to follow Java convention
* Made FunctionID an enum rather than class with constants
* Added more robust parameter checking
* Changed some logging methods to return boolean for easier unit tests
* Changed putFile building methods to accept Longs instead of ints. Old methods were deprecated.
* Cleaned up SdlDataTypeConverter in terms of readability. 
* MTU size increased to 128kb up from 1.5k for v4. This will be the expected MTU for this version.
* Added a SDL Proxy builder that will enable simpler building of proxy objects as the large amount of constructors could be confusing.
* Changed the outgoing message queues to actually be FIFO queues.
* Heartbeat is now fully implemented which is needed for audio and video streaming. *[(See spec for more details)](https://github.com/smartdevicelink/protocol_spec).*
* Device info will now automatically populate in the Register App Interface RPC.


### Bugfixes
* Fixed issue with onProxyClosed not always called in multiple session scenario
* Removed recursion from HandleReceivedBytes
* Fixed wrong key issue in UpdateTurnList during turn list retrieval
* Fixed class cast exception caused by calling toArray from enums:
  * FrameData
  * FrameDataControlFrameType
  * FrameType
  * SessionType
* Redirected deprecated methods to new methods when available for following classes: 
  * GetVehicleData
  * GetVehicleDataResponse
  * OnVehicleData
  * SubscribeVehicleData
  * SubscribeVehicleDataResponse
  * UnsubscribeVehicleData
  * UnsubscribeVehicleDataResponse
* Fixed incorrect naming conventions of variables (SdlTrace)
* Fixed an issue RPC base classes that allowed for null values to be passed and cause issues with the underlying hashtable.
* Fixed issue where different types of RPC’s (response, request, notification) could be used in their parent class (RPCMessage) state to create other children of that class. 
* Fixed SendLocation to use Doubles instead of Floats for degrees
* Fixed naming of SessionTypes to reflect spec and Sava naming conventions
* Fixed ByteEnumer to catch class cast exceptions
* Removed outdated logging.
* Implemented missing callbacks for turn by turn RPCs.
