package com.smartdevicelink.Dispatcher;

import java.util.Comparator;

import com.smartdevicelink.proxy.callbacks.InternalProxyMessage;

public class InternalProxyMessageComparitor implements Comparator<InternalProxyMessage> {

	@Override
	public int compare(InternalProxyMessage arg0, InternalProxyMessage arg1) {
		// Always return 0, turning the priority queue into a FIFO queue. 
		return 0;
	}
}
