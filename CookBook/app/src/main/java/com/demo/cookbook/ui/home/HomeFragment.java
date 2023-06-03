package com.demo.cookbook.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.demo.cookbook.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BookRecyclerAdapter bookRecyclerAdapter;
    private List<CookBookBean> cookBookBeanList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.pullLoadMoreRecyclerView.setPullRefreshEnable(false);
        binding.pullLoadMoreRecyclerView.setPushRefreshEnable(false);
        binding.pullLoadMoreRecyclerView.setStaggeredGridLayout(2);

        bookRecyclerAdapter = new BookRecyclerAdapter(getContext(), cookBookBeanList);
        binding.pullLoadMoreRecyclerView.setAdapter(bookRecyclerAdapter);
        requestData();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void requestData() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("category");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("name").getValue();
                    String img = (String) messageSnapshot.child("img").getValue();
                    cookBookBeanList.add(new CookBookBean(img, name));
                }
                if (!cookBookBeanList.isEmpty()) {
                    bookRecyclerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
    }
}