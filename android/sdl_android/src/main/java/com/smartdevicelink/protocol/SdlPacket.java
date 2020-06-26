package com.smartdevicelink.protocol;

import android.os.Parcel;
import android.os.Parcelable;

import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;

public class SdlPacket extends BaseSdlPacket implements Parcelable {
    private static final int EXTRA_PARCEL_DATA_LENGTH 			= 24;

    public SdlPacket(int version, boolean encryption, int frameType,
                         int serviceType, int frameInfo, int sessionId,
                         int dataSize, int messageId, byte[] payload) {
        super(version, encryption, frameType, serviceType, frameInfo, sessionId, dataSize, messageId, payload);
    }

    public SdlPacket(int version, boolean encryption, int frameType,
                         int serviceType, int frameInfo, int sessionId,
                         int dataSize, int messageId, byte[] payload, int offset, int bytesToWrite) {
        super(version, encryption, frameType, serviceType, frameInfo, sessionId, dataSize, messageId, payload, offset, bytesToWrite);
    }

    protected  SdlPacket() {
        super();
    }

    protected SdlPacket(BaseSdlPacket packet) {
        super(packet);
    }

    /* ***************************************************************************************************************************************************
     * ***********************************************************  Parceable Overrides  *****************************************************************
     *****************************************************************************************************************************************************/



    //I think this is FIFO...right?
    public SdlPacket(Parcel p) {
        this.version = p.readInt();
        this.encryption = (p.readInt() == 0) ? false : true;
        this.frameType = p.readInt();
        this.serviceType = p.readInt();
        this.frameInfo = p.readInt();
        this.sessionId = p.readInt();
        this.dataSize = p.readInt();
        this.messageId = p.readInt();
        if(p.readInt() == 1){ //We should have a payload attached
            payload = new byte[dataSize];
            p.readByteArray(payload);
        }

        this.priorityCoefficient = p.readInt();

        if(p.dataAvail() > EXTRA_PARCEL_DATA_LENGTH) {	//See note on constant for why not 0
            try {
                messagingVersion = p.readInt();
                if (messagingVersion >= 2) {
                    if (p.readInt() == 1) { //We should have a transport type attached
                        this.transportRecord = (TransportRecord) p.readParcelable(TransportRecord.class.getClassLoader());
                    }
                }
            }catch (RuntimeException e){
                DebugTool.logError(null, "Error creating packet from parcel", e);
            }
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(version);
        dest.writeInt(encryption? 1 : 0);
        dest.writeInt(frameType);
        dest.writeInt(serviceType);
        dest.writeInt(frameInfo);
        dest.writeInt(sessionId);
        dest.writeInt(dataSize);
        dest.writeInt(messageId);
        dest.writeInt(payload!=null? 1 : 0);
        if(payload!=null){
            dest.writeByteArray(payload);
        }
        dest.writeInt(priorityCoefficient);

        ///Additions after initial creation
        if(messagingVersion > 1){
            dest.writeInt(messagingVersion);

            dest.writeInt(transportRecord!=null? 1 : 0);
            if(transportRecord != null){
                dest.writeParcelable(transportRecord,0);
            }
        }

    }

    public static final Parcelable.Creator<SdlPacket> CREATOR = new Parcelable.Creator<SdlPacket>() {
        public SdlPacket createFromParcel(Parcel in) {
            return new SdlPacket(in);
        }

        @Override
        public SdlPacket[] newArray(int size) {
            return new SdlPacket[size];
        }

    };
}
