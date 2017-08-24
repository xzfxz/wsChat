package com.zhao.dao;
import com.zhao.bean.Message_;

import java.util.List;

/**
 * Created by zhao on 17-8-18.
 */
public interface MessageDaoI {

    void addMessage(Message_ message_);

    void deleteMessage(String uid);

    void updateMeaage(Message_ message_);

    Message_ getMessage(String uid);

    List<Message_> listMessage(long start, long end, String uname);

}
