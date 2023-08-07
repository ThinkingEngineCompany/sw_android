/*
 * Copyright (C) 2020 Baidu, Inc. All Rights Reserved.
 */
package com.sw.beauty.network;


import com.sw.beauty.bean.BaseResponse;

public class NetUtils {
    public static boolean checkRespRaw(BaseResponse baseResponse) {
        if (baseResponse == null || baseResponse.getErrorCode() != 0) {
            return false;
        }
        return true;
    }

    public static boolean checkResp(BaseResponse baseResponse) {
        // 特殊code 401 未登录
        // 黄色警戒 900
        if (baseResponse == null) {
            return false;
        }
        if (baseResponse.getErrorCode() != 0) {
            if (baseResponse.getErrorCode() == 401 ||
                    baseResponse.getErrorCode() == 404) {
                //HLog.i("checkResp 登录已过期, code ->401");
                //LoginPhoneActivity.starActivityWithNewFlag();
            }
            return false;
        }
        return true;
    }
}
