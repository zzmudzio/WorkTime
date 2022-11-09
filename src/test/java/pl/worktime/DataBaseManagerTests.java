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
        System.out.println("[Positive Test] Verifying positive(valid data) scenario");
        Assert.assertTrue(DataBaseManager.checkIfDbExists(connection));
    }

    @Test(priority=0)
    public void negativeTestCheckIfDbExists() {
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
