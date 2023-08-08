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

import java.util.List;

public class MyHomeAdapter extends RecyclerView.Adapter<MyHomeAdapter.ViewHolder> {
    private List<String> dataList;

    private ClickL c;

    public MyHomeAdapter(List<String> dataList, ClickL onClickListener) {
        this.dataList = dataList;
        this.c = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int p = position;
        holder.bindData(dataList.get(position));
        holder.allView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.onClick(dataList.get(p));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        public View allView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allView = itemView;
            titleView = itemView.findViewById(R.id.title_tv);

        }

        public void bindData(String text) {
            titleView.setText(text);
        }
    }

    public interface ClickL {
        void onClick(String text);
    }
}
