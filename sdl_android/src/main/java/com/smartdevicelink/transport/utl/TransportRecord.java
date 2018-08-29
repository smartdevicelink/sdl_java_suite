package com.smartdevicelink.transport.utl;

import android.os.Parcel;
import android.os.Parcelable;

import com.smartdevicelink.transport.enums.TransportType;

public class TransportRecord implements Parcelable{

    TransportType type;
    String address;

    public TransportRecord(TransportType transportType, String address){
        this.type = transportType;
        this.address = address;
    }

    public TransportType getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj !=null && obj instanceof TransportRecord){
            TransportRecord record = (TransportRecord)obj;
            return record.type != null && record.type.equals(type)  //Transport type is the same
                    && ((record.address == null && address == null) //Both addresses are null
                    || (record.address != null && record.address.equals(address))); //Or they match
        }

        return super.equals(obj);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Transport Type: ");
        builder.append(type.name());
        builder.append(" Address: ");
        builder.append(address);
        return builder.toString();
    }

    public TransportRecord(Parcel p){
        if (p.readInt() == 1) { //We should have a transport type attached
            String transportName = p.readString();
            if(transportName != null){
                this.type = TransportType.valueOf(transportName);
            }
        }

        if (p.readInt() == 1) { //We should have a transport address attached
            address = p.readString();
        }
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
            return new TransportRecord(in);
        }

        @Override
        public TransportRecord[] newArray(int size) {
            return new TransportRecord[size];
        }

    };
}
