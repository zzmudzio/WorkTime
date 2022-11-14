package pl.worktime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
  return codes: -1 = problem during process creation or env path not set correctly;
  0 = sql server service has not started yet; 1 = sql service has started
 */
public class SqlServerServices {
    private static final String SQL_SRV_NAME_ENV = "ZZMUDZIO_SQL_NAME"; //this env holds a Sql Server service name
    private static final String SQL_SRV_BROWSER_NAME_ENV = "ZZMUDZIO_SQL_BRW_NAME"; // this env hold a Sql Server browser service name
    public static boolean checkIfEnvVarsAreSet() {
        return getServiceName(SQL_SRV_BROWSER_NAME_ENV) != null && getServiceName(SQL_SRV_NAME_ENV) != null;
    }
    public static int checkIfServiceIsRunning(String serviceName) {
        String serviceData;
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c","sc query "
                + getServiceName(serviceName));
        try {
            Process myProcess = builder.start();
            BufferedReader myReader = new BufferedReader(new InputStreamReader(myProcess.getInputStream()));
            while ((serviceData = myReader.readLine()) != null) {
                if (serviceData.contains("RUNNING")) {
                    System.out.println("Usługa " + getServiceName(serviceName) + " jest uruchomiona.");
                    return 1;
                }
            }
            System.out.println("Usługa " + getServiceName(serviceName) +" oczekuje na uruchomienie..");
            return 0;
        }
        catch(IOException ioe) {
            System.out.println("Błąd: wystąpił błąd poczas próby utworzenia procesu");
            return -1;
        }
    }
    public static String getServiceName(String envName) {
        return System.getenv(envName) == null? "ERROR" : System.getenv(envName);
    }
    public static String[] getEnvs() {
        return new String[] {SQL_SRV_BROWSER_NAME_ENV, SQL_SRV_NAME_ENV};
    }

    public static boolean areServicesReady(){
        boolean sysEnvVarsStatus = SqlServerServices.checkIfEnvVarsAreSet();
        if(!sysEnvVarsStatus) {
            System.out.println("Błąd: Zmienne środowiskowe nie zostały prawidłowo ustawione, przerywam.");
            return false;
        }
        else {
            String[] sqlServices = SqlServerServices.getEnvs();
            boolean loopContinue = true;
            int serviceStatus = 0;
            for (String service : sqlServices) { //to check whether all desired services are running
                System.out.println("Oczekiwanie na uruchomienie usługi " + getServiceName(service));
                while(loopContinue) {
                    serviceStatus = checkIfServiceIsRunning(service);
                    switch(serviceStatus) {
                        case -1:
                            return false;
                        case 0:
                            try {
                                Thread.sleep(5000);
                            }
                            catch(InterruptedException ie) {
                                System.out.println("Błąd: interrupted exception.");
                            }
                            break;
                        case 1:
                            loopContinue = false;
                    }
                }
                loopContinue = true;
            }
            return true;
        }
    }
}

