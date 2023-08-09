package com.sw.beauty.home;


import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sw.beauty.my.SceneManageActivity;
import com.sw.beauty.util.ConUtil;

import java.util.ArrayList;

public class MyHomeManager {

    private final HomeActivity act;
    private final HomeViewModel viewModel;
    private final MyHomeAdapter adapter;

    public MyHomeManager(HomeActivity homeActivity, View view) {
        this.act = homeActivity;

        ViewModelProvider viewModelProvider = new ViewModelProvider(act);
        viewModel = viewModelProvider.get(HomeViewModel.class);
        ArrayList<String> ms = new ArrayList<>();
        // 已付费,已使用,已下载
        ms.add("场景管理");
        RecyclerView rv = (RecyclerView) view;

        rv.setPadding(0, ConUtil.dp2px(30), 0, 0);
        rv.setLayoutManager(new LinearLayoutManager(act));
        adapter = new MyHomeAdapter(ms, new MyHomeAdapter.ClickL() {
            @Override
            public void onClick(String s) {
                // 前往场景管理页面
                SceneManageActivity.start(act);
            }
        });
        rv.setAdapter(adapter);

    }
}
