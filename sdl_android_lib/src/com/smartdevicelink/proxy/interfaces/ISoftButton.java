package com.smartdevicelink.proxy.interfaces;

import java.util.List;

import com.smartdevicelink.proxy.rpc.SoftButton;

public interface ISoftButton {
	public void addSoftButton(SoftButton softButton);
	public List<SoftButton> getSoftButtons();
	public void setSoftButtons(List<SoftButton> softButtons);
}
