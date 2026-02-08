<%@ page import="java.util.*" %>
<%@ page session="true" %>
<%
    String user = (String) session.getAttribute("user");
    if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Home</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<h1>Welcome, <%= user %></h1>
<nav>
    <a href="subjects">Subjects</a> | 
    <a href="game">Game</a> | 
    <a href="shop">Shop</a> | 
    <a href="inventory.jsp">Inventory</a> | 
    <a href="logout">Logout</a>
</nav>
</body>
</html>
