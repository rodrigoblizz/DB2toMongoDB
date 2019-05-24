/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mongodbconnection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import static com.mycompany.mongodbconnection.Config.getProp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author dharesh
 *
 */

public class MongoConnection {
    
    public static MongoClient connectionMongo() throws IOException{
        
        Properties prop = getProp();
        
        String user = prop.getProperty("prop.server.mongo.user");
        String pwd = prop.getProperty("prop.server.mongo.pwd");
        String host = prop.getProperty("prop.server.mongo.host");
        String port = prop.getProperty("prop.server.mongo.port");
        
        MongoClient mongoClient = new 
        MongoClient(new MongoClientURI("mongodb://"+user+":"+ pwd +"@"+ host +":"+ port));
        
        return mongoClient;
    }
}