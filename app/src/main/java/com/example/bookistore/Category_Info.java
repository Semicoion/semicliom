package com.example.bookistore;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Category_Info extends AppCompatActivity {
    TextView t1,t2,t3;
    FirebaseFirestore fStore;
    DocumentReference dRef;
    StorageReference storageReference;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_info);

        fStore = FirebaseFirestore.getInstance();
        t1=findViewById(R.id.txt1);
        t2=findViewById(R.id.txt2);
        t3=findViewById(R.id.txt3);
        img =findViewById(R.id.imgBook2);
        Intent intent = getIntent();
        String s= intent.getStringExtra("BookId");
        dRef = fStore.collection("Newbook").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                t1.setText(documentSnapshot.getString("name"));
                t2.setText(documentSnapshot.getString("description"));
                t3.setText(documentSnapshot.getString("price"));

              // StorageReference bookReference= FirebaseStorage. getInstance().getReferenceFromUrl("Newbook/"+s+"/mainImage.jpg");

            }
        });
        storageReference= FirebaseStorage.getInstance().getReference();
        if (s != null) {
            StorageReference bookReference = storageReference .child("Newbook/"+s+"/mainImage.jpg");

            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Category_Info.this).load(uri).into(img);
                }
            });
    }
}}