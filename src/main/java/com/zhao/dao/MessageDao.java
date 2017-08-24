package com.zhao.dao;


import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.zhao.bean.Message;
import com.zhao.bean.Message_;
import com.zhao.tool.MongoManager;
import org.bson.Document;
import org.bson.types.ObjectId;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 17-8-18.
 */
public class MessageDao implements MessageDaoI {

    private static final  String Table="message";

    private MongoDatabase mongoDatabase = MongoManager.getDB(null);

    private MongoCollection<Document> coll = mongoDatabase.getCollection(Table);

    public void addMessage(Message_ message_) {

        Document document = message_.toDocuments();
        coll.insertOne(document);

    }

    public void deleteMessage(String uid) {

        BasicDBObject id = new BasicDBObject("_id", new ObjectId(uid));
        coll.findOneAndDelete(id);

    }

    public void updateMeaage(Message_ message_) {

        BasicDBObject id = new BasicDBObject("_id", new ObjectId(message_.getId()));
        BasicDBObject object = new BasicDBObject(Message_.Meta.MsgAt, message_.getMsgAt());
        object.put(Message_.Meta.Msg, message_.getMsg());
        object.put(Message_.Meta.From, message_.getFrom());
        object.put(Message_.Meta.To, message_.getTo());
        object.put(Message_.Meta.Nickname, message_.getNickname());
        object.put(Message_.Meta.Ua, message_.getUa());
        object.put(Message_.Meta.Pic, message_.getPic());
        object.put(Message_.Meta.Clientcount, message_.getClientcount());

        BasicDBObject append = new BasicDBObject().append("$set", object);

        coll.updateOne(id,append);

    }

    public Message_ getMessage(String uid) {

        ObjectId objectId = new ObjectId(uid);

        FindIterable<Document> id = coll.find(new BasicDBObject("_id", objectId));
        if(null!=id){
            Document first = id.first();
            if(null ==first){
                return null;
            }
            Message_ message_ = new Message_().toMessage(first);
            return message_;
        }
        return null;

    }

    public List<Message_> listMessage(long start, long end, String uname) {
        FindIterable<Document> documents = coll.find();
        BasicDBObject bb = null;
        if(0!=start && 0!=end){
            bb = new BasicDBObject(Message_.Meta.MsgAt, new BasicDBObject("$gt", start).append("$lte", end));
        }
        if(0 !=start && 0==end){
            bb = new BasicDBObject(Message_.Meta.MsgAt, new BasicDBObject("$gt", start).append("$lte", System.currentTimeMillis()));
        }


        if(null!=uname && !"".equals(uname.trim())){
            bb = new BasicDBObject().append(Message_.Meta.Nickname,uname);
        }
        if(null !=bb){
            documents = documents.filter(bb);
        }
        ArrayList<Message_> msgs = new ArrayList<Message_>();

        for(Document doc:documents){
            Message_ message_ = new Message_().toMessage(doc);
            msgs.add(message_);
        }

        return msgs;
    }
}
