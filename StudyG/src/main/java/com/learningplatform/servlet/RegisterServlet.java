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
import com.learningplatform.util.PasswordUtils;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
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
        request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            // Validate inputs
            if (username == null || username.trim().isEmpty()) {
                ServletUtils.setErrorMessage(request, "Username is required");
                request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
                return;
            }

            if (!ServletUtils.isValidEmail(email)) {
                ServletUtils.setErrorMessage(request, "Invalid email format");
                request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
                return;
            }

            if (password == null || !password.equals(confirmPassword)) {
                ServletUtils.setErrorMessage(request, "Passwords do not match");
                request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
                return;
            }

            if (!PasswordUtils.isStrongPassword(password)) {
                ServletUtils.setErrorMessage(request, 
                    "Password must be at least 8 characters with uppercase, lowercase, and numbers");
                request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
                return;
            }

            // Create user with hashed password
            User newUser = new User(username, email, PasswordUtils.hashPassword(password));
            
            if (userService.registerUser(newUser)) {
                ServletUtils.setSuccessMessage(request, "Registration successful! Please login.");
                response.sendRedirect("login");
            } else {
                ServletUtils.setErrorMessage(request, "Username already exists");
                request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            ServletUtils.setErrorMessage(request, "An error occurred during registration: " + e.getMessage());
            request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
        }
    }
}
