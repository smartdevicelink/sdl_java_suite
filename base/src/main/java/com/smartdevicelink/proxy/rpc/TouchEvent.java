/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
	 * @param hash The Hashtable to use
	 */
    public TouchEvent(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new TouchEvent object
	 * @param id A touch's unique identifier.
	 * @param ts The time that the touch was recorded.
	 * @param c The coordinates of the screen area where the touch event occurred.
	 */
	public TouchEvent(@NonNull Integer id, @NonNull List<Long> ts, @NonNull List<TouchCoord> c){
		this();
		setId(id);
		setTimestamps(ts);
		setTouchCoordinates(c);
	}
    
    public void setId(@NonNull Integer id) {
        setValue(KEY_ID, id);
    }
    
    public Integer getId() {
        return getInteger(KEY_ID);
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
    	if(getValue(KEY_TS) instanceof List<?>){
    		List<?> list = (List<?>) getValue(KEY_TS);
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
    
    public void setTimestamps(@NonNull List<Long> ts){
        setValue(KEY_TS, ts);
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
        return (List<TouchCoord>) getObject(TouchCoord.class, KEY_C);
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
    
    public void setTouchCoordinates(@NonNull List<TouchCoord> c ) {
        setValue(KEY_C, c);
    }          
}
