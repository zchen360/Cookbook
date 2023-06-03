package com.demo.cookbook.ui.shop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.cookbook.R;
import com.demo.cookbook.utils.HtmlTextUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShopRecyclerAdapter extends RecyclerView.Adapter<ShopRecyclerAdapter.ViewHolder> {

    private List<ShopBean> mShopBeanList;
    private Context mContext;

    public ShopRecyclerAdapter(Context context, List<ShopBean> shopBeanList) {
        this.mContext = context;
        this.mShopBeanList = shopBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_material, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder != null) {
            Glide.with(mContext).load(mShopBeanList.get(position).getImg()).placeholder(R.mipmap.ic_launcher).into(holder.imgCook);
            holder.tvTitle.setText(mShopBeanList.get(position).getName());
            StringBuilder stringBuilder = new StringBuilder();
            for (String item : mShopBeanList.get(position).getList()) {
                stringBuilder.append(item + "\n");
            }
            holder.tvContent.setText(stringBuilder.toString());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog builder = new AlertDialog.Builder(mContext).setTitle("Warning")
                            .setMessage("Are you sure you want to delete this list?")
                            .setPositiveButton("OK", (dialogInterface, i) -> {
                                DatabaseReference removeRef = FirebaseDatabase.getInstance().getReference("menu").child(String.valueOf(mShopBeanList.get(position).getId()));
                                removeRef.removeValue().addOnSuccessListener((Activity) mContext, new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
                                    }
                                });
                            })
                            .setNegativeButton("Cancel", null).show();

                    builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.black));
                    builder.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.black));
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mShopBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCook;
        private TextView tvTitle;
        private TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);

            imgCook = (ImageView) itemView.findViewById(R.id.img_cook);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}