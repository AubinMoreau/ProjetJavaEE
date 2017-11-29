/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnexionTest;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import model.DAO;
import model.DataSourceFactory;
import org.junit.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Nicolas
 */
public class ConnexionTest {
    private static DataSource myDataSource; // La source de données à utiliser
    private static Connection myConnection ;	
    private DAO myDAO;
    
   @Before
    public void setUp() throws SQLException {
	myDataSource = DataSourceFactory.getDataSource();
	myDAO = new DAO(myDataSource);
	}
    
    @Test
    public void verifExistCustomer() throws SQLException {
        String email = "jumboeagle@example.com";
        String id = "1";
        assertTrue(myDAO.verifClientConnexion(email, id));
    }
    
    @Test
    public void verifDifferentIdExistCustomer() throws SQLException {
        String email = "jumboeagle@example.com";
        String id = "2";
        assertFalse(myDAO.verifClientConnexion(email, id));
    }
    
    @Test
    public void verifNotExistMailCustomer() throws SQLException {
        String email = "juboeagle@example.com";
        String id = "1";
        assertFalse(myDAO.verifClientConnexion(email, id));
    }
    
    @Test
    public void verifNotExistIDCustomer() throws SQLException {
        String email = "juboeagle@example.com";
        String id = "-10";
        assertFalse(myDAO.verifClientConnexion(email, id));
    }
    
    public static DataSource getDataSource() throws SQLException {
		org.apache.derby.jdbc.ClientDataSource ds = new org.apache.derby.jdbc.ClientDataSource();
		ds.setDatabaseName("sample");
		ds.setUser("app");
		ds.setPassword("app");
		// The host on which Network Server is running
		ds.setServerName("localhost");
		// port on which Network Server is listening
		ds.setPortNumber(1527);
		return ds;
	}
}
