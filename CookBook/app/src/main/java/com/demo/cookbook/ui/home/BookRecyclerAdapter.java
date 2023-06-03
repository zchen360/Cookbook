package com.demo.cookbook.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.cookbook.R;

import java.util.List;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder> {

    private List<CookBookBean> mCookBookBeanList;
    private Context mContext;

    public BookRecyclerAdapter(Context context, List<CookBookBean> cookBookBeanList) {
        this.mContext = context;
        this.mCookBookBeanList = cookBookBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cook_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder != null) {
            Glide.with(mContext).load(mCookBookBeanList.get(position).getImgUrl()).placeholder(R.mipmap.ic_launcher).into(holder.imgCook);
            holder.tvContent.setText(mCookBookBeanList.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mCookBookBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCook;
        private TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);

            imgCook = (ImageView) itemView.findViewById(R.id.img_cook);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}