package pl.worktime;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        if(!SqlServerServices.areServicesReady()) {
            System.out.println("Usługi nie są gotowe, przerywam. Sprawdź" +
                    ",czy odpowiedie usługi SqlServer oraz SqlServer Browser zostały uruchomione");
            System.exit(2);
        }
        /* Creating a sql server connection.. */
        SqlServerConnectionManager connectionManager = SqlServerConnectionManager.getInstance();
        Connection connection;
        if((connection = connectionManager.connectToServer()) == null) {
            System.out.println("Błąd: połączenie nie zostało utworzone, przerywam za 3 sekundy.");
            Thread.sleep(3000);
            System.exit(2);
        }
        /* Checking whether app has been started with a parameters */
        if(args.length > 0 && args[0].equals("install")) {
            System.out.println("Aplikacja uruchomiona w trybie instalacyjnym. ");
            DataBaseManager.createDatabase(connection);
        }
        else if(args.length > 0 && args[0].equals("insert")) {
            WorkTimeManager.insertDateTime(connection);
        }
        else {
            System.out.println(WorkTimeManager.displayTodayStartTime(connection));
            Thread.sleep(5000);
        }
        connectionManager.disconnectFromServer(connection);
    }
}
