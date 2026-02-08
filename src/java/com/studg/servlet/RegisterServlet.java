package com.studg.servlet;

import com.studg.dao.UserDAO;
import com.studg.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = com.studg.dao.DAOFactory.getUserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            resp.sendRedirect("register.jsp?error=Missing+fields");
            return;
        }
        if (userDAO.findByUsername(username) != null) {
            resp.sendRedirect("register.jsp?error=Username+exists");
            return;
        }
        User u = new User(username, password);
        // default starter reward
        u.setPoints(100);
        u.setPlays(1);
        userDAO.save(u);
        resp.sendRedirect("login.jsp?success=Registered+successfully");
    }
}
