package pl.worktime;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SqlServerConnectionManager connectionManager = SqlServerConnectionManager.getInstance();
        Connection conn;
        if((conn = connectionManager.connectToServer()) == null) {
            System.out.println("Błąd: połączenie nie zostało utworzone, przerywam za 3 sekundy.");
            Thread.sleep(3000);
            System.exit(0);
        }
        if(args.length > 0 && !args[0].equals("install")) {
            System.out.println("Błąd: podano nieprawidłowe " +
                    "parametry wejściowe - użyj 'install'" +
                    "aby zainstalować bazę na serwerze.");
        }
        else if(args.length > 0) {
            System.out.println("Aplikacja uruchomiona w trybie instalacyjnym. ");
            DataBaseManager.createDatabase(conn);
        }
        else {
            WorkTimeManager.insertDateTime(conn);
            System.out.println(WorkTimeManager.displayTodayStartTime(conn));
            connectionManager.disconnectFromServer(conn);
        }
    }
}
