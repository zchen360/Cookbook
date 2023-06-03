package com.demo.cookbook.ui.favor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.demo.cookbook.R;
import com.demo.cookbook.view.PullLoadMoreRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavorFragment extends Fragment {
    private FavorRecyclerAdapter favorRecyclerAdapter;
    private List<FavorCookBean> favorCookBeanList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favor, container, false);
        PullLoadMoreRecyclerView pullLoadMoreRecyclerViewFavor = view.findViewById(R.id.pullLoadMoreRecyclerViewFavor);
        pullLoadMoreRecyclerViewFavor.setPullRefreshEnable(false);
        pullLoadMoreRecyclerViewFavor.setPushRefreshEnable(false);
        pullLoadMoreRecyclerViewFavor.setLinearLayout();

        favorRecyclerAdapter = new FavorRecyclerAdapter(getContext(), favorCookBeanList);
        pullLoadMoreRecyclerViewFavor.setAdapter(favorRecyclerAdapter);
        requestData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void requestData() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("variety");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    FavorCookBean value =  messageSnapshot.getValue(FavorCookBean.class);
                    favorCookBeanList.add(value);
                }
                if (!favorCookBeanList.isEmpty()) {
                    favorRecyclerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
    }
}