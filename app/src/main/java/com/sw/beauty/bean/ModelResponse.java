package com.sw.beauty.bean;

import java.util.List;

public class ModelResponse extends BaseResponse{

    private List<Model> data;

    public ModelResponse(List<Model> ms) {
        data = ms;
    }

    public List<Model> getData() {
        return data;
    }

    public void setData(List<Model> data) {
        this.data = data;
    }
}
