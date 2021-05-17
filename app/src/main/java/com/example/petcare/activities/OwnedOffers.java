package com.example.petcare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.example.petcare.ItemAdapter;
import com.example.petcare.Offer;
import com.example.petcare.R;
import com.example.petcare.User;
import com.example.petcare.activities.MainActivity;
import com.example.petcare.activities.MainActivity2;
import com.example.petcare.activities.MainActivity4;
import com.example.petcare.activities.MainActivity5;
import com.example.petcare.view_models.MainActivity2ViewModel;
import com.example.petcare.view_models.OwnedOffersViewModel;

import java.util.ArrayList;

//activity for displaying user's offers
public class OwnedOffers extends AppCompatActivity implements ItemAdapter.OnListClickListener  {

    RecyclerView rv;
    User currentUser;
    ItemAdapter adapter;
    ArrayList<Offer> offers;
    OwnedOffersViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owned_offers);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(OwnedOffersViewModel.class);

        //setting toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //get current user
        Bundle bundle = getIntent().getExtras();
        currentUser = (User)bundle.getSerializable("current_user");

        //recycle view
        rv = findViewById(R.id.rv_owned);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        rv.hasFixedSize();
        rv.setLayoutManager(layoutManager);

        //loading offers
        offers = new ArrayList<>();
        ArrayList<Offer> allOffers  = viewModel.getOffers();
        for (int i = 0; i < allOffers.size(); i++) {
            if(allOffers.get(i).getOffer_userID()==currentUser.getId()){
                offers.add(allOffers.get(i));
            }
        }
        adapter = new ItemAdapter(offers,this);
        rv.setAdapter(adapter);
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
                Intent intent2 =  new Intent(getApplicationContext(), MainActivity4.class);
                intent2.putExtra("current_user",currentUser);
                startActivity(intent2);
                return true;
            case R.id.action_profile:
                Intent intent1 = new Intent(getApplicationContext(), MainActivity5.class);
                intent1.putExtra("current_user",currentUser);
                startActivity(intent1);
                return true;
            case R.id.action_view:
                Intent intent3 = new Intent(getApplicationContext(), MainActivity2.class);
                intent3.putExtra("current_user",currentUser);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(int clickItemIndex) {
        Intent intent1 = new Intent(getApplicationContext(),MainActivity5.class);
        intent1.putExtra("offer",offers.get(clickItemIndex));
        intent1.putExtra("current_user",currentUser);
        startActivity(intent1);
    }
}