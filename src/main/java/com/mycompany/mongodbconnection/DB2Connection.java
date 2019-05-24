/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mongodbconnection;

import static com.mycompany.mongodbconnection.Config.getProp;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author rodri
 */

public class DB2Connection {
    
    public static Connection connectionDB2() throws SQLException, IOException{
        
        Properties prop = getProp();
        
        String jdbcClassName= prop.getProperty("prop.db.jdbcClassName");
        String url= prop.getProperty("prop.db.url");
        String user= prop.getProperty("prop.db.user");
        String password= prop.getProperty("prop.db.password");
        
                Connection connection = null;
        try {
            //Load class into memory
            Class.forName(jdbcClassName);
            //Establish connection
            connection = DriverManager.getConnection(url, user, password);
 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
            }
        } 
        return connection;   
    }
}