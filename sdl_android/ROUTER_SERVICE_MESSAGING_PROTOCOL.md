# Router Service Messaging Protocol

This document should aid in the understanding and change log of the messages sent between the client proxy and the router service.

Modifications between versions will likely be breaking and have to be dealt with accordingly as the binder can't handle new parcelable objects or additions to existing parcelable objects. The router service and transport broker will have to handle this appropriately.

All values included are their constant names in the project, not the actual values. 


## Version 1

First version of the messaging protocol. There is no versioning negotiation or awareness at this point.

__Notes:__

- This version can only handle a single transport. If a different transport connects/disconnects beyond the one being used by the app during the session the router service will not inform the client.  

`ROUTER_REGISTER_CLIENT`

`APP_ID_EXTRA_STRING`

`ROUTER_REGISTER_CLIENT_RESPONSE`

`Message.arg1` will be results. Possible results:

- `ROUTER_REGISTER_ALT_TRANSPORT_RESPONSE_SUCESS`
- `ROUTER_REGISTER_ALT_TRANSPORT_ALREADY_CONNECTED`
- `ROUTER_SHUTTING_DOWN_NOTIFICATION`
- `ROUTER_SHUTTING_DOWN_REASON_NEWER_SERVICE`

### Version 1.1


## Version 2

Version 2 adds the ability to perform versioning of the messaging protocol between the client app (TransportBroker) and the router service. 


### Additions


###### TransportRecord

`TransportRecord` is a new parcelable object that contains more in depth information about the transport specifics than simply supplying a `TransportType` enum as a string.

### Modifications

###### ROUTER\_REGISTER\_CLIENT

The `ROUTER_REGISTER_CLIENT ` message now includes an integer value with key `ROUTER_MESSAGING_VERSION` that contains the clients max router service messaging protocol version.



###### SdlPacket

The `SdlPacket` object will now contain the messaging version and a `TransportRecord` entry. The messaging version will help inform 


###### HARDWARE\_CONNECTION\_EVENT

The `HARDWARE_CONNECTION_EVENT` message has the following changes:

- `Message.arg1` will now contain the actual type of event:
    - `HARDWARE_CONNECTION_EVENT_CONNECTED`
    - `HARDWARE_CONNECTION_EVENT_DISCONNECTED` 
- `CURRENT_HARDWARE_CONNECTED` key has been added which has a value of an `ArrayList<TransportRecord`. It will always contain the currently connected hardware from the router service.
- During a disconnect, `TRANSPORT_DISCONNECTED` key has been added that contains a `` of the transport that previously disconnected
- The extra from the `HARDWARE_CONNECTION_EVENT` message has been deprecated in favor of `TRANSPORT_DISCONNECTED`.


