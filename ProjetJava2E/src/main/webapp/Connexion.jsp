<%-- 
    Document   : Connexion
    Created on : 28 nov. 2017, 19:33:57
    Author     : Nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page de Connexion</title>
        <link rel="icon" type="image/png" href="Pictures/favicon.png" />
        <link rel="stylesheet" href="Connexion.css" />
    </head>
    <body>
        <div style="color:red">${errorMessage}</div></br>
        <form action="<c:url value="/" />" mehod="POST">
            <fieldset>
                <legend>Connexion</legend>
            <label for="log"> Login :<span class="requis">*</span></label>
            <input name="login" /></br>
            <label for="mdp"> Password :<span class="requis">*</span></label>
            <input type="password" name="password" /></br>
            
            </br><input type="submit" name="action" value="connexion" />
            </fieldset>
        </form>
    </body>
</html>