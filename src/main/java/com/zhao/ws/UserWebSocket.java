package com.zhao.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhao.bean.Message;
import com.zhao.bean.MsgConstant;
import com.zhao.bean.RoomInfo;
import com.zhao.bean.User;
import com.zhao.dao.UserDao;
import com.zhao.dao.UserDaoI;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by zhao on 17-8-16.
 */

@ServerEndpoint(value = "/ws/user")
public class UserWebSocket {

    private Session session;

    private UserDaoI userDao = new UserDao();


    //连接集合
    private static final Set<UserWebSocket> connections = new CopyOnWriteArraySet<UserWebSocket>();

    /**
     * websock连接建立后触发
     *
     * @param session
     * @param config
     */
    @OnOpen
    public void OnOpen(Session session, EndpointConfig config, @PathParam(value = "uid") String uid) {
        //设置websock连接的session
        setSession(session);
        // 获取HttpSession
        //加入连接集合
        getConnections().add(this);
        //广播通知所有连接有新用户加入
        List<User> list = userDao.list(0, 0, "");
        String s = JSON.toJSONString(list);
        broadcastToAll(s);
    }

    /**
     * websock连接断开后触发
     */
    @OnClose
    public void OnClose() {
        //从连接集合中移除
        getConnections().remove(this);

    }

    /**
     * 接受到客户端发送的字符串时触发
     *
     * @param message
     */
    @OnMessage(maxMessageSize = 1500)
    public void OnMessage(String message) {
        User user = JSON.parseObject(message, User.class);
//        通过对mark字段的值进行不同的操作，例如1,为增加，2为删除，3为修改
        String mark = user.getMark();
        String trim = mark.trim();

        if("1".equals(trim)){
            String name = user.getName();
            userDao.addUser(user);

        }
        if("2".equals(trim)){

            userDao.deleteUser(user.getId());
        }
        if("3".equals(trim)){

            userDao.updateUser(user);
        }

        List<User> list = userDao.list(0, 0, "");
        String s = JSON.toJSONString(list);
        broadcastToAll(s);

    }

    @OnError
    public void onError(Throwable t) throws Throwable {

        System.err.println("Chat Error: " + t.toString());
    }

    /**
     * 广播给所有用户
     *
     * @param msg
     */
    private static void broadcastToAll(String msg) {

        for (UserWebSocket client : getConnections())
            client.call(msg);
    }

    private void call(String msg) {

       try {
            synchronized (this) {
                this.getSession().getBasicRemote().sendText(msg);
            }
        } catch (IOException e) {
            try {
                //断开连接
                this.getSession().close();
            } catch (IOException e1) {

            }
            OnClose();
        }

    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }




    public static Set<UserWebSocket> getConnections() {
        return connections;
    }

}

