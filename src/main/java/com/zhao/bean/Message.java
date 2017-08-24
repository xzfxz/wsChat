package com.zhao.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhao on 17-8-16.
 */
public class Message {

    private int type;//消息类型

    private String msg;//消息主题

    private String host;// 发送者

    private String[] dests;// 接受者

    private RoomInfo roomInfo;//聊天室信息

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String[] getDests() {
        return dests;
    }

    public void setDests(String[] dests) {
        this.dests = dests;
    }

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }


    public Message() {
        setType(MsgConstant.MsgToAll);
    }

    public Message(String host, int type) {
        setHost(host);
        setType(type);
    }

    public Message(String host, int type, String msg) {
        this(host, type);
        setMsg(msg);
    }

    public Message(String host, int type, String[] dests) {
        this(host, type);
        setDests(dests);
    }

    @Override
    public String toString() {
        // 序列化成json串
        return JSONObject.toJSONString(this);
    }
}

