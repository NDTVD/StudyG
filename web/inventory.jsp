<%@ page import="java.util.*" %>
<%@ page session="true" %>
<%
    String username = (String) session.getAttribute("user");
    if (username == null) { response.sendRedirect("login.jsp"); return; }
    com.studg.dao.UserDAO userDAO = com.studg.dao.DAOFactory.getUserDAO();
    com.studg.model.User user = userDAO.findByUsername(username);
    Set<String> items = user != null ? user.getProfileItems() : Collections.emptySet();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inventory</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
    <h2>Your Inventory</h2>
    <p>Player: <b><%= username %></b></p>
    <ul>
        <% if (items.isEmpty()) { %>
            <li>No items yet.</li>
        <% } else {
            for (String it : items) { %>
                <li><%= it %></li>
        <%  }
        } %>
    </ul>
    <p><a href="home.jsp">Back</a></p>
</body>
</html>
