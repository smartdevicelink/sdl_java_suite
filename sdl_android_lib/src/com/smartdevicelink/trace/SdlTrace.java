package com.smartdevicelink.trace;

import java.sql.Timestamp;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.text.format.DateFormat;

import com.smartdevicelink.protocol.ProtocolFrameHeader;
import com.smartdevicelink.protocol.enums.FrameDataControlFrameType;
import com.smartdevicelink.protocol.enums.FrameType;
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

@SuppressLint("DefaultLocale")
public class SdlTrace {
	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
		
	public static final String SYSTEM_LOG_TAG = "SdlTrace";
	
	static final long BASE_TICS  = java.lang.System.currentTimeMillis();
	private final static String KeyStr = SDL_LIB_TRACE_KEY;
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
	}

	public static void setAppTraceLevel(DetailLevel dt) {
		if ( dt != null && acceptAPITraceAdjustments)
			DiagLevel.setLevel(Mod.app, dt);
	}

	public static void setProxyTraceLevel(DetailLevel dt) {
		if (dt != null && acceptAPITraceAdjustments)
			DiagLevel.setLevel(Mod.proxy, dt);
	}

	public static void setRpcTraceLevel(DetailLevel dt) {
		if (dt != null && acceptAPITraceAdjustments)
			DiagLevel.setLevel(Mod.rpc, dt);
	}

	public static void setMarshallingTraceLevel(DetailLevel dt) {
		if (dt != null && acceptAPITraceAdjustments)
			DiagLevel.setLevel(Mod.mar, dt);
	}

	public static void setProtocolTraceLevel(DetailLevel dt) {
		if (dt != null && acceptAPITraceAdjustments)
			DiagLevel.setLevel(Mod.proto, dt);
	}

	public static void setTransportTraceLevel(DetailLevel dt) {
		if (dt != null && acceptAPITraceAdjustments)
				DiagLevel.setLevel(Mod.tran, dt);
	}

	private static String encodeTraceMessage(Mod module, InterfaceActivityDirection msgDirection, String msgBodyXml) {
		long timestamp = java.lang.System.currentTimeMillis() - BASE_TICS;
		StringBuilder sb = new StringBuilder("<msg><dms>");
		sb.append(timestamp);
		sb.append("</dms><pid>");
		sb.append(Process.myPid());
		sb.append("</pid><tid>");
		sb.append(Thread.currentThread().getId());
		sb.append("</tid><mod>");
		sb.append(module.toString());
		sb.append("</mod>");
		if (msgDirection != InterfaceActivityDirection.None) {
			sb.append("<dir>");
			sb.append(msgDirection.toString());
			sb.append("</dir>");
		}
		sb.append(msgBodyXml);
		sb.append("</msg>");

		return sb.toString();
	} 

	static String B64EncodeForXML(String data) {
		return Mime.base64Encode(data);
		// Base64 only available in 2.2, when SmartDeviceLink base is 2.2 use: return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
	}
	
	public static boolean logProxyEvent(String eventText, String token) {
		if (DiagLevel.getLevel(Mod.proxy) == DetailLevel.OFF || !token.equals(KeyStr)) {
			return false;
		}

		String msg = SdlTrace.B64EncodeForXML(eventText);
		String xml = SdlTrace.encodeTraceMessage(Mod.proxy, InterfaceActivityDirection.None, "<d>" + msg + "</d>");
		return writeXmlTraceMessage(xml);
	}

	public static boolean logAppEvent(String eventText) {
		if (DiagLevel.getLevel(Mod.app) == DetailLevel.OFF) {
			return false;
		}

		String msg = SdlTrace.B64EncodeForXML(eventText);
		String xml = SdlTrace.encodeTraceMessage(Mod.app, InterfaceActivityDirection.None, "<d>" + msg + "</d>");
		return writeXmlTraceMessage(xml);
	}

	public static boolean logRPCEvent(InterfaceActivityDirection msgDirection, RPCMessage rpcMsg, String token) {
		DetailLevel dl = DiagLevel.getLevel(Mod.rpc);
		if (dl == DetailLevel.OFF || !token.equals(KeyStr)) {
			return false;
		}

		String xml = SdlTrace.encodeTraceMessage(Mod.rpc, msgDirection, rpc2Xml(dl, rpcMsg));
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
		}
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
		}
		return rpcAsXml.toString();
	}

	public static boolean logMarshallingEvent(InterfaceActivityDirection msgDirection, byte[] marshalledMessage, String token) {
		DetailLevel dl = DiagLevel.getLevel(Mod.mar);
		if (dl == DetailLevel.OFF || !token.equals(KeyStr)) {
			return false;
		}

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
		String xml = SdlTrace.encodeTraceMessage(Mod.mar, msgDirection, msg.toString());
		return writeXmlTraceMessage(xml);
	}

	public static boolean logProtocolEvent(InterfaceActivityDirection frameDirection, ProtocolFrameHeader frameHeader, byte[] frameData, int frameDataOffset, int frameDataLength, String token) {
		DetailLevel dl = DiagLevel.getLevel(Mod.proto);
		if (dl == DetailLevel.OFF || !token.equals(KeyStr)) {
			return false;
		}

		StringBuffer protoMsg = new StringBuffer();
		protoMsg.append("<frame>");
		protoMsg.append(SdlTrace.getProtocolFrameHeaderInfo(frameHeader, frameData));
		if (dl == DetailLevel.VERBOSE) {
			if (frameData != null && frameDataLength > 0) {
				protoMsg.append("<d>");
				String bytesInfo = "";
				bytesInfo = Mime.base64Encode(frameData, frameDataOffset, frameDataLength);
				// Base64 only available in 2.2, when SmartDeviceLink base is 2.2 use: bytesInfo = Base64.encodeToString(frameData, frameDataOffset, frameDataLength, Base64.DEFAULT);
				protoMsg.append(bytesInfo);
				protoMsg.append("</d>");
			}
		}
		protoMsg.append("</frame>");
		String xml = SdlTrace.encodeTraceMessage(Mod.proto, frameDirection, protoMsg.toString());
		return writeXmlTraceMessage(xml);
	}

	private static String getProtocolFrameHeaderInfo(ProtocolFrameHeader hdr, byte[] buf) {
		StringBuilder sb = new StringBuilder();
		sb.append("<hdr>");
		sb.append("<ver>");
		sb.append(hdr.getVersion());
		sb.append("</ver><cmp>");
		sb.append(hdr.isCompressed());
		sb.append("</cmp><ft>");
		sb.append(hdr.getFrameType().toString());
		sb.append("</ft><st>");
		sb.append(hdr.getSessionType().toString());
		sb.append("</st><sid>");
		sb.append(hdr.getSessionID());
		sb.append("</sid><sz>");
		sb.append(hdr.getDataSize());
		sb.append("</sz>");

		int frameData = hdr.getFrameData();
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
			int totalSize = BitConverter.intFromByteArray(buf, 0);			
			int numFrames = BitConverter.intFromByteArray(buf, 4);
			sb.append("<total>" + totalSize + "</total><numframes>" + numFrames + "</numframes>");
		} else if (hdr.getFrameType() == FrameType.Single ) {
			sb.append("<single/>");
		}

		sb.append("</hdr>");

		return sb.toString();
	}

	public static String getBTDeviceInfo(BluetoothDevice btDevice) {
		StringBuilder sb = new StringBuilder();
		sb.append("<btp>");
		String btdn = btDevice.getName();
		sb.append("<btn>");
		sb.append(SdlTrace.B64EncodeForXML(btdn));
		sb.append("</btn>");
		sb.append("<bta>" + btDevice.getAddress() + "</bta>");
		sb.append("<bts>" + btDevice.getBondState() + "</bts>");
		sb.append("</btp>");
		return sb.toString();
	}

	public static boolean logTransportEvent(String preamble, String transportSpecificInfoXml, InterfaceActivityDirection msgDirection, byte buf[], int byteLength, String token) {
		return logTransportEvent(preamble, transportSpecificInfoXml, msgDirection, buf, 0, byteLength, token);
	} 

	private static void checkB64(String x, byte[] buf, int offset, int byteLength) {
		if ((x.length() % 4) != 0) {
			NativeLogTool.logWarning(SdlTrace.SYSTEM_LOG_TAG, "b64 string length (" + x.length() + ") isn't multiple of 4: buf.length=" + buf.length + ", offset=" + offset + ", len=" + byteLength);
		}
	}

	public static boolean logTransportEvent(String preamble, String transportSpecificInfoXml, InterfaceActivityDirection msgDirection, byte buf[], int offset, int byteLength, String token) {
		if (DiagLevel.getLevel(Mod.tran) == DetailLevel.OFF || !token.equals(KeyStr)) {
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
		String xml = SdlTrace.encodeTraceMessage(Mod.tran, msgDirection, msg.toString());
		return writeXmlTraceMessage(xml);
	}
	
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
					localTraceListener.logXmlMsg(msg, KeyStr);
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
	
	public static String getLogHeader(String dumpReason, int seqNo) {
    	
    	// Indicates which version of XML to parse the message with.
    	StringBuilder result = new StringBuilder("<?xml version=\"1.0\"?>");
    	
    	// The block that contains all of the XML information.
    	result.append("<info>");
    	
    	// Hardware and carrier information.
    	result.append("<host>");    	
    	StringBuilder hostInfo = new StringBuilder(Build.BRAND);
    	hostInfo.append("-");
    	hostInfo.append(Build.MANUFACTURER);
    	hostInfo.append("-");
    	hostInfo.append(Build.MODEL);
    	hostInfo.append("(");
    	hostInfo.append(Build.HOST);
    	hostInfo.append(")");
    	result.append(SdlTrace.B64EncodeForXML(hostInfo.toString()));
    	result.append("</host>");
    	
    	// Release information.
    	result.append("<osv>");    	
    	StringBuilder osvInfo = new StringBuilder(Build.VERSION.RELEASE);
    	osvInfo.append("(");
    	osvInfo.append(Build.VERSION.CODENAME);
    	osvInfo.append(")");
    	result.append(SdlTrace.B64EncodeForXML(osvInfo.toString()));
    	result.append("</osv>");
    	
    	// Network information.
    	result.append(TraceDeviceInfo.getTelephonyHeader());
    	    	
    	// Memory information.
    	result.append("<mem>");
    	result.append("<hf>");
    	result.append((Debug.getNativeHeapFreeSize() / 1024));
    	result.append("KB");
    	result.append("</hf>");
    	result.append("<ha>");
    	result.append((Debug.getNativeHeapAllocatedSize() / 1024));
    	result.append("KB");
    	result.append("</ha>");
    	result.append("</mem>");

    	// Process and thread information.
    	result.append("<np>");
    	result.append(Runtime.getRuntime().availableProcessors());
    	result.append("</np>");
    	result.append("<pid>");
    	result.append(Process.myPid());
    	result.append("</pid>");
    	result.append("<tid>");
    	result.append(Thread.currentThread().getId());
    	result.append("</tid>");
    	
    	// Time of message construction.
    	result.append("<utc>");
    	result.append(DateFormat.format("yy-MM-dd hh:mm:ss SSS", new Timestamp(java.lang.System.currentTimeMillis())));
    	result.append("</utc>");
    	
    	// Bluetooth information.
    	result.append(TraceDeviceInfo.getLogHeaderBluetoothPairs());
    	
    	// SDL specific information.
    	result.append("<sdltraceroot>");    	
    	result.append("<sequencenum>");
    	result.append(seqNo);
    	result.append("</sequencenum>");    	
    	result.append("<dumpreason>");
    	result.append(dumpReason);
    	result.append("</dumpreason>");    	
    	result.append("<tracelevel>");
    	result.append("<tran>");
    	result.append(DiagLevel.getLevel(Mod.tran));
    	result.append("</tran>");
    	result.append("<proto>");
    	result.append(DiagLevel.getLevel(Mod.proto));
    	result.append("</proto>");
    	result.append("<mar>");
    	result.append(DiagLevel.getLevel(Mod.mar));
    	result.append("</mar>");
    	result.append("<rpc>");
    	result.append(DiagLevel.getLevel(Mod.rpc));
    	result.append("</rpc>");
    	result.append("<proxy>");
    	result.append(DiagLevel.getLevel(Mod.proxy));
    	result.append("</proxy>");
    	result.append("<app>");
    	result.append(DiagLevel.getLevel(Mod.app));
    	result.append("</app>");
    	result.append("</tracelevel>");    	
    	result.append("</sdltraceroot>");    	    	
		result.append("</info>");
		
		return result.toString();
	}
}