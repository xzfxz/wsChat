package com.zhao.t1;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.zhao.tool.MongoManager;
import org.bson.Document;

/**
 * Created by zhao on 17-10-17.
 */
public class Dotest01 {
    public static void main(String[] args) {
        MongoDatabase mongoDatabase = MongoManager.getDB("kiteServer");
        MongoCollection<Document> table = mongoDatabase.getCollection("news");
        BasicDBObject bb = new BasicDBObject();
        bb.put("updAt",new BasicDBObject("$gt",new Long(0L)));

        FindIterable<Document> iterable = table.find(bb).sort(new BasicDBObject("updAt", -1)).limit(2000);
        /*for(Document document:iterable){
            String s = document.toString();
            System.out.println(s);
        }*/
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()){
            Document next = cursor.next();
            String s = next.toString();
            System.out.println(s);
        }


    }
}
