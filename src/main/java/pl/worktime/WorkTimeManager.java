package pl.worktime;

/* This class is responsible for checking if a date is present in the database - if it's not, then date and
time will be inserted into the applicable table. If it is - nothing will happen.
 */

import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WorkTimeManager {
    private static ResultSet queryResult;
    private static PreparedStatement statement;
    private static boolean checkDatePresence( Connection srvConnection) {
        // Date format is YYYY-MM-DD
        try {
             statement = srvConnection.prepareStatement("USE " + DataBaseManager.getDatabaseName()
                     +";SELECT start_date " +
                     "from " + DataBaseManager.getTableName() + " where start_date = ?");
             statement.setString(1, LocalDate.now().toString());
             queryResult = statement.executeQuery();
             if(!queryResult.next()) {
                 return false;
             }
        }
        catch(SQLException se) {
            System.out.println("Błąd: wystąpił błąd podczas próby weryfikacji wpisu z datą w tabeli.");
        }
        return true;
    }
    public static void insertDateTime(Connection srvConnection) {
        if(!checkDatePresence(srvConnection)) { // if a current date is not present in the table
            try {
                statement = srvConnection.prepareStatement("USE " + DataBaseManager.getDatabaseName() +
                        ";INSERT INTO " + DataBaseManager.getTableName() + "(start_date, start_time) values(?, ?)");
                statement.setString(1, LocalDate.now().toString()); // current date
                Calendar calendar = GregorianCalendar.getInstance();
                statement.setString(2, String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + ":" +
                                (calendar.get(Calendar.MINUTE) < 10 ?
                                        ("0" +String.valueOf(calendar.get(Calendar.MINUTE))):
                        String.valueOf(calendar.get(Calendar.MINUTE))));
                int queryInsertResult = statement.executeUpdate();
                if(queryInsertResult > 0) System.out.println("Sukces: data została dodana do bazy danych.");
                else System.out.println("Błąd: data nie została dodana do bazy danych.");
            }
            catch(SQLException se) {
                System.out.println("Wystąpił błąd podczas próby dodania daty do bazy danych. ");
                se.printStackTrace();
            }
        }
        else {
            System.out.println("Podana data występuje już w bazie danych i nie zostanie dodana. ");
        }
    }
    public static String displayTodayStartTime(Connection srvConnection) { //it will display an information alert consisting of
        // date and time of the first system start
        try {
            statement = srvConnection.prepareStatement("USE " + DataBaseManager.getDatabaseName()
            + " SELECT start_date, start_time from " + DataBaseManager.getTableName() +
                    " WHERE start_date=" + "'"+LocalDate.now().toString()+"'");
            queryResult = statement.executeQuery();
            queryResult.next();
            return "Data rozpoczęcia: " + queryResult.getString("start_date")
                    + ", godzina rozpoczęcia: " + queryResult.getString("start_time");
        }
        catch(SQLException se) {
            System.out.println("Błąd: wystąpił błąd podczas próby odczytu godziny rozpoczęcia pracy w dniu " +
                    LocalDate.now().toString());
            return "";
        }
    }
}
