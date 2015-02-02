package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;
import com.smartdevicelink.util.JsonUtils;

public class TireStatus extends RPCObject {
	public static final String KEY_PRESSURE_TELL_TALE = "pressureTellTale";
	public static final String KEY_LEFT_FRONT = "leftFront";
	public static final String KEY_RIGHT_FRONT = "rightFront";
	public static final String KEY_LEFT_REAR = "leftRear";
	public static final String KEY_INNER_LEFT_REAR = "innerLeftRear";
	public static final String KEY_INNER_RIGHT_REAR = "innerRightRear";
	public static final String KEY_RIGHT_REAR = "rightRear";

	private SingleTireStatus leftFront, rightFront, leftRear, rightRear, innerLeftRear,
	    innerRightRear;
	private String pressureTellTale;
	
    public TireStatus() { }
    
    /**
     * Creates a TireStatus object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public TireStatus(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.pressureTellTale = JsonUtils.readStringFromJsonObject(jsonObject, KEY_PRESSURE_TELL_TALE);
            
            JSONObject temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_LEFT_FRONT);
            if(temp != null){
                this.leftFront = new SingleTireStatus(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_LEFT_REAR);
            if(temp != null){
                this.rightFront = new SingleTireStatus(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_RIGHT_FRONT);
            if(temp != null){
                this.leftRear = new SingleTireStatus(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_RIGHT_REAR);
            if(temp != null){
                this.rightRear = new SingleTireStatus(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_INNER_LEFT_REAR);
            if(temp != null){
                this.innerLeftRear = new SingleTireStatus(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_INNER_RIGHT_REAR);
            if(temp != null){
                this.innerRightRear = new SingleTireStatus(temp);
            }
            break;
        }
    }
    
    public void setPressureTellTale(WarningLightStatus pressureTellTale) {
    	this.pressureTellTale = pressureTellTale.getJsonName(sdlVersion);
    }
    
    public WarningLightStatus getPressureTellTale() {
        return WarningLightStatus.valueForJsonName(this.pressureTellTale, sdlVersion);
    }
    
    public void setLeftFront(SingleTireStatus leftFront) {
    	this.leftFront = leftFront;
    }
    
    public SingleTireStatus getLeftFront() {
    	return this.leftFront;
    }
    
    public void setRightFront(SingleTireStatus rightFront) {
    	this.rightFront = rightFront;
    }
    
    public SingleTireStatus getRightFront() {
    	return this.rightFront;
    }
    
    public void setLeftRear(SingleTireStatus leftRear) {
    	this.leftRear = leftRear;
    }
    
    public SingleTireStatus getLeftRear() {
    	return this.leftRear;
    }
    
    public void setRightRear(SingleTireStatus rightRear) {
    	this.rightRear = rightRear;
    }
    
    public SingleTireStatus getRightRear() {
    	return this.rightRear;
    }
    
    public void setInnerLeftRear(SingleTireStatus innerLeftRear) {
    	this.innerLeftRear = innerLeftRear;
    }
    
    public SingleTireStatus getInnerLeftRear() {
    	return this.innerLeftRear;
    }
    
    public void setInnerRightRear(SingleTireStatus innerRightRear) {
    	this.innerRightRear = innerRightRear;
    }
    
    public SingleTireStatus getInnerRightRear() {
    	return this.innerRightRear;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_PRESSURE_TELL_TALE, this.pressureTellTale);
            JsonUtils.addToJsonObject(result, KEY_LEFT_FRONT, (this.leftFront == null) ? null :
                this.leftFront.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_LEFT_REAR, (this.leftRear == null) ? null :
                this.leftFront.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_RIGHT_FRONT, (this.rightFront == null) ? null :
                this.leftFront.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_RIGHT_REAR, (this.rightRear == null) ? null :
                this.leftFront.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_INNER_LEFT_REAR, (this.innerLeftRear == null) ? null :
                this.leftFront.getJsonParameters(sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_INNER_RIGHT_REAR, (this.innerRightRear == null) ? null :
                this.leftFront.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }
}
