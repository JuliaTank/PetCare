package com.example.petcare.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petcare.Offer;
import com.example.petcare.R;
import com.example.petcare.User;
import com.example.petcare.view_models.MainActivity4ViewModel;
import com.example.petcare.view_models.MainActivityViewModel;

import java.io.ByteArrayOutputStream;

//activity for creating new offer
public class MainActivity4 extends AppCompatActivity {


    MainActivity4ViewModel viewModel;

    ImageView img;
    ProgressBar progressBar;
    TextView errorText;
    EditText title;
    EditText price;
    EditText description;
    EditText city;
    EditText dateFrom;
    EditText dateTo;

    User currentUser;

    private static  final int IMAGE_PICK = 1000;
    private static  final int PERMISSION =1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        //toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get current user
        Bundle bundle = getIntent().getExtras();
        currentUser = (User)bundle.getSerializable("current_user");

        // view elements
        img = findViewById(R.id.offerPicture);
        progressBar  = findViewById(R.id.progressBarNewOffer);
        errorText = findViewById(R.id.error_text_newOffer);
        title =findViewById(R.id.titleInput);
        price =findViewById(R.id.priceInput);
        description =findViewById(R.id.descriptionInput);
        city =findViewById(R.id.cityInput);
        dateFrom = findViewById(R.id.editTextDate);
        dateTo = findViewById(R.id.editTextDate2);

        //view model
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainActivity4ViewModel.class);

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                int visibility = isLoading ? View.VISIBLE : View.INVISIBLE;
                progressBar.setVisibility(visibility);
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                errorText.setText(error);
            }
        });

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

    //adding picture
    public void addPicture(View view){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
            {
               // permission not granted request it
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show pop up for runtime permission
                requestPermissions(permissions,PERMISSION);
            }
            else {
                //permission already granted
                choosePicture();
            }
        }
        else {
            choosePicture();
        }
    }

    private void choosePicture(){
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK);
    }
    //handle result of the runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePicture();
                } else {
                    //permission denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==IMAGE_PICK){
            //set image view
            img.setImageURI(data.getData());
        }
    }

    private byte[] toByteArray(Drawable drawable){
        BitmapDrawable bitmapDrawable  = (BitmapDrawable)drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        return  stream.toByteArray();
    }


    //saving offer
    public void addOffer(View view){


        if(title.getText().toString().isEmpty() || dateFrom.getText().toString().isEmpty()||dateTo.getText().toString().isEmpty()||city.getText().toString().isEmpty())
        {
            errorText.setText("Title, dates and city fields have to be filled");
        }
        else
        {
            Offer offer =new Offer(description.getText().toString(),toByteArray(img.getDrawable()),title.getText().toString(),Double.parseDouble(price.getText().toString()),dateFrom.getText().toString(),dateTo.getText().toString(),city.getText().toString(),currentUser.getId());
            viewModel.addOffer(offer);
            Intent intent3 = new Intent(getApplicationContext(),MainActivity2.class);
            intent3.putExtra("current_user",currentUser);
            startActivity(intent3);

        }
    }
}