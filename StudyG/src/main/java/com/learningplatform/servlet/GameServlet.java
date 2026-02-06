package com.learningplatform.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.learningplatform.model.User;
import com.learningplatform.service.IGameService;
import com.learningplatform.service.impl.GameServiceImpl;
import com.learningplatform.util.ServletUtils;
import com.google.gson.Gson;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IGameService gameService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.gameService = new GameServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = ServletUtils.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            int gameTickets = user.getGameTickets();
            request.setAttribute("gameTickets", gameTickets);
            request.setAttribute("user", user);
            request.getRequestDispatcher("jsp/game.jsp").forward(request, response);
        } catch (Exception e) {
            ServletUtils.setErrorMessage(request, "Error loading game: " + e.getMessage());
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = ServletUtils.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("playMysteryBox".equals(action)) {
                IGameService.GameReward reward = gameService.playMysteryBox(user.getUserId());
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(new Gson().toJson(reward));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
