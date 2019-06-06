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



import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.enums.FrameDataControlFrameType;
import com.smartdevicelink.protocol.enums.FrameType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.trace.enums.DetailLevel;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;
import com.smartdevicelink.trace.enums.Mod;
import com.smartdevicelink.transport.SiphonServer;
import com.smartdevicelink.util.BitConverter;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.NativeLogTool;

/* This class handles the global TraceSettings as requested by the users either through the combination of the following
   1. System defaults
   2. Application XML config
   3. Programmatic requests from application itself

   It is manifested in the <SmartDeviceLink>...</SmartDeviceLink> tags
 */

//@SuppressLint("DefaultLocale")
public class SdlTrace {
	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
			
	public static final String SYSTEM_LOG_TAG = "SdlTrace";
	
	private static long baseTics  = System.currentTimeMillis();
	private static boolean acceptAPITraceAdjustments = true;

	protected static ISTListener m_appTraceListener = null;

	///
	///  The PUBLIC interface to SdlTrace starts here
	///


	public static void setAcceptAPITraceAdjustments(Boolean APITraceAdjustmentsAccepted) {
		if (APITraceAdjustmentsAccepted != null) {
			acceptAPITraceAdjustments = APITraceAdjustmentsAccepted;
		}
	}
	
	public static boolean getAcceptAPITraceAdjustments() {
		return acceptAPITraceAdjustments;
	}
	
	public static void setAppTraceListener(ISTListener listener) {
		m_appTraceListener = listener;
	} // end-method

	public static void setAppTraceLevel(DetailLevel dt) {
		if ( dt != null && acceptAPITraceAdjustments)
			DiagLevel.setLevel(Mod.app, dt);
	} // end-method

	public static void setProxyTraceLevel(DetailLevel dt) {
		if (dt != null && acceptAPITraceAdjustments)
			DiagLevel.setLevel(Mod.proxy, dt);
	} // end-method

	public static void setRpcTraceLevel(DetailLevel dt) {
		if (dt != null && acceptAPITraceAdjustments)
			DiagLevel.setLevel(Mod.rpc, dt);
	} // end-method

	public static void setMarshallingTraceLevel(DetailLevel dt) {
		if (dt != null && acceptAPITraceAdjustments)
			DiagLevel.setLevel(Mod.mar, dt);
	} // end-method

	public static void setProtocolTraceLevel(DetailLevel dt) {
		if (dt != null && acceptAPITraceAdjustments)
			DiagLevel.setLevel(Mod.proto, dt);
	} // end-method

	public static void setTransportTraceLevel(DetailLevel dt) {
		if (dt != null && acceptAPITraceAdjustments)
				DiagLevel.setLevel(Mod.tran, dt);
	} // end-method

	private static String getPid(){
		//Default implementation is not able to get this information
		return "UNKNOWN";
	}

	private static String encodeTraceMessage(long timestamp, Mod module, InterfaceActivityDirection msgDirection, String msgBodyXml) {
		StringBuilder sb = new StringBuilder("<msg><dms>");
		sb.append(timestamp);
		sb.append("</dms><pid>");
		sb.append(getPid());
		sb.append("</pid><tid>");
		sb.append(Thread.currentThread().getId());
		sb.append("</tid><mod>");
		sb.append(module.toString());
		sb.append("</mod>");
		if (msgDirection != InterfaceActivityDirection.None) {
			sb.append("<dir>");
			sb.append(interfaceActivityDirectionToString(msgDirection));
			sb.append("</dir>");
		} // end-if
		sb.append(msgBodyXml);
		sb.append("</msg>");

		return sb.toString();
	} // end-method

	private static String interfaceActivityDirectionToString(InterfaceActivityDirection iaDirection) {
		String str = "";
		switch (iaDirection) {
			case Receive:
				str = "rx";
				break;
			case Transmit:
				str = "tx";
				break;
        default:
            break;
		} // end-switch
		return str;
	} // end-method

	static String B64EncodeForXML(String data) {
		return Mime.base64Encode(data);
		// Base64 only available in 2.2, when SmartDeviceLink base is 2.2 use: return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
	} // end-method
	
	public static boolean logProxyEvent(String eventText, String token) {
		if (DiagLevel.getLevel(Mod.proxy) == DetailLevel.OFF || !token.equals(SDL_LIB_TRACE_KEY)) {
			return false;
		}

		String msg = SdlTrace.B64EncodeForXML(eventText);
		String xml = SdlTrace.encodeTraceMessage(SdlTrace.getBaseTicsDelta(), Mod.proxy, InterfaceActivityDirection.None, "<d>" + msg + "</d>");
		return writeXmlTraceMessage(xml);
	}

	public static boolean logAppEvent(String eventText) {
		if (DiagLevel.getLevel(Mod.app) == DetailLevel.OFF) {
			return false;
		}

		long timestamp = SdlTrace.getBaseTicsDelta();
		String msg = SdlTrace.B64EncodeForXML(eventText);
		String xml = SdlTrace.encodeTraceMessage(timestamp, Mod.app, InterfaceActivityDirection.None, "<d>" + msg + "</d>");
		return writeXmlTraceMessage(xml);
	}

	public static boolean logRPCEvent(InterfaceActivityDirection msgDirection, RPCMessage rpcMsg, String token) {
		DetailLevel dl = DiagLevel.getLevel(Mod.rpc);
		if (dl == DetailLevel.OFF || !token.equals(SDL_LIB_TRACE_KEY)) {
			return false;
		}

		long timestamp = SdlTrace.getBaseTicsDelta();
		String xml = SdlTrace.encodeTraceMessage(timestamp, Mod.rpc, msgDirection, rpc2Xml(dl, rpcMsg));
		return writeXmlTraceMessage(xml);
	}

	private static String rpc2Xml(DetailLevel dl, RPCMessage rpcMsg) {
		StringBuilder rpcAsXml = new StringBuilder();
		rpcAsXml.append("<op>");
		rpcAsXml.append(rpcMsg.getFunctionName());
		rpcAsXml.append("</op>");
		boolean hasCorrelationID = false;
		Integer correlationID = -1;
		if (rpcMsg instanceof RPCRequest) {
			hasCorrelationID = true;
			correlationID = ((RPCRequest)rpcMsg).getCorrelationID();
		} else if (rpcMsg instanceof RPCResponse) {
			hasCorrelationID = true;
			correlationID = ((RPCResponse)rpcMsg).getCorrelationID();
		} // end-if
		if (hasCorrelationID) {
			rpcAsXml.append("<cid>");
			rpcAsXml.append(correlationID);
			rpcAsXml.append("</cid>");
		} // end-if
		rpcAsXml.append("<type>");
		rpcAsXml.append(rpcMsg.getMessageType());
		rpcAsXml.append("</type>");
		//rpcAsXml.append(newline);

		if (dl == DetailLevel.VERBOSE) {
			OpenRPCMessage orpcmsg = new OpenRPCMessage(rpcMsg);
			String rpcParamList = orpcmsg.msgDump();
			String msg = SdlTrace.B64EncodeForXML(rpcParamList);
			rpcAsXml.append("<d>");
			rpcAsXml.append(msg);
			rpcAsXml.append("</d>");
		} // end-if
		return rpcAsXml.toString();
	} // end-method

	public static boolean logMarshallingEvent(InterfaceActivityDirection msgDirection, byte[] marshalledMessage, String token) {
		DetailLevel dl = DiagLevel.getLevel(Mod.mar);
		if (dl == DetailLevel.OFF || !token.equals(SDL_LIB_TRACE_KEY)) {
			return false;
		}

		long timestamp = SdlTrace.getBaseTicsDelta();
		StringBuilder msg = new StringBuilder();
		msg.append("<sz>");
		msg.append(marshalledMessage.length);
		msg.append("</sz>");
		if (dl == DetailLevel.VERBOSE) {
			msg.append("<d>");
			msg.append(Mime.base64Encode(marshalledMessage));
			// Base64 only available in 2.2, when SmartDeviceLink base is 2.2 use: msg.append(Base64.encodeToString(marshalledMessage, Base64.DEFAULT));
			msg.append("</d>");
		}
		String xml = SdlTrace.encodeTraceMessage(timestamp, Mod.mar, msgDirection, msg.toString());
		return writeXmlTraceMessage(xml);
	}

	public static boolean logProtocolEvent(InterfaceActivityDirection frameDirection, SdlPacket packet, int frameDataOffset, int frameDataLength, String token) {
		DetailLevel dl = DiagLevel.getLevel(Mod.proto);
		if (dl == DetailLevel.OFF || !token.equals(SDL_LIB_TRACE_KEY)) {
			return false;
		}

		StringBuffer protoMsg = new StringBuffer();
		protoMsg.append("<frame>");
		protoMsg.append(SdlTrace.getProtocolFrameHeaderInfo(packet));
		if (dl == DetailLevel.VERBOSE) {
			if (packet.getPayload() != null && frameDataLength > 0) {
				protoMsg.append("<d>");
				String bytesInfo = "";
				bytesInfo = Mime.base64Encode(packet.getPayload(), frameDataOffset, frameDataLength);
				// Base64 only available in 2.2, when SmartDeviceLink base is 2.2 use: bytesInfo = Base64.encodeToString(frameData, frameDataOffset, frameDataLength, Base64.DEFAULT);
				protoMsg.append(bytesInfo);
				protoMsg.append("</d>");
			}
		}
		protoMsg.append("</frame>");
		String xml = SdlTrace.encodeTraceMessage(SdlTrace.getBaseTicsDelta(), Mod.proto, frameDirection, protoMsg.toString());
		return writeXmlTraceMessage(xml);
	}

	private static String getProtocolFrameType(FrameType f) {
		if (f == FrameType.Control)
			return "Control";
		else if (f == FrameType.Consecutive)
			return "Consecutive";
		else if (f == FrameType.First)
			return "First";
		else if (f == FrameType.Single)
			return "Single";

		return "Unknown";
	} // end-method

	private static String getProtocolSessionType(SessionType serviceType) {
		String s;
		if (serviceType == SessionType.RPC )
			s = "rpc";
		else if (serviceType == SessionType.BULK_DATA)
			s = "bulk";
		else
			s = "Unknown";
		return s;
	} // end-method

	private static String getProtocolFrameHeaderInfo(SdlPacket hdr) {
		StringBuilder sb = new StringBuilder();
		sb.append("<hdr>");
		sb.append("<ver>");
		sb.append(hdr.getVersion());
		sb.append("</ver><cmp>");
		sb.append(hdr.isEncrypted());
		sb.append("</cmp><ft>");
		sb.append(getProtocolFrameType(hdr.getFrameType()));
		sb.append("</ft><st>");
		sb.append(getProtocolSessionType(SessionType.valueOf((byte)hdr.getServiceType())));
		sb.append("</st><sid>");
		sb.append(hdr.getSessionId());
		sb.append("</sid><sz>");
		sb.append(hdr.getDataSize());
		sb.append("</sz>");

		int frameData = hdr.getFrameInfo();
		if (hdr.getFrameType() == FrameType.Control) {
			sb.append("<ca>");
			if (frameData == FrameDataControlFrameType.StartSession.getValue()) 
				sb.append("StartSession");
			else if (frameData == FrameDataControlFrameType.StartSessionACK.getValue())
				sb.append("StartSessionACK");
			else if (frameData == FrameDataControlFrameType.StartSessionNACK.getValue())
				sb.append("StartSessionNACK");
			else if (frameData == FrameDataControlFrameType.EndSession.getValue())
				sb.append("EndSession");
			sb.append("</ca>");
		} else if (hdr.getFrameType() == FrameType.Consecutive ) {
			sb.append("<fsn>");
			if (frameData == 0 )
				sb.append("lastFrame");
			else
				sb.append(String.format("%02X",frameData)); 
			sb.append("</fsn>");
		} else if (hdr.getFrameType() == FrameType.First ) {
			int totalSize = BitConverter.intFromByteArray(hdr.getPayload(), 0);			
			int numFrames = BitConverter.intFromByteArray(hdr.getPayload(), 4);
			sb.append("<total>" + totalSize + "</total><numframes>" + numFrames + "</numframes>");
		} else if (hdr.getFrameType() == FrameType.Single ) {
			sb.append("<single/>");
		}

		sb.append("</hdr>");

		return sb.toString();
	} // end-method

	public static boolean logTransportEvent(String preamble, String transportSpecificInfoXml, InterfaceActivityDirection msgDirection, byte buf[], int byteLength, String token) {
		return logTransportEvent(preamble, transportSpecificInfoXml, msgDirection, buf, 0, byteLength, token);
	} 

	private static void checkB64(String x, byte[] buf, int offset, int byteLength) {
		if ((x.length() % 4) != 0) {
			NativeLogTool.logWarning(SdlTrace.SYSTEM_LOG_TAG, "b64 string length (" + x.length() + ") isn't multiple of 4: buf.length=" + buf.length + ", offset=" + offset + ", len=" + byteLength);
		} // end-if
	} // end-method

	public static boolean logTransportEvent(String preamble, String transportSpecificInfoXml, InterfaceActivityDirection msgDirection, byte buf[], int offset, int byteLength, String token) {
		if (DiagLevel.getLevel(Mod.tran) == DetailLevel.OFF || !token.equals(SDL_LIB_TRACE_KEY)) {
			return false;
		}

		StringBuilder msg = new StringBuilder();
		if (transportSpecificInfoXml != null && transportSpecificInfoXml.length() > 0) {
			msg.append(transportSpecificInfoXml);
		}
		if (preamble != null && preamble.length() > 0) {
			msg.append("<desc>");
			msg.append(preamble);
			msg.append("</desc>");
		}
		if (buf != null) {
			msg.append("<sz>");
			msg.append(byteLength);
			msg.append("</sz>");
			DetailLevel dl = DiagLevel.getLevel(Mod.tran);
			if (dl == DetailLevel.VERBOSE) {
				if (buf != null && byteLength > 0) {
					msg.append("<d>");
					String bytesInfo = Mime.base64Encode(buf, offset, byteLength);
					checkB64(bytesInfo, buf, offset, byteLength);
					msg.append(bytesInfo);
					msg.append("</d>");
				}
			}
		}
		String xml = SdlTrace.encodeTraceMessage(SdlTrace.getBaseTicsDelta(), Mod.tran, msgDirection, msg.toString());
		return writeXmlTraceMessage(xml);
	}

	// Package-scoped
	static long getBaseTicsDelta() {
		return System.currentTimeMillis() - getBaseTics();
	}

	// Package-scoped
	static long getBaseTics() {
		return baseTics;
	} // end-method

	public static Boolean writeMessageToSiphonServer(String info) {
		return SiphonServer.sendFormattedTraceMessage(info);
	}

	private static boolean writeXmlTraceMessage(String msg) {
		try {			
			// Attempt to write formatted message to the Siphon
			if (false == writeMessageToSiphonServer(msg)) {				
				// If writing to the Siphon fails, write to the native log
				NativeLogTool.logInfo(SdlTrace.SYSTEM_LOG_TAG, msg);
				return false;
			}
			
			ISTListener localTraceListener = m_appTraceListener;

			if (localTraceListener != null) {
				try {
					localTraceListener.logXmlMsg(msg, SDL_LIB_TRACE_KEY);
				} catch (Exception ex) {
					DebugTool.logError("Failure calling ISTListener: " + ex.toString(), ex);
					return false;
				}
			}
		} catch (Exception ex) {
			NativeLogTool.logError(SdlTrace.SYSTEM_LOG_TAG, "Failure writing XML trace message: " + ex.toString());
			return false;
		}
		return true;
	}




	private static String getSmartDeviceLinkTraceRoot(String dumpReason, int seqNo) {
		StringBuilder write = new StringBuilder("<SmartDeviceLinktraceroot>" + "<sequencenum>" + seqNo
				+ "</sequencenum>" + "<dumpreason>" + dumpReason
				+ "</dumpreason><tracelevel>");

		write.append("<tran>" + DiagLevel.getLevel(Mod.tran) + "</tran>");
		write.append("<proto>" + DiagLevel.getLevel(Mod.proto) + "</proto>");
		write.append("<mar>" + DiagLevel.getLevel(Mod.mar) + "</mar>");
		write.append("<rpc>" + DiagLevel.getLevel(Mod.rpc) + "</rpc>");
		write.append("<proxy>" + DiagLevel.getLevel(Mod.proxy) + "</proxy>");
		write.append("<app>" + DiagLevel.getLevel(Mod.app) + "</app>");

		write.append("</tracelevel>");
		write.append("</SmartDeviceLinktraceroot>");
		return write.toString();
	} // end-method
} // end-class