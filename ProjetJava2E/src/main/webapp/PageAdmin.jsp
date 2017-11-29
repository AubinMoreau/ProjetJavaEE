<%-- 
    Document   : PageAdmin
    Created on : 28 nov. 2017, 20:47:22
    Author     : Nicolas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${userAdmin}</title>
    </head>
    <body>
        <h1>Bienvenue ${userAdmin}</h1>
        <form action="<c:url value="/" />" method="POST" >
            <input type='submit' name='action' value='deconnexion'>
	</form>
    </body>
</html>
