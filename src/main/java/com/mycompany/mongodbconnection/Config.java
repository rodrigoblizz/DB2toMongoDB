/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mongodbconnection;

/**
 *
 * @author rodri
 */
 
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class Config {
 
    public static Properties getProp() throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(
                "./properties/config.properties");
        props.load(file);
        return props;
    }   
}
