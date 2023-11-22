package com.example.androidprojectml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    private EditText inputEmail;
    private EditText inputPassword;
    private Button loginBtn;
    private TextView signUpRedirectText;
    DBHelper DB;

/*
    //Validator
    public void validate(){


        //Get values from EditText fields
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();


        boolean emailIsValid= false;
        boolean passwordIsValid= false;

        //Handling validation for email field
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            inputEmail.setError("Please enter a valid email adresse!!");
            inputEmail.requestFocus();
        }else {
            inputEmail.setError(null);
            emailIsValid = true;
        }

        //Handling validation for password filed

        if (password.isEmpty()){
            inputPassword.setError("Please enter a valid password!");
            inputPassword.requestFocus();
        }else{

            if (password.length()>=7){
                inputPassword.setError(null);
                passwordIsValid = true ;
            }else{
                inputPassword.setError("Password must be 7 or more characters!");
                inputPassword.requestFocus();
            }
        }

        if (emailIsValid && passwordIsValid){
            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, HomePage.class));
            finish();
        }

    }

*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword= (EditText) findViewById(R.id.password);


        loginBtn = findViewById(R.id.btn);
        signUpRedirectText = findViewById(R.id.signup);

        DB= new DBHelper(this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                Log.d("email////////",password + "$$$$$");
                if (email.equals("") || password.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{

                    Boolean checkUserPass = DB.checkEmailPassword(email,password);
                    if (checkUserPass==true ) {
                        Toast.makeText(MainActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

        signUpRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });



    }}

 