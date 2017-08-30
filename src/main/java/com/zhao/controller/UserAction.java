package com.zhao.controller;

import com.zhao.bean.User;
import com.zhao.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhao on 17-6-21.
 */
@Controller
@RequestMapping(value = "/user")
public class UserAction {

    @Resource(name="userDao")
    private UserDao userDao;



    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request){
        String result="user/list";
        List<User> list = userDao.list(0, 0, "");

        request.setAttribute("data",list);

        return result;
    }
    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request){
        String result="user/index";

        String uid = request.getParameter("id");
        User user = userDao.getUser(uid);
        long lat = user.getLat();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date(lat));


        user.setMark(format);
        request.setAttribute("data",user);
        return result;
    }
    @RequestMapping(value = "/delete")
    public String delete(HttpServletRequest request){
        String result="redirect:/user/list";
        String uid = request.getParameter("id");
        userDao.deleteUser(uid);
        return result;
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request){
        String result="redirect:/user/list";
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String time = request.getParameter("time");
        String uid = request.getParameter("id");
        User user ;
        boolean flag ;
        if(null==uid ||"".equals(uid.trim())){
            flag=true;
            user = new User();
        }else {
            flag = false;
            user = userDao.getUser(uid);
        }

        user.setName(name);
        user.setPhoneNum(phone);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long times=0;
        try {
            Date parse = sdf.parse(time);
            times =parse.getTime();
        } catch (ParseException e) {
        }
        user.setCat(times);
        user.setLat(times);
        if(flag){
            userDao.addUser(user);
        }else {
            userDao.updateUser(user);
        }

        return result;
    }

    @RequestMapping(value = "/index")
    public String index(){
        String result="user/index";

        return result;
    }
}
