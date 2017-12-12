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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        /**
         * Renvoie la liste des produits du client passé en paramètre
         * @param id
         * @return
         * @throws SQLException 
         */
        public List<PurchaseEntity> produitClient(String id) throws SQLException {
            ArrayList<PurchaseEntity> result = new ArrayList<>();
            
            String sql ="SELECT PURCHASE_ORDER.ORDER_NUM,PRODUCT.DESCRIPTION,PRODUCT.PURCHASE_COST,PURCHASE_ORDER.QUANTITY,PURCHASE_ORDER.SHIPPING_COST,PURCHASE_ORDER.SALES_DATE,PURCHASE_ORDER.SHIPPING_DATE \n" +
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
                            String dateach = rs.getString("SALES_DATE");
                            String dateliv = rs.getString("SHIPPING_DATE");
                            String description = rs.getString("DESCRIPTION");
                            
                            result.add(new PurchaseEntity(order,Integer.parseInt(id),quantite,prix,fdp,dateach,dateliv,description));
                            
                        }
                    }catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new SQLException(ex.getMessage());
                    }
                return result;
        }
}
        public void ajoutCommande(PurchaseEntity commande) throws SQLException {
            
            String sql = "SELECT MAX(order_num)+1 FROM PURCHASE_ORDER";
            int newId =-1;
            try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)){
                
                try(ResultSet rs = stmt.executeQuery()){
                        if(rs.next()){
                         newId = rs.getInt("ORDER_NUM");   
                        }
                }
            }
            String sql2 = "INSERT INTO PURCHASE_ORDER VALUES(?,?,?,?,?,?,?)";
            try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)){
                
                stmt.setInt(1, newId);
                stmt.setInt(2,commande.getQuantite());
                stmt.setFloat(3,commande.getPrix());
                stmt.setFloat(4,commande.getFraisport());
                stmt.setString(5,commande.getDateachat());
                stmt.setString(6,commande.getDatelivraison());
                stmt.setString(7,commande.getDescription());
                
                stmt.executeUpdate();
            }
     }
        
        public Map<String, Float> PriceCategoryEntity(String dateDebut, String dateFin) throws Exception {
                Map<String, Float> result = new HashMap<>();
                if (dateDebut == null) dateDebut="2010-05-24";
                if (dateFin == null) dateFin="2012-05-24";
		// Une requête SQL paramétrée
		String sql = "SELECT SUM(QUANTITY*PURCHASE_COST) AS TOTAL,PRODUCT_CODE.DESCRIPTION\n" +
                                "FROM PRODUCT INNER JOIN PURCHASE_ORDER ON PRODUCT.PRODUCT_ID = PURCHASE_ORDER.PRODUCT_ID \n" +
                                "INNER JOIN PRODUCT_CODE ON PRODUCT_CODE.PROD_CODE=PRODUCT.PRODUCT_CODE\n" +
                                "WHERE SALES_DATE BETWEEN ? AND ? " +
                                "GROUP BY PRODUCT_CODE.DESCRIPTION";
		try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql);
                   ) {  stmt.setString(1,dateDebut);
                        stmt.setString(2,dateFin);
                        ResultSet rs = stmt.executeQuery();                         
			while (rs.next()) {
				// On récupère les champs nécessaires de l'enregistrement courant
				String description = rs.getString("DESCRIPTION");
				float prix = rs.getFloat("TOTAL");
				// On l'ajoute à la liste des résultats
				result.put(description, prix);
			}
		}
		return result;
	}
       
       
       public Map<Float, Float> PriceLocalisationEntity(String dateDebut, String dateFin) throws Exception {
                Map<Float, Float> result = new HashMap<>();
                if (dateDebut == null) dateDebut="2010-05-24";
                if (dateFin == null) dateFin="2012-05-24";
		// Une requête SQL paramétrée
		String sql = "SELECT SUM(QUANTITY*PURCHASE_COST) AS TOTAL,CUSTOMER.ZIP\n" +
                    "FROM PRODUCT INNER JOIN PURCHASE_ORDER ON PRODUCT.PRODUCT_ID = PURCHASE_ORDER.PRODUCT_ID \n" +
                    "INNER JOIN CUSTOMER ON CUSTOMER.CUSTOMER_ID = PURCHASE_ORDER.CUSTOMER_ID\n" +
                    "WHERE SALES_DATE BETWEEN ? AND ? " +
                    "GROUP BY CUSTOMER.ZIP";
		try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql);
                   ) {  stmt.setString(1,dateDebut);
                        stmt.setString(2,dateFin);
                        ResultSet rs = stmt.executeQuery();                         
			while (rs.next()) {
				// On récupère les champs nécessaires de l'enregistrement courant
				float codePostal = (float) rs.getInt("ZIP");
				float prix = rs.getFloat("TOTAL");
				// On l'ajoute à la liste des résultats
				result.put(codePostal, prix);
			}
		}
		return result;
	}
}

