package com.smartdevicelink.protocol;
//Seems silly, but....welcome to Android
//We need to do this so the compiler knows that the SdlPacket.java class ia actually a parceable object and how to handle it
// when dealing with other interface (aidl) files as well as being able to send them through intents
parcelable SdlPacket;