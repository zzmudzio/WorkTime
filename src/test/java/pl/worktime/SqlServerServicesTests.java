package pl.worktime;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SqlServerServicesTests {

    @Test
    public void positiveTestCheckEnvVars() {
        System.out.println("[Positive Test] checkIfEnvVarsAreSet: Verifying positive(valid data) scenario");
        Assert.assertTrue(SqlServerServices.checkIfEnvVarsAreSet());
    }

    @Test(priority=1)
    public void positiveTestServiceRunning() {
        System.out.println("[Positive Test] checkIfServiceIsRunning: Verifying positive(valid data) scenario");
        Assert.assertEquals(SqlServerServices.checkIfServiceIsRunning(SqlServerServices.getEnvs()[0]), 1);
    }

    @Test(priority=2)
    public void negativeTestServiceRunning() {
        System.out.println("[Negative Test] checkIfServiceIsRunning: Verifying negative(invalid data) scenario");
        Assert.assertEquals(SqlServerServices.checkIfServiceIsRunning("RANDOM_STRING"), 0);
    }

    @Test(priority=3)
    public void positiveTestServiceName() {
        System.out.println("[Positive Test] serviceName Verifying positive(valid data) scenario");
        Assert.assertNotEquals(SqlServerServices.getServiceName(SqlServerServices.getEnvs()[0]), "ERROR");
    }

    @Test(priority=4)
    public void negativeTestServiceName() {
        System.out.println("[Negative Test] serviceName Verifying negative(invalid data) scenario");
        Assert.assertEquals(SqlServerServices.getServiceName("RANDOM_INVALID_DATA"), "ERROR");
    }

    @Test(priority=5)
    public void positiveTestGetEnvs() {
        System.out.println("[Positive Test] getEnvs Verifying positive(valid data) scenario");
        Assert.assertEquals(SqlServerServices.getEnvs()[0], "ZZMUDZIO_SQL_BRW_NAME");
    }

    @Test(priority=6)
    public void positiveTestAreServicesReady() {
        System.out.println("[Positive Test] areServicesReady Verifying positive(valid data) scenario");
        Assert.assertTrue(SqlServerServices.areServicesReady());
    }

}
