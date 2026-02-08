package com.studg.servlet;

import com.studg.dao.InMemoryUserDAO;
import com.studg.dao.UserDAO;
import com.studg.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShopServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = com.studg.dao.DAOFactory.getUserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("shop.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String buy = req.getParameter("buy");
        HttpSession session = req.getSession(false);
        if (session==null || session.getAttribute("user")==null) { resp.sendRedirect("login.jsp"); return; }
        String username = (String) session.getAttribute("user");
        User user = userDAO.findByUsername(username);
        if (user==null) { resp.sendRedirect("login.jsp"); return; }

        if ("play".equals(buy)) {
            if (user.getPoints()>=100) {
                user.setPoints(user.getPoints()-100);
                user.setPlays(user.getPlays()+1);
                session.setAttribute("points", user.getPoints());
                session.setAttribute("plays", user.getPlays());
            }
        } else if ("item".equals(buy)) {
            if (user.getPoints()>=200) {
                user.setPoints(user.getPoints()-200);
                session.setAttribute("points", user.getPoints());
                // item purchase placeholder: could add to profile items
            }
        }
        userDAO.save(user);
        resp.sendRedirect("shop");
    }
}
