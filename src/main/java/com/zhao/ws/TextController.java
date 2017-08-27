package com.zhao.ws;

import com.alibaba.fastjson.JSONObject;
import com.zhao.bean.Message;
import com.zhao.bean.MsgConstant;
import com.zhao.bean.RoomInfo;
import com.zhao.bean.User;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by zhao on 17-8-16.
 */

@ServerEndpoint(value = "/ws/chat", configurator = WsConfigurator.class)
public class TextController {

    private Session session;

    private User loginUser;

    private static RoomInfo roomInfo;

    //连接集合
    private static final Set<TextController> connections = new CopyOnWriteArraySet<TextController>();

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
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        // 从HttpSession中取得当前登录的用户作为当前连接的用户
        setUser((User) httpSession.getAttribute("User"));
        if (getUser() == null) {
            requireLogin();// 未登录需要进行登录
            return;
        }
        // 设置聊天室信息
        if (getConnections().size() == 0) {// 如果当前聊天室为空，建立新的信息
            setRoomInfo(new RoomInfo(getUserName(), (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date())));
        }
        //加入连接集合
        getConnections().add(this);
        //广播通知所有连接有新用户加入
//        broadcastToAll(new Message(getUserName(), MsgConstant.Open, getUsers()));
    }

    /**
     * websock连接断开后触发
     */
    @OnClose
    public void OnClose() {
        //从连接集合中移除
        getConnections().remove(this);
        //广播通知所有连接有用户退出
//        broadcastToAll(new Message(getUserName(), MsgConstant.Close, getUsers()));
    }

    /**
     * 接受到客户端发送的字符串时触发
     *
     * @param message
     */
    @OnMessage(maxMessageSize = 1000)
    public void OnMessage(String message) {
        //消息内容反序列化
        Message msg = JSONObject.parseObject(message, Message.class);
//        msg.setHost(getUserName());
        //对html代码进行转义
        msg.setMsg(txt2htm(msg.getMsg()));
        /*
        if (msg.getDests() == null){
            broadcastToAll(msg);

        }
        else{
            broadcastToSpecia(msg);
        }*/
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
    private static void broadcastToAll(Message msg) {
        for (TextController client : getConnections())
            client.call(msg);
    }

    /**
     * 发送给指定的用户
     *
     * @param msg
     */
    private static void broadcastToSpecia(Message msg) {

//        for (TextController client : getConnections())
            // 感觉用map进行映射会更好点
//            if (Contains(msg.getDests(), client.getUserName()))
//                client.call(msg);
    }

    private void call(Message msg) {


      /*  try {
            synchronized (this) {
                if (getUserName().equals(msg.getHost()) && msg.getType() == MsgConstant.Open)
                    msg.setRoomInfo(getRoomInfo());
                this.getSession().getBasicRemote().sendText(msg.toString());
            }
        } catch (IOException e) {
            try {
                //断开连接
                this.getSession().close();
            } catch (IOException e1) {
            }
            OnClose();
        }
        */
    }

    private void requireLogin() {
        Message msg = new Message();
//        msg.setType(MsgConstant.RequireLogin);
        call(msg);
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }

    public User getUser() {
        return loginUser;
    }

    public void setUser(User loginUser) {
        this.loginUser = loginUser;
    }

    /**
     * 设置聊天室信息
     */
    public static void setRoomInfo(RoomInfo info) {
        roomInfo = info;
    }

    public static RoomInfo getRoomInfo() {
        return roomInfo;
    }

    private String getUserName() {
        if (getUser() == null)
            return "";
        return getUser().getName();
    }

    public static Set<TextController> getConnections() {
        return connections;
    }

    private String[] getUsers() {
        int i = 0;
        String[] destArrary = new String[getConnections().size()];
        for (TextController client : getConnections())
            destArrary[i++] = client.getUserName();
        return destArrary;
    }

    /**
     * html代码转义
     *过滤他特殊符号
     * @param txt
     * @return
     */
    public static String txt2htm(String txt) {
        if (null == txt ||"".equals(txt.trim())) {
            return txt;
        }
        return txt.replaceAll("&", "").replaceAll("<", "").replaceAll(">", "").replaceAll("\"", "").replaceAll(" ", "").replaceAll("\n", "").replaceAll("\'", "");
    }

    /**
     * 字符串数组是否包含指定字符串
     *
     * @param strs
     * @param str
     * @return
     */
    public static boolean Contains(String[] strs, String str) {
        if (null ==str || strs.length == 0)
            return false;
        for (String s : strs)
            if (s.contains(str)){
                return true;
            }
        return false;
    }
}

