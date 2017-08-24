package com.zhao.dao;

import com.zhao.bean.User;

import java.util.List;

/**
 * Created by zhao on 17-8-18.
 */
public interface UserDaoI {
    void addUser(User user);
    void deleteUser(String uid);
    void updateUser(User user);
    User getUser(String uid);

    List<User> list(long start,long end,String name);

}
