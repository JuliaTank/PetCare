package com.example.petcare.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petcare.Offer;
import com.example.petcare.R;
import com.example.petcare.User;
import com.example.petcare.view_models.MainActivity5ViewModel;

//class for displaying single offer details
public class MainActivity5 extends AppCompatActivity {

    User currentUser;
    Offer offer;
    User owner;

    MainActivity5ViewModel viewModel;

    ImageView img;
    TextView title;
    TextView price;
    TextView description;
    TextView dates;
    TextView localization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        //toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //view model
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainActivity5ViewModel.class);

        //get current user and offer
        Bundle bundle = getIntent().getExtras();
        currentUser = (User) bundle.getSerializable("current_user");
        offer = (Offer) bundle.getSerializable("offer");
        owner =  viewModel.getUser(offer.getOffer_userID());

        //view elements
        img = findViewById(R.id.imageView2);
        title= findViewById(R.id.titleTextOffer);
        price = findViewById(R.id.priceTextOffer);
        description = findViewById(R.id.descriptionTextOffer);
        dates = findViewById(R.id.datesTextOffer);
        localization = findViewById(R.id.localizationtextOffer);
        ImageButton deleteButton = findViewById(R.id.deleteButton);
        ImageButton editButton = findViewById(R.id.editButton);

        //setting view elements
        img.setImageBitmap(getImageResource(offer.getPhoto()));
        title.setText(offer.getTitle());
        price.setText(offer.getPrice()+" DKK");
        description.setText(offer.getDescription());
        dates.setText("From: "+offer.getDateFrom()+"    To: "+offer.getDateTo());
        localization.setText(offer.getLocalization());

        //if user is not the owner of displayed offer dont show 'edit' and 'delete' buttons
        if(offer.getOffer_userID()!=currentUser.getId()){
            deleteButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
        }

    }

    private Bitmap getImageResource(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
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
                Intent intent2 = new Intent(getApplicationContext(), MainActivity4.class);
                intent2.putExtra("current_user", currentUser);
                startActivity(intent2);
                return true;
            case R.id.action_profile:
                Intent intent1 = new Intent(getApplicationContext(),OwnedOffers.class);
                intent1.putExtra("current_user", currentUser);
                startActivity(intent1);
                return true;
            case R.id.action_view:
                Intent intent3 = new Intent(getApplicationContext(), MainActivity2.class);
                intent3.putExtra("current_user", currentUser);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //email the owner
    public void contact(View view){
      Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL,new String[] {owner.getEmail()});
            startActivity(intent);
    }
    //call the owner
    public void call(View view){
        if( owner.getPhone_nr()!=null){
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+owner.getPhone_nr()));
                startActivity(intent);

        }else {
            Toast.makeText(getApplicationContext(),"No phone number provided, try email",Toast.LENGTH_SHORT).show();
        }
    }

    //show localization in google maps
    public void findPlace(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q="+offer.getLocalization()));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    //didn't manage to finish it :(
    public void edit(View view){
        /*use 'new offer' view but if offer does exist just update it in database*/
    }

    //delete the offer
    public void delete(View view) {
        viewModel.delete(offer);
        Intent intent3 = new Intent(getApplicationContext(), MainActivity2.class);
        intent3.putExtra("current_user", currentUser);
        startActivity(intent3);
    }
}