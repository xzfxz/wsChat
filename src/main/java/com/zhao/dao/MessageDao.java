package com.zhao.dao;


import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.zhao.bean.Message;
import com.zhao.bean.Message;
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

    public void addMessage(Message message) {

        Document document = message.toDocuments();
        coll.insertOne(document);

    }

    public void deleteMessage(String uid) {

        BasicDBObject id = new BasicDBObject("_id", new ObjectId(uid));
        coll.findOneAndDelete(id);

    }

    public void updateMeaage(Message message) {

        BasicDBObject id = new BasicDBObject("_id", new ObjectId(message.getId()));
        BasicDBObject object = new BasicDBObject(Message.Meta.MsgAt, message.getMsgAt());
        object.put(Message.Meta.Msg, message.getMsg());
        object.put(Message.Meta.From, message.getFrom());
        object.put(Message.Meta.To, message.getTo());
        object.put(Message.Meta.Nickname, message.getNickname());
        object.put(Message.Meta.Ua, message.getUa());
        object.put(Message.Meta.Pic, message.getPic());
        object.put(Message.Meta.Clientcount, message.getClientcount());

        BasicDBObject append = new BasicDBObject().append("$set", object);

        coll.updateOne(id,append);

    }

    public Message getMessage(String uid) {

        ObjectId objectId = new ObjectId(uid);

        FindIterable<Document> id = coll.find(new BasicDBObject("_id", objectId));
        if(null!=id){
            Document first = id.first();
            if(null ==first){
                return null;
            }
            Message message = new Message().toMessage(first);
            return message;
        }
        return null;

    }

    public List<Message> listMessage(long start, long end, String uname) {
        FindIterable<Document> documents = coll.find();
        BasicDBObject bb = null;
        if(0!=start && 0!=end){
            bb = new BasicDBObject(Message.Meta.MsgAt, new BasicDBObject("$gt", start).append("$lte", end));
        }
        if(0 !=start && 0==end){
            bb = new BasicDBObject(Message.Meta.MsgAt, new BasicDBObject("$gt", start).append("$lte", System.currentTimeMillis()));
        }


        if(null!=uname && !"".equals(uname.trim())){
            bb = new BasicDBObject().append(Message.Meta.Nickname,uname);
        }
        if(null !=bb){
            documents = documents.filter(bb);
        }
        ArrayList<Message> msgs = new ArrayList<Message>();

        for(Document doc:documents){
            Message message = new Message().toMessage(doc);
            msgs.add(message);
        }

        return msgs;
    }
}
