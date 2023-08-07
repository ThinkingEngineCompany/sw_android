package com.sw.beauty.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.sw.beauty.bean.ModelResponse;
import com.sw.beauty.bean.PicModelResponse;
import com.sw.beauty.network.NetManager;
import com.sw.beauty.network.NetUtils;
import com.sw.beauty.network.SchedulerProvider;

import java.util.ArrayList;

public class HomeRep {

    private static volatile HomeRep instance;

    private HomeRep() {

    }

    public static HomeRep getInstance() {
        if (instance == null) {
            synchronized (HomeRep.class) {
                if (instance == null) {
                    instance = new HomeRep();
                }
            }
        }
        return instance;
    }

    public void getHomeModels(MutableLiveData<ModelResponse> modelInfo) {
        NetManager.getService().getHomeModels().compose(
                        SchedulerProvider.getInstance().applySchedulers()).
                subscribe(
                        response -> {
                            if (NetUtils.checkResp(response)) {
                                modelInfo.postValue(response);
                            }
                        },
                        throwable -> {
                            //Util.showError(throwable);
                            Log.e("xie", "throw:" + throwable.getMessage());
                        }
                );
    }

    public void getPicModels(MutableLiveData<PicModelResponse> modelInfo, String id) {
        NetManager.getService().getPicModelsById(id).compose(
                        SchedulerProvider.getInstance().applySchedulers()).
                subscribe(
                        response -> {
                            if (NetUtils.checkResp(response)) {
                                modelInfo.postValue(response);
                            }

                        },
                        throwable -> {
                            //Util.showError(throwable);
                            Log.e("xie", "throw:" + throwable.getMessage());
                        }
                );
    }
}
