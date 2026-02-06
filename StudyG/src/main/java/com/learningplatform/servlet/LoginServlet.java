package com.learningplatform.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.learningplatform.model.User;
import com.learningplatform.service.IUserService;
import com.learningplatform.service.impl.UserServiceImpl;
import com.learningplatform.util.ServletUtils;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        if (ServletUtils.isUserLoggedIn(request)) {
            response.sendRedirect("home");
            return;
        }
        request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Validate inputs
            if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                ServletUtils.setErrorMessage(request, "Username and password are required");
                request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
                return;
            }

            // Authenticate user
            User user = userService.authenticate(username, password);
            if (user != null) {
                // Set user in session
                ServletUtils.setCurrentUser(request, user);
                ServletUtils.setSuccessMessage(request, "Login successful!");
                response.sendRedirect("home");
            } else {
                ServletUtils.setErrorMessage(request, "Invalid username or password");
                request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            ServletUtils.setErrorMessage(request, "An error occurred during login: " + e.getMessage());
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }
    }
}
