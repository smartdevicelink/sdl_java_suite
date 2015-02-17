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
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_RIGHT_FRONT);
            if(temp != null){
                this.rightFront = new SingleTireStatus(temp);
            }
            temp = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_LEFT_REAR);
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
    	this.pressureTellTale = (pressureTellTale == null) ? null : pressureTellTale.getJsonName(sdlVersion);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((innerLeftRear == null) ? 0 : innerLeftRear.hashCode());
		result = prime * result + ((innerRightRear == null) ? 0 : innerRightRear.hashCode());
		result = prime * result + ((leftFront == null) ? 0 : leftFront.hashCode());
		result = prime * result + ((leftRear == null) ? 0 : leftRear.hashCode());
		result = prime * result + ((pressureTellTale == null) ? 0 : pressureTellTale.hashCode());
		result = prime * result + ((rightFront == null) ? 0 : rightFront.hashCode());
		result = prime * result + ((rightRear == null) ? 0 : rightRear.hashCode());
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
		TireStatus other = (TireStatus) obj;
		if (innerLeftRear == null) {
			if (other.innerLeftRear != null) { 
				return false;
			}
		} else if (!innerLeftRear.equals(other.innerLeftRear)) { 
			return false;
		}
		if (innerRightRear == null) {
			if (other.innerRightRear != null) { 
				return false;
			}
		} else if (!innerRightRear.equals(other.innerRightRear)) { 
			return false;
		}
		if (leftFront == null) {
			if (other.leftFront != null) { 
				return false;
			}
		} else if (!leftFront.equals(other.leftFront)) { 
			return false;
		}
		if (leftRear == null) {
			if (other.leftRear != null) { 
				return false;
			}
		} else if (!leftRear.equals(other.leftRear)) { 
			return false;
		}
		if (pressureTellTale == null) {
			if (other.pressureTellTale != null) { 
				return false;
			}
		} else if (!pressureTellTale.equals(other.pressureTellTale)) { 
			return false;
		}
		if (rightFront == null) {
			if (other.rightFront != null) { 
				return false;
			}
		} else if (!rightFront.equals(other.rightFront)) { 
			return false;
		}
		if (rightRear == null) {
			if (other.rightRear != null) { 
				return false;
			}
		} else if (!rightRear.equals(other.rightRear)) { 
			return false;
		}
		return true;
	}
}
