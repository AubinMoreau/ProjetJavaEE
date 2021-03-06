/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO;
import model.DataSourceFactory;
import model.PurchaseEntity;


/**
 *
 * @author Nicolas
 */
//@WebServlet(name = "ConnectionControler", urlPatterns = {"/ConnectionControler"})
public class ConnectionControler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try (PrintWriter out = response.getWriter()){
        //Action appelée ? 
        String action = request.getParameter("action");
		if (null != action) {
			switch (action) {
				case "connexion":
					checkLogin(request);
					break;
				case "deconnexion":
					doLogout(request);
					break;
                                case "Commander":
                                        AjouterCommande(request);
                                        break;
			}
		}

		// Est-ce que l'utilisateur est connecté ?
		// On cherche l'attribut userName dans la session
		String userName = findUserInSession(request);
                String userAdmin = findAdminInSession(request);
		String jspView;
		if (userName != null){
                    jspView = "Page Client.jsp";
                }else {
                    jspView = "Connexion.jsp";
                }
                if(userAdmin != null){
                    jspView = "GraphiqueParCatégorie.jsp";
                }
		// On va vers la page choisie
		request.getRequestDispatcher(jspView).forward(request, response);

	}
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
        private void checkLogin(HttpServletRequest request) throws SQLException{
                DAO dao = new DAO(DataSourceFactory.getDataSource());
                
		// Les paramètres transmis dans la requête
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		/*Le login/password défini dans web.xml*/
		String loginAd = getInitParameter("loginAdmin");
		String passwordAd = getInitParameter("passwordAdmin");
		String userName = getInitParameter("ID");
                
                if(loginAd.equals(login) && passwordAd.equals(password)){
                    HttpSession session = request.getSession(true); // démarre la session
                    session.setAttribute("userAdmin",userName);
                    } else 
                    if (dao.verifClientConnexion(login,password)) {
                        // On a trouvé la combinaison login / password
                        // On stocke l'information dans la session
                        HttpSession session = request.getSession(true); // démarre la session
                        //Nom du client (Non admin)
                        String name = dao.nomClient(login, password);
                        session.setAttribute("userName",name);
                        session.setAttribute("id",password);
                            }else{ // On positionne un message d'erreur pour l'afficher dans la JSP
                                request.setAttribute("errorMessage", "Login/Password incorrect");
                            }
            }
    
        private void doLogout(HttpServletRequest request) {
		// On termine la session
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}
        
        private String findUserInSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session == null) ? null : (String) session.getAttribute("userName");
	}
        
        private String findAdminInSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session == null) ? null : (String) session.getAttribute("userAdmin");
	}
        private void AjouterCommande(HttpServletRequest request) throws SQLException{
            DAO dao = new DAO(DataSourceFactory.getDataSource());
            
            int quantite = Integer.parseInt(request.getParameter("quantite"));
            int id = Integer.parseInt(request.getParameter("id"));
            float fraisport = Float.parseFloat(request.getParameter("fraisport"));
            String dateAchat = request.getParameter("dateAchat");
            String dateLivraison = request.getParameter("dateLivraison");
            String description = request.getParameter("produit");
            
            PurchaseEntity commande = new PurchaseEntity(1,id,quantite,30,fraisport,dateAchat,dateLivraison,description);
            
            dao.ajoutCommande(commande);
            
        }
}
