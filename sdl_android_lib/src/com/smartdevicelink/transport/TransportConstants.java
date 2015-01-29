package com.smartdevicelink.transport;


/**
 * These constants are shared between the bluetooth serial service and the livio connect base service.
 * They are defined as strings/actions/values that both of them can understand.
 * Attempting to use standard HTTP error codes as definitions.  
 * @author Joey Grover
 *
 */
public class TransportConstants {
	public static final String WAKE_UP_BLUETOOTH_SERVICE_INTENT				="livio.bluetooth.startservice";
	protected static final String REQUEST_BT_CLIENT_CONNECT 				= "com.livio.android.requestBtClientConnect";

	//public static final String REGISTER_WITH_ROUTER_ACTION 					= "com.lvio.android.register"; 
	//public static final String REREGISTER_WITH_ROUTER_ACTION 				= "com.lvio.android.reregister"; 
	public static final String UNREGISTER_WITH_ROUTER_ACTION 				= "com.lvio.android.unregister"; 
	public static final String SEND_PACKET_ACTION 							= "com.livio.android.sendpacket";
	public static final String SEND__GLOBAL_PACKET_ACTION 					= "com.livio.android.sendglobalpacket";
	//public static final String REGISTER_NEWER_SERVER_INSTANCE_ACTION		= "com.livio.android.newservice";
	//public static final String START_ROUTER_SERVICE_ACTION 					= "livio.bluetooth.startservice";
	public static final String START_ROUTER_SERVICE_ACTION_SUFFIX			= ".startservice";

	public static final String USB_INIT_CONNECT = "usb_init_connect";

	public static final String 	ALT_TRANSPORT_RECEIVER 						= "com.lvio.android.alttransport";
	public static final String 	ALT_TRANSPORT_CONNECTION_STATUS_EXTRA		= "connection_status";
	public static final int 	ALT_TRANSPORT_DISCONNECTED					= 0;
	public static final int 	ALT_TRANSPORT_CONNECTED						= 1;
	public static final String 	ALT_TRANSPORT_READ 							= "read";//Read from the alt transport, goes to the app
	public static final String 	ALT_TRANSPORT_WRITE 						= "write";//Write to the alt transport, comes from the app
	public static final String 	ALT_TRANSPORT_ADDRESS_EXTRA					= "altTransportAddress";
	
	public static final String START_ROUTER_SERVICE_LIVIOCONNECT_ENABLED_EXTRA	= "livioconnect_enabled";
	public static final String PING_REGISTERED_SERVICE_EXTRA				= "ping";
	public static final String PING_REGISTERED_SERVICE_REPLY_EXTRA			= "pingreply";


	public static final String LIVIO_ALHU_ACTION							= "com.livio.android.alhu";
	public static final String LIVIO_ALHU_PACKET_TYPE						= "packet_type";
	public static final String LIVIO_ALHU_PACKET_CHARS						= "packet_chars";



	
	public static final String REPLY_TO_INTENT_EXTRA 						= "ReplyAddress";
	public static final String CONNECT_AS_CLIENT_BOOLEAN_EXTRA				= "connectAsClient";
	public static final String PACKAGE_NAME_STRING							= "package.name";
	public static final String APP_ID_EXTRA									= "app.id";

	public static final String LOG_BASIC_DEBUG_BOOLEAN_EXTRA				= "basicDebugBool";
	public static final String LOG_TRACE_BT_DEBUG_BOOLEAN_EXTRA				= "btTraceBool";

	public static final String CONNECTED_DEVICE_STRING_EXTRA_NAME			= "devicestring";


	public static final String HARDWARE_DISCONNECTED						= "hardware.dissconect";
	public static final String HARDWARE_CONNECTED							= "hardware.connected";

	
	public static final String UNREGISTER_EXTRA 							= "unregister.request";
	public static final int	UNREGISTER_EXTRA_REASON_PING_TIMEOUT			= 408;
	public static final int	UNREGISTER_EXTRA_REASON_NEW_FOREGROUND_APP		= 101;
	public static final int	UNREGISTER_EXTRA_REASON_LOST_CONNECTION			= 499;

	
	
	public static final String SEND_PACKET_TO_APP_LOCATION_EXTRA_NAME 		= "senderintent";
	public static final String SEND_PACKET_TO_ROUTER_LOCATION_EXTRA_NAME 	= "routerintent";

	
	public static final String REGISTRATION_DENIED_EXTRA_NAME 				= "registrationdenied";
	public static final char REGISTRATION_DENIED_ANOTHER_APP_HAS_FOCUS 		= 0x00;
	public static final char REGISTRATION_DENIED_AUTHENTICATION_FAILED 		= 0x01;
	public static final char REGISTRATION_DENIED_NO_CONNECTION 				= 0x02;
	public static final char REGISTRATION_DENIED_UNKNOWN 					= 0xFF;
	
	public static final String PACKET_TO_SEND_EXTRA_NAME 					= "packet";
	public static final String BYTES_TO_SEND_EXTRA_NAME 					= "bytes";
	public static final String BYTES_TO_SEND_EXTRA_OFFSET					= "offset";
	public static final String BYTES_TO_SEND_EXTRA_COUNT 					= "count";
	
	
	public static final int PACKET_SENDING_ERROR_NOT_REGISTERED_APP 		= 0x00;
	public static final int PACKET_SENDING_ERROR_NOT_CONNECTED 				= 0x01;
	public static final int PACKET_SENDING_ERROR_UKNOWN 					= 0xFF;

	public static final String GPS_LIVIO_CONNECT 							= "com.livio.gps";
	public static final String GPS_TOGGLE_UPDATES_EXTRA						= "GPS_TOGGLE_UPDATES_EXTRA";


}
