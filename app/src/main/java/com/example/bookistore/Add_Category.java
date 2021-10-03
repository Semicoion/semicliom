package com.example.bookistore;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_Category extends AppCompatActivity {
    String bookId ;
    EditText nameB , desB , prB;
    Button add;
    FirebaseFirestore fStore;
    CheckBox c1,c2,c3;
    ImageView img;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage,mButtonUpload;
    ArrayList <Integer> checkArr = new ArrayList();
    public Uri mImageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
    private EditText mEditTextFileName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        main();

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // uploadImg();
               /* if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(AddBooks.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                  //  uploadFile();
                }*/
            }
        });

        //..................................................................................




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Add_books();
                Intent i = new Intent(Add_Category.this,MainActivity.class);
                startActivity(i);
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

           // Picasso.get().load(mImageUri).into(img);
        }
    }




    public void Add_books() {

        fStore = FirebaseFirestore.getInstance();

        if(c1.isChecked())
            checkArr.add(1);
        if(c2.isChecked())
            checkArr.add(2);
        if(c3.isChecked())
            checkArr.add(3);
        bookId = fStore.collection("Newbook").document().getId();
        uploadImg(mImageUri);
        Map<String,Object> itemsB = new HashMap<>();
        itemsB.put("description", desB.getText().toString());
        itemsB.put("name",nameB.getText().toString());
        itemsB.put("typeOfFragment",checkArr);
        itemsB.put("bookId",bookId);
        itemsB.put("price",prB.getText().toString());

        DocumentReference documentReference = fStore.collection("Newbook").document(bookId);
        documentReference.set(itemsB).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Add_Category.this,"mabrooooooook",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Add_Category.this,"toz 3lekom",Toast.LENGTH_LONG).show();
                Toast.makeText(Add_Category.this,bookId,Toast.LENGTH_LONG).show();
                Log.d("myTag",e.getMessage());

            }
        });

        checkArr.clear();

    }
    private void uploadImg(Uri uri){

        final ProgressDialog pd=new ProgressDialog((this));
        pd.setTitle("Uploading Image ...");
        pd.show();


        //final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("Newbook/"+bookId+"/mainImage.jpg");
        riversRef.putFile(mImageUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Failed To Upload",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content),"Image Uploded",Snackbar.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent =(100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentage:"+(int) progressPercent + "%");
            }
         });


    }
    //...................................................
   /* private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    ProgressDialog progressDialog ;

    private void uploadFile() {
        progressDialog = new ProgressDialog(this);

        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            Toast.makeText(AddBooks.this, "Upload successful", Toast.LENGTH_LONG).show();
                            item i = new item(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl()   );
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(i);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddBooks.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                   .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }*/
    private void main(){
        nameB=findViewById(R.id.nameOfBook);
        desB=findViewById(R.id.desOfBook);
        add=findViewById(R.id.addBook);
        prB=findViewById(R.id.priceOfBook);
        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        img=findViewById(R.id.imgBook);
        mButtonUpload = findViewById(R.id.button_upload);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mProgressBar = findViewById(R.id.progress_bar);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Booki");

    }
}