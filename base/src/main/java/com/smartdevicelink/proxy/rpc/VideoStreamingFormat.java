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
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;

import java.util.Hashtable;

/**
 * Video streaming formats and their specifications.
 */

public class VideoStreamingFormat extends RPCStruct {
	public static final String KEY_PROTOCOL = "protocol";
	public static final String KEY_CODEC = "codec";

	public VideoStreamingFormat(){}
	public VideoStreamingFormat(Hashtable<String, Object> hash){super(hash);}

	/**
	 * Create the VideoStreamingFormat object
	 * @param protocol The protocol used
	 * @param codec The codec used
	 */
	public VideoStreamingFormat(@NonNull VideoStreamingProtocol protocol, @NonNull VideoStreamingCodec codec){
		this();
		setProtocol(protocol);
		setCodec(codec);
	}

	public VideoStreamingFormat setProtocol(@NonNull VideoStreamingProtocol protocol) {
        setValue(KEY_PROTOCOL, protocol);
        return this;
    }

	public VideoStreamingProtocol getProtocol(){
		return (VideoStreamingProtocol) getObject(VideoStreamingProtocol.class, KEY_PROTOCOL);
	}

	public VideoStreamingFormat setCodec(@NonNull VideoStreamingCodec codec) {
        setValue(KEY_CODEC, codec);
        return this;
    }

	public VideoStreamingCodec getCodec(){
		return (VideoStreamingCodec) getObject(VideoStreamingCodec.class, KEY_CODEC);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof VideoStreamingFormat){
			VideoStreamingFormat compareTo = (VideoStreamingFormat) obj;
			return getCodec() == compareTo.getCodec() && getProtocol() == compareTo.getProtocol();
		}
		return false;
	}

	@Override
	public String toString() {
		return "codec=" + getCodec() +
				", protocol=" + getProtocol();
	}
}
