package com.sw.beauty.my;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sw.beauty.R;

import java.util.List;

public class SceneManAdapter extends RecyclerView.Adapter<SceneManAdapter.ViewHolder> {
    private List<ModelManItem> dataList;

    private ClickL c;

    public SceneManAdapter(List<ModelManItem> dataList, ClickL onClickListener) {
        this.dataList = dataList;
        this.c = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_m_item_layout, parent, false);
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
        private TextView descView;
        private TextView deleteView;
        public View allView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allView = itemView;
            titleView = itemView.findViewById(R.id.title_tv);
            descView = itemView.findViewById(R.id.size_tv);
            deleteView = itemView.findViewById(R.id.delete_tv);
        }

        public void bindData(ModelManItem item) {
            titleView.setText(item.getName());
            descView.setText(item.getFileSize());
        }
    }

    public interface ClickL {
        void onClick(ModelManItem item);
    }
}
