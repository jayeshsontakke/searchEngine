package com.Accio;

//import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.*;

public class DatabaseConnection {
    static Connection connection=null;

    public static Connection getConnection() {
        if(connection!=null){
            return connection ;
        }
        String db="searchenginejava";
        String user="root";
        String pwd="Acciojob_8";
        return getConnection(db,user,pwd);
    }
    private  static Connection getConnection(String db,String user,String pwd) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/searchenginejava","root","Acciojob_8");

        }
        catch(ClassNotFoundException | SQLException classNotFoundException){
            classNotFoundException.printStackTrace();
        }
        return connection;
    }
}
