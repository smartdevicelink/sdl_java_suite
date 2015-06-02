package com.smartdevicelink.transport;


/**
 * These constants are shared between the router service and the SDL base service.
 * They are defined as strings/actions/values that both of them can understand.
 * Attempting to use standard HTTP error codes as definitions.  
 * @author Joey Grover
 *
 */
public class TransportConstants {
	public static final String WAKE_UP_BLUETOOTH_SERVICE_INTENT				="sdl.bluetooth.startservice";
	//protected static final String REQUEST_BT_CLIENT_CONNECT 				= "com.sdl.android.requestBtClientConnect";

	public static final String UNREGISTER_WITH_ROUTER_ACTION 				= "com.sdl.android.unregister"; 
	public static final String SEND_PACKET_ACTION 							= "com.sdl.android.sendpacket";
	public static final String SEND__GLOBAL_PACKET_ACTION 					= "com.sdl.android.sendglobalpacket";
	public static final String START_ROUTER_SERVICE_ACTION_SUFFIX			= ".startservice";

	public static final String BIND_LOCATION_PACKAGE_NAME_EXTRA 			= "BIND_LOCATION_PACKAGE_NAME_EXTRA";
	public static final String BIND_LOCATION_CLASS_NAME_EXTRA				= "BIND_LOCATION_CLASS_NAME_EXTRA";
	
	public static final String 	ALT_TRANSPORT_RECEIVER 						= "com.sdl.android.alttransport";
	public static final String 	ALT_TRANSPORT_CONNECTION_STATUS_EXTRA		= "connection_status";
	public static final int 	ALT_TRANSPORT_DISCONNECTED					= 0;
	public static final int 	ALT_TRANSPORT_CONNECTED						= 1;
	public static final String 	ALT_TRANSPORT_READ 							= "read";//Read from the alt transport, goes to the app
	public static final String 	ALT_TRANSPORT_WRITE 						= "write";//Write to the alt transport, comes from the app
	public static final String 	ALT_TRANSPORT_ADDRESS_EXTRA					= "altTransportAddress";
	
	public static final String START_ROUTER_SERVICE_SDL_ENABLED_EXTRA		= "sdl_enabled";
	
	public static final String REPLY_TO_INTENT_EXTRA 						= "ReplyAddress";
	public static final String CONNECT_AS_CLIENT_BOOLEAN_EXTRA				= "connectAsClient";
	public static final String PACKAGE_NAME_STRING							= "package.name";
	public static final String APP_ID_EXTRA									= "app.id";

	public static final String LOG_BASIC_DEBUG_BOOLEAN_EXTRA				= "basicDebugBool";
	public static final String LOG_TRACE_BT_DEBUG_BOOLEAN_EXTRA				= "btTraceBool";


	public static final String ENABLE_LEGACY_MODE_EXTRA 					= "ENABLE_LEGACY_MODE_EXTRA";
	
	public static final String HARDWARE_DISCONNECTED						= "hardware.disconect";
	public static final String HARDWARE_CONNECTED							= "hardware.connected";	
	
	public static final String SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME 		= "senderintent";
	public static final String SEND_PACKET_TO_ROUTER_LOCATION_EXTRA_NAME 	= "routerintent";

	/**
	 * Binding service
	 * 
	 */
	
	//WHATS
	/**
     * Command to the service to register a client, receiving callbacks
     * from the service.  The Message's replyTo field must be a Messenger of
     * the client where callbacks should be sent.
     */
	public static final int ROUTER_REGISTER_CLIENT 						= 0x01;
	/**
	 * This response message will contain if the registration request was sucessful or not. If not, the reason will be
	 * great or equal to 1 and be descriptive of why it was denied.
	 */
	public static final int ROUTER_REGISTER_CLIENT_RESPONSE 						= 0x02;
	//Response arguments
	public static final int REGISTRATION_RESPONSE_SUCESS 							= 0x00;
	public static final int REGISTRATION_RESPONSE_DENIED_AUTHENTICATION_FAILED 		= 0x01;
	public static final int REGISTRATION_RESPONSE_DENIED_NO_CONNECTION 				= 0x02;
	public static final int REGISTRATION_RESPONSE_DENIED_APP_ID_NOT_INCLUDED		= 0x03;
	public static final int REGISTRATION_RESPONSE_DENIED_UNKNOWN 					= 0xFF;
   
	/**
     * Command to the service to unregister a client, to stop receiving callbacks
     * from the service.  The Message's replyTo field must be a Messenger of
     * the client as previously given with MSG_REGISTER_CLIENT. Also include the app id as arg1.
     */
	public static final int ROUTER_UNREGISTER_CLIENT 						= 0x03;
	public static final int ROUTER_UNREGISTER_CLIENT_RESPONSE 				= 0x04;
    
	/**
	 * what message type to notify apps of a hardware connection event. The connection event will be placed in the bundle
	 * attached to the message
	 */
	public static final int HARDWARE_CONNECTION_EVENT						= 0x05;
	
	public static final int ROUTER_REQUEST_BT_CLIENT_CONNECT 				= 0x10;
	public static final int ROUTER_REQUEST_BT_CLIENT_CONNECT_RESPONSE		= 0x11;

	public static final int ROUTER_REQUEST_ADDITIONAL_SERVICE 				= 0x12;
	public static final int ROUTER_REQUEST_ADDITIONAL_SERVICE_RESPONSE 		= 0x13;

    /**
     * Command to service to send a packet
     */
	public  static final int ROUTER_SEND_PACKET 							= 0x20;
	
	
	//response
	/**
	 * Router has received a packet and sent it to the client
	 */
	public  static final int ROUTER_RECEIVED_PACKET 						= 0x22;
	//response

	//BUNDLE EXTRAS
	
	public static final String FORMED_PACKET_EXTRA_NAME 					= "packet";
	
	public static final String BYTES_TO_SEND_EXTRA_NAME 					= "bytes";
	public static final String BYTES_TO_SEND_EXTRA_OFFSET					= "offset";
	public static final String BYTES_TO_SEND_EXTRA_COUNT 					= "count";
	
	public static final String CONNECTED_DEVICE_STRING_EXTRA_NAME			= "devicestring";
	
	public static final int PACKET_SENDING_ERROR_NOT_REGISTERED_APP 		= 0x00;
	public static final int PACKET_SENDING_ERROR_NOT_CONNECTED 				= 0x01;
	public static final int PACKET_SENDING_ERROR_UKNOWN 					= 0xFF;

	

}
