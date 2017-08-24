package com.zhao.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by zhao on 17-8-16.
 */

public  class RoomInfo implements Serializable{

    private static final long serialVersionUID = -5286818932143264232L;

    private String name;// 聊天室名称

    private String creater;//创建人

    private String createTime;// 创建时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public RoomInfo() {
    }

    public RoomInfo(String creater, String createTime) {
        this.creater = creater;
        this.createTime = createTime;
    }

    public RoomInfo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
