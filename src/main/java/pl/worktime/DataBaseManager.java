package pl.worktime;

/* this class is responsible for database operations, i.e. db creation, data inserting and so on */

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
        /* I assume that if the database was not created before, the table also wasn't */
        createRequiredTable(srvConnection);
        return true;
    }

    public static void createRequiredTable(Connection srvConnection) {
            try {
                String createTable = "USE "+ DATABASE_NAME+
                        ";CREATE TABLE work_time(id int identity(1,1), start_date varchar(100), start_time varchar(100))";
                Statement statement = srvConnection.createStatement();
                statement.execute(createTable);
            }
            catch(SQLException se) {
                System.out.println("Błąd: wystąpił błąd podczas próby utworzenia wymaganej tabeli 'work_time'");
                se.printStackTrace();
            }
            System.out.println("Informacja: pomyślnie utworzono tabelę 'work_time'");
        }
    }
