package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.util.JsonUtils;

public class BeltStatus extends RPCObject {
    public static final String KEY_DRIVER_BUCKLE_BELTED = "driverBuckleBelted";
    public static final String KEY_MIDDLE_ROW_1_BUCKLE_BELTED = "middleRow1BuckleBelted";
    public static final String KEY_PASSENGER_BUCKLE_BELTED = "passengerBuckleBelted";
    public static final String KEY_LEFT_ROW_2_BUCKLE_BELTED = "leftRow2BuckleBelted";
    public static final String KEY_MIDDLE_ROW_2_BUCKLE_BELTED = "middleRow2BuckleBelted";
    public static final String KEY_RIGHT_ROW_2_BUCKLE_BELTED = "rightRow2BuckleBelted";
    public static final String KEY_LEFT_ROW_3_BUCKLE_BELTED = "leftRow3BuckleBelted";
    public static final String KEY_MIDDLE_ROW_3_BUCKLE_BELTED = "middleRow3BuckleBelted";
    public static final String KEY_RIGHT_ROW_3_BUCKLE_BELTED = "rightRow3BuckleBelted";
    public static final String KEY_REAR_INFLATABLE_BELTED = "rearInflatableBelted";
    public static final String KEY_RIGHT_REAR_INFLATABLE_BELTED = "rightRearInflatableBelted";
    public static final String KEY_DRIVER_BELT_DEPLOYED = "driverBeltDeployed";
    public static final String KEY_MIDDLE_ROW_1_BELT_DEPLOYED = "middleRow1BeltDeployed";
    public static final String KEY_PASSENGER_BELT_DEPLOYED = "passengerBeltDeployed";
    public static final String KEY_PASSENGER_CHILD_DETECTED = "passengerChildDetected";

    // all strings represent VehicleDataEventStatus enum
    private String driverBeltBuckled, middleRow1BeltBuckled, passengerBeltBuckled, 
    			   leftRow2BeltBuckled, middleRow2BeltBuckled, rightRow2BeltBuckled,
    			   leftRow3BeltBuckled, middleRow3BeltBuckled, rightRow3BeltBuckled,
    			   rearInflatableBuckled, rightRearInflatableBuckled,
    			   driverBeltDeployed, passengerBeltDeployed, middleRow1BeltDeployed,
    			   passengerChildDetected;
    
    public BeltStatus() { }
    
    /**
     * Creates a BeltStatus object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     * @param sdlVersion The version of SDL represented in the JSON
     */
    public BeltStatus(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
        	this.driverBeltBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_DRIVER_BUCKLE_BELTED);
        	this.middleRow1BeltBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MIDDLE_ROW_1_BUCKLE_BELTED);
        	this.passengerBeltBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_PASSENGER_BUCKLE_BELTED);
        	this.leftRow2BeltBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_LEFT_ROW_2_BUCKLE_BELTED);
        	this.middleRow2BeltBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MIDDLE_ROW_2_BUCKLE_BELTED);
        	this.rightRow2BeltBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_RIGHT_ROW_2_BUCKLE_BELTED);
        	this.leftRow3BeltBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_LEFT_ROW_3_BUCKLE_BELTED);
        	this.middleRow3BeltBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MIDDLE_ROW_3_BUCKLE_BELTED);
        	this.rightRow3BeltBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_RIGHT_ROW_3_BUCKLE_BELTED);
        	this.rearInflatableBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_REAR_INFLATABLE_BELTED);
        	this.rightRearInflatableBuckled = JsonUtils.readStringFromJsonObject(jsonObject, KEY_RIGHT_REAR_INFLATABLE_BELTED);
        	this.driverBeltDeployed = JsonUtils.readStringFromJsonObject(jsonObject, KEY_DRIVER_BELT_DEPLOYED);
            this.middleRow1BeltDeployed = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MIDDLE_ROW_1_BELT_DEPLOYED);
        	this.passengerBeltDeployed = JsonUtils.readStringFromJsonObject(jsonObject, KEY_PASSENGER_BELT_DEPLOYED);
        	this.passengerChildDetected = JsonUtils.readStringFromJsonObject(jsonObject, KEY_PASSENGER_CHILD_DETECTED);
        	break;
        }
    }

    public void setDriverBeltDeployed(VehicleDataEventStatus driverBeltDeployed) {
        this.driverBeltDeployed = (driverBeltDeployed == null) ? null : driverBeltDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverBeltDeployed() {
        return VehicleDataEventStatus.valueForJsonName(this.driverBeltDeployed, sdlVersion);
    }
    
    public void setPassengerBeltDeployed(VehicleDataEventStatus passengerBeltDeployed) {
    	this.passengerBeltDeployed = (passengerBeltDeployed == null) ? null : passengerBeltDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerBeltDeployed() {
    	return VehicleDataEventStatus.valueForJsonName(this.passengerBeltDeployed, sdlVersion);
    }
    
    public void setPassengerBuckleBelted(VehicleDataEventStatus passengerBuckleBelted) {
    	this.passengerBeltBuckled = (passengerBuckleBelted == null) ? null : passengerBuckleBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerBuckleBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.passengerBeltBuckled, sdlVersion);
    }
    
    public void setDriverBuckleBelted(VehicleDataEventStatus driverBuckleBelted) {
    	this.driverBeltBuckled = (driverBuckleBelted == null) ? null : driverBuckleBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getDriverBuckleBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.driverBeltBuckled, sdlVersion);
    }
    
    public void setLeftRow2BuckleBelted(VehicleDataEventStatus leftRow2BuckleBelted) {
    	this.leftRow2BeltBuckled = (leftRow2BuckleBelted == null) ? null : leftRow2BuckleBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getLeftRow2BuckleBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.leftRow2BeltBuckled, sdlVersion);
    }
    
    public void setPassengerChildDetected(VehicleDataEventStatus passengerChildDetected) {
    	this.passengerChildDetected = (passengerChildDetected == null) ? null : passengerChildDetected.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getPassengerChildDetected() {
    	return VehicleDataEventStatus.valueForJsonName(this.passengerChildDetected, sdlVersion);
    }
    
    public void setRightRow2BuckleBelted(VehicleDataEventStatus rightRow2BuckleBelted) {
    	this.rightRow2BeltBuckled = (rightRow2BuckleBelted == null) ? null : rightRow2BuckleBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getRightRow2BuckleBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.rightRow2BeltBuckled, sdlVersion);
    }
    
    public void setMiddleRow2BuckleBelted(VehicleDataEventStatus middleRow2BuckleBelted) {
    	this.middleRow2BeltBuckled = (middleRow2BuckleBelted == null) ? null : middleRow2BuckleBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getMiddleRow2BuckleBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.middleRow2BeltBuckled, sdlVersion);
    }
    
    public void setMiddleRow3BuckleBelted(VehicleDataEventStatus middleRow3BuckleBelted) {
    	this.middleRow3BeltBuckled = (middleRow3BuckleBelted == null) ? null : middleRow3BuckleBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getMiddleRow3BuckleBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.middleRow3BeltBuckled, sdlVersion);
    }
    
    public void setLeftRow3BuckleBelted(VehicleDataEventStatus leftRow3BuckleBelted) {
    	this.leftRow3BeltBuckled = (leftRow3BuckleBelted == null) ? null : leftRow3BuckleBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getLeftRow3BuckleBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.leftRow3BeltBuckled, sdlVersion);
    }
    
    public void setRightRow3BuckleBelted(VehicleDataEventStatus rightRow3BuckleBelted) {
    	this.rightRow3BeltBuckled = (rightRow3BuckleBelted == null) ? null : rightRow3BuckleBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getRightRow3BuckleBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.rightRow3BeltBuckled, sdlVersion);
    }
    
    public void setLeftRearInflatableBelted(VehicleDataEventStatus rearInflatableBelted) {
    	this.rearInflatableBuckled = (rearInflatableBelted == null) ? null : rearInflatableBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getLeftRearInflatableBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.rearInflatableBuckled, sdlVersion);
    }
    
    public void setRightRearInflatableBelted(VehicleDataEventStatus rightRearInflatableBelted) {
    	this.rightRearInflatableBuckled = (rightRearInflatableBelted == null) ? null : rightRearInflatableBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getRightRearInflatableBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.rightRearInflatableBuckled, sdlVersion);
    }
    
    public void setMiddleRow1BeltDeployed(VehicleDataEventStatus middleRow1BeltDeployed) {
    	this.middleRow1BeltDeployed = (middleRow1BeltDeployed == null) ? null : middleRow1BeltDeployed.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getMiddleRow1BeltDeployed() {
    	return VehicleDataEventStatus.valueForJsonName(this.middleRow1BeltDeployed, sdlVersion);
    }
    
    public void setMiddleRow1BuckleBelted(VehicleDataEventStatus middleRow1BuckleBelted) {
    	this.middleRow1BeltBuckled = (middleRow1BuckleBelted == null) ? null : middleRow1BuckleBelted.getJsonName(sdlVersion);
    }
    
    public VehicleDataEventStatus getMiddleRow1BuckleBelted() {
    	return VehicleDataEventStatus.valueForJsonName(this.middleRow1BeltBuckled, sdlVersion);
    }

	@Override
	public JSONObject getJsonParameters(int sdlVersion) {
		JSONObject result = super.getJsonParameters(sdlVersion);
		
		switch(sdlVersion){
		default:
			JsonUtils.addToJsonObject(result, KEY_DRIVER_BUCKLE_BELTED, this.driverBeltBuckled);
			JsonUtils.addToJsonObject(result, KEY_MIDDLE_ROW_1_BUCKLE_BELTED, this.middleRow1BeltBuckled);
			JsonUtils.addToJsonObject(result, KEY_PASSENGER_BUCKLE_BELTED, this.passengerBeltBuckled);
			JsonUtils.addToJsonObject(result, KEY_LEFT_ROW_2_BUCKLE_BELTED, this.leftRow2BeltBuckled);
			JsonUtils.addToJsonObject(result, KEY_MIDDLE_ROW_2_BUCKLE_BELTED, this.middleRow2BeltBuckled);
			JsonUtils.addToJsonObject(result, KEY_RIGHT_ROW_2_BUCKLE_BELTED, this.rightRow2BeltBuckled);
			JsonUtils.addToJsonObject(result, KEY_LEFT_ROW_3_BUCKLE_BELTED, this.leftRow3BeltBuckled);
			JsonUtils.addToJsonObject(result, KEY_MIDDLE_ROW_3_BUCKLE_BELTED, this.middleRow3BeltBuckled);
			JsonUtils.addToJsonObject(result, KEY_RIGHT_ROW_3_BUCKLE_BELTED, this.rightRow3BeltBuckled);
			JsonUtils.addToJsonObject(result, KEY_REAR_INFLATABLE_BELTED, this.rearInflatableBuckled);
			JsonUtils.addToJsonObject(result, KEY_RIGHT_REAR_INFLATABLE_BELTED, this.rightRearInflatableBuckled);
			JsonUtils.addToJsonObject(result, KEY_DRIVER_BELT_DEPLOYED, this.driverBeltDeployed);
			JsonUtils.addToJsonObject(result, KEY_MIDDLE_ROW_1_BELT_DEPLOYED, this.middleRow1BeltDeployed);
			JsonUtils.addToJsonObject(result, KEY_PASSENGER_BELT_DEPLOYED, this.passengerBeltDeployed);
			JsonUtils.addToJsonObject(result, KEY_PASSENGER_CHILD_DETECTED, this.passengerChildDetected);
			break;
		}
		
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driverBeltBuckled == null) ? 0 : driverBeltBuckled.hashCode());
		result = prime * result + ((driverBeltDeployed == null) ? 0 : driverBeltDeployed.hashCode());
		result = prime * result + ((leftRow2BeltBuckled == null) ? 0 : leftRow2BeltBuckled.hashCode());
		result = prime * result + ((leftRow3BeltBuckled == null) ? 0 : leftRow3BeltBuckled.hashCode());
		result = prime * result + ((middleRow1BeltBuckled == null) ? 0 : middleRow1BeltBuckled.hashCode());
		result = prime * result + ((middleRow1BeltDeployed == null) ? 0 : middleRow1BeltDeployed.hashCode());
		result = prime * result + ((middleRow2BeltBuckled == null) ? 0 : middleRow2BeltBuckled.hashCode());
		result = prime * result + ((middleRow3BeltBuckled == null) ? 0 : middleRow3BeltBuckled.hashCode());
		result = prime * result + ((passengerBeltBuckled == null) ? 0 : passengerBeltBuckled.hashCode());
		result = prime * result + ((passengerBeltDeployed == null) ? 0 : passengerBeltDeployed.hashCode());
		result = prime * result + ((passengerChildDetected == null) ? 0 : passengerChildDetected.hashCode());
		result = prime * result + ((rearInflatableBuckled == null) ? 0 : rearInflatableBuckled.hashCode());
		result = prime * result + ((rightRearInflatableBuckled == null) ? 0 : rightRearInflatableBuckled.hashCode());
		result = prime * result + ((rightRow2BeltBuckled == null) ? 0 : rightRow2BeltBuckled.hashCode());
		result = prime * result + ((rightRow3BeltBuckled == null) ? 0 : rightRow3BeltBuckled.hashCode());
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
		BeltStatus other = (BeltStatus) obj;
		if (driverBeltBuckled == null) {
			if (other.driverBeltBuckled != null) { 
				return false;
			}
		} else if (!driverBeltBuckled.equals(other.driverBeltBuckled)) { 
			return false;
		}
		if (driverBeltDeployed == null) {
			if (other.driverBeltDeployed != null) { 
				return false;
			}
		} else if (!driverBeltDeployed.equals(other.driverBeltDeployed)) { 
			return false;
		}
		if (leftRow2BeltBuckled == null) {
			if (other.leftRow2BeltBuckled != null) { 
				return false;
			}
		} else if (!leftRow2BeltBuckled.equals(other.leftRow2BeltBuckled)) { 
			return false;
		}
		if (leftRow3BeltBuckled == null) {
			if (other.leftRow3BeltBuckled != null) { 
				return false;
			}
		} else if (!leftRow3BeltBuckled.equals(other.leftRow3BeltBuckled)) { 
			return false;
		}
		if (middleRow1BeltBuckled == null) {
			if (other.middleRow1BeltBuckled != null) { 
				return false;
			}
		} else if (!middleRow1BeltBuckled.equals(other.middleRow1BeltBuckled)) { 
			return false;
		}
		if (middleRow1BeltDeployed == null) {
			if (other.middleRow1BeltDeployed != null) { 
				return false;
			}
		} else if (!middleRow1BeltDeployed.equals(other.middleRow1BeltDeployed)) { 
			return false;
		}
		if (middleRow2BeltBuckled == null) {
			if (other.middleRow2BeltBuckled != null) { 
				return false;
			}
		} else if (!middleRow2BeltBuckled.equals(other.middleRow2BeltBuckled)) { 
			return false;
		}
		if (middleRow3BeltBuckled == null) {
			if (other.middleRow3BeltBuckled != null) { 
				return false;
			}
		} else if (!middleRow3BeltBuckled.equals(other.middleRow3BeltBuckled)) { 
			return false;
		}
		if (passengerBeltBuckled == null) {
			if (other.passengerBeltBuckled != null) { 
				return false;
			}
		} else if (!passengerBeltBuckled.equals(other.passengerBeltBuckled)) { 
			return false;
		}
		if (passengerBeltDeployed == null) {
			if (other.passengerBeltDeployed != null) { 
				return false;
			}
		} else if (!passengerBeltDeployed.equals(other.passengerBeltDeployed)) { 
			return false;
		}
		if (passengerChildDetected == null) {
			if (other.passengerChildDetected != null) { 
				return false;
			}
		}else if (!passengerChildDetected.equals(other.passengerChildDetected)) { 
			return false;
		}
		if (rearInflatableBuckled == null) {
			if (other.rearInflatableBuckled != null) { 
				return false;
			}
		} else if (!rearInflatableBuckled.equals(other.rearInflatableBuckled)) { 
			return false;
		}
		if (rightRearInflatableBuckled == null) {
			if (other.rightRearInflatableBuckled != null) { 
				return false;
			}
		} else if (!rightRearInflatableBuckled.equals(other.rightRearInflatableBuckled)) { 
			return false;
		}
		if (rightRow2BeltBuckled == null) {
			if (other.rightRow2BeltBuckled != null) { 
				return false;
			}
		} else if (!rightRow2BeltBuckled.equals(other.rightRow2BeltBuckled)) { 
			return false;
		}
		if (rightRow3BeltBuckled == null) {
			if (other.rightRow3BeltBuckled != null) { 
				return false;
			}
		} else if (!rightRow3BeltBuckled.equals(other.rightRow3BeltBuckled)) { 
			return false;
		}
		return true;
	}
}
