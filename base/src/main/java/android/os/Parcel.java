package android.os;

public class Parcel {

    public void writeInt(int data){

    }

    public void writeByteArray(byte[] bytes){

    }

    public void writeString(String data){

    }

    public void writeParcelable(Parcelable p, int flags){

    }

    public int readInt(){
        return 0;
    }

    public String readString(){
        return "hello";
    }

    public byte[] readByteArray(byte[] array){
        return array;
    }

    public Parcelable readParcelable(ClassLoader loader){
        return  null;
    }

    public int dataAvail(){
        return 0;
    }
}
