package com.example.bookistore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Ctegory_Dialog extends AppCompatDialogFragment {


    public Dialog onCreateDialog(Bundle savedInstanceState) {
      //  AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

       /* LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ctegory_dialog,null);
        builder.setView(view).setTitle("Test Custom Dialog").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Title");
        builder.setItems(new CharSequence[]
                        {"button 1", "button 2", "button 3", "button 4"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                Toast.makeText(getContext(), "clicked 1", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(getContext(), "clicked 2", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(getContext(), "clicked 3", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(getContext(), "clicked 4", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
        builder.create().show();

        return super.onCreateDialog(savedInstanceState);
    }
}