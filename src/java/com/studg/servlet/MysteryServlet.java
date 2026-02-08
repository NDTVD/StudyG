package com.studg.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MysteryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // block access if user has no plays
        javax.servlet.http.HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        Integer plays = (Integer) session.getAttribute("plays");
        if (plays == null) plays = 0;
        if (plays <= 0) {
            req.getRequestDispatcher("mystery_blocked.jsp").forward(req, resp);
            return;
        }
        // show the 16-box mystery page
        req.getRequestDispatcher("mystery.jsp").forward(req, resp);
    }
}
