package com.saurov.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.saurov.android.R;
import com.saurov.android.database.User;

import java.util.Iterator;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends Activity {

    private EditText emailId;
    private EditText passwordId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        emailId = (EditText) findViewById(R.id.emailLogInEditText);
        passwordId  = (EditText) findViewById(R.id.passwordLoginEditText);

        try
        {
            for (Iterator<User> iter = User.findAll(User.class); iter.hasNext(); ) {
                User element = iter.next();

                if(element.getIsLoggedIn()==1){

                    Intent i = new Intent(this,MainActivity.class);

                    startActivity(i);

                    break;
                }
            }

        }
        catch (Exception e)
        {
            Intent i = new Intent(Login.this, SignUp.class);
            startActivity(i);
        }
    }

    @OnClick(R.id.toSignUp)
    public void ontoSignUpClick(){

        //if(emailId.toString().trim().isEmpty()){

            //Toast.makeText(getApplicationContext(),"Invalid Input!",Toast.LENGTH_LONG).show();
        //}
        //else {

            Intent i = new Intent(this,SignUp.class);

            startActivity(i);
        //}
    }

    @OnClick(R.id.logInButton)
    public void onLogInClick(){

        boolean logInFound = false;

        Intent i = new Intent(this,MainActivity.class);

        //Checking if the log in data is correct
        try {
            for(Iterator<User> iter = User.findAll(User.class); iter.hasNext();){

                User user = iter.next();

                if(emailId.getText().toString().equals(user.getEmail())
                        && passwordId.getText().toString().equals(user.getPassword())){
                    logInFound = true;
                    user.setIsLoggedIn(1);
                    user.save();

                    startActivity(i);

                    break;

                }
            }
        } catch (Exception e) {

            Toast.makeText(this,"Invalid Username or Password", Toast.LENGTH_LONG).show();
        }

        //LogIn credential didn't match
        if(!logInFound)
            Toast.makeText(this,"Invalid Username or Password", Toast.LENGTH_LONG).show();
    }
}
