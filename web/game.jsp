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
<!DOCTYPE html>
<html>
    <head><meta charset="UTF-8"><title>Game</title>
        <link rel="stylesheet" href="css/style.css" />
    </head>
    <body>
        <h2>Mystery Box</h2>
        <p>Plays available: <%= plays%></p>
        <p>
            <a class="btn" href="mystery">Enter Mystery Boxes</a>
        </p>
        <p><a href="home.jsp">Back</a></p>
    </body>
</html>
