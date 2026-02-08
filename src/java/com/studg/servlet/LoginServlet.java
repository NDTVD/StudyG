package com.studg.servlet;

import com.studg.dao.InMemoryUserDAO;
import com.studg.dao.UserDAO;
import com.studg.model.User;
import com.studg.service.AuthService;
import com.studg.service.SimpleAuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private AuthService authService;

    @Override
    public void init() throws ServletException {
        com.studg.dao.UserDAO userDAO = com.studg.dao.DAOFactory.getUserDAO();
        authService = new SimpleAuthService(userDAO);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");
        User user = authService.authenticate(u, p);
        if (user==null) {
            resp.sendRedirect("login.jsp?error=Invalid+credentials");
            return;
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("user", user.getUsername());
        session.setAttribute("points", user.getPoints());
        session.setAttribute("plays", user.getPlays());
        resp.sendRedirect("home.jsp");
    }
}
