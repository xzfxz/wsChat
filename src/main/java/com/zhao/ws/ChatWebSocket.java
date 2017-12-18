package com.zhao.ws;

/**
 * Created by zhao on 17-8-9.
 */

import com.alibaba.fastjson.JSON;
import com.zhao.bean.Message;
import com.zhao.bean.User;
import com.zhao.dao.MessageDao;
import com.zhao.dao.UserDao;

import java.io.IOException;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/wsChat")
public class ChatWebSocket {


//    private MessageDao messageDao = new MessageDao();

//    private UserDao userDao = new UserDao();


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的ChatWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String,ChatWebSocket> map = new ConcurrentHashMap<String,ChatWebSocket>();

    private static CopyOnWriteArraySet<String> names = new CopyOnWriteArraySet();
    private String name;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
//        chatWebSocketSet.add(this); //加入set中
        map.put(session.getId(),this);
        addOnlineCount(); //在线数加
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
//        chatWebSocketSet.remove(this); //从set中删除
        map.remove(session.getId());
        subOnlineCount(); //在线数减
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        Message msg = JSON.parseObject(message, Message.class);
        msg.setMsgAt(System.currentTimeMillis());


        Enumeration<String> keys = map.keys();

        while (keys.hasMoreElements()){
            String s = keys.nextElement();
            ChatWebSocket chatWebSocket = map.get(s);

            if(0==msg.getType()){
                this.name = msg.getFrom();
//               排除null的情况
                if(null!=name&&!"".equals(name)){
                    names.add(name);
                }
                //将在线用户返回给新登录的用户
                String usersJson = JSON.toJSONString(names);
                System.out.println("userJson: "+usersJson);
                msg.setMsg(usersJson);
            }
            chatWebSocket.sendMessage(msg);
        }

    }

    public void dealMsg(){

    }
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(Object message){

        RemoteEndpoint.Basic basicRemote = this.session.getBasicRemote();
        try {
            basicRemote.sendText(message.toString());
        } catch (IOException e) {

        }

    }
    public void sendOtherMessage(String msg){

        this.session.getAsyncRemote().sendText(msg);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        ChatWebSocket.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        ChatWebSocket.onlineCount--;
    }
}