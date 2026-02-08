<%@ page import="java.util.*" %>
<%@ page session="true" %>
<%
    String user = (String) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String subject = (String) request.getAttribute("subject");
    if (subject == null) {
        subject = request.getParameter("subject");
    }
    String question = (String) request.getAttribute("question");
%>
<!DOCTYPE html>
<html>
    <head><meta charset="UTF-8"><title>Test</title>
        <link rel="stylesheet" href="css/style.css" />
    </head>
    <body>
        <h2>Quick Review: <%= subject%></h2>
        <form action="test" method="post">
            <input type="hidden" name="subject" value="<%= subject%>"/>
            Answer: <input type="text" name="answer" required/>
            <button type="submit">Submit</button>
        </form>
        <p><a href="home.jsp">Back</a></p>
    </body>
</html>
