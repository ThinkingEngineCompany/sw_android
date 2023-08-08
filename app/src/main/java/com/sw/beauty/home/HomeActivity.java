package com.sw.beauty.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.sw.beauty.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ArrayList<View> views = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getInitView()));
        MainHomeManager homeManager = new MainHomeManager(this, views.get(0));
        MyHomeManager myHomeManager = new MyHomeManager(this, views.get(1));
        findViewById(R.id.home_page_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, false);
            }
        });
        findViewById(R.id.my_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1, false);
            }
        });
    }

    private List<View> getInitView() {
        RecyclerView rv = new RecyclerView(this);
        views.add(rv);
        rv = new RecyclerView(this);
        views.add(rv);
        return views;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}