<%-- 
    Document   : PageClient
    Created on : 21 nov. 2017, 15:29:12
    Author     : Nicolas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${userName}</title>
    </head>
    <body>
        <h1>Bienvenue ${userName}</h1>
        
        <form action="<c:url value="/" />" method="POST"/> 
            <input type='submit' name='action' value='deconnexion'>
	</form>
    </body>
</html>
