package com.smartdevicelink.trace;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.JsonUtils;

class OpenRPCMessage {
    private RPCStruct struct;
    
	public OpenRPCMessage(RPCMessage rpcm) {
		this.struct = rpcm;
	} // end-method
	public OpenRPCMessage(RPCStruct rpcs){
	    this.struct = rpcs;
	}

	public String msgDump() {
		StringBuilder pd = new StringBuilder();
		
		if(this.struct instanceof RPCMessage){
		    RPCMessage temp = (RPCMessage) this.struct;
		    pd.append(temp.getFunctionName() + " " + temp.getMessageType());
		}
		
		msgDump(pd);

		return pd.toString();
	} // end-method

	public void msgDump(StringBuilder pd) {
		pd.append("[");

		dumpParams(this.struct.toJson(RPCStruct.getSdlVersion()), pd);
		
		pd.append("]");

		return;
	} // end-method

	private void dumpParams(JSONObject json, StringBuilder pd) {
	    @SuppressWarnings("unchecked")
        Iterator<String> keySet = json.keys();
		Object obj = null;
		String key = "";
		boolean isFirstParam = true;

		while (keySet.hasNext()) {
			key = (String)keySet.next();
			obj = JsonUtils.readObjectFromJsonObject(json, key);
			if (isFirstParam) {
				isFirstParam = false;
			} else {
				pd.append(", ");
			} // end-if

			dumpParamNode(key, obj, pd);

		} // end-while
	} // end-method
	
    private void dumpParamNode(String key, Object obj, StringBuilder pd) {

		if (obj instanceof RPCStruct) {
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
