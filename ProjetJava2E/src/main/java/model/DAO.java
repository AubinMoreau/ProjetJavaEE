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
import java.util.ArrayList;
import java.util.List;
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
        
        public boolean verifClientConnexion(String email,String id) throws SQLException {
            boolean verif = false;
            String sql = "SELECT COUNT(*) AS Nombre FROM CUSTOMER WHERE EMAIL=? AND CUSTOMER_ID=? ";
            try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)){
                    
                    stmt.setString(1,email);
                    stmt.setString(2,id);
                    
                    try(ResultSet resultSet = stmt.executeQuery()){
                        if(resultSet.next()){
                            verif = resultSet.getInt("Nombre")==1;
                        }
                    }catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new SQLException(ex.getMessage());
            }
            return verif;
        }
}
        
        public String nomClient(String email,String id) throws SQLException{
            String result = "";
            String sql = "SELECT NAME FROM CUSTOMER WHERE EMAIL=? AND CUSTOMER_ID=? ";
            
            try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)){
                    
                    stmt.setString(1,email);
                    stmt.setString(2,id);
                    
                    try(ResultSet resultSet = stmt.executeQuery()){
                        if(resultSet.next()){
                            result = resultSet.getString("NAME");
                        }
            }catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new SQLException(ex.getMessage());
                    }
            return result;
            }
        }
        
        public List<PurchaseEntity> produitClient(String id) throws SQLException {
            ArrayList<PurchaseEntity> result = new ArrayList<>();
            
            String sql ="SELECT PURCHASE_ORDER.ORDER_NUM,PRODUCT.DESCRIPTION,PRODUCT.PURCHASE_COST,PURCHASE_ORDER.QUANTITY,PURCHASE_ORDER.SHIPPING_COST,PURCHASE_ORDER.SHIPPING_DATE \n" +
                        "FROM PURCHASE_ORDER\n" +
                        "    INNER JOIN PRODUCT\n" +
                        "    ON PURCHASE_ORDER.PRODUCT_ID = PRODUCT.PRODUCT_ID\n" +
                        "WHERE CUSTOMER_ID=?";
            
            try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)){
                    
                    stmt.setString(1,id);
                    
                    try(ResultSet rs = stmt.executeQuery()){
                        if(rs.next()){
                            int order = rs.getInt("ORDER_NUM");
                            int quantite = rs.getInt("QUANTITY");
                            float prix = rs.getFloat("PURCHASE_COST");
                            float fdp = rs.getFloat("SHIPPING_COST");
                            String dateliv = rs.getString("SHIPPING_DATE");
                            String description = rs.getString("DESCRIPTION");
                            
                            result.add(new PurchaseEntity(order,quantite,prix,fdp,dateliv,description));
                            
                        }
                    }catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new SQLException(ex.getMessage());
                    }
                return result;
        }
}
}

