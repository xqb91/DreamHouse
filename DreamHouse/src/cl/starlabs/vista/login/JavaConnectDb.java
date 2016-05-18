/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.login;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author claudio
 */
public class JavaConnectDb {
    public static Connection ConnectDb()
    {
        try{
           Class.forName("oracle.jdbc.OracleDriver");
           Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","user1","user1");
           return conn;
        }catch(Exception e){
            
        JOptionPane.showMessageDialog(null, e);
    }
       return null;
   
}
    
}
