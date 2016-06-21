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
    
    /**
     * Use getTimestamps
     * @deprecated 4.0.2
     * @return
     */
    @Deprecated
    public List<Long> getTs() {
    	return getTimestamps();
    }
    
    @SuppressWarnings("unchecked")
    public List<Long> getTimestamps() {
    	if(store.get(KEY_TS) instanceof List<?>){
    		List<?> list = (List<?>)store.get(KEY_TS);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){ //Backwards case
        			int size = list.size();
        			List<Integer> listOfInt = (List<Integer>) list;
        			List<Long> listofLongs = new ArrayList<Long>(size);
        			for(int i = 0; i<size;i++){
        				listofLongs.add(listOfInt.get(i).longValue());
        			}
        			return listofLongs;
        		}else if(obj instanceof Long){
        			return (List<Long>) list;
        		}    		
        	}
    	}
        return null;
    }
    
    public void setTimestamps(List<Long> ts){
    	if (ts != null) {
            store.put(KEY_TS, ts);
        } else {
        	store.remove(KEY_TS);
        }
    }
    
    /**
     * Use setTimestamps. 
     * @deprecated 4.0.2
     * @param ts
     */
    @Deprecated
    public void setTs(List<Long> ts) {
       setTimestamps(ts);
    }
    
    /**
     * Use getTouchCoordinates
     * @deprecated 4.0.2
     * @return
     */
    @Deprecated
    public List<TouchCoord> getC() {
    	return getTouchCoordinates();
    }
    @SuppressWarnings("unchecked")
    public List<TouchCoord> getTouchCoordinates() {
        if (store.get(KEY_C) instanceof List<?>) {
        	List<?> list = (List<?>)store.get(KEY_C);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TouchCoord) {
	                return (List<TouchCoord>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TouchCoord> newList = new ArrayList<TouchCoord>();
	                for (Object hashObj : list) {
	                    newList.add(new TouchCoord((Hashtable<String, Object>) hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    } 
    
    /**
     * Use setTouchCoordinates
     * @deprecated 4.0.2
     * @return
     */
    @Deprecated
    public void setC( List<TouchCoord> c ) {
    	setTouchCoordinates(c);
    }
    
    public void setTouchCoordinates( List<TouchCoord> c ) {
        if (c != null) {
            store.put(KEY_C, c );
        } else {
        	store.remove(KEY_C);
        }        
    }          
}
