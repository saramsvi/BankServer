package server.Configurations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import  server.Configurations.Configs;

public class DataBaseHandler {
    private static Connection dbConnection;

    public static Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + Configs.dbHost + ":"
                + Configs.dbPort + "/" + Configs.dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
       // Class.forName("com.mysql.jdbc.Driver");
        //com.mysql.cj.jdbc.Driver
        dbConnection = DriverManager.getConnection(connectionString, Configs.dbUser,Configs.dbPass);
        return dbConnection;
    }
}
