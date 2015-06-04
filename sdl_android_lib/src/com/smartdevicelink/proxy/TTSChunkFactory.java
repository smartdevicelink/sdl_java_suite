package com.smartdevicelink.proxy;

import java.util.Vector;

import com.smartdevicelink.rpc.datatypes.TtsChunk;
import com.smartdevicelink.rpc.enums.SpeechCapabilities;

public class TtsChunkFactory {

	public static TtsChunk createChunk(SpeechCapabilities type, String text) {
		TtsChunk ret = new TtsChunk();
		ret.setType(type);
		ret.setText(text);
		return ret;
	}

	public static Vector<TtsChunk> createSimpleTtsChunks(String simple) {
		if (simple == null) {
			return null;
		}
		
		Vector<TtsChunk> chunks = new Vector<TtsChunk>();
		
		TtsChunk chunk = createChunk(SpeechCapabilities.TEXT, simple);
		chunks.add(chunk);
		return chunks;
	}

	public static Vector<TtsChunk> createPrerecordedTtsChunks(String prerecorded) {
		if (prerecorded == null) {
			return null;
		}
		
		Vector<TtsChunk> chunks = new Vector<TtsChunk>();
		TtsChunk chunk = createChunk(SpeechCapabilities.PRE_RECORDED, prerecorded);
		chunks.add(chunk);
		return chunks;
	}
}
