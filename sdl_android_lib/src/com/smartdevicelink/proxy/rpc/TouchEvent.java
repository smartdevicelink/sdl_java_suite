package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCStruct;
/**
 * <p>For touchscreen interactions, the mode of how the choices are presented.</p>
 *
 * <p><b>Parameter List</b></p>
 * 
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>id</td>
 * 			<td>Integer</td>
 * 			<td> A touch's unique identifier.  The application can track the current touch events by id. If a touch event has type begin, the id should be added to the set of touches. If a touch event has type end, the id should be removed from the set of touches.</td>
 *                 <td>N</td>
 *                 <td>Min Value: 0; Max Value: 9</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>ts</td>
 * 			<td>Float</td>
 * 			<td>The time that the touch was recorded. <p> This number can the time since the beginning of the session or something else as long as the units are in milliseconds.</p>
 * <p>The timestamp is used to determined the rate of change of position of a touch.</p><p>The application also uses the time to verify whether two touches,with different ids, are part of a single action by the user.</p>
 * 			<p>If there is only a single timestamp in this array,</p> it is the same for every coordinate in the coordinates array.</td>
 *                 <td>Y</td>
 *                 <td>minvalue="0" maxvalue="5000000000" minsize="1" maxsize="1000"</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>c</td>
 * 			<td>Integer</td>
 * 			<td>The coordinates of the screen area where the touch event occurred.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0</td>
 * 		</tr>
 *  </table>
 *  
 *   @since SmartDeviceLink 3.0 
 *   
 *   @see SoftButtonCapabilities
 *   @see ButtonCapabilities
 *   @see OnButtonPress
 */

public class TouchEvent extends RPCStruct {
    public static final String KEY_ID = "id";
    public static final String KEY_TS = "ts";
    public static final String KEY_C = "c";
    
    public TouchEvent() { }
    /**
	 * <p>Constructs a new TouchEvent object indicated by the Hashtable parameter</p>
	 * 
	 * 
	 * @param hash
	 * <p>
	 *            The Hashtable to use</p>
	 */
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
