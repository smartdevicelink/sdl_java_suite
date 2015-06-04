package com.smartdevicelink.streaming;

import java.io.IOException;
import java.io.InputStream;

import com.smartdevicelink.connection.SdlConnection;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.streaming.interfaces.IStreamListener;

public class StreamPacketizer extends AbstractPacketizer implements Runnable{

	public final static String TAG = "StreamPacketizer";

	private Thread t = null;
	private final static int BUFF_READ_SIZE = 1000000;

	public SdlConnection sdlConnection = null;
    private Object mPauseLock;
    private boolean mPaused;

	public StreamPacketizer(IStreamListener streamListener, InputStream is, SessionType sType, byte rpcSessionId) throws IOException {
		super(streamListener, is, sType, rpcSessionId);
        mPauseLock = new Object();
        mPaused = false;
	}

	public void start() throws IOException {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void stop() {
		try {
			is.close();
		} catch (IOException ignore) {}
		t.interrupt();
		t = null;
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

				length = is.read(buffer, 0, BUFF_READ_SIZE);
				
				if (length >= 0) {
					ProtocolMessage pm = new ProtocolMessage();
					pm.setSessionId(rpcSessionId);
					pm.setSessionType(session);
					pm.setFunctionId(0);
					pm.setCorrId(0);
					pm.setData(buffer, length);
					
			        streamListener.sendStreamPacket(pm);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
}
