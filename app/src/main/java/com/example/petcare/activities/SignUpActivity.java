package com.example.petcare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.petcare.R;
import com.example.petcare.User;
import com.example.petcare.view_models.OwnedOffersViewModel;
import com.example.petcare.view_models.SignUpViewModel;

//sign up activity
public class SignUpActivity extends AppCompatActivity {

    TextView error;
    EditText username;
    EditText password;
    EditText repeatPassword;
    EditText email;
    EditText phone;
    SignUpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //setting view elements
        error = findViewById(R.id.errorSignUp);
        error.setText("");
        username = findViewById(R.id.usernameTextSignUp);
        username.setText("");
        password = findViewById(R.id.passwordTextSignUp);
        password.setText("");
        repeatPassword = findViewById(R.id.repeatPasswordText);
        repeatPassword.setText("");
        email = findViewById(R.id.editTextTextEmailAddress);
        email.setText("");
        phone  =findViewById(R.id.editTextPhone);
        phone.setText("");

        //view model
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(SignUpViewModel.class);

    }

    public void signUp(View view){
        if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty() ||email.getText().toString().isEmpty()){
            error.setText("fill username, password and email fields");
        }
        else if(!password.getText().toString().equals(repeatPassword.getText().toString()))
        {
            error.setText("passwords don't match :(");
        }
        else{
            User user = new User(username.getText().toString(),password.getText().toString(),phone.getText().toString(),email.getText().toString());
            try{
                viewModel.signUp(user);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            catch (Exception e){
             e.printStackTrace();
             error.setText(e.getMessage());
            }
        }
    }
    public void cancel(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}