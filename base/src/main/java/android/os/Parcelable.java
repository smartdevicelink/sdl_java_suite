package android.os;


public interface Parcelable {

    int describeContents();
    void writeToParcel(Parcel dest, int flags);


    abstract class Creator<T>{
        public abstract T[] newArray(int size);
    }

}
