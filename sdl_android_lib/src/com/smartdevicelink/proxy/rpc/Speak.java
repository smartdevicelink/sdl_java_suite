package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequest;

/**
 * Speaks a phrase over the vehicle audio system using SDL's TTS
 * (text-to-speech) engine. The provided text to be spoken can be simply a text
 * phrase, or it can consist of phoneme specifications to direct SDL's TTS
 * engine to speak a "speech-sculpted" phrase
 * <p>
 * Receipt of the Response indicates the completion of the Speak operation,
 * regardless of how the Speak operation may have completed (i.e. successfully,
 * interrupted, terminated, etc.)
 * <p>
 * Requesting a new Speak operation while the application has another Speak
 * operation already in progress (i.e. no corresponding Response for that
 * in-progress Speak operation has been received yet) will terminate the
 * in-progress Speak operation (causing its corresponding Response to be sent by
 * SDL) and begin the requested Speak operation
 * <p>
 * Requesting a new Speak operation while the application has an <i>
 * {@linkplain Alert}</i> operation already in progress (i.e. no corresponding
 * Response for that in-progress <i>{@linkplain Alert}</i> operation has been
 * received yet) will result in the Speak operation request being rejected
 * (indicated in the Response to the Request)
 * <p>
 * Requesting a new <i>{@linkplain Alert}</i> operation while the application
 * has a Speak operation already in progress (i.e. no corresponding Response for
 * that in-progress Speak operation has been received yet) will terminate the
 * in-progress Speak operation (causing its corresponding Response to be sent by
 * SDL) and begin the requested <i>{@linkplain Alert}</i> operation
 * <p>
 * Requesting a new Speak operation while the application has a <i>
 * {@linkplain PerformInteraction}</i> operation already in progress (i.e. no
 * corresponding Response for that in-progress <i>
 * {@linkplain PerformInteraction}</i> operation has been received yet) will
 * result in the Speak operation request being rejected (indicated in the
 * Response to the Request)
 * <p>
 * Requesting a <i> {@linkplain PerformInteraction}</i> operation while the
 * application has a Speak operation already in progress (i.e. no corresponding
 * Response for that in-progress Speak operation has been received yet) will
 * terminate the in-progress Speak operation (causing its corresponding Response
 * to be sent by SDL) and begin the requested <i>
 * {@linkplain PerformInteraction}</i> operation
 * <p>
 * 
 * <b>HMI Status Requirements:</b><br/>
 * HMILevel: FULL, Limited<br/>
 * AudioStreamingState: Any<br/>
 * SystemContext: MAIN, MENU, VR
 * </p>
 * <b>Notes: </b>
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
 * <p>
 * 
 * @since SmartDeviceLink 1.0
 * @see Alert
 */
public class Speak extends RPCRequest {
	public static final String ttsChunks = "ttsChunks";

	/**
	 * Constructs a new Speak object
	 */    
	public Speak() {
        super("Speak");
    }
	/**
	 * Constructs a new Speak object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */	
    public Speak(Hashtable hash) {
        super(hash);
    }
	/**
	 * Gets a Vector<TTSChunk> representing an array of 1-100 TTSChunk structs
	 * which, taken together, specify the phrase to be spoken
	 * 
	 * @return Vector<TTSChunk> -an Array of 1-100 TTSChunk specify the phrase to be spoken
	 */    
    public Vector<TTSChunk> getTtsChunks() {
    	if (parameters.get(Speak.ttsChunks) instanceof Vector<?>) {
	        Vector<?> list = (Vector<?>)parameters.get(Speak.ttsChunks);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (Vector<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TTSChunk> newList = new Vector<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable)hashObj));
	                }
	                return newList;
	            }
	        }
    	}
        return null;
    }
	/**
	 * Sets a Vector<TTSChunk> representing an array of 1-100 TTSChunk structs
	 * which, taken together, specify the phrase to be spoken
	 * 
	 * @param ttsChunks
	 *            a Vector<TTSChunk> value representing an array of 1-100 TTSChunk structs
	 * which specify the phrase to be spoken
	 *            <p>
	 *            <ul>
	 *            <li>The array must have 1-100 elements</li>
	 *            <li>The total length of the phrase composed from the ttsChunks
	 *            provided must be less than 500 characters or the request will
	 *            be rejected</li>
	 *            <li>Each chunk can be no more than 500 characters</li>
	 *            </ul>
	 */    
    public void setTtsChunks( Vector<TTSChunk> ttsChunks ) {
        if (ttsChunks != null) {
            parameters.put(Speak.ttsChunks, ttsChunks );
        }
    }
}
