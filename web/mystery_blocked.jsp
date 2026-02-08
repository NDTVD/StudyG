<%@ page session="true" %>
<%
    String user = (String) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
    Integer plays = (Integer) session.getAttribute("plays"); if (plays==null) plays=0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Access Denied - Mystery Boxes</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
    <h2>Cannot access Mystery Boxes</h2>
    <p>You need at least 1 play to open Mystery Boxes. Your available plays: <b><%= plays %></b></p>
    <p>Earn plays by learning subjects or buying them in the <a href="shop">Shop</a>.</p>
    <p>Or earn plays by studying more in the <a href="subjects">Subjects</a></p>
    <p><a href="game">Back to game</a></p>
</body>
</html>
