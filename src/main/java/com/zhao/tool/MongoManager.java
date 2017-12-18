package com.zhao.tool;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;

/**
 * Created by zhao on 17-8-18.
 */

public class MongoManager {

    private final static String HOST = "localhost";// 端口
    private final static int PORT = 27017;// 端口
    private final static String DataBase="zhao_ws";

    private static MongoClient mongoClient = null;

    static {
        mongoClient =  new MongoClient(HOST,PORT);
        MongoClientOptions.Builder options = new MongoClientOptions.Builder();
//         options.autoConnectRetry(true);// 自动重连true
        // options.maxAutoConnectRetryTime(10); // the maximum auto connect retry time
        options.connectionsPerHost(300);// 连接池设置为300个连接,默认为100
        options.connectTimeout(15000);// 连接超时，推荐>3000毫秒
        options.maxWaitTime(5000); //
        options.socketTimeout(0);// 套接字超时时间，0无限制
        options.threadsAllowedToBlockForConnectionMultiplier(5000);// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
        options.writeConcern(WriteConcern.UNACKNOWLEDGED);//
        options.build();

    }

    private MongoManager() { }

    //获取指定的数据库
    public  static  MongoDatabase getDB(String dbName){

        MongoDatabase database;

        if(null==dbName || "".equals(dbName.trim())){
             database = mongoClient.getDatabase(DataBase);
        }else {
            database = mongoClient.getDatabase(dbName);
        }

        return database;
    }

//


}