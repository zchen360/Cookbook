package com.demo.cookbook.ui.favor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.cookbook.DetailActivity;
import com.demo.cookbook.R;

import java.util.List;

public class FavorRecyclerAdapter extends RecyclerView.Adapter<FavorRecyclerAdapter.ViewHolder> {

    private List<FavorCookBean> mFavorCookBeanList;
    private Context mContext;

    public FavorRecyclerAdapter(Context context, List<FavorCookBean> favorCookBeanList) {
        this.mContext = context;
        this.mFavorCookBeanList = favorCookBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favor_cook, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder != null && !mFavorCookBeanList.isEmpty()) {
            Glide.with(mContext).load(mFavorCookBeanList.get(position).getImg()).placeholder(R.mipmap.ic_launcher).into(holder.imgCook);
            holder.tvContent.setText(mFavorCookBeanList.get(position).getName());
            holder.tvTips.setText(mFavorCookBeanList.get(position).getTips());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog builder = new AlertDialog.Builder(mContext).setTitle("Warning")
                            .setMessage("Are you sure you want to delete this message?")
                            .setPositiveButton("OK", (dialogInterface, i) -> {
                                mFavorCookBeanList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                                Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
                            })
                            .setNegativeButton("Cancel", null).show();

                    builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.black));
                    builder.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.black));
                    // 删除
                    return false;
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 编辑
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("id", mFavorCookBeanList.get(position).getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFavorCookBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCook;
        private TextView tvContent;
        private TextView tvTips;

        public ViewHolder(View itemView) {
            super(itemView);

            imgCook = (ImageView) itemView.findViewById(R.id.img_cook);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvTips = (TextView) itemView.findViewById(R.id.tv_tips);
        }
    }
}