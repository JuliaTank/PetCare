package com.example.petcare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.petcare.R;
import com.example.petcare.User;
import com.example.petcare.activities.MainActivity;
import com.example.petcare.activities.MainActivity2;
import com.example.petcare.view_models.MainActivity2ViewModel;
import com.example.petcare.view_models.MainActivity3ViewModel;

//welcoming activity with random cat facts
public class MainActivity3 extends AppCompatActivity {


    TextView helloText;
    ImageButton newButton;
    ImageButton viewButton;
    TextView randomCatFact;

    MainActivity3ViewModel viewModel;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //view elements
        helloText = findViewById(R.id.helloText);
        newButton = findViewById(R.id.NewOfferButton);
        viewButton = findViewById(R.id.viewOffersButton);
        randomCatFact = findViewById(R.id.randomCatText);

        //view model
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainActivity3ViewModel.class);

        viewModel.getFact().observe(this,v->{
            randomCatFact.setText(v.getFact());
        });

        //get current user
        Bundle bundle = getIntent().getExtras();
        currentUser = (User)bundle.getSerializable("current_user");

        helloText.setText("Hello "+currentUser.getUsername()+"!");
    }

    //toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goToActivity1:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add:
                Intent intent2 =  new Intent(getApplicationContext(),MainActivity4.class);
                intent2.putExtra("current_user",currentUser);
                startActivity(intent2);
                return true;
            case R.id.action_profile:
                Intent intent1 = new Intent(getApplicationContext(),OwnedOffers.class);
                intent1.putExtra("current_user",currentUser);
                startActivity(intent1);
                return true;
            case R.id.action_view:
                Intent intent3 = new Intent(getApplicationContext(),MainActivity2.class);
                intent3.putExtra("current_user",currentUser);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //go to viewing offers activity
    public void viewOffers(View view){
        Intent intent1 = new Intent(getApplicationContext(), MainActivity2.class);
        intent1.putExtra("current_user",currentUser);
        startActivity(intent1);
    }
    //go to new offer activity
    public void newOffer(View view){
        Intent intent2 =  new Intent(getApplicationContext(),MainActivity4.class);
        intent2.putExtra("current_user",currentUser);
        startActivity(intent2);
    }

    //display random cat fact
    public void randomFact(View view){
        viewModel.getFact();
    }

}