package com.example.bookistore;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ItemAdabter extends RecyclerView.Adapter<ItemAdabter.BookHolder> {
    List<item> BookItm;
    private Context mContext;
    private static ItemAdabter.ClickListener clickListener;
    FirebaseFirestore fStore;
    StorageReference storageReference;


    public ItemAdabter(List<item> BItems) {
        this.BookItm= BItems;

        fStore = FirebaseFirestore.getInstance();
    }

    @Override
    public int getItemCount() {
        return BookItm.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        storageReference=FirebaseStorage.getInstance().getReference();

        return new BookHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        item itm = BookItm.get(position);
        String BookId = itm.getBookId();

        if (BookId != null) {
            StorageReference bookReference = storageReference.child("Newbook/"+BookId+"/mainImage.jpg");
            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(mContext).load(uri).into(holder.img);
                }
            });
           /* try {
                //Task<Uri> l= niceLink("Newbook/"+BookId+"/mainImage.jpg");
                Glide.with(mContext).load(sstorageReference).into(img);
                Toast.makeText(mContext,sstorageReference+"",Toast.LENGTH_LONG).show();
            } catch (Exception e)
            {
                Log.d("Tag",e+"");
            }*/
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Newbook").document(BookId);
            documentReference.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    holder.tname.setText(itm.getName());
                    holder.tpr.setText(itm.getPrice());

                } else {
                    holder.tname.setText("no name");
                    holder.tpr.setText("no des");
                }
            });
        }

        else {
            holder.tname.setText("no name");
            holder.tpr.setText("no des");
        }


    }
    public class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tname, tpr;
        ImageView img;


        public BookHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            img = itemView.findViewById(R.id.imgItem);
            tname = itemView.findViewById(R.id.itemName);
            tpr = itemView.findViewById(R.id.itemPr);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v, BookItm);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v, BookItm);
            return false;
        }
    }

    public static void setOnItemClickListener(ItemAdabter.ClickListener clickListener) {
        ItemAdabter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v, List<item> BookItems);

        void onItemLongClick(int position, View v, List<item> BookItems);
    }
}