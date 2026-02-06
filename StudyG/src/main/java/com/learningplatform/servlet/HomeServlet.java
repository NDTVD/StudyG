package com.learningplatform.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.learningplatform.model.User;
import com.learningplatform.util.ServletUtils;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in
        User user = ServletUtils.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        // Set user info in request
        request.setAttribute("user", user);
        request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
    }
}
