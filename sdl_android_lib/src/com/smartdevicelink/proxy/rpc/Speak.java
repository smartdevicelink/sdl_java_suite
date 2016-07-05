package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Speaks a phrase over the vehicle audio system using SDL's TTS
 * (text-to-speech) engine. The provided text to be spoken can be simply a text
 * phrase, or it can consist of phoneme specifications to direct SDL's TTS
 * engine to speak a "speech-sculpted" phrase
 * <p></p>
 * Receipt of the Response indicates the completion of the Speak operation,
 * regardless of how the Speak operation may have completed (i.e. successfully,
 * interrupted, terminated, etc.)
 * <p></p>
 * Requesting a new Speak operation while the application has another Speak
 * operation already in progress (i.e. no corresponding Response for that
 * in-progress Speak operation has been received yet) will terminate the
 * in-progress Speak operation (causing its corresponding Response to be sent by
 * SDL) and begin the requested Speak operation
 * <p></p>
 * Requesting a new Speak operation while the application has an <i>
 * {@linkplain Alert}</i> operation already in progress (i.e. no corresponding
 * Response for that in-progress <i>{@linkplain Alert}</i> operation has been
 * received yet) will result in the Speak operation request being rejected
 * (indicated in the Response to the Request)
 * <p></p>
 * Requesting a new <i>{@linkplain Alert}</i> operation while the application
 * has a Speak operation already in progress (i.e. no corresponding Response for
 * that in-progress Speak operation has been received yet) will terminate the
 * in-progress Speak operation (causing its corresponding Response to be sent by
 * SDL) and begin the requested <i>{@linkplain Alert}</i> operation
 * <p></p>
 * Requesting a new Speak operation while the application has a <i>
 * {@linkplain PerformInteraction}</i> operation already in progress (i.e. no
 * corresponding Response for that in-progress <i>
 * {@linkplain PerformInteraction}</i> operation has been received yet) will
 * result in the Speak operation request being rejected (indicated in the
 * Response to the Request)
 * <p></p>
 * Requesting a <i> {@linkplain PerformInteraction}</i> operation while the
 * application has a Speak operation already in progress (i.e. no corresponding
 * Response for that in-progress Speak operation has been received yet) will
 * terminate the in-progress Speak operation (causing its corresponding Response
 * to be sent by SDL) and begin the requested <i>
 * {@linkplain PerformInteraction}</i> operation
 * <p></p>
 * 
 * <p><b>HMI Status Requirements:</b></p>
 * <p>HMILevel: FULL, Limited</p>
 *<p> AudioStreamingState: Any</p>
 * <p>SystemContext: MAIN, MENU, VR</p>
 * 
 * <p><b>Notes: </b></p>
 * <ul>
 * <li>When <i>{@linkplain Alert}</i> is issued with MENU in effect, <i>
 * {@linkplain Alert}</i> is queued and "played" when MENU interaction is
 * completed (i.e. SystemContext reverts to MAIN). When <i>{@linkplain Alert}
 * </i> is issued with VR in effect, <i>{@linkplain Alert}</i> is queued and
 * "played" when VR interaction is completed (i.e. SystemContext reverts to
 * MAIN)</li>
 * <li>When both <i>{@linkplain Alert}</i> and Speak are queued during MENU or
 * VR, they are "played" back in the order in which they were queued, with all
 * existing rules for "collisions" still in effect</li>
 * </ul>
 * 
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th> Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>ttsChunks</td>
 * 			<td>String</td>
 * 			<td>An array of 1-100 TTSChunk structs which, taken together, specify the phrase to be spoken.</td>
 * 			<td>Y</td>
 * 			<td><p>The array must have 1-100 elements. </p><p>The total length of the phrase composed from the ttsChunks provided must be less than 500 characters or the request will be rejected. </p>Each chunk can be no more than 500 characters.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 * <p><b>Response</b> </p>
 * <p>This Response notifies the application of the completion, interruption, or failure of a Speak Request.</p>
 * 
 * <p><b>Non-default Result Codes:</b> </p>
 * 
 *	<p> SUCCESS </p>
 *<p>	 INVALID_DATA</p>
 *	<p> OUT_OF_MEMORY </p>
 *	<p>APPLICATION_NOT_REGISTERED </p>
 *	<p>TOO_MANY_PENDING_REQUESTS </p>
 *<p>	GENERIC_ERROR </p>
 *	<p>REJECTED  </p>
 *	<p>DISALLOWED </p>
 *	<p>ABORTED </p>
 * 
 * <p><b>Additional Notes:</b></p><ul>Total character limit depends on platform. Chunks are limited to 500 characters; however you can have multiple TTS chunks. On Gen 1.1 there is a total character limit of 500 characters across all chunks. This could vary according to the VCA. 
 * 
 * @since SmartDeviceLink 1.0
 * @see Alert
 */
public class Speak extends RPCRequest {
	public static final String KEY_TTS_CHUNKS = "ttsChunks";

	/**
	 * Constructs a new Speak object
	 */    
	public Speak() {
        super(FunctionID.SPEAK.toString());
    }
	/**
	 * Constructs a new Speak object indicated by the Hashtable parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */	
    public Speak(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets a List<TTSChunk> representing an array of 1-100 TTSChunk structs
	 * which, taken together, specify the phrase to be spoken
	 * 
	 * @return List<TTSChunk> -an Array of 1-100 TTSChunk specify the phrase to be spoken
	 */    
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsChunks() {
    	if (parameters.get(KEY_TTS_CHUNKS) instanceof List<?>) {
    		List<?> list = (List<?>)parameters.get(KEY_TTS_CHUNKS);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (List<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TTSChunk> newList = new ArrayList<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
    	}
        return null;
    }
	/**
	 * Sets a List<TTSChunk> representing an array of 1-100 TTSChunk structs
	 * which, taken together, specify the phrase to be spoken
	 * 
	 * @param ttsChunks
	 *            a List<TTSChunk> value representing an array of 1-100 TTSChunk structs
	 * which specify the phrase to be spoken
	 *            <p></p>
	 *            <ul>
	 *            <li>The array must have 1-100 elements</li>
	 *            <li>The total length of the phrase composed from the ttsChunks
	 *            provided must be less than 500 characters or the request will
	 *            be rejected</li>
	 *            <li>Each chunk can be no more than 500 characters</li>
	 *            </ul>
	 */    
    public void setTtsChunks( List<TTSChunk> ttsChunks ) {
        if (ttsChunks != null) {
            parameters.put(KEY_TTS_CHUNKS, ttsChunks );
        } else {
            parameters.remove(KEY_TTS_CHUNKS);
        }
    }
}
