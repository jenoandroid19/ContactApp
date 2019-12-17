package com.example.contactapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    public void addContact(Contact contact);

    @Insert
    public void addContacts(List<Contact> contactList);

    @Query("select * from contacts ORDER BY contact_name ASC")
    public List<Contact> getContacts();

    @Delete
    public void deleteContact(Contact contact);

    @Update
    public void updateContact(Contact contact);
}
