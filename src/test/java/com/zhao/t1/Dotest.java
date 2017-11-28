package com.zhao.t1;

import com.zhao.bean.User;
import com.zhao.dao.MessageDao;
import com.zhao.dao.UserDao;
import org.junit.Test;

import java.util.List;

/**
 * Created by zhao on 17-8-18.
 */
public class Dotest {

    @Test
    public void dotest01(){

    }
    public static void main(String[] args) {
        MessageDao messageDao = new MessageDao();
//        System.out.println(message_s);

        UserDao userDao = new UserDao();
        List<User> list = userDao.list(0, 0, null);
        System.out.println(list);


      /*
        User user = new User();
        user.setName("zzz");
//        userDao.addUser(user);

        User user1 = userDao.getUser("5996af26dec56f72118bd790");
        user1.setName("abcdefgs");
        userDao.updateUser(user1);
        user1 = userDao.getUser("5996af26dec56f72118bd790");*/
//        System.out.println(user1);
//        List<User> list = userDao.list(0, 0, null);
//        System.out.println(list);
        /*

        Message_ message_ = new Message_();
        message_.setMsg("44444444");
        message_.setClientcount(2333);



//        messageDao.deleteMessage("59968daedec56f60384bb562");
        messageDao.addMessage(message_);

//        //        System.out.println(message_s);
        Message_ message = messageDao.getMessage("5996aa3adec56f70a58aef85");
//        System.out.println(message);

        message.setFrom("56565a");
        message.setTo("a");
        message.setUa("uua");
        messageDao.updateMeaage(message);
        Message_ messa1ge = messageDao.getMessage("5996aa3adec56f70a58aef85");

        System.out.println(messa1ge);*/
//        System.out.println(message);



    }
}
