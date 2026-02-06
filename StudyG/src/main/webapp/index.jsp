<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.learningplatform.util.ServletUtils" %>
<%
    // Check if user is logged in
    if (ServletUtils.isUserLoggedIn(request)) {
        response.sendRedirect("home");
    } else {
        response.sendRedirect("login");
    }
%>
