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
package com.smartdevicelink.encoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;

import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.util.DebugTool;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class SdlEncoder {

	private static final String TAG = "SdlEncoder";

	// parameters for the encoder
	private static final String _MIME_TYPE = "video/avc"; // H.264/AVC video
	private static final long KEEPALIVE_INTERVAL_MSEC = 100;

	// private static final String MIME_TYPE = "video/mp4v-es"; //MPEG4 video
	private int frameRate = 30;
	private int frameInterval = 5;
	private int frameWidth = 800;
	private int frameHeight = 480;
	private int bitrate = 6000000;

	// encoder state
	private MediaCodec mEncoder;
	private PipedOutputStream mOutputStream;
	private IVideoStreamListener mOutputListener;
	private long mLastEmittedFrameTimestamp;

	// allocate one of these up front so we don't need to do it every time
	private MediaCodec.BufferInfo mBufferInfo;

	// Codec-specific data (SPS and PPS)
	private byte[] mH264CodecSpecificData = null;

	public SdlEncoder () {
	}
	public void setFrameRate(int iVal){
		frameRate = iVal;
	}
	public void setFrameInterval(int iVal){
		frameInterval = iVal;
	}
	public void setFrameWidth(int iVal){
		frameWidth = iVal;
	}
	public void setFrameHeight(int iVal){
		frameHeight = iVal;
	}
	public void setBitrate(int iVal){
		bitrate = iVal;
	}
	public void setOutputStream(PipedOutputStream mStream){
		mOutputStream = mStream;
	}
	public void setOutputListener(IVideoStreamListener listener) {
		mOutputListener = listener;
	}
	public Surface prepareEncoder () {

		mBufferInfo = new MediaCodec.BufferInfo();

		MediaFormat format = MediaFormat.createVideoFormat(_MIME_TYPE, frameWidth,
				frameHeight);

		// Set some properties. Failing to specify some of these can cause the
		// MediaCodec
		// configure() call to throw an unhelpful exception.
		format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
				MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
		format.setInteger(MediaFormat.KEY_BIT_RATE, bitrate);
		format.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);
		format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, frameInterval);

		// Create a MediaCodec encoder, and configure it with our format. Get a
		// Surface
		// we can use for input and wrap it with a class that handles the EGL
		// work.
		//
		// If you want to have two EGL contexts -- one for display, one for
		// recording --
		// you will likely want to defer instantiation of CodecInputSurface
		// until after the
		// "display" EGL context is created, then modify the eglCreateContext
		// call to
		// take eglGetCurrentContext() as the share_context argument.
		try {
			mEncoder = MediaCodec.createEncoderByType(_MIME_TYPE);
		} catch (Exception e) {e.printStackTrace();}

		if(mEncoder != null) {
		   mEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
           return mEncoder.createInputSurface();
		} else {
			return null;
		}
	}

	public void startEncoder () {
		if(mEncoder != null) {
		  mEncoder.start();
		}
	}

	/**
	 * Releases encoder resources.
	 */
	public void releaseEncoder() {
		if (mEncoder != null) {
			mEncoder.stop();
			mEncoder.release();
			mEncoder = null;
		}
		if (mOutputStream != null) {
			try {
				mOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mOutputStream = null;
		}
		mH264CodecSpecificData = null;
	}

	/**
	 * Extracts all pending data from the encoder
	 * <p>
	 * If endOfStream is not set, this returns when there is no more data to
	 * drain. If it is set, we send EOS to the encoder, and then iterate until
	 * we see EOS on the output. Calling this with endOfStream set should be
	 * done once, right before stopping the muxer.
	 */
	public void drainEncoder(boolean endOfStream) {
		final int TIMEOUT_USEC = 10000;

		if(mEncoder == null || (mOutputStream == null && mOutputListener == null)) {
		   return;
		}
		if (endOfStream) {
			  mEncoder.signalEndOfInputStream();
		}

		ByteBuffer[] encoderOutputBuffers = mEncoder.getOutputBuffers();
		while (true) {
			int encoderStatus = mEncoder.dequeueOutputBuffer(mBufferInfo,
					TIMEOUT_USEC);
			if (encoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {
				// no output available yet
				if (!endOfStream) {
					trySendVideoKeepalive();
					break; // out of while
				}
			} else if (encoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
				// not expected for an encoder
				encoderOutputBuffers = mEncoder.getOutputBuffers();
			} else if (encoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
				if (mH264CodecSpecificData == null) {
					MediaFormat format = mEncoder.getOutputFormat();
					mH264CodecSpecificData = EncoderUtils.getCodecSpecificData(format);
				} else {
					DebugTool.logWarning("Output format change notified more than once, ignoring.");
				}
			} else if (encoderStatus < 0) {
			} else {
				if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
					// If we already retrieve codec specific data via OUTPUT_FORMAT_CHANGED event,
					// we do not need this data.
					if (mH264CodecSpecificData != null) {
						mBufferInfo.size = 0;
					} else {
						DebugTool.logInfo(TAG, "H264 codec specific data not retrieved yet.");
					}
				}

				if (mBufferInfo.size != 0) {
					ByteBuffer encoderOutputBuffer = encoderOutputBuffers[encoderStatus];
					byte[] dataToWrite = null;
					int dataOffset = 0;

					// append SPS and PPS in front of every IDR NAL unit
					if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_KEY_FRAME) != 0
							&& mH264CodecSpecificData != null) {
						dataToWrite = new byte[mH264CodecSpecificData.length + mBufferInfo.size];
						System.arraycopy(mH264CodecSpecificData, 0,
								dataToWrite, 0, mH264CodecSpecificData.length);
						dataOffset = mH264CodecSpecificData.length;
					} else {
						dataToWrite = new byte[mBufferInfo.size];
					}

					try {
						encoderOutputBuffer.position(mBufferInfo.offset);
						encoderOutputBuffer.limit(mBufferInfo.offset + mBufferInfo.size);

						encoderOutputBuffer.get(dataToWrite, dataOffset, mBufferInfo.size);

                        emitFrame(dataToWrite);
                    } catch (Exception e) {}
				}

				mEncoder.releaseOutputBuffer(encoderStatus, false);

				if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
					break; // out of while
				}
			}
		}
	}

	private void trySendVideoKeepalive() {
		if (mH264CodecSpecificData == null) {
			return;
		}

        try {
		    long timeSinceLastEmitted = System.currentTimeMillis() - mLastEmittedFrameTimestamp;
		    if (timeSinceLastEmitted >= KEEPALIVE_INTERVAL_MSEC) {
				emitFrame(mH264CodecSpecificData);
			}
        } catch (IOException e) {}
	}

    private void emitFrame(final byte[] dataToWrite) throws IOException {
        if (mOutputStream != null) {
            mOutputStream.write(dataToWrite, 0, mBufferInfo.size);
        } else if (mOutputListener != null) {
            mOutputListener.sendFrame(
                    dataToWrite, 0, dataToWrite.length, mBufferInfo.presentationTimeUs);
        }
        mLastEmittedFrameTimestamp = System.currentTimeMillis();
    }
}