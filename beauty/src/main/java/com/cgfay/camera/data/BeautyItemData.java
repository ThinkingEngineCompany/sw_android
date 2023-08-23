package com.cgfay.camera.data;

import android.content.Context;

import com.cgfay.camera.adapter.PreviewBeautyAdapter;
import com.cgfay.video.R;

import java.util.ArrayList;

public class BeautyItemData {
    public static ArrayList<PreviewBeautyAdapter.BeautyData> getInitData(Context context) {
        ArrayList<PreviewBeautyAdapter.BeautyData> data = new ArrayList<>();
        String[] beautyLists = context.getResources().getStringArray(R.array.preview_beauty_info);
        String[] item0 = context.getResources().getStringArray(R.array.preview_beauty_face);
        String[] item1 = context.getResources().getStringArray(R.array.preview_beauty_chin);
        String[] item2 = context.getResources().getStringArray(R.array.preview_beauty_eye);
        String[] item3 = context.getResources().getStringArray(R.array.preview_beauty_nose);
        String[] item4 = context.getResources().getStringArray(R.array.preview_beauty_lip);
        for (int i = 0; i < beautyLists.length; i++) {
            String con = beautyLists[i];
            PreviewBeautyAdapter.BeautyData e = new PreviewBeautyAdapter.BeautyData();
            e.name = con;
            // 脸型
            if (i == 2) {
                e.type = 1;
                e.items = getItem(item0, con);
            } else if(i == 3){
                e.type = 1;
                e.items = getItem(item1, con);
            } else if (i == 4) {
                e.type = 1;
                e.items = getItem(item2, con);
            } else if (i == 5) {
                e.type = 1;
                e.items = getItem(item3, con);
            } else if (i == 6) {
                e.type = 1;
                e.items = getItem(item4, con);
            }
            data.add(e);
        }
        return data;
    }

    private static ArrayList<PreviewBeautyAdapter.BeautyData> getItem(String[] beautyLists,String title) {
        ArrayList<PreviewBeautyAdapter.BeautyData> data = new ArrayList<>();
        PreviewBeautyAdapter.BeautyData dd = new PreviewBeautyAdapter.BeautyData();
        dd.type = 2;
        dd.name = title;
        dd.iconRes = R.drawable.icon_back;
        data.add(dd);
        for (int i = 0; i < beautyLists.length; i++) {
            String con = beautyLists[i];
            PreviewBeautyAdapter.BeautyData e = new PreviewBeautyAdapter.BeautyData();
            e.name = con;
            e.type = 3;
            data.add(e);
        }
        return data;
    }
}
