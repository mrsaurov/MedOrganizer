package com.saurov.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.User;
import com.saurov.android.helpers.MySharedPreference;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUp extends Activity {

    private EditText userName;
    private EditText emailId;
    private EditText passwordId;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        emailId = (EditText) findViewById(R.id.emailSignUpEditText);
        passwordId  = (EditText) findViewById(R.id.passwordSignUpEditText);
        userName = (EditText) findViewById(R.id.userNameEditText);

    }

    @OnClick(R.id.signUpButton)
    public void onSignUpClick(){

        Intent i = new Intent(this,MainActivity.class);

        if(emailId.getText().toString().trim().isEmpty() || passwordId.getText().toString().trim().isEmpty()
                || userName.getText().toString().trim().isEmpty()){

            Toast.makeText(getApplicationContext(),"Invalid Input!",Toast.LENGTH_LONG).show();
        }
        else {

            addUser();

            MySharedPreference mySharedPreference = new MySharedPreference(this, user.getId());

            //i.putExtra(Login.ARG_USER_ID,user.getId());

            startActivity(i);

            finish();
        }
    }

    @OnClick(R.id.toSignIn)
    public void toSignInOnClick(){

        Intent i = new Intent(getApplicationContext(),Login.class);

        startActivity(i);
    }

    public void addUser(){

        String emailAddUser = emailId.getText().toString();
        String password = passwordId.getText().toString();
        String userNameAdd = userName.getText().toString();

        user = new User(userNameAdd,emailAddUser,password,1);

        user.save();

        Toast.makeText(getApplicationContext(), "User Saved", Toast.LENGTH_LONG).show();
    }
}
