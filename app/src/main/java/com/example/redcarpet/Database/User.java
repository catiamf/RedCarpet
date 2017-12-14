package com.example.redcarpet.Database;

/**
 * Created by dioni on 06/12/2017.
 */

public class User {

    public String Nickname;
    public String Gender;
    public String Age;
    public String ShareLocation;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String nickname, String gender, String age, String shareLocation) {
        this.Nickname = nickname;
        this.Gender = gender;
        this.Age = age;
        this.ShareLocation = shareLocation;
    }

}