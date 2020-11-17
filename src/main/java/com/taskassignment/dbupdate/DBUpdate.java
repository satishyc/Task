package com.taskassignment.dbupdate;

import com.mongodb.*;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import com.taskassignment.kafkatopicwriter.KafkaTopicWriter;
import org.bson.Document;
import java.util.List;
import java.util.logging.Logger;

public class DBUpdate {
    public static void insertIntoDB(List<String> C100,List<String> C101,List<String> C102,List<String> C103) {
        Logger logger = Logger.getLogger(
                KafkaTopicWriter.class.getName());

        try {
            MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
            MongoClient mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("Test");

            MongoCollection<Document> collection1 = database.getCollection("C100");
            MongoCollection<Document> collection2 = database.getCollection("C101");
            MongoCollection<Document> collection3 = database.getCollection("C102");
            MongoCollection<Document> collection4 = database.getCollection("C103");
            Document found1 = (org.bson.Document) collection1.find(new org.bson.Document("CID", "C100")).first();
            Document found2 = (org.bson.Document) collection2.find(new org.bson.Document("CID", "C101")).first();
            Document found3 = (org.bson.Document) collection3.find(new org.bson.Document("CID", "C102")).first();
            Document found4 = (org.bson.Document) collection4.find(new org.bson.Document("CID", "C103")).first();
            Document C100Content = new Document();
            C100Content.append("CID", "C100");
            C100Content.append("Time", C100.get(0));
            C100Content.append("Temperature", C100.get(1));
            C100Content.append("StatusCode", C100.get(2));
            Document updateC100 = new Document("$set", C100Content);
            collection1.updateMany(found1, updateC100);

            Document C101Content = new Document();
            C101Content.append("CID", "C101");
            C101Content.append("Time", C101.get(0));
            C101Content.append("Temperature", C101.get(1));
            C101Content.append("StatusCode", C101.get(2));
            Document updateC101 = new Document("$set", C101Content);
            collection2.updateMany(found2, updateC101);

            Document C102Content = new Document();
            C102Content.append("CID", "C102");
            C102Content.append("Time", C102.get(0));
            C102Content.append("Temperature", C102.get(1));
            C102Content.append("StatusCode", C102.get(2));
            Document updateC102 = new Document("$set", C102Content);
            collection3.updateMany(found3, updateC102);


            Document C103Content = new Document();
            C103Content.append("CID", "C103");
            C103Content.append("Time", C103.get(0));
            C103Content.append("Temperature", C103.get(1));
            C103Content.append("StatusCode", C103.get(2));
            Document updateC103 = new Document("$set", C103Content);
            collection4.updateMany(found4, updateC103);
            logger.info("Mongodb Connected And Updated the Data");
        }
        catch (Exception e){
            logger.warning("Problem in Connecting MONGODB "+e);
        }


    }

}
