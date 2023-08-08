package com.sw.beauty.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sw.beauty.R;
import com.sw.beauty.util.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class SceneManageActivity extends AppCompatActivity {
    private SceneManAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_manage);
        RecyclerView rv = findViewById(R.id.recycler_view);
        // 获取文件夹中全部文件名称和大小
        // 增加删除操作
        ArrayList<ModelManItem> ms = FileUtils.getAllModelItems();
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SceneManAdapter(ms, new SceneManAdapter.ClickL() {
            @Override
            public void onClick(ModelManItem item) {
                // delete model file
                // 更新recyclerview
                File f = new File(FileUtils.getFilePath(item));
                if (f.exists()) {
                    f.delete();
                }
                ms.remove(item);
                adapter.notifyDataSetChanged();
            }
        });
        rv.setAdapter(adapter);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SceneManageActivity.class);
        context.startActivity(starter);
    }
}