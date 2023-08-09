package com.sw.beauty.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.sw.beauty.PlayActivity;
import com.sw.beauty.R;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ArrayList<View> views = new ArrayList<>();
    private TextView homeTv;
    private TextView myTv;
    private TextView takeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_home);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getInitView()));
        MainHomeManager homeManager = new MainHomeManager(this, views.get(0));
        MyHomeManager myHomeManager = new MyHomeManager(this, views.get(1));
        homeTv = findViewById(R.id.home_page_tv);
        myTv = findViewById(R.id.my_tv);
        takeTv = findViewById(R.id.take_photo_tv);
        homeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeTv.setTextColor(0xdd000000);
                myTv.setTextColor(0x66000000);
                viewPager.setCurrentItem(0, false);
            }
        });
        myTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeTv.setTextColor(0x66000000);
                myTv.setTextColor(0xdd000000);
                viewPager.setCurrentItem(1, false);
            }
        });
        takeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayActivity.start(HomeActivity.this, null);
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