package com.sw.beauty.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PicVideoModel {
    private int id;   // int(0) NOT NULL AUTO_INCREMENT,

    private int modelId;   // int(0) NOT NULL AUTO_INCREMENT,
    private String url;   //     varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    private int type;
    private int showIndex;
    private String createTime;   //     timestamp(6) NULL DEFAULT NULL,
    private String updateTime;   // timestamp(6) NULL DEFAULT NULL,
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    @Override
    public String toString() {
        return "PicVideoModel{" +
                "id=" + id +
                ", modelId=" + modelId +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", showIndex=" + showIndex +
                ", createTime=" + sdf.format(createTime) +
                ", updateTime=" + sdf.format(updateTime) +
                '}';
    }
}
