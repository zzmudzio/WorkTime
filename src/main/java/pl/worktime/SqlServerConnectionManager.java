package pl.worktime;

/* this class is responsible for sql server operations - i.e. connecting, disconnecting to/from SQL server */

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class SqlServerConnectionManager {
    private static final String CONNECTION_FILE_PATH = System.getProperty("user.dir")+"\\connection-data.txt";
    public static HashMap<String, String> getConnectionData() {
        HashMap<String, String> connectionData = new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(CONNECTION_FILE_PATH));
            String textLine;
            while((textLine = reader.readLine()) != null) {
                String[] lineReadSplit = textLine.split("=="); //data inside file is stored as e.g.:
                // USER-LOGIN==admin
                connectionData.put(lineReadSplit[0], lineReadSplit[1]); //[0] - USER-LOGIN, [1] - admin
            }
        }
        catch(Exception e) {
            System.out.println("Błąd: nie odnaleziono pliku connection-data.txt w scieżce: " + CONNECTION_FILE_PATH);
            return null;
        }
        return connectionData;
    }

    public static Connection connectToServer() {
        HashMap<String, String> connectionData;
        String connectionURL;
        Connection srvConnection;
        if((connectionData = getConnectionData()) == null) {
            System.out.println("Błąd: nie uzyskano danych do połączenia z serwerem SQL.");
            return null;
        }
        connectionURL = connectionData.get("server")+";"+"user="+connectionData.get("user")+";"
                +"password="+connectionData.get("password");
        try {
            srvConnection = DriverManager.getConnection(connectionURL);
        }
        catch(SQLException se) {
            System.out.println("Błąd: nie udało się połączyć z serwerem SQL.");
            return null;
        }
        return srvConnection;
    }

    public static void disconnectFromServer(Connection srvConnection) {
        try {
            srvConnection.close();
        }
        catch(SQLException se) {
            System.out.println("Błąd: wystąpił błąd podczas próby rozłączenia z serwerem SQL.");
        }
    }
}
