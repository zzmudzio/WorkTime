package pl.worktime;

/* this class is responsible for database operations, i.e. db creation, data inserting and so on */

import java.sql.*;

public abstract class DataBaseManager {
    private static final String DATABASE_NAME = "zzmudzio_work_time";
    private static final String TABLE_NAME = "work_time";

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }
    public static String getTableName() {
        return TABLE_NAME;
    }
    public static boolean checkIfDbExists(Connection srvConnection) {
        try {
            Statement statement = srvConnection.createStatement();
            String listAllDatabases = "SELECT name, database_id, create_date FROM sys.databases";
            ResultSet queryResult = statement.executeQuery(listAllDatabases);
            while (queryResult.next()) {
                if (queryResult.getString("name").equals(DATABASE_NAME)) {
                    return true;
                }
            }
            return false;
        }
        catch(Exception se) {
            System.out.println("Błąd: wystąpił błąd podczas próby pobrania listy baz danych.");
            return false;
        }
    }
    public static boolean createDatabase(Connection srvConnection){
        if(checkIfDbExists(srvConnection)) {
            System.out.println("Informacja: odnaleziono bazę " + DATABASE_NAME +" nowa baza nie zostanie założona.");
            return false;
        }
        System.out.println("Informacja: nie odnaleziono bazy danych o nazwie " + DATABASE_NAME
                + ", nastąpi rejestracja nowej bazy.");
        try {
            String createDatabase = "CREATE DATABASE " + DATABASE_NAME;
            Statement statement = srvConnection.createStatement();
            statement.execute(createDatabase);
            System.out.println("Informacja: pomyślnie utworzono nową bazę danych.");
        }
        catch(SQLException se) {
            System.out.println("Błąd: wystąpił błąd podczas próby rejestracji nowej bazy danych.");
            return false;
        }
        /* I assume that if the database was not created before, the table also wasn't */
        createRequiredTable(srvConnection);
        return true;
    }

    public static void createRequiredTable(Connection srvConnection) {
            try {
                String createTable = "USE "+ DATABASE_NAME +
                        ";CREATE TABLE " + TABLE_NAME + "(id int identity(1,1), start_date varchar(100), " +
                        "start_time varchar(100))";
                Statement statement = srvConnection.createStatement();
                statement.execute(createTable);
            }
            catch(SQLException se) {
                System.out.println("Błąd: wystąpił błąd podczas próby utworzenia wymaganej tabeli: " + TABLE_NAME);
                se.printStackTrace();
            }
            System.out.println("Informacja: pomyślnie utworzono tabelę: " + TABLE_NAME);
        }
    }
