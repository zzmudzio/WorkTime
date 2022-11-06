package pl.worktime;

/* This class is responsible for checking if a date is present in the database - if it's not, then date and
time will be inserted into the applicable table. If it is - nothing will happen.
 */

import java.sql.*;
import java.time.LocalDate;

public class WorkTimeManager {
    public static boolean checkDatePresence(Connection srvConnection) {
        // Date format is YYYY-MM-DD
        ResultSet queryResult;
        PreparedStatement statement;
        try {
             statement = srvConnection.prepareStatement("USE zzmudzio_work_time;SELECT start_date " +
                     "from work_time where start_date = ?");
             statement.setString(1, LocalDate.now().toString());
             queryResult = statement.executeQuery();
             if(!queryResult.next()) {
                 return false;
             }
        }
        catch(SQLException se) {
            System.out.println("Błąd: wystąpił błąd podczas próby weryfikacji wpisu z datą w tabeli.");
            se.printStackTrace();
        }
        return true;
    }
}
