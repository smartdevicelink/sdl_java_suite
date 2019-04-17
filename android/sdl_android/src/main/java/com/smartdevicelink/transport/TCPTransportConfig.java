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
package com.smartdevicelink.transport;

import com.smartdevicelink.transport.enums.TransportType;

/**
 * Container of TCP transport specific configuration. 
 */
public final class TCPTransportConfig extends BaseTransportConfig {
	
	/**
	 * Value of port to use in TCP connection.
	 */
	private final int mPort;
	
	/**
	 * Value of IP address to use in TCP connection.
	 */
	private final String mIpAddress;

    /**
     * Value of flag which is set to true if tcp connection must be automatically reestablished in case of disconnection
     */
    private final boolean mAutoReconnect;

    /**
	 * Constructor. Objects of this class must be created for known port and IP address value.
	 * 
	 * @param port Port for TCP connection.
	 * @param ipAddress IP address for TCP connection.
     * @param autoReconnect Flag which must be set to true if tcp connection must be automatically reestablished in
     *                      case of disconnection
	 */
    public TCPTransportConfig(int port, String ipAddress, boolean autoReconnect) {
		mPort = port;
		mIpAddress = ipAddress;
        mAutoReconnect = autoReconnect;
    }
	
	/**
	 * Gets value of Port.
	 * 
	 * @return Port for TCP connection.
	 */
	public int getPort() {
		return mPort;
	}
		
	/**
	 * Gets value of IP address.
	 * 
	 * @return IP address for TCP connection.
	 */
	public String getIPAddress() {
		return mIpAddress;
	}

    /**
     * Gets value of AutoReconnect
     * @return Flag that determines automatic reconnection
     */
    public boolean getAutoReconnect() {
        return mAutoReconnect;
    }

    /**
	 * Overridden abstract method which returns specific type of this transport configuration.
	 * 
	 * @return Constant value TransportType.TCP. 
	 * 
	 * @see TransportType
	 */
	public TransportType getTransportType() {
		return TransportType.TCP;
	}

    @Override
    public String toString() {
        return "TCPTransportConfig{" +
                "Port=" + mPort +
                ", IpAddress='" + mIpAddress + '\'' +
                ", AutoReconnect=" + mAutoReconnect +
                '}';
    }
}
