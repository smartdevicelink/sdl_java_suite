package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class TouchCoord extends RPCObject {
    public static final String KEY_X = "x";
    public static final String KEY_Y = "y";
    
    private Integer x, y;
    
    public TouchCoord() {}
    
    /**
     * Creates a TouchCoord object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public TouchCoord(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.x = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_X);
            this.y = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_Y);
            break;
        }
    }
    
    public void setX(Integer x) {
        this.x = x;
    }
    
    public Integer getX() {
        return this.x;
    }
    
    public void setY(Integer y) {
        this.y = y;
    }
    
    public Integer getY() {
        return this.y;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_X, this.x);
            JsonUtils.addToJsonObject(result, KEY_Y, this.y);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		TouchCoord other = (TouchCoord) obj;
		if (x == null) {
			if (other.x != null) { 
				return false;
			}
		} else if (!x.equals(other.x)) { 
			return false;
		}
		if (y == null) {
			if (other.y != null) { 
				return false;
			}
		} else if (!y.equals(other.y)) { 
			return false;
		}
		return true;
	}
}
