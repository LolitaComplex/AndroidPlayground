package com.dong.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 杜营 on 2016/8/11.
 *
 */
public class User implements Parcelable {
    public User(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    private String name;
    private String sex;

    protected User(Parcel in) {
        name = in.readString();
        sex = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(sex);
    }
}
