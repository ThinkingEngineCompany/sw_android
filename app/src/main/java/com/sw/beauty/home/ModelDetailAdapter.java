package com.sw.beauty.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sw.beauty.R;
import com.sw.beauty.bean.Model;
import com.sw.beauty.bean.PicVideoModel;

import java.util.List;

public class ModelDetailAdapter extends RecyclerView.Adapter<ModelDetailAdapter.ViewHolder> {
    private List<PicVideoModel> dataList;

    private ClickL c;

    public ModelDetailAdapter(List<PicVideoModel> dataList, ClickL onClickListener) {
        this.dataList = dataList;
        this.c = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_detail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int p = position;
        holder.bindData(dataList.get(position));
        holder.allView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.onClick(dataList.get(p).getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        public View allView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allView = itemView;
            img = itemView.findViewById(R.id.show_img_iv);
        }

        public void bindData(PicVideoModel data) {
            Glide.with(img).load(data.getUrl()).into(img);
        }
    }

    public interface ClickL {
        void onClick(String model);
    }
}
