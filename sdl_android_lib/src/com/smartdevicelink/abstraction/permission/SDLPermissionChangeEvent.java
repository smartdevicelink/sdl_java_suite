package com.smartdevicelink.abstraction.permission;

public class SDLPermissionChangeEvent {
	final boolean mVehicleInfo;
	final boolean mDrivingChar;
	final boolean mSpeedAndGPS;
	final boolean mPermissionsAvailable;
	
	public SDLPermissionChangeEvent(boolean vehicleInfo, boolean drivingChar, 
			boolean speedGps){
		mVehicleInfo = vehicleInfo;
		mDrivingChar = drivingChar;
		mSpeedAndGPS = speedGps;
		mPermissionsAvailable = mVehicleInfo && (mDrivingChar || mSpeedAndGPS);
	}
	
	public boolean getDrivingCharacteristicsAvailable(){
		return mDrivingChar;
	}
	
	public boolean getPermissionsAvailable(){
		return mPermissionsAvailable;
	}

}
