package com.example.redcarpet.Database;

/**
 * Created by dioni on 06/12/2017.
 */

public class Party {

    public String Description;
    public String Address;

    public Party() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Party(String description, String address) {
        this.Description = description;
        this.Address = address;
    }

}
