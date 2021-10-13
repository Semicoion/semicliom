package com.example.bookistore;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_Category extends AppCompatActivity {
    TextView txtF1,txtF2;
    EditText foodName, foodNum;
    Button add;
    FirebaseFirestore fStore;
    CheckBox f1,f2,f3;
    ImageView img;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    ArrayList <Integer> checkArrfood = new ArrayList();
    public Uri mImageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String  categotyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        add=findViewById(R.id.addBook);
        txtF1 =findViewById(R.id.txtf1);
        txtF2=findViewById(R.id.txtf2);
        foodName=findViewById(R.id.foodName);
        foodNum=findViewById(R.id.NumF);
        f1=findViewById(R.id.f1);
        f2=findViewById(R.id.f2);
        f3=findViewById(R.id.f3);
        img=findViewById(R.id.imgBook);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        foodName = findViewById(R.id.foodName);

        Intent i =getIntent();
        /*Bundle extras = getIntent().getExtras();
        if (extras != null){food_category();}*/
            if(i.getStringExtra("category").equals("0")){
                    Toast.makeText(this,i.getStringExtra("category"),Toast.LENGTH_LONG).show();
                    food_category();}

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openFileChooser();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food_category_DB ();
                // Add_books();
                Intent i2 = new Intent(Add_Category.this,MainActivity.class);
                startActivity(i2);
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            img.setImageURI(mImageUri);

        }
    }


    public void food_category (){
    foodName.setVisibility(View.VISIBLE);
    foodNum.setVisibility(View.VISIBLE);
    f1.setVisibility(View.VISIBLE);
    f2.setVisibility(View.VISIBLE);
    f3.setVisibility(View.VISIBLE);
    txtF1.setVisibility(View.VISIBLE);
    txtF2.setVisibility(View.VISIBLE);}
//................................................
public void food_category_DB (){
        fStore = FirebaseFirestore.getInstance();
        categotyId = fStore.collection("food_category").document().getId();
        Map<String,Object> itemsFood = new HashMap<>();
        itemsFood.put("foodId", categotyId);
        itemsFood.put("foodName", foodName.getText().toString());
        itemsFood.put("typeOfFood", checkArrfood);
        itemsFood.put("NumF", foodNum.getText().toString());

        DocumentReference documentReference = fStore.collection("food_category").document(categotyId);
    documentReference.set(itemsFood).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void unused) {
            Toast.makeText(Add_Category.this,"mabrooooooook",Toast.LENGTH_LONG).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull @NotNull Exception e) {
            Toast.makeText(Add_Category.this,"toz 3lekom",Toast.LENGTH_LONG).show();
            Toast.makeText(Add_Category.this,categotyId,Toast.LENGTH_LONG).show();
            Log.d("myTag",e.getMessage());

        }
    });

    checkArrfood.clear();
    }

}