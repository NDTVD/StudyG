<%@ page session="true" %>
<%
    String user = (String) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Integer plays = (Integer) session.getAttribute("plays");
    if (plays == null) {
        plays = 0;
    }
    String reward = (String) request.getAttribute("reward");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page is not found!</title>
        <link rel="stylesheet" href="css/style.css" />
    </head>
    <body>
        <h1>Page is not found or this function is not available!</h1>
        <p><a href="home.jsp">Back</a></p>
    </body>
</html>
