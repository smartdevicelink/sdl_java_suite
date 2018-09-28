package com.smartdevicelink.streaming;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.smartdevicelink.SdlConnection.SdlConnection;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.AbstractProtocol;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.WiProProtocol;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;

public class StreamPacketizer extends AbstractPacketizer implements IVideoStreamListener, IAudioStreamListener, Runnable{

	public final static String TAG = "StreamPacketizer";

	private Thread t = null;


	private final static int TLS_MAX_RECORD_SIZE = 16384;
	private final static int TLS_RECORD_HEADER_SIZE = 5;
	private final static int TLS_RECORD_MES_AUTH_CDE_SIZE = 32;
	private final static int TLS_MAX_RECORD_PADDING_SIZE = 256;


	private final static int BUFF_READ_SIZE = TLS_MAX_RECORD_SIZE - TLS_RECORD_HEADER_SIZE - TLS_RECORD_MES_AUTH_CDE_SIZE - TLS_MAX_RECORD_PADDING_SIZE;

	// Approximate size of data that mOutputQueue can hold in bytes.
	// By adding a buffer, we accept underlying transport being stuck for a short time. By setting
	// a limit of the buffer size, we avoid buffer overflows when underlying transport is too slow.
	private static final int MAX_QUEUE_SIZE = 256 * 1024;

	public SdlConnection sdlConnection = null;	//TODO remove completely
    private Object mPauseLock;
    private boolean mPaused;
    private boolean isServiceProtected = false;
    private BlockingQueue<ByteBuffer> mOutputQueue;

	public StreamPacketizer(IStreamListener streamListener, InputStream is, SessionType sType, byte rpcSessionID, SdlSession session) throws IOException {
		super(streamListener, is, sType, rpcSessionID, session);
        mPauseLock = new Object();
        mPaused = false;
        isServiceProtected = _session.isServiceProtected(_serviceType);
		if (bufferSize == 0) {
			// fail safe
			bufferSize = BUFF_READ_SIZE;
		}
		if(isServiceProtected){ //If our service is encrypted we can only use 1024 as the max buffer size. 
			bufferSize = BUFF_READ_SIZE;
			buffer = new byte[bufferSize];
		}
		mOutputQueue = new LinkedBlockingQueue<ByteBuffer>(MAX_QUEUE_SIZE / bufferSize);
	}

	public void start() throws IOException {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void stop() {

		if (t != null)
		{
			t.interrupt();
			t = null;
		}

	}

	public void run() {
		int length;
		try 
		{
			while (t != null && !t.isInterrupted()) 
			{
				synchronized(mPauseLock)
				{
					while (mPaused)
                    {
						try
                        {
							mPauseLock.wait();
                        }
                        catch (InterruptedException e) {}
                    }
                }

				if (is != null) { // using InputStream interface
					length = is.read(buffer, 0, bufferSize);

					if (length >= 0) {
						ProtocolMessage pm = new ProtocolMessage();
						pm.setSessionID(_rpcSessionID);
						pm.setSessionType(_serviceType);
						pm.setFunctionID(0);
						pm.setCorrID(0);
						pm.setData(buffer, length);
						pm.setPayloadProtected(isServiceProtected);

						if (t != null && !t.isInterrupted()) {
							_streamListener.sendStreamPacket(pm);
						}
					}
				} else { // using sendFrame interface
					ByteBuffer frame;
					try {
						frame = mOutputQueue.take();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}

					while (frame.hasRemaining()) {
						int len = frame.remaining() > bufferSize ? bufferSize : frame.remaining();

						ProtocolMessage pm = new ProtocolMessage();
						pm.setSessionID(_rpcSessionID);
						pm.setSessionType(_serviceType);
						pm.setFunctionID(0);
						pm.setCorrID(0);
						pm.setData(frame.array(), frame.arrayOffset() + frame.position(), len);
						pm.setPayloadProtected(isServiceProtected);

						if (t != null && !t.isInterrupted()) {
							_streamListener.sendStreamPacket(pm);
						}

						frame.position(frame.position() + len);
					}
				}
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(_session == null) {
				if (sdlConnection != null) {
					sdlConnection.endService(_serviceType, _rpcSessionID);
				}
			}else{
				_session.endService(_serviceType,_rpcSessionID);
			}


		}
	}

    @Override
	public void pause() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    @Override
    public void resume() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }

	/**
	 * Called by the app.
	 *
	 * @see com.smartdevicelink.proxy.interfaces.IVideoStreamListener#sendFrame(byte[], int, int, long)
	 */
	@Override
	public void sendFrame(byte[] data, int offset, int length, long presentationTimeUs)
			throws ArrayIndexOutOfBoundsException {
		sendArrayData(data, offset, length);
	}

	/**
	 * Called by the app.
	 *
	 * @see com.smartdevicelink.proxy.interfaces.IVideoStreamListener#sendFrame(ByteBuffer, long)
	 */
	@Override
	public void sendFrame(ByteBuffer data, long presentationTimeUs) {
		sendByteBufferData(data);
	}

	/**
	 * Called by the app.
	 *
	 * @see com.smartdevicelink.proxy.interfaces.IAudioStreamListener#sendAudio(byte[], int, int, long)
	 */
	@Override
	public void sendAudio(byte[] data, int offset, int length, long presentationTimeUs)
			throws ArrayIndexOutOfBoundsException {
		sendArrayData(data, offset, length);
	}

	/**
	 * Called by the app.
	 *
	 * @see com.smartdevicelink.proxy.interfaces.IAudioStreamListener#sendAudio(ByteBuffer, long)
	 */
	@Override
	public void sendAudio(ByteBuffer data, long presentationTimeUs) {
		sendByteBufferData(data);
	}

	private void sendArrayData(byte[] data, int offset, int length)
			throws ArrayIndexOutOfBoundsException {
		if (offset < 0 || offset > data.length || length <= 0 || offset + length > data.length) {
			throw new ArrayIndexOutOfBoundsException();
		}

		// StreamPacketizer does not need to split a video frame into NAL units
		ByteBuffer buffer = ByteBuffer.allocate(length);
		buffer.put(data, offset, length);
		buffer.flip();

		try {
			mOutputQueue.put(buffer);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void sendByteBufferData(ByteBuffer data) {
		if (data == null || data.remaining() == 0) {
			return;
		}

		// copy the whole buffer, so that even if the app modifies original ByteBuffer after
		// sendFrame() or sendAudio() call, our buffer will stay intact
		ByteBuffer buffer = ByteBuffer.allocate(data.remaining());
		buffer.put(data);
		buffer.flip();

		try {
			mOutputQueue.put(buffer);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
