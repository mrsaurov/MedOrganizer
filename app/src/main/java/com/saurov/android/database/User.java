package com.saurov.android.database;

import com.orm.SugarRecord;

//User object class
public class User extends SugarRecord<User> {

    private String userName;
    private String email;
    private String password;
    private int isLoggedIn = 0;


    public User(){};

    public User(String userName, String email, String password, int isLoggedIn) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isLoggedIn = isLoggedIn;
    }


    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(int isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
