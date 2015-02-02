package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.JsonUtils;

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
	public static final String KEY_TTS_CHUNKS = "ttsChunks";
	
	private List<TTSChunk> ttsChunks;

	/**
	 * Constructs a new Speak object
	 */    
	public Speak() {
        super(FunctionID.SPEAK);
    }
	
    /**
     * Creates a Speak object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public Speak(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            List<JSONObject> ttsChunkObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_TTS_CHUNKS);
            if(ttsChunkObjs != null){
                this.ttsChunks = new ArrayList<TTSChunk>(ttsChunkObjs.size());
                for(JSONObject ttsChunkObj : ttsChunkObjs){
                    this.ttsChunks.add(new TTSChunk(ttsChunkObj));
                }
            }
            break;
        }
    }
	
	/**
	 * Gets a List<TTSChunk> representing an array of 1-100 TTSChunk structs
	 * which, taken together, specify the phrase to be spoken
	 * 
	 * @return List<TTSChunk> -an Array of 1-100 TTSChunk specify the phrase to be spoken
	 */
    public List<TTSChunk> getTtsChunks() {
    	return this.ttsChunks;
    }
    
	/**
	 * Sets a List<TTSChunk> representing an array of 1-100 TTSChunk structs
	 * which, taken together, specify the phrase to be spoken
	 * 
	 * @param ttsChunks
	 *            a List<TTSChunk> value representing an array of 1-100 TTSChunk structs
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
    public void setTtsChunks( List<TTSChunk> ttsChunks ) {
        this.ttsChunks = ttsChunks;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_TTS_CHUNKS, (this.ttsChunks == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.ttsChunks, sdlVersion));
            break;
        }
        
        return result;
    }
}
