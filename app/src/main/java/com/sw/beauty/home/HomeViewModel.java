package com.sw.beauty.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sw.beauty.bean.ModelResponse;
import com.sw.beauty.bean.PicModelResponse;

public class HomeViewModel extends ViewModel {

    protected HomeRep repository;
    private MutableLiveData<ModelResponse> homeM = new MutableLiveData<>();
    private MutableLiveData<PicModelResponse> picM = new MutableLiveData<>();

    public HomeViewModel() {
        this.repository = HomeRep.getInstance();
    }

    public MutableLiveData<ModelResponse> getHomeM() {
        return homeM;
    }

    public MutableLiveData<PicModelResponse> getPicM() {
        return picM;
    }

    public void getHomeModel() {
        repository.getHomeModels(homeM);
    }

    public void getPicModel(String id) {
        repository.getPicModels(picM, id);
    }
}
