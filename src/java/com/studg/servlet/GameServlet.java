package com.studg.servlet;

import com.studg.dao.InMemoryUserDAO;
import com.studg.dao.UserDAO;
import com.studg.model.User;
import com.studg.service.RewardService;
import com.studg.service.SimpleRewardService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GameServlet extends HttpServlet {
    private UserDAO userDAO;
    private RewardService rewardService;

    @Override
    public void init() throws ServletException {
        userDAO = com.studg.dao.DAOFactory.getUserDAO();
        rewardService = new SimpleRewardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session==null || session.getAttribute("user")==null) { resp.sendRedirect("login.jsp"); return; }
        String username = (String) session.getAttribute("user");
        User user = userDAO.findByUsername(username);
        if (user==null) { resp.sendRedirect("login.jsp"); return; }

        String action = req.getParameter("action");
        if ("play".equals(action)) {
            if (user.getPlays()<=0) {
                req.setAttribute("reward", "No plays available.");
                req.getRequestDispatcher("game.jsp").forward(req, resp); return;
            }
            user.setPlays(user.getPlays()-1);
            String reward = rewardService.openMysteryBox();
            if (reward.startsWith("points:")) {
                int pts = Integer.parseInt(reward.split(":")[1]);
                user.setPoints(user.getPoints()+pts);
                session.setAttribute("points", user.getPoints());
            } else if (reward.startsWith("play:")) {
                int pl = Integer.parseInt(reward.split(":")[1]);
                user.setPlays(user.getPlays()+pl);
                session.setAttribute("plays", user.getPlays());
            } else if (reward.startsWith("item:")) {
                req.setAttribute("reward", reward.split(":")[1]);
            }
            userDAO.save(user);
            session.setAttribute("plays", user.getPlays());
            req.setAttribute("reward", reward);
            req.getRequestDispatcher("game.jsp").forward(req, resp); return;
        }
        req.getRequestDispatcher("game.jsp").forward(req, resp);
    }
}
