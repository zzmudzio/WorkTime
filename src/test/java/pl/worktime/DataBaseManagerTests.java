package pl.worktime;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseManagerTests {
    private Connection connection;
    @BeforeTest
    public void createConnection() {
        this.connection = SqlServerConnectionManager.getInstance().connectToServer();
    }

    @Test(priority=0)
    public void positiveTestCheckIfDbExists() {
        System.out.println("[Positive Test] checkIfDbExists: Verifying positive(valid data) scenario");
        Assert.assertTrue(DataBaseManager.checkIfDbExists(connection));
    }

    @Test(priority=1)
    public void negativeTestCheckIfDbExists() {
        System.out.println("[Negative Test] checkIfDbExists: Verifying negative(invalid data) scenario");
        Connection fakeConnection = null;
        Assert.assertFalse(DataBaseManager.checkIfDbExists(fakeConnection));
    }

    @Test(priority=2)
    public void positiveTestCreateDb() {
        System.out.println("[Positive Test] createDb: Verifying positive(valid data) scenario");
        Assert.assertTrue(DataBaseManager.createDatabase(connection));
    }

    @Test(priority=3)
    public void negativeTestCreateDb() {
        System.out.println("[Negative Test] createDb: Verifying negative(invalid data) scenario");
        Connection fakeConnection = null;
        Assert.assertFalse(DataBaseManager.checkIfDbExists(fakeConnection));
    }

    @AfterTest
    public void closeConnection() {
        try {
            connection.close();
        }
        catch(SQLException sq) {
            System.out.println("Error: Unable to close the sql server connection.");
        }
    }
}
