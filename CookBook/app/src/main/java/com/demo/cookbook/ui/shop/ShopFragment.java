package com.demo.cookbook.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.demo.cookbook.bean.CookDetailBean;
import com.demo.cookbook.databinding.FragmentShopBinding;
import com.demo.cookbook.ui.home.CookBookBean;
import com.demo.cookbook.view.PullLoadMoreRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;
    private ShopRecyclerAdapter shopRecyclerAdapter;
    private List<ShopBean> shopBeanList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.pullLoadMoreRecyclerView.setPullRefreshEnable(false);
        binding.pullLoadMoreRecyclerView.setPushRefreshEnable(false);
        binding.pullLoadMoreRecyclerView.setStaggeredGridLayout(2);

        shopRecyclerAdapter = new ShopRecyclerAdapter(getContext(), shopBeanList);
        binding.pullLoadMoreRecyclerView.setAdapter(shopRecyclerAdapter);
        requestData();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void requestData() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("menu");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                shopBeanList.clear();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    ShopBean value =  messageSnapshot.getValue(ShopBean.class);
                    shopBeanList.add(value);
                }
                shopRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
        shopRecyclerAdapter.notifyDataSetChanged();
    }
}