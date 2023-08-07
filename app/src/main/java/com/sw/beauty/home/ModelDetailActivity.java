package com.sw.beauty.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sw.beauty.R;
import com.sw.beauty.bean.Model;

public class ModelDetailActivity extends AppCompatActivity {
    public static String EXTRA_MODEL = "extra_model";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_detail);
        DetailManager dm = new DetailManager(this);


    }
    
    public static void start(Context context, Model model) {
        Intent starter = new Intent(context, ModelDetailActivity.class);
        starter.putExtra(EXTRA_MODEL, model);
        context.startActivity(starter);
    }
}