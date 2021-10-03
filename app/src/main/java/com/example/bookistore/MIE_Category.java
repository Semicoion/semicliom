package com.example.bookistore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Objects;

public class MIE_Category extends Fragment {
    private RecyclerView mRecyclerView;
    ItemAdabter BookAdapter;
    FirebaseFirestore fStore;
    ListenerRegistration BookListListener;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_m_i_e__category, container, false);
        return  v;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setViews();
        setFirebase();
    }

    private void setViews() {
        mRecyclerView = requireView().findViewById(R.id.recyclerView2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
        setFirebase();
    }

    private void setFirebase() {

        fStore = FirebaseFirestore.getInstance();

        Query query = fStore.collection("Newbook").whereArrayContains("typeOfFragment",2);

        BookListListener = query.addSnapshotListener((documentSnapshots, error) -> {
            BookAdapter = new ItemAdabter(Objects.requireNonNull(documentSnapshots).toObjects(item.class));
            mRecyclerView.setAdapter(BookAdapter);
            BookAdapter.setOnItemClickListener(new ItemAdabter.ClickListener() {
                @Override
                public void onItemClick(int position, View v, List<item> BookItems) {
                    item BItem = BookItems.get(position);
                    if (BItem.getName() != null) {

                        Intent intent = new Intent(getActivity(), Category_Info.class);
                        intent.putExtra("BookId", BItem.getBookId());
                        startActivity(intent);


                    }else
                        Toast.makeText(requireActivity(),"Nooot Dooonee", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onItemLongClick(int position, View v, List<item> productItems) {

                }
            });
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        if (BookListListener!=null)
            BookListListener.remove();
    }}
