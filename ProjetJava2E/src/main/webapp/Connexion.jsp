<%-- 
    Document   : Connexion
    Created on : 17 nov. 2017, 15:48:49
    Author     : Nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page de Connexion</title>
    </head>
    <body>
        <h1>Connexion</h1>
        <form action="ConnectionControleur" method="GET">
            Login : </br><input type="email" name="login" /></br>
            Password : </br><input type="password" name="password" /></br>
            <input type="submit" name="action" value="Connexion" />
        </form>
    </body>
</html>
