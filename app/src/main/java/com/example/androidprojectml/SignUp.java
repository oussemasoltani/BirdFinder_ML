package com.example.androidprojectml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    //Declaration
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button signupBtn;
    private TextView loginRedirectText;
    DBHelper DB;


    //method to validate input given by a user !!
/*
    public void checkDataEntered(){
        //fetch data from editText fields!!

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();


        // Variables to check if all inputs are valid or not
        boolean emailIsValid = false;
        boolean passwordIsValid = false;
        boolean confPasswordIsValid = false;

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email address!");
            editTextEmail.requestFocus();
        } else {
            editTextEmail.setError(null);
            emailIsValid = true;
        }


        //Handling validation for Password field
        if (password.isEmpty()) {
            editTextPassword.setError("Please enter a valid password!");
            editTextPassword.requestFocus();
        } else {
            if (password.length() >= 7) {
                editTextPassword.setError(null);
                passwordIsValid = true;
            } else {
                editTextPassword.setError("Password must be 7 or more characters!");
                editTextPassword.requestFocus();
            }
        }

        //Handling validation for Confirm Password field
        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Please confirm the password written above!");
            editTextConfirmPassword.requestFocus();
        } else {
            if(!confirmPassword.equalsIgnoreCase(password)) {
                editTextConfirmPassword.setError("Please make sure your passwords match!");
                editTextConfirmPassword.requestFocus();
            } else {
                editTextConfirmPassword.setError(null);
                confPasswordIsValid = true;
            }
        }

        if (emailIsValid && passwordIsValid && confPasswordIsValid) {
            Toast.makeText(SignUp.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
        }
    }

 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail = (EditText) findViewById(R.id.emailSig);
        editTextPassword = (EditText) findViewById(R.id.passwordSig);
        editTextConfirmPassword= (EditText) findViewById(R.id.repasswordSig);

        signupBtn = findViewById(R.id.btnS);
        loginRedirectText = findViewById(R.id.login);
        DB = new DBHelper(this);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String pass = editTextPassword.getText().toString();
                String repass = editTextConfirmPassword.getText().toString();


                if(email.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(SignUp.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkUser = DB.checkEmail(email);
                        if(checkUser==false){
                            Boolean insert = DB.insertData(email, pass);
                            if(insert==true){
                                Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignUp.this, "User already exists! please log in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUp.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }


}