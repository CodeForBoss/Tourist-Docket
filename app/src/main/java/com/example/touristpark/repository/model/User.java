package com.example.touristpark.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name,email,phone,password,profileImageUri;

    public User(String name, String email, String phone, String password,String profileImageUri) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profileImageUri = profileImageUri;
    }

    public User(String name,String email) {
        this.email = email;
        this.name = name;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
