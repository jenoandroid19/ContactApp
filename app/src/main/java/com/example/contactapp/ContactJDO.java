package com.example.contactapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class ContactJDO implements Parcelable {

    private long contactID;

    private String contactName;

    private String contactHomeNumber;
    private String contactWorkNumber;
    private String contactMobileNumber;
    private String contactOtherNumber;

    private String contactHomeEmail;
    private String contactWorkEmail;
    private String contactOtherEmail;

    private String contactHomeAddress;
    private String contactWorkAddress;
    private String contactOtherAddress;

    private String contactPhoto;

    public ContactJDO(long contactID, String contactName, String contactHomeNumber, String contactWorkNumber, String contactMobileNumber, String contactOtherNumber, String contactHomeEmail, String contactWorkEmail, String contactOtherEmail, String contactHomeAddress, String contactWorkAddress, String contactOtherAddress, String contactPhoto) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactHomeNumber = contactHomeNumber;
        this.contactWorkNumber = contactWorkNumber;
        this.contactMobileNumber = contactMobileNumber;
        this.contactOtherNumber = contactOtherNumber;
        this.contactHomeEmail = contactHomeEmail;
        this.contactWorkEmail = contactWorkEmail;
        this.contactOtherEmail = contactOtherEmail;
        this.contactHomeAddress = contactHomeAddress;
        this.contactWorkAddress = contactWorkAddress;
        this.contactOtherAddress = contactOtherAddress;
        this.contactPhoto = contactPhoto;
    }


    protected ContactJDO(Parcel in) {
        contactID = in.readLong();
        contactName = in.readString();
        contactHomeNumber = in.readString();
        contactWorkNumber = in.readString();
        contactMobileNumber = in.readString();
        contactOtherNumber = in.readString();
        contactHomeEmail = in.readString();
        contactWorkEmail = in.readString();
        contactOtherEmail = in.readString();
        contactHomeAddress = in.readString();
        contactWorkAddress = in.readString();
        contactOtherAddress = in.readString();
        contactPhoto = in.readString();
    }

    public static final Creator<ContactJDO> CREATOR = new Creator<ContactJDO>() {
        @Override
        public ContactJDO createFromParcel(Parcel in) {
            return new ContactJDO(in);
        }

        @Override
        public ContactJDO[] newArray(int size) {
            return new ContactJDO[size];
        }
    };

    public String getContactName() {
        return contactName;
    }

    public String getContactHomeNumber() {
        return contactHomeNumber;
    }

    public String getContactWorkNumber() {
        return contactWorkNumber;
    }

    public String getContactMobileNumber() {
        return contactMobileNumber;
    }

    public String getContactOtherNumber() {
        return contactOtherNumber;
    }

    public String getContactHomeEmail() {
        return contactHomeEmail;
    }

    public String getContactWorkEmail() {
        return contactWorkEmail;
    }

    public String getContactOtherEmail() {
        return contactOtherEmail;
    }

    public String getContactHomeAddress() {
        return contactHomeAddress;
    }

    public String getContactWorkAddress() {
        return contactWorkAddress;
    }

    public String getContactOtherAddress() {
        return contactOtherAddress;
    }

    public String getContactPhoto() {
        return contactPhoto;
    }

    public long getContactID() {
        return contactID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(contactID);
        dest.writeString(contactName);
        dest.writeString(contactHomeNumber);
        dest.writeString(contactMobileNumber);
        dest.writeString(contactWorkNumber);
        dest.writeString(contactOtherNumber);
        dest.writeString(contactHomeEmail);
        dest.writeString(contactWorkEmail);
        dest.writeString(contactOtherEmail);
        dest.writeString(contactHomeAddress);
        dest.writeString(contactWorkAddress);
        dest.writeString(contactOtherAddress);
        dest.writeString(contactPhoto);


    }


    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof ContactJDO)) {
            return false;
        }

        ContactJDO user = (ContactJDO) o;

        return user.contactID == contactID
                && user.contactName.equals(contactName)
                && user.contactHomeNumber.equals(contactHomeNumber)
                && user.contactWorkNumber.equals(contactWorkNumber) &&
                user.contactMobileNumber.equals(contactMobileNumber) &&
                user.contactOtherNumber.equals(contactOtherNumber) &&
                user.contactHomeEmail.equals(contactHomeEmail) &&
                user.contactWorkEmail.equals(contactWorkEmail) &&
                user.contactOtherEmail.equals(contactOtherEmail) &&
                user.contactHomeAddress.equals(contactHomeAddress) &&
                user.contactWorkAddress.equals(contactWorkAddress) &&
                user.contactOtherAddress.equals(contactOtherAddress) &&
                user.contactPhoto.equals(contactPhoto);
    }
}
