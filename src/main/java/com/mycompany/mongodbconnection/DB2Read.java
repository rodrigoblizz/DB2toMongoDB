/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mongodbconnection;

import static com.mycompany.mongodbconnection.Config.getProp;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author rodri
 */
public class DB2Read {

    public static ArrayList readDB(int numberQ) throws IOException, SQLException{
            Properties prop = getProp();
            ArrayList listDB2 = new ArrayList();
      
    
        Statement stmt = DB2Connection.connectionDB2().createStatement();
        
        String query = prop.getProperty("prop.query."+numberQ); 
        ResultSet rs = stmt.executeQuery(query);
        
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        
        

        while (rs.next()) {
            int i = 1;
            while (i <= columnCount){
                listDB2.add(rs.getString(i++));
            }
        }

        DB2Connection.connectionDB2().close();

        return listDB2;
    }
    
    public static ArrayList readDBColumns(int numberQ) throws SQLException, IOException{
            ArrayList listDB2Columns = new ArrayList();
            Properties prop = getProp();
      

        Statement stmt = DB2Connection.connectionDB2().createStatement();
        
        // CHOICE YOUR QUERY HERE
        String query = prop.getProperty("prop.query."+numberQ);
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        
        for (int i=1; i <= columnCount; i++){
          listDB2Columns.add(rsmd.getColumnLabel(i));
        }

        DB2Connection.connectionDB2().close();
             return listDB2Columns;
    }
     
    public static ArrayList readDBforUpdate(int numberQ) throws IOException, SQLException, ParseException{
            Properties prop = getProp();
            ArrayList listDB2 = new ArrayList();
            String stringDate = updateDate();
            
        Statement stmt = DB2Connection.connectionDB2().createStatement();
        
        String query = prop.getProperty("prop.query."+numberQ);
        String comparingColumn = prop.getProperty("prop.key.of.date."+numberQ);
        
        String comparingQuery = (query + " WHERE " + comparingColumn + " > TIMESTAMP('" + stringDate + "')");        
        ResultSet rs = stmt.executeQuery(comparingQuery);
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        while (rs.next()) {
            int i = 1;
            while (i <= columnCount){
                listDB2.add(rs.getString(i++));
            }
        }

        DB2Connection.connectionDB2().close();

        return listDB2;
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
    
    
    // 
    static String updateDate() throws IOException, ParseException{
            String dateFile = readFile("executeDate.txt", StandardCharsets.UTF_8);
            
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            Date dateD = formatter.parse(dateFile);
            
            Calendar calendarData = Calendar.getInstance();
            calendarData.setTime(dateD);
            calendarData.add(Calendar.DAY_OF_MONTH, -1);
            //calendarData.add(Calendar.HOUR, 0);
            
            dateD = calendarData.getTime();
            String stringDate = formatter.format(dateD);
            
            return stringDate;
    }
}
