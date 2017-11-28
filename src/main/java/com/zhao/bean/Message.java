package com.zhao.bean;

import com.alibaba.fastjson.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * Created by zhao on 17-8-9.
 * 信息封装
 */

public class Message implements Serializable {

    private static final long serialVersionUID = 6084013618120400274L;
    private String id;

    private String from;
    private String to;

    private String nickname;

    private String ua;

    private String msg;

    private long msgAt;

    private String pic;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    @Override
    public String toString() {
        String s = JSON.toJSONString(this);
        return s;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getMsgAt() {
        return msgAt;
    }

    public void setMsgAt(long msgAt) {
        this.msgAt = msgAt;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Document toDocuments(){
        Document document = new Document();

        document.put(Meta.From,getFrom());
        document.put(Meta.To,getTo());
        document.put(Meta.Nickname,getNickname());
        document.put(Meta.Ua,getUa());
        document.put(Meta.Msg,getMsg());
        document.put(Meta.MsgAt,getMsgAt());
        document.put(Meta.Pic,getPic());

        return document;
    }

    public Message toMessage(Document doc){
        Message message_ = new Message();
        String from = doc.getString(Meta.From);
        String to = doc.getString(Meta.To);
        String msg = doc.getString(Meta.Msg);
        Long msgAt = doc.getLong(Meta.MsgAt);
        String pic = doc.getString(Meta.Pic);

        String ua = doc.getString(Meta.Ua);
        ObjectId id = doc.getObjectId("_id");

        message_.setId(id.toString());

        message_.setMsg(msg);
        message_.setNickname(nickname);
        message_.setFrom(from);
        message_.setTo(to);
        message_.setMsgAt(msgAt);
        message_.setPic(pic);
        message_.setUa(ua);

        return message_;
    }

    public static final class Meta {


        public static final String From = "from";
        public static final String To = "to";
        public static final String Nickname = "nickname";
        public static final String Ua = "ua";
        public static final String Msg = "msg";
        public static final String MsgAt = "msgat";
        public static final String Pic = "pic";
    }

}
