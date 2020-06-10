package com.smartdevicelink.transport.utl;

import android.os.Parcel;
import android.os.Parcelable;

import com.smartdevicelink.transport.enums.TransportType;

public class TransportRecord extends BaseTransportRecord implements Parcelable {

    private TransportType type;
    private String address;

    public TransportRecord(TransportType transportType, String address) {
        super(transportType, address);
        this.type = transportType;
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type!=null? 1 : 0);
        if(type != null){
            dest.writeString(type.name());
        }

        dest.writeInt(address !=null? 1 : 0);
        if(address != null){
            dest.writeString(address);
        }
    }

    public static final Parcelable.Creator<TransportRecord> CREATOR = new Parcelable.Creator<TransportRecord>() {
        public TransportRecord createFromParcel(Parcel in) {
            TransportType type = null;
            String address = "";
            if (in.readInt() == 1) { //We should have a transport type attached
                String transportName = in.readString();
                if(transportName != null){
                    type = TransportType.valueOf(transportName);
                }
            }

            if (in.readInt() == 1) { //We should have a transport address attached
                address = in.readString();
            }
            return new TransportRecord(type, address);
        }

        @Override
        public TransportRecord[] newArray(int size) {
            return new TransportRecord[size];
        }

    };
}
