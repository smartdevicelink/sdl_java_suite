package com.smartdevicelink.abstraction.permission;

import java.util.ArrayList;

public class SDLPermissionsManager {

	private static SDLPermissionsManager instance;
	private Object permissionLock = new Object();
	private boolean mVDataAvail;
	private boolean mDrivingCharAvail;
	private boolean mReadDidAvail;
	private boolean mSpeedGpsAvail;
	private ArrayList<SDLPermissionListener> mListeners;

	SDLPermissionsManager(){
		instance = this;
		mListeners = new ArrayList<SDLPermissionListener>();
		mDrivingCharAvail = false;
		mSpeedGpsAvail = false;
		mVDataAvail = false;
		mReadDidAvail = false;
	}

	public void setReadDidAvail(boolean readDidAvail) {
		synchronized (permissionLock) {
			if(readDidAvail != this.mReadDidAvail){
				this.mReadDidAvail = readDidAvail;
				if(mVDataAvail) notifyListeners();
			}
		}
	}

	public void setVehicleDataAvail(boolean vDataAvail) {
		synchronized (permissionLock) {
			if(vDataAvail != this.mVDataAvail){
				this.mVDataAvail = vDataAvail;
				if(mReadDidAvail) notifyListeners();
			}
		}
	}

	public void setSpeedGpsAvail(boolean speedGpsAvail) {
		synchronized (permissionLock) {
			if(speedGpsAvail != this.mSpeedGpsAvail){
				this.mSpeedGpsAvail = speedGpsAvail;
				notifyListeners();
			}
		}
	}

	public void setDrivingCharAvail(boolean drivingCharAvail) {
		synchronized (permissionLock) {
			if(drivingCharAvail != this.mDrivingCharAvail){
				this.mDrivingCharAvail = drivingCharAvail;
				notifyListeners();
			}
		}
	}

	public static SDLPermissionsManager getInstance() {
		return instance;
	}

	public void reset() {
		synchronized (permissionLock) {
			mVDataAvail = false;
			mReadDidAvail = false;
			mSpeedGpsAvail = false;
			mDrivingCharAvail = false;
			notifyListeners();
		}
	}

	public SDLPermissionChangeEvent addPermissionListener(SDLPermissionListener listener) {
		mListeners.add(listener);
		SDLPermissionChangeEvent event = new SDLPermissionChangeEvent(mVDataAvail && mReadDidAvail, 
				mDrivingCharAvail, mSpeedGpsAvail);
		return event;
	}
	
	private void notifyListeners(){
		SDLPermissionChangeEvent event = new SDLPermissionChangeEvent(mVDataAvail && mReadDidAvail, 
				mDrivingCharAvail, mSpeedGpsAvail);
		for(int i = 0; i < mListeners.size(); i++){
			mListeners.get(i).onVHRPermissionChange(event);
		}
	}

	public boolean isVehicleDataAvail() {
		synchronized(permissionLock){
			return mVDataAvail && mReadDidAvail;
		}
	}
	
	public boolean isDrivingCharAvail(){
		synchronized(permissionLock){
			return mDrivingCharAvail;
		}
	}
	
	public boolean isSpeedGpsAvail(){
		synchronized(permissionLock){
			return mSpeedGpsAvail;
		}
	}

}
