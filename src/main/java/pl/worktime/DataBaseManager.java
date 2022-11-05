package pl.worktime;

/* this class is responsible for database operations, i.e. db creation, data inserting and so on */

import org.jetbrains.annotations.NotNull;

import java.sql.*;

public abstract class DataBaseManager {
    private static final String DATABASE_NAME = "zzmudzio_work_time";
    private static boolean checkIfDbExists(Connection srvConnection) throws SQLException {
        Statement statement = srvConnection.createStatement();
        String listAllDatabases = "SELECT name, database_id, create_date FROM sys.databases";
        ResultSet queryResult = statement.executeQuery(listAllDatabases);
        while(queryResult.next()) {
            if(queryResult.getString("name").equals(DATABASE_NAME)) {
                return true;
            }
        }
        return false;
    }
    public static boolean createDatabase(Connection srvConnection) throws SQLException {
        if(checkIfDbExists(srvConnection)) {
            System.out.println("Informacja: odnaleziono bazę " + DATABASE_NAME +" nowa baza nie zostanie założona.");
            return false;
        }
        System.out.println("Informacja: nie odnaleziono bazy danych o nazwie " + DATABASE_NAME
                + ", nastąpi rejestracja nowej bazy.");
        String createDatabase = "CREATE DATABASE " + DATABASE_NAME;
        Statement statement = srvConnection.createStatement();
        statement.execute(createDatabase);
        System.out.println("Informacja: pomyślnie utworzono nową bazę danych.");
        return true;
    }

    public static void createRequiredTable(Connection srvConnection) {
        //....
    }
}
