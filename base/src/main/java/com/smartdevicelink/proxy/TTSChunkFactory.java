package com.smartdevicelink.proxy;

import java.util.Vector;

import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

public class TTSChunkFactory {

	public static TTSChunk createChunk(SpeechCapabilities type, String text) {
		TTSChunk ret = new TTSChunk();
		ret.setType(type);
		ret.setText(text);
		return ret;
	}

	public static Vector<TTSChunk> createSimpleTTSChunks(String simple) {
		if (simple == null) {
			return null;
		}
		
		Vector<TTSChunk> chunks = new Vector<TTSChunk>();
		
		TTSChunk chunk = createChunk(SpeechCapabilities.TEXT, simple);
		chunks.add(chunk);
		return chunks;
	}

	public static Vector<TTSChunk> createPrerecordedTTSChunks(String prerecorded) {
		if (prerecorded == null) {
			return null;
		}
		
		Vector<TTSChunk> chunks = new Vector<TTSChunk>();
		TTSChunk chunk = createChunk(SpeechCapabilities.PRE_RECORDED, prerecorded);
		chunks.add(chunk);
		return chunks;
	}
}
