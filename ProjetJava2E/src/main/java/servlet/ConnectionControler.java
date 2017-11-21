/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.console;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO;
import model.DataSourceFactory;


/**
 *
 * @author Nicolas
 */
@WebServlet(name = "ConnectionControler", urlPatterns = {"/ConnectionControler"})
public class ConnectionControler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try {
                DAO dao = new DAO(DataSourceFactory.getDataSource());
                
                String action = request.getParameter("action");
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                String msgErr = "";
                
                if(action != null){
                    //ACTION Une Connexion
                    if(action.equals("Connexion")){
                        if(login!=null && password!= null){
                            try{
                                if(dao.verifClientConnexion(login,password)){
                                    // On a trouvé la combinaison login / password
                                    // On stocke l'information dans la session
                                    HttpSession session = request.getSession(true);
                                    session.setAttribute("userName", "Didier");
                                }else{// On positionne un message d'erreur pour l'afficher dans la JSP
                                    request.setAttribute("errorMessage", "Login/Password incorrect");
                                }
                                
                            } catch (SQLException e) {
                                msgErr = e.getMessage();
                            }
                        }
                    }
                }
                String userName = findUserInSession(request);
                String jspView;
		if (null == userName) { // L'utilisateur n'est pas connecté
			// On choisit la page de login
			jspView = "Connexion.jsp";

		} else { // L'utilisateur est connecté
			// On choisit la page d'affichage
			jspView = "PageClient.jsp";
		}
		// On va vers la page choisie
		request.getRequestDispatcher(jspView).forward(request, response);
                
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionControler.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
    
        private String findUserInSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session == null) ? null : (String) session.getAttribute("login");
	}
}
