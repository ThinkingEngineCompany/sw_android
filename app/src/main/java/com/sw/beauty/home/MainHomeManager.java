package com.sw.beauty.home;

import android.annotation.SuppressLint;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sw.beauty.R;
import com.sw.beauty.bean.Model;
import com.sw.beauty.bean.ModelResponse;

import java.util.ArrayList;

public class MainHomeManager {

    private final HomeActivity act;
    private final HomeViewModel viewModel;
    private final HomeAdapter adapter;

    public MainHomeManager(HomeActivity homeActivity) {
        this.act = homeActivity;

        ViewModelProvider viewModelProvider = new ViewModelProvider(act);
        viewModel = viewModelProvider.get(HomeViewModel.class);
        ArrayList<Model> ms = new ArrayList<>();
        RecyclerView rv = act.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(act));
        adapter = new HomeAdapter(ms, new HomeAdapter.ClickL() {
            @Override
            public void onClick(Model model) {
                ModelDetailActivity.start(act, model);
            }
        });
        rv.setAdapter(adapter);

        viewModel.getHomeM().observe(act, new Observer<ModelResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ModelResponse modelResponse) {
                ms.addAll(modelResponse.getData());
                adapter.notifyDataSetChanged();
            }
        });


        viewModel.getHomeModel();

    }
}
