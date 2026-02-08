package com.studg.servlet;

import com.studg.dao.InMemoryUserDAO;
import com.studg.dao.UserDAO;
import com.studg.model.User;
import com.studg.service.SimpleRewardService;
import com.studg.service.RewardService;
import com.studg.service.SubRewardService;
import com.studg.service.SimpleSubRewardService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TestServlet extends HttpServlet {
    private UserDAO userDAO;
    private RewardService rewardService;
    private SubRewardService subRewardService;

    @Override
    public void init() throws ServletException {
        userDAO = com.studg.dao.DAOFactory.getUserDAO();
        rewardService = new SimpleRewardService();
        subRewardService = new SimpleSubRewardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("subject", req.getParameter("subject"));
        req.setAttribute("question", "What is 1+1?");
        req.getRequestDispatcher("test.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subject = req.getParameter("subject");
        String answer = req.getParameter("answer");
        HttpSession session = req.getSession(false);
        if (session==null || session.getAttribute("user")==null) { resp.sendRedirect("login.jsp"); return; }
        String username = (String) session.getAttribute("user");
        User user = userDAO.findByUsername(username);
        if (user==null) { resp.sendRedirect("login.jsp"); return; }

        boolean ok = "2".equals(answer.trim());
        if (ok) {
            // reward for learning (different from mystery box)
            String reward = subRewardService.rewardForLearning();
            // apply reward
            if (reward.startsWith("points:")) {
                int pts = Integer.parseInt(reward.split(":")[1]);
                user.setPoints(user.getPoints()+pts);
                session.setAttribute("points", user.getPoints());
            } else if (reward.startsWith("play:")) {
                int pl = Integer.parseInt(reward.split(":")[1]);
                user.setPlays(user.getPlays()+pl);
                session.setAttribute("plays", user.getPlays());
            } else if (reward.startsWith("item:")) {
                // store as last reward in session
                req.setAttribute("rewardItem", reward.split(":")[1]);
            }
            userDAO.save(user);
            req.setAttribute("subject", subject);
            req.setAttribute("question", "Answered successfully. Reward: "+reward);
            req.getRequestDispatcher("test.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("subject", subject);
        req.setAttribute("question", "Wrong answer, try again later.");
        req.getRequestDispatcher("test.jsp").forward(req, resp);
    }
}
