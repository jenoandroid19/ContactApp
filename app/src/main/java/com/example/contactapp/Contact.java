package com.example.contactapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "contacts")

public class Contact {


    @PrimaryKey(autoGenerate = false)
    private long contactID;

    @ColumnInfo(name = "contact_name")
    private String name;

    @ColumnInfo(name = "home_number")
    private String homeNumber;
    @ColumnInfo(name = "mobile_number")
    private String mobileNumber;
    @ColumnInfo(name = "work_number")
    private String workNumber;
    @ColumnInfo(name = "other_number")
    private String otherNumber;

    @ColumnInfo(name = "home_email")
    private String homeEmail;
    @ColumnInfo(name = "work_email")
    private String workEmail;
    @ColumnInfo(name = "other_email")
    private String otherEmail;

    @ColumnInfo(name = "home_address")
    private String homeAddress;
    @ColumnInfo(name = "work_address")
    private String workAddress;
    @ColumnInfo(name = "other_address")
    private String otherAddress;


    @ColumnInfo(name = "contact_image")
    private String imageSource;

    public Contact(long contactID, String name, String homeNumber, String mobileNumber, String workNumber, String otherNumber, String homeEmail, String workEmail, String otherEmail, String homeAddress, String workAddress, String otherAddress, String imageSource) {
        this.contactID = contactID;
        this.name = name;
        this.homeNumber = homeNumber;
        this.mobileNumber = mobileNumber;
        this.workNumber = workNumber;
        this.otherNumber = otherNumber;
        this.homeEmail = homeEmail;
        this.workEmail = workEmail;
        this.otherEmail = otherEmail;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
        this.otherAddress = otherAddress;
        this.imageSource = imageSource;
    }

    public Contact() {

    }

    public long getContactID() {
        return contactID;
    }

    public void setContactID(long contactID) {
        this.contactID = contactID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getOtherNumber() {
        return otherNumber;
    }

    public void setOtherNumber(String otherNumber) {
        this.otherNumber = otherNumber;
    }

    public String getHomeEmail() {
        return homeEmail;
    }

    public void setHomeEmail(String homeEmail) {
        this.homeEmail = homeEmail;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getOtherEmail() {
        return otherEmail;
    }

    public void setOtherEmail(String otherEmail) {
        this.otherEmail = otherEmail;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getOtherAddress() {
        return otherAddress;
    }

    public void setOtherAddress(String otherAddress) {
        this.otherAddress = otherAddress;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
}
