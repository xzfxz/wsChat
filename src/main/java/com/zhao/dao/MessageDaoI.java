package com.zhao.dao;
import com.zhao.bean.Message;

import java.util.List;

/**
 * Created by zhao on 17-8-18.
 */
public interface MessageDaoI {

    void addMessage(Message message);

    void deleteMessage(String uid);

    void updateMeaage(Message message);

    Message getMessage(String uid);

    List<Message> listMessage(long start, long end, String uname);

}
