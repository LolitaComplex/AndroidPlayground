package com.doing.androidipc.aidl;

import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable {

    private String name;
    private int age;
    private int type;

    public User(String name, int age, int type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeInt(this.type);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", type=" + type +
                '}';
    }
}
