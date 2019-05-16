package com.example.myapplication.Etc;

import android.os.Parcel;
import android.os.Parcelable;

public class DataObject implements Parcelable {
    public String str;

    protected DataObject(Parcel in) {
        str = in.readString();
    }

    public static final Creator<DataObject> CREATOR = new Creator<DataObject>() {
        @Override
        public DataObject createFromParcel(Parcel in) {
            return new DataObject(in);
        }

        @Override
        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(str);
    }
}
