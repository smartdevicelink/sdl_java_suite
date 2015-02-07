package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Set Display Layout Response is sent, when SetDisplayLayout has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetDisplayLayoutResponse extends RPCResponse {
	public static final String KEY_BUTTON_CAPABILITIES = "buttonCapabilities";
	public static final String KEY_DISPLAY_CAPABILITIES = "displayCapabilities";
    public static final String KEY_SOFT_BUTTON_CAPABILITIES = "softButtonCapabilities";
    public static final String KEY_PRESET_BANK_CAPABILITIES = "presetBankCapabilities";

    private DisplayCapabilities displayCapabilities;
    private PresetBankCapabilities presetBankCapabilities;
    private List<ButtonCapabilities> buttonCapabilities;
    private List<SoftButtonCapabilities> softButtonCapabilities;
    
	/**
	 * Constructs a new SetDisplayLayoutResponse object
	 */
    public SetDisplayLayoutResponse() {
        super(FunctionID.SET_DISPLAY_LAYOUT);
    }
    
    /**
     * Creates a SetDisplayLayoutResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SetDisplayLayoutResponse(JSONObject jsonObject){
        super(SdlCommand.SET_DISPLAY_LAYOUT, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            JSONObject displayCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_DISPLAY_CAPABILITIES);
            if(displayCapabilitiesObj != null){
                this.displayCapabilities = new DisplayCapabilities(displayCapabilitiesObj);
            }
            
            JSONObject presetBankCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_PRESET_BANK_CAPABILITIES);
            if(presetBankCapabilitiesObj != null){
                this.presetBankCapabilities = new PresetBankCapabilities(presetBankCapabilitiesObj);
            }
            
            List<JSONObject> buttonCapabilitiesObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_BUTTON_CAPABILITIES);
            if(buttonCapabilitiesObjs != null){
                this.buttonCapabilities = new ArrayList<ButtonCapabilities>(buttonCapabilitiesObjs.size());
                for(JSONObject buttonCapabilitiesObj : buttonCapabilitiesObjs){
                    this.buttonCapabilities.add(new ButtonCapabilities(buttonCapabilitiesObj));
                }
            }
            
            List<JSONObject> softButtonCapabilitiesObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_SOFT_BUTTON_CAPABILITIES);
            if(softButtonCapabilitiesObjs != null){
                this.softButtonCapabilities = new ArrayList<SoftButtonCapabilities>(softButtonCapabilitiesObjs.size());
                for(JSONObject softButtonCapabilitiesObj : softButtonCapabilitiesObjs){
                    this.softButtonCapabilities.add(new SoftButtonCapabilities(softButtonCapabilitiesObj));
                }
            }
            break;
        }
    }
    
    public DisplayCapabilities getDisplayCapabilities() {
        return this.displayCapabilities;
    }

    public void setDisplayCapabilities(DisplayCapabilities displayCapabilities) {
        this.displayCapabilities = displayCapabilities;
    }

    public List<ButtonCapabilities> getButtonCapabilities() {
        return this.buttonCapabilities;
    }

    public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
        this.buttonCapabilities = buttonCapabilities;
    }

    public List<SoftButtonCapabilities> getSoftButtonCapabilities() {
        return this.softButtonCapabilities;
    }

    public void setSoftButtonCapabilities(List<SoftButtonCapabilities> softButtonCapabilities) {
        this.softButtonCapabilities = softButtonCapabilities;
    }

    public PresetBankCapabilities getPresetBankCapabilities() {
        return this.presetBankCapabilities;
    }

    public void setPresetBankCapabilities(PresetBankCapabilities presetBankCapabilities) {
        this.presetBankCapabilities = presetBankCapabilities;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DISPLAY_CAPABILITIES, (this.displayCapabilities == null) ? null :
                this.displayCapabilities.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_PRESET_BANK_CAPABILITIES, (this.presetBankCapabilities == null) ? null :
                this.presetBankCapabilities.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_BUTTON_CAPABILITIES, (this.buttonCapabilities == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.buttonCapabilities, sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_SOFT_BUTTON_CAPABILITIES, (this.softButtonCapabilities == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.softButtonCapabilities, sdlVersion));
            break;
        }
        
        return result;
    }
}
