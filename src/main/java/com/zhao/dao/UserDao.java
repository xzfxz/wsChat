package com.zhao.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zhao.bean.User;
import com.zhao.bean.User;
import com.zhao.tool.MongoManager;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 17-8-18.
 */
public class UserDao implements UserDaoI {

    private static final  String Table="z_ws_users";

    private MongoDatabase mongoDatabase = MongoManager.getDB(null);

    private MongoCollection<Document> coll = mongoDatabase.getCollection(Table);

    public void addUser(User user) {

        Document document = user.toDocuments();
        coll.insertOne(document);
    }

    public void deleteUser(String uid) {
        BasicDBObject id = new BasicDBObject("_id", new ObjectId(uid));
        coll.findOneAndDelete(id);
    }

    public void updateUser(User user) {

        BasicDBObject id = new BasicDBObject("_id", new ObjectId(user.getId()));
        BasicDBObject object = new BasicDBObject(User.Meta.Cat, user.getCat());
        object.put(User.Meta.Name, user.getName());
        object.put(User.Meta.Lat, user.getLat());
        object.put(User.Meta.Vip, user.getVip());
        object.put(User.Meta.VipLevel, user.getVipLevel());


        BasicDBObject append = new BasicDBObject().append("$set", object);

        coll.updateOne(id,append);
    }

    public User getUser(String uid) {
        ObjectId objectId = new ObjectId(uid);

        FindIterable<Document> id = coll.find(new BasicDBObject("_id", objectId));
        if(null!=id){
            Document first = id.first();
            if(null ==first){
                return null;
            }
            User user = new User().toUser(first);
            return user;
        }
        return null;
    }

    public List<User> list(long start, long end, String uname) {

        FindIterable<Document> documents = coll.find();
        BasicDBObject bb = null;
        if(0!=start && 0!=end){
            bb = new BasicDBObject(User.Meta.Cat, new BasicDBObject("$gt", start).append("$lte", end));
        }
        if(0 !=start && 0==end){
            bb = new BasicDBObject(User.Meta.Cat, new BasicDBObject("$gt", start).append("$lte", System.currentTimeMillis()));
        }

        if(null!=uname && !"".equals(uname.trim())){
            bb = new BasicDBObject(User.Meta.Name,uname);
        }

        if(bb !=null){
            documents =  documents.filter(bb);
        }



        ArrayList<User> msgs = new ArrayList<User>();

        for(Document doc:documents){
            User u = new User().toUser(doc);
            msgs.add(u);
        }
        return msgs;
    }
}
