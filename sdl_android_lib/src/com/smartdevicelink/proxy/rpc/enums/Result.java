package com.smartdevicelink.proxy.rpc.enums;

/**
 * Defines the possible result codes returned by SDL to the application in a
 * Response to a requested operation
 * 
 * 
 * @since SmartDeviceLink 1.0
 */
public enum Result {
	/**
	 * The request succeeded
	 */    
	SUCCESS,
	/**
	 * <p>The data sent is invalid. For example:</p>
	 * <ul>
	 * <li>Invalid Json syntax</li>
	 * <li>Parameters out of bounds (number or enum range)</li>
	 * <li>Mandatory parameters not provided</li>
	 * <li>Parameter provided with wrong type</li>
	 * <li>Invalid characters</li>
	 * <li>Empty string</li>
	 * </ul>
	 */    
	INVALID_DATA,
	/**
	 * The request is not supported by SDL
	 */    
	UNSUPPORTED_REQUEST,
	/**
	 * The system could not process the request because the necessary memory
	 * couldn't be allocated
	 */    
	OUT_OF_MEMORY,
	/**
	 * There are too many requests pending (means that the response has not been
	 * delivered yet). There is a limit of 1000 pending requests at a time
	 */    
	TOO_MANY_PENDING_REQUESTS,
	/**
	 * <p>One of the provided IDs is not valid. For example:</p>
	 * <ul>
	 * <li>CorrelationID</li>
	 * <li>CommandID</li>
	 * <li>MenuID</li>
	 * </ul>
	 */
	INVALID_ID,
	/**
	 * The provided name or synonym is a duplicate of some already-defined name
	 * or synonym
	 */    
    DUPLICATE_NAME,
	/**
	 * Specified application name is already associated with an active interface
	 * registration. Attempts at doing a second <i>
	 * {@linkplain com.smartdevicelink.proxy.rpc.RegisterAppInterface}</i> on a
	 * given protocol session will also cause this
	 */    
    TOO_MANY_APPLICATIONS,
	/**
	 * SDL does not support the interface version requested by the mobile
	 * application
	 */    
    APPLICATION_REGISTERED_ALREADY,
	/**
	 * The requested language is currently not supported. Might be because of a
	 * mismatch of the currently active language
	 */    
    UNSUPPORTED_VERSION,
	/**
	 * The request cannot be executed because no application interface has been
	 * registered via <i>
	 * {@linkplain com.smartdevicelink.proxy.rpc.RegisterAppInterface}</i>
	 */    
	WRONG_LANGUAGE,
	/**
	 * The request cannot be executed because no application interface has been
	 * registered via <i>
	 * {@linkplain com.smartdevicelink.proxy.rpc.RegisterAppInterface}</i>
	 */
	APPLICATION_NOT_REGISTERED,
	/**
	 * The data may not be changed, because it is currently in use. For example,
	 * when trying to delete a Choice Set that is currently involved in an
	 * interaction
	 */
	IN_USE,
	/**
	 *The user has turned off access to vehicle data, and it is globally unavailable to mobile applications.
	 */
    VEHICLE_DATA_NOT_ALLOWED,
    /**
     * The requested vehicle data is not available on this vehicle or is not published.
     */
	VEHICLE_DATA_NOT_AVAILABLE,
	/**
	 * The requested operation was rejected. No attempt was made to perform the
	 * operation
	 */
	REJECTED,
	/**
	 * The requested operation was aborted due to some pre-empting event (e.g.
	 * button push, <i>{@linkplain com.smartdevicelink.proxy.rpc.Alert}</i>
	 * pre-empts <i>{@linkplain com.smartdevicelink.proxy.rpc.Speak}</i>, etc.)
	 */
	ABORTED,
	/**
	 * The requested operation was ignored because it was determined to be
	 * redundant (e.g. pause media clock when already paused)
	 */
	IGNORED,
	/**
	 * A button that was requested for subscription is not supported on the
	 * currently connected SDL platform. See DisplayCapabilities for further
	 * information on supported buttons on the currently connected SDL platform
	 */
    UNSUPPORTED_RESOURCE,
    /**
     * A specified file could not be found on Sync.
     */
    FILE_NOT_FOUND,
    /**
     * Provided data is valid but something went wrong in the lower layers.
     */
    GENERIC_ERROR,
    /**
     * RPC is not authorized in local policy table.
     */
    DISALLOWED,
    /**
     * RPC is included in a functional group explicitly blocked by the user.
     */
    USER_DISALLOWED,
    /**
     * Overlay reached the maximum timeout and closed.
     */
    TIMED_OUT,
    /**
     * User selected to Cancel Route.
     */
    CANCEL_ROUTE,
    /**
     * The RPC (e.g. ReadDID) executed successfully but the data exceeded the platform maximum threshold and thus, only part of the data is available.
     */
    TRUNCATED_DATA,
    /**
     * The user interrupted the RPC (e.g. PerformAudioPassThru) and indicated to start over.  Note, the app must issue the new RPC.
     */
    RETRY,
    /**
     * The RPC (e.g. SubscribeVehicleData) executed successfully but one or more items have a warning or failure.
     */
    WARNINGS,
    /**
     * The RPC (e.g. Slider) executed successfully and the user elected to save the current position / value.
     */
    SAVED,
    /**
     * The certificate provided during authentication is invalid.
     */
    INVALID_CERT,
    /**
     * The certificate provided during authentication is expired.
     */
    EXPIRED_CERT,
    /**
     * The provided hash ID does not match the hash of the current set of registered data or the core could not resume the previous data.
     */
    RESUME_FAILED;
	 /**
     * Convert String to Result
     * @param value String
     * @return Result
     */

    public static Result valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
