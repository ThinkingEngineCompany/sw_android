package com.sw.beauty.bean;

import java.util.List;

public class PicModelResponse extends BaseResponse{
    private List<PicVideoModel> data;

    public PicModelResponse(List<PicVideoModel> ms) {
        data = ms;
    }

    public List<PicVideoModel> getData() {
        return data;
    }

    public void setData(List<PicVideoModel> data) {
        this.data = data;
    }
}
