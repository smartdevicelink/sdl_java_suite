package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class TouchEvent extends RPCObject {
    public static final String KEY_ID = "id";
    public static final String KEY_TS = "ts";
    public static final String KEY_C = "c";
    
    private Integer id;
    private List<Integer> timeStamps;
    private List<TouchCoord> touchCoords;
    
    public TouchEvent() { }
    
    /**
     * Creates a TouchEvent object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public TouchEvent(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.id = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_ID);
            this.timeStamps = JsonUtils.readIntegerListFromJsonObject(jsonObject, KEY_TS);
            
            List<JSONObject> touchCoordObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_C);
            if(touchCoordObjs != null){
                this.touchCoords = new ArrayList<TouchCoord>(touchCoordObjs.size());
                for(JSONObject touchCoordObj : touchCoordObjs){
                    this.touchCoords.add(new TouchCoord(touchCoordObj));
                }
            }
            break;
        }
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public List<Integer> getTs() {
    	return this.timeStamps;
    }
    
    public void setTs(List<Integer> ts) {
        this.timeStamps = ts;
    }
    
    public List<TouchCoord> getC() {
        return this.touchCoords;
    }
    
    public void setC( List<TouchCoord> c ) {
        this.touchCoords = c;        
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_ID, this.id);
            JsonUtils.addToJsonObject(result, KEY_TS, (this.timeStamps == null) ? null :
                JsonUtils.createJsonArray(this.timeStamps));
            JsonUtils.addToJsonObject(result, KEY_C, (this.touchCoords == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.touchCoords, sdlVersion));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((timeStamps == null) ? 0 : timeStamps.hashCode());
		result = prime * result + ((touchCoords == null) ? 0 : touchCoords.hashCode());
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
		TouchEvent other = (TouchEvent) obj;
		if (id == null) {
			if (other.id != null) { 
				return false;
			}
		}
		else if (!id.equals(other.id)) { 
			return false;
		}
		if (timeStamps == null) {
			if (other.timeStamps != null) { 
				return false;
			}
		} 
		else if (!timeStamps.equals(other.timeStamps)) { 
			return false;
		}
		if (touchCoords == null) {
			if (other.touchCoords != null) { 
				return false;
			}
		} 
		else if (!touchCoords.equals(other.touchCoords)) { 
			return false;
		}
		return true;
	}
}
