package com.smartdevicelink.abstraction.permission;

public class SDLPermissionFilter {
	private int hmiNone = 0;
	private int hmiBackground = 0;
	private int hmiFull = 0;
	
	public void addHmiNoneFlags(int flags){
		hmiNone |= flags;
	}
	
	public int getHmiNoneFlags(){
		return hmiNone;
	}
	
	public void addHmiBackgroundFlags(int flags){
		hmiBack
	}
}
