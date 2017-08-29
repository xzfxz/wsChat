package com.zhao.bean;

import com.alibaba.fastjson.JSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * Created by zhao on 17-8-16.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 3594535705205078340L;
    private String id;
    private String name;
    private String sessionMark;
    private String phoneNum;
// 一个操作的标识，例如增加或者删除修改等
    private String mark;
    private int vip;
    private int vipLevel;
    private long cat;
    private long lat;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCat() {
        return cat;
    }

    public void setCat(long cat) {
        this.cat = cat;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getSessionMark() {
        return sessionMark;
    }

    public void setSessionMark(String sessionMark) {
        this.sessionMark = sessionMark;
    }
    @Override
    public String toString() {

        return JSONObject.toJSONString(this);
    }

    public Document toDocuments(){
        Document document = new Document();

        document.put(Meta.Name,getName());
        document.put(Meta.Cat,getCat());
        document.put(Meta.Lat,getLat());
        document.put(Meta.SessionMark,getSessionMark());
        document.put(Meta.Vip,getVip());
        document.put(Meta.VipLevel,getVipLevel());
        return document;
    }

    public User toUser(Document doc){
        String name = doc.getString(Meta.Name);
        String session = doc.getString(Meta.SessionMark);
        Integer vip = doc.getInteger(Meta.Vip);
        Integer vipL = doc.getInteger(Meta.VipLevel);
        Long cat = doc.getLong(Meta.Cat);
        Long lat = doc.getLong(Meta.Lat);
        String phoneNum = doc.getString(Meta.PhoneNum);

        ObjectId id = doc.getObjectId("_id");

        User user = new User();
        user.setId(id.toString());
        user.setPhoneNum(phoneNum);
        user.setName(name);
        user.setCat(cat);
        user.setLat(lat);
        user.setSessionMark(session);
        user.setVip(vip);
        user.setVipLevel(vipL);
        return user;
    }

    public static final class Meta {


        public static final String Name = "name";
        public static final String SessionMark = "sessionMark";
        public static final String Vip = "isVip";
        public static final String VipLevel = "vipLevel";
        public static final String Cat = "cat";
        public static final String Lat = "lat";
        public static final String PhoneNum="phoneNum";

    }
}
