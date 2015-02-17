package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.util.JsonUtils;

/**
 * Specifies what is to be spoken. This can be simply a text phrase, which SDL will speak according to its own rules.
 *  It can also be phonemes from either the Microsoft SAPI phoneme set, or from the LHPLUS phoneme set. 
 *  It can also be a pre-recorded sound in WAV format (either developer-defined, or provided by the SDL platform).
 *  
 *  <p>In SDL, words, and therefore sentences, can be built up from phonemes and are used to explicitly provide the proper pronounciation to the TTS engine.
 *   For example, to have SDL pronounce the word "read" as "red", rather than as when it is pronounced like "reed",
 *   the developer would use phonemes to express this desired pronounciation.
 *  <p>For more information about phonemes, see <a href="http://en.wikipedia.org/wiki/Phoneme">http://en.wikipedia.org/wiki/Phoneme</a>.
 *  <p><b> Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>text</td>
 * 			<td>String</td>
 * 			<td>Text to be spoken, or a phoneme specification, or the name of a pre-recorded sound. The contents of this field are indicated by the "type" field.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>type</td>
 * 			<td>SpeechCapabilities</td>
 * 			<td>Indicates the type of information in the "text" field (e.g. phrase to be spoken, phoneme specification, name of pre-recorded sound).	</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 1.0
 */
public class TTSChunk extends RPCObject {
	public static final String KEY_TEXT = "text";
	public static final String KEY_TYPE = "type";
	
	private String text, type;
	
	/**
	 * Constructs a newly allocated TTSChunk object
	 */
    public TTSChunk() { }
    
    /**
     * Creates a TTSChunk object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public TTSChunk(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.text = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TEXT);
            this.type = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TYPE);
            break;
        }
    }
    
    /**
     * Get text to be spoken, or a phoneme specification, or the name of a pre-recorded sound. The contents of this field are indicated by the "type" field.
     * @return text to be spoken, or a phoneme specification, or the name of a pre-recorded sound
     */    
    public String getText() {
        return this.text;
    }
    
    /**
     * Set the text to be spoken, or a phoneme specification, or the name of a pre-recorded sound. The contents of this field are indicated by the "type" field.
     * @param text to be spoken, or a phoneme specification, or the name of a pre-recorded sound.
     */    
    public void setText( String text ) {
        this.text = text;
    }
    
    /**
     * Get the type of information in the "text" field (e.g. phrase to be spoken, phoneme specification, name of pre-recorded sound).	
     * @return the type of information in the "text" field
     */    
    public SpeechCapabilities getType() {
        return SpeechCapabilities.valueForJsonName(this.type, sdlVersion);
    }
    
    /**
     * Set the type of information in the "text" field (e.g. phrase to be spoken, phoneme specification, name of pre-recorded sound).	
     * @param type the type of information in the "text" field
     */    
    public void setType( SpeechCapabilities type ) {
        this.type = (type == null) ? null : type.getJsonName(sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_TEXT, this.text);
            JsonUtils.addToJsonObject(result, KEY_TYPE, this.type);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		TTSChunk other = (TTSChunk) obj;
		if (text == null) {
			if (other.text != null) { 
				return false;
			}
		} else if (!text.equals(other.text)) { 
			return false;
		}
		if (type == null) {
			if (other.type != null) { 
				return false;
			}
		} else if (!type.equals(other.type)) { 
			return false;
		}
		return true;
	}
}
