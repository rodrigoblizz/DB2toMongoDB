/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mongodbconnection;

import static com.mycompany.mongodbconnection.Config.getProp;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Date;

/**
 *
 * @author rodri
 */

public class Execute {
    
    private static String getDateTime() { 
        // TIMESTAMP('2015-01-20 12:00:00')
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Date date = new Date(); 
        return dateFormat.format(date); 
    }
    
    private static void saveDate(String dataInicio) throws IOException{
        FileWriter writer = new FileWriter("executeDate.txt");
                PrintWriter saida = new PrintWriter(writer);
                saida.println(dataInicio);
                saida.close();
                writer.close();
    }
           
    public static void main(String[] args) throws SQLException, IOException {
    Properties prop = getProp();
        String typeString = prop.getProperty("prop.type");
        int type = Integer.parseInt(typeString);
        String dataInicio = Execute.getDateTime();

        if(type == 1){
            try{
                MongoInsert.updateMongo();
                saveDate(dataInicio);
            } catch (SQLException e){
                System.out.println(e);
            } catch (IOException e){
                System.out.println(e);
            }
             
        } else if (type == 2){
            
            try{
                MongoInsert.replaceMongo();
                saveDate(dataInicio);
            } catch (SQLException e){
                System.out.println(e);             
            } catch (IOException e){
                System.out.println(e);                
            }
        
        }  else {
            System.out.println("SELECT THE TYPE OF OPERATION IN PROPERTIES");
        }
    }
}