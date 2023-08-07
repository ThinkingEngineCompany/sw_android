package com.sw.beauty.bean;

import java.io.Serializable;
import java.util.Date;

public class Model implements Serializable {
    // https://blog.csdn.net/xijinno1/article/details/130877607
    private String id;   // int(0) NOT NULL AUTO_INCREMENT,
    private String name;   // varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    private String description;   //     varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    private String img;
    private String createTime;   //     timestamp(6) NULL DEFAULT NULL,
    private String updateTime;   // timestamp(6) NULL DEFAULT NULL,

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
