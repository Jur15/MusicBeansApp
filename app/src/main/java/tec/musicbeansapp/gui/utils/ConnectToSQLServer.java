package tec.musicbeansapp.gui.utils;

import net.sourceforge.jtds.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectToSQLServer {

    public ConnectToSQLServer(){}

    public Connection getConnection(){
        Connection connection = null;
        try{
            DriverManager.registerDriver(new Driver());
            String url = "jdbc:jtds:sqlserver://172.19.32.104:1433;instance=SQLMaster;databaseName=musicbeans;user=danielsanchez;password=danielsanchez";
            connection = DriverManager.getConnection(url);
            connection.createStatement();
            return connection;
        }
        catch(Exception e){
            e.printStackTrace();
            return connection;
        }


    }
}
