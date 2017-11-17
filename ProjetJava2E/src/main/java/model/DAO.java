/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Nicolas
 */
public class DAO {
    private final DataSource myDataSource;
	/**
	 *
	 * @param dataSource la source de données à utiliser
	 */
	public DAO(DataSource dataSource) {
		this.myDataSource = dataSource;
	}
        
        public void verifClientConnexion(CustomerEntity entite) throws SQLException {
            int verif = 0;
            String sql = "SELECT COUNT(*) AS Nombre FROM CUSTOMER WHERE CUSTOMER_ID=? AND EMAIL=?";
            try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)){
                    
                    stmt.setString(1,entite.getEmail());
                    stmt.setInt(2,entite.getCustomerID());
                    
                    try(ResultSet resultSet = stmt.executeQuery()){
                        if(resultSet.next()){
                            verif = resultSet.getInt("Nombre");
                        }
                    }catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new SQLException(ex.getMessage());
            }
        }
}
}
