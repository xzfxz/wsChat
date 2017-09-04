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

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request){
        String result="user/list";
        String start = request.getParameter("start");
        long time= System.currentTimeMillis();
        long oneDay=86400000L;

        if(null==start ||start.trim().equals("")){
            Date date = new Date(time);
            start=sdf2.format(date);
        }

        try {
            Date date = sdf2.parse(start.trim());
            time = date.getTime();

        } catch (Exception e) {

        }
        List<User> list = userDao.list(time, time+oneDay, "");

        request.setAttribute("start",start);
        request.setAttribute("data",list);

        return result;
    }
    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request){
        String result="user/index";

        String uid = request.getParameter("id");
        User user = userDao.getUser(uid);
        long lat = user.getLat();

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
