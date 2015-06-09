package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCStruct;

public class TouchEvent extends RPCStruct {
    public static final String KEY_ID = "id";
    public static final String KEY_TS = "ts";
    public static final String KEY_C = "c";
    
    public TouchEvent() { }
  
    public TouchEvent(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setId(Integer id) {
        if (id != null) {
            store.put(KEY_ID, id);
        } else {
        	store.remove(KEY_ID);
        }
    }
    
    public Integer getId() {
        return (Integer) store.get(KEY_ID);
    }
    
    @SuppressWarnings("unchecked")
    public List<Long> getTs() {
    	if(store.get(KEY_TS) instanceof List<?>){
    		List<?> list = (List<?>)store.get(KEY_TS);
    		if(list != null && list.size()>0){
    			for( Object obj : list ) {
        			if (!(obj instanceof Integer) || !(obj instanceof Long)) {
        				return null;
        			}
        		}

				if (list.get(0) instanceof Integer) {
        			List<Integer> listOfInt = (List<Integer>) list;
					List<Long> listofLongs = new ArrayList<Long>(list.size());
        			for(int i = 0; i < list.size(); i++){
        				listofLongs.add(listOfInt.get(i).longValue());
        			}
        			return listofLongs; 
				} else if (list.get(0) instanceof Long) {
					return (List<Long>) list;
				}   		
        	}
    	}
        return null;
    }
    
    public void setTs(List<Long> ts) {
        if (ts != null) {
            store.put(KEY_TS, ts);
        } else {
        	store.remove(KEY_TS);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<TouchCoord> getC() {
        if (store.get(KEY_C) instanceof List<?>) {
        	List<?> list = (List<?>)store.get(KEY_C);
	        if (list != null && list.size() > 0) {

	        	List<TouchCoord> touchCoordList  = new ArrayList<TouchCoord>();

	        	boolean flagRaw  = false;
	        	boolean flagHash = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw TouchCoord and a Hashtable value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof TouchCoord) {
	        			if (flagHash) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof Hashtable) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagHash = true;
	        			touchCoordList.add(new TouchCoord((Hashtable<String, Object>) obj));

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<TouchCoord>) list;
	        	} else if (flagHash) {
	        		return touchCoordList;
	        	}
	        }
        }
        return null;
    } 
    
    public void setC( List<TouchCoord> c ) {
        if (c != null) {
            store.put(KEY_C, c );
        } else {
        	store.remove(KEY_C);
        }        
    }          
}
