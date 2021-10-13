package com.example.bookistore;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {
    Fragment f ;
    Button f1,f2,f3,f4;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar = findViewById(R.id.myToolBar);
       // setSupportActionBar(toolbar);

        f1=findViewById(R.id.f1);
        f2=findViewById(R.id.f2);
        f3=findViewById(R.id.f3);
        f4=findViewById(R.id.f4);

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Food_Category());
            }
        });
        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MIE_Category());
            }
        });
        f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Clothing_Category());
            }
        });

    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment1,fragment);
        fragmentTransaction.commit();

    }


    public void Add_Item(View view) {
       /* Intent i = new Intent(MainActivity.this, Add_Category.class);
        startActivity(i);*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        builder.setItems(new CharSequence[]
                        {"طعام", "اثاث", "ملابس", "button 4"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                Intent i = new Intent(MainActivity.this, Add_Category.class);
                                i.putExtra("category","0");
                                startActivity(i);
                                  break;
                            case 1:
                                Intent i1 = new Intent(MainActivity.this, Add_Category.class);
                                i1.putExtra("category","1");
                                startActivity(i1);   break;
                            case 2:
                                Intent i2 = new Intent(MainActivity.this, Add_Category.class);
                                startActivity(i2); break;
                            case 3:
                                Toast.makeText(MainActivity.this, "clicked 4", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
        builder.create().show();

    }
}