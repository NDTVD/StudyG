package com.learningplatform.service.impl;

import com.learningplatform.service.IGameService;
import com.learningplatform.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class GameServiceImpl implements IGameService {

    private static final Random random = new Random();

    @Override
    public GameReward playMysteryBox(int userId) throws Exception {
        // Check if user has game tickets
        if (!hasGameTickets(userId)) {
            throw new Exception("No game tickets available");
        }

        // Deduct a ticket
        deductGameTicket(userId);

        // Get random reward
        return getRandomReward();
    }

    @Override
    public boolean hasGameTickets(int userId) throws Exception {
        String sql = "SELECT gameTickets FROM Users WHERE userId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("gameTickets") > 0;
            }
        }
        return false;
    }

    @Override
    public boolean deductGameTicket(int userId) throws Exception {
        String sql = "UPDATE Users SET gameTickets = gameTickets - 1 WHERE userId = ? AND gameTickets > 0";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public GameReward getRandomReward() throws Exception {
        int randomValue = random.nextInt(100);
        
        // 40% chance: 50 points
        if (randomValue < 40) {
            return new GameReward(RewardType.POINTS, 50, "You won 50 points!");
        }
        // 35% chance: 100 points
        else if (randomValue < 75) {
            return new GameReward(RewardType.POINTS, 100, "You won 100 points!");
        }
        // 20% chance: 1 game ticket
        else if (randomValue < 95) {
            return new GameReward(RewardType.GAME_TICKET, 1, "You won 1 game ticket!");
        }
        // 5% chance: Rare decoration item
        else {
            return new GameReward(RewardType.RARE_ITEM, 1, "You won a rare decoration item!");
        }
    }
}
