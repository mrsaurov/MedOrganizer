package com.saurov.android.database;

import com.orm.SugarRecord;

public class User extends SugarRecord<User> {

    String userName;
    String email;
    String password;
    int isLoggedIn = 0;

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
}
