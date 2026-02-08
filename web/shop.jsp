<%@ page session="true" %>
<%
    String user = (String) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Integer points = (Integer) session.getAttribute("points");
    if (points == null)
        points = 0;
%>
<!DOCTYPE html>
<html>
    <head><meta charset="UTF-8"><title>Shop</title>
        <link rel="stylesheet" href="css/style.css" />
    </head>
    <body>
        <h2>Shop</h2>
        <p>Points: <%= points%></p>
        <form action="shop" method="post">
            <button type="submit" name="buy" value="play">Buy 1 Play (100 points)</button>
            <button type="submit" name="buy" value="item">Buy Profile Item (200 points)</button>
        </form>
        <p><a href="home.jsp">Back</a></p>
    </body>
</html>
