package com.studg.servlet;

import com.studg.dao.DAOFactory;
import com.studg.dao.UserDAO;
import com.studg.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LearnServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = DAOFactory.getUserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        String username = (String) session.getAttribute("user");
        User user = userDAO.findByUsername(username);
        if (user == null) { resp.sendRedirect("login.jsp"); return; }

        String subject = req.getParameter("subject");
        if (subject == null || subject.trim().isEmpty()) { resp.sendRedirect("subjects"); return; }

        // rule: cannot learn a subject already learned
//        if (user.getLearnedSubjects().contains(subject)) {
//            req.setAttribute("message", "You already learned this subject.");
//            req.getRequestDispatcher("subjects.jsp").forward(req, resp);
//            return;
//        }

        user.addSubject(subject);
        userDAO.save(user);

        // update session counts
        session.setAttribute("points", user.getPoints());
        session.setAttribute("plays", user.getPlays());

        // store learned subjects in a cookie (URL-encoded to avoid invalid characters)
        List<String> list = new ArrayList<>(user.getLearnedSubjects());
        String joined = String.join(",", list);
        String encoded = URLEncoder.encode(joined, StandardCharsets.UTF_8.name());
        Cookie c = new Cookie("learned", encoded);
        c.setPath(req.getContextPath().isEmpty()?"/":req.getContextPath());
//        c.setMaxAge(60*60*24*30); // 30 days
        resp.addCookie(c);

        // forward to a quick test for the subject
        req.setAttribute("subject", subject);
        req.setAttribute("question", "Quick test for " + subject + ": What is 1+1?");
        req.getRequestDispatcher("test.jsp").forward(req, resp);
    }

    // helper to read cookie value (URL-decoded)
    public static String readDecodedCookieValue(Cookie cookie) {
        if (cookie == null) return null;
        try {
            return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.name());
        } catch (Exception e) { return cookie.getValue(); }
    }
}
