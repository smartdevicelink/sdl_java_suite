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
package com.smartdevicelink.trace;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

class OpenRPCMessage extends RPCMessage {
	private OpenRPCMessage() {super("");}
	public OpenRPCMessage(RPCMessage rpcm) {
		super(rpcm);
	} // end-method

	public OpenRPCMessage(RPCStruct rpcs) {
		super(rpcs);
	} // end-method

	public String msgDump() {
		StringBuilder pd = new StringBuilder();
		
		pd.append(this.getFunctionName()).append(" ").append(this.getMessageType());
		
		msgDump(pd);

		return pd.toString();
	} // end-method

	public void msgDump(StringBuilder pd) {
		pd.append("[");

		dumpParams(parameters, pd);
		
		pd.append("]");

		return;
	} // end-method

	private void dumpParams(Hashtable<String, Object> ht, StringBuilder pd) {
		Iterator<String> keySet = ht.keySet().iterator();
		Object obj = null;
		String key = "";
		boolean isFirstParam = true;

		while (keySet.hasNext()) {
			key = (String)keySet.next();
			obj = ht.get(key);
			if (isFirstParam) {
				isFirstParam = false;
			} else {
				pd.append(", ");
			} // end-if

			dumpParamNode(key, obj, pd);

		} // end-while
	} // end-method
	
	@SuppressWarnings("unchecked")
    private void dumpParamNode(String key, Object obj, StringBuilder pd) {

		if (obj instanceof Hashtable) {
			pd.append("[");
			dumpParams((Hashtable<String, Object>)obj, pd);
			pd.append("]");
		} else if (obj instanceof RPCStruct) {
			pd.append("[");
			OpenRPCMessage orpcm = new OpenRPCMessage((RPCStruct)obj);
			orpcm.msgDump(pd);
			pd.append("]");
		} else if (obj instanceof List) {
			pd.append("[");
			List<?> list = (List<?>)obj;
			for (int idx=0;idx < list.size();idx++) {
				if (idx > 0) {
					pd.append(", ");
				}
				dumpParamNode(key, list.get(idx), pd);
			} // end-for
			pd.append("]");
		} else {
			pd.append("\"" + key + "\" = \"" + obj.toString() + "\"");
		}
	} // end-method
} // end-class OpenRPCMessage
