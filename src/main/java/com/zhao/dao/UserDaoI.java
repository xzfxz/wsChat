package com.zhao.dao;

import com.zhao.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhao on 17-8-18.
 */
@Repository
public interface UserDaoI {
    void addUser(User user);
    void deleteUser(String uid);
    void updateUser(User user);
    User getUser(String uid);
//通过lat时间查询
    List<User> list(long start,long end,String name);

}
