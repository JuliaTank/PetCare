package com.example.petcare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.petcare.ItemAdapter;
import com.example.petcare.Offer;
import com.example.petcare.R;
import com.example.petcare.User;
import com.example.petcare.view_models.MainActivity2ViewModel;
import com.example.petcare.view_models.MainActivity4ViewModel;
import com.example.petcare.view_models.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//display all offers activity with recycle view and search
public class MainActivity2 extends AppCompatActivity implements ItemAdapter.OnListClickListener {


    RecyclerView offersList;
    ItemAdapter adapter;
    User currentUser;
    MainActivity2ViewModel viewModel;
    ProgressBar progressBar;
    ArrayList<Offer> offers;
    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //set current user
        Bundle bundle = getIntent().getExtras();
        currentUser = (User)bundle.getSerializable("current_user");

        //view elements
        progressBar = findViewById(R.id.progressBar_offers);
        searchText = findViewById(R.id.searchInput);

        //recycle view
        offersList = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        offersList.hasFixedSize();
        offersList.setLayoutManager(layoutManager);

        //view model and live data
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainActivity2ViewModel.class);

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                int visibility = isLoading ? View.VISIBLE : View.INVISIBLE;
                progressBar.setVisibility(visibility);
            }
        });

        //loading offers
        offers = viewModel.getOffers();

        adapter = new ItemAdapter(offers,this);
        offersList.setAdapter(adapter);

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

    @Override
    public void onListItemClick(int clickItemIndex) {
        Intent intent1 = new Intent(getApplicationContext(),MainActivity5.class);
        intent1.putExtra("offer",offers.get(clickItemIndex));
        intent1.putExtra("current_user",currentUser);
        startActivity(intent1);
    }

    public void search(View view) {
        if(!searchText.getText().toString().isEmpty()){
            try{ offers = viewModel.getOffers(searchText.getText().toString());}
            catch (Exception e){
             e.printStackTrace();
            }
            adapter = new ItemAdapter(offers,this);
            offersList.setAdapter(adapter);
        }
    }
}