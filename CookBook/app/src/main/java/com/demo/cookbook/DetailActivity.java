package com.demo.cookbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.demo.cookbook.bean.CookDetailBean;
import com.demo.cookbook.ui.shop.ShopBean;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {
    private Long id;
    private ImageView imgCook;
    private TextView tvTitle;
    private RatingBar ratingBar;
    private ImageView imgShop;
    private LinearLayout linearIngredients;
    private LinearLayout linearMethod;
    private LinearLayout linearInfo;

    private Context mContext;

    private CookDetailBean currentValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusIconColorAndActionBar();
        setContentView(R.layout.activity_detail);
        mContext = this;
        id = getIntent().getLongExtra("id", 0);

        imgCook = findViewById(R.id.img_cook);
        tvTitle = findViewById(R.id.tv_title);
        ratingBar = findViewById(R.id.ratingBar);
        imgShop = findViewById(R.id.img_shop);
        linearIngredients = findViewById(R.id.li_ingredients);
        linearMethod = findViewById(R.id.li_method);
        linearInfo = findViewById(R.id.li_info);

        imgShop.setOnClickListener(view -> {
            AlertDialog builder = new AlertDialog.Builder(mContext).setTitle("Warning")
                    .setMessage("Are you sure to add ingredients to your shopping list?")
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        updateData();
                        Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
                    })
                    .setNegativeButton("Cancel", null).show();

            builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.black));
            builder.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.black));
        });
        requestData();
    }

    public void setStatusIconColorAndActionBar() {
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            getWindow().setStatusBarColor(getColor(R.color.white));
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    private void updateData() {
        if (currentValue != null) {
            ShopBean menuBean = new ShopBean();
            menuBean.setName(currentValue.getName());
            menuBean.setImg(currentValue.getImg());
            menuBean.setList(currentValue.getIngredients());
            menuBean.setId(currentValue.getId());
            FirebaseDatabase.getInstance().getReference().child("menu").child(String.valueOf(currentValue.getId())).setValue(menuBean);
        }

    }

    private void requestData() {
        FirebaseDatabase.getInstance()
                .getReference("typeinfolist")
                .orderByChild("id")
                .equalTo(id)
                .limitToFirst(1)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                            CookDetailBean value =  messageSnapshot.getValue(CookDetailBean.class);

                            if (value == null) return;
                            currentValue = value;
                            Glide.with(mContext).load(value.getImg()).placeholder(R.mipmap.ic_launcher).into(imgCook);
                            tvTitle.setText(value.getName());
                            ratingBar.setRating(value.getUserrate());
                            for (String item : value.getIngredients()) {
                                TextView textView = new TextView(mContext);
                                textView.setText(item);
                                linearIngredients.addView(textView);
                            }
                            for (int i = 0; i < value.getMethod().size(); i++) {
                                TextView textView = new TextView(mContext);
                                textView.setText("Step " + (i + 1) + ". " + value.getMethod().get(i) + "\n");
                                linearMethod.addView(textView);
                            }
                            for (String item : value.getNutritional_Information()) {
                                TextView textView = new TextView(mContext);
                                textView.setText(item);
                                linearInfo.addView(textView);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                    }
                });
    }
}
