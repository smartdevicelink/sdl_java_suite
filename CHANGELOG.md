# 4.7.2 Release Notes

## Hot Fix

- Fix legacy router service [issue #925](https://github.com/smartdevicelink/sdl_android/issues/925)
    - Addressed issue in `TransportBroker` that caused previous router service's connection messages to be dropped. 
    - Add logic to check router service version and perform appropriate logic.
    - Added check if packet doesn't include a transport record
    - Fixed a parcel issue in the `SdlPacket` class 
