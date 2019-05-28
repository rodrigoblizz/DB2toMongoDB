/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mongodbconnection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import static com.mycompany.mongodbconnection.Config.getProp;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author rodri
 */

public class MongoInsert {
    public static void insertMongo() throws SQLException, IOException{
        Properties prop = getProp();

        List<String> dbs = MongoConnection.connectionMongo().getDatabaseNames();
        DB database= MongoConnection.connectionMongo().getDB(prop.getProperty("prop.mongo.database"));
        
        String cs = prop.getProperty("prop.number.of.querys");
        int c = Integer.parseInt(cs);
        int numberQ;

        for (numberQ=1; numberQ<=c; numberQ++){
            
            DBCollection collection = database.getCollection(prop.getProperty("prop.mongo.collection."+numberQ));
            
            int i = 0;
            int k= 0;
            while (i<DB2Read.readDB(numberQ).size()){
                BasicDBObject document = new BasicDBObject();
                
                for(k=0; k < DB2Read.readDBColumns(numberQ).size(); k++){
                document.put(DB2Read.readDBColumns(numberQ).get(k).toString(), DB2Read.readDB(numberQ).get(i++));
                }                
                collection.insert(document);        
            }
        }
    }
    
    
       public static void replaceMongo() throws SQLException, IOException{
        Properties prop = getProp();

        List<String> dbs = MongoConnection.connectionMongo().getDatabaseNames();
        DB database= MongoConnection.connectionMongo().getDB(prop.getProperty("prop.mongo.database"));
        
        String cs = prop.getProperty("prop.number.of.querys");
        int c = Integer.parseInt(cs);
        int numberQ;

        for (numberQ=1; numberQ<=c; numberQ++){
            
            DBCollection collection = database.getCollection(prop.getProperty("prop.mongo.collection."+numberQ));
            collection.remove(new BasicDBObject());
            
            int i = 0;
            int k= 0;
            while (i<DB2Read.readDB(numberQ).size()){
                BasicDBObject document = new BasicDBObject();
                
                for(k=0; k < DB2Read.readDBColumns(numberQ).size(); k++){
                document.put(DB2Read.readDBColumns(numberQ).get(k).toString(), DB2Read.readDB(numberQ).get(i++));
                }                
                collection.insert(document);        
            }
        }
    }
       
        public static void updateMongo() throws SQLException, IOException, ParseException{
        Properties prop = getProp();

        List<String> dbs = MongoConnection.connectionMongo().getDatabaseNames();
        DB database= MongoConnection.connectionMongo().getDB(prop.getProperty("prop.mongo.database"));
        
        String cs = prop.getProperty("prop.number.of.querys");
        int c = Integer.parseInt(cs);
        int numberQ;

        for (numberQ=1; numberQ<=c; numberQ++){
            
            DBCollection collection = database.getCollection(prop.getProperty("prop.mongo.collection."+numberQ));
            String pkey = prop.getProperty("prop.pkey."+numberQ);
            
            int i = 0;
            int k= 0;
            while (i<DB2Read.readDBforUpdate(numberQ).size()){
                BasicDBObject document = new BasicDBObject();
                BasicDBObject key = new BasicDBObject();
                
                for(k=0; k < DB2Read.readDBColumns(numberQ).size(); k++){
                    document.put(DB2Read.readDBColumns(numberQ).get(k).toString(), DB2Read.readDBforUpdate(numberQ).get(i));
                        if(pkey.equals(DB2Read.readDBColumns(numberQ).get(k).toString())){
                            key.put(pkey, DB2Read.readDBforUpdate(numberQ).get(i));
                        }   i++;
                }
                
                if(collection.findOne(key) == null){
                collection.insert(document);
                    System.out.println("INSERINDO PQ NAO TEM");
              
                } else {
                    collection.update(key, document);
                    System.out.println("ESSE JA TEM" + key);
                }               
            }
        }
    }
}