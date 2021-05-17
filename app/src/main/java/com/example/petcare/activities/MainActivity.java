package com.example.petcare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.petcare.view_models.MainActivityViewModel;
import com.example.petcare.R;
import com.example.petcare.User;
import com.example.petcare.view_models.SignUpViewModel;
//Log in activity
public class MainActivity extends AppCompatActivity {

    MainActivityViewModel viewModel;

    EditText usernameText;
    EditText passwordText;
    TextView errorText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view elements
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        errorText = findViewById(R.id.errorText);
        progressBar = findViewById(R.id.progressBar);

        //view model and live data
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainActivityViewModel.class);

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                int visibility = isLoading ? View.VISIBLE : View.INVISIBLE;
                progressBar.setVisibility(visibility);
            }
        });
    }

    public void login(View view) {

        try{
            if(usernameText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty())
            {
                errorText.setText("Fill the username and password fields");
                usernameText.setText("");
                passwordText.setText("");
            }
            else
            {
                LiveData<User> user = viewModel.login(usernameText.getText().toString(),passwordText.getText().toString());
                errorText.setText("");
                usernameText.setText("");
                passwordText.setText("");
                Intent intent1 = new Intent(getApplicationContext(), MainActivity3.class);
                intent1.putExtra("current_user",user.getValue());
                startActivity(intent1);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            usernameText.setText("");
            passwordText.setText("");
           errorText.setText(e.getMessage());
        }
    }
    public void goToSignUp(View view){
        Intent intent1 = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent1);
    }
}