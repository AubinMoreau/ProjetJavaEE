<%-- 
    Document   : Page Client
    Created on : 5 déc. 2017, 11:04:58
    Author     : Nicolas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<sql:setDataSource 
	driver="org.apache.derby.jdbc.ClientDriver"
	url="jdbc:derby://localhost:1527/sample"
	user="app" password="app"
/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Client.css" />
        <title>${userName}</title>
        <link rel="icon" type="image/png" href="Pictures/favicon_user.png" />
    </head>
    <body>
        <sql:query var="commande">
            SELECT PURCHASE_ORDER.ORDER_NUM,PRODUCT.DESCRIPTION,PRODUCT.PURCHASE_COST,PURCHASE_ORDER.QUANTITY,PURCHASE_ORDER.SHIPPING_COST,PURCHASE_ORDER.SHIPPING_DATE
                FROM PURCHASE_ORDER
                    INNER JOIN PRODUCT
                    ON PURCHASE_ORDER.PRODUCT_ID = PRODUCT.PRODUCT_ID
                WHERE CUSTOMER_ID=?
            
            <sql:param value="${id}" />
        </sql:query>
                   
                <h1>Bienvenue ${userName}</h1> 
                </br>
        <table border="1">
            <tr>
                <th>Numéro de Commande</th>
                <th>Description</th>
                <th id="qt">Quantité</th>
                <th id="px">Prix</th>
                <th id="fp">Frais de port</th>
                <th id="dl">Date livraison</th>
                
                <c:forEach var="record" items="${commande.rows}">
                <tr><td>${record.ORDER_NUM}</td><td>${record.DESCRIPTION}</td><td>${record.QUANTITY}</td><td>${record.PURCHASE_COST}</td>
                <td>${record.SHIPPING_COST}</td><td>${record.SHIPPING_DATE}</td></tr>
                </c:forEach>
            </tr>
        </table>
        
        </br><form action="<c:url value="/" />" method="POST">
            <input type='submit' name='action' value='deconnexion'>
            <input type='submit' name='action' value='Commander'>
	</form>
    </body>
