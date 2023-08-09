package com.sw.beauty.util;

import com.sw.beauty.SApp;
import android.content.res.Resources;

public class ConUtil {

    public static int dp2px(int dpValue) {
        Resources resources = SApp.s.getResources();

// 获取dp值对应的像素密度
        float density = resources.getDisplayMetrics().density;

// 将dp转换为px
        int px = (int) (dpValue * density);
        return px;
    }
}
