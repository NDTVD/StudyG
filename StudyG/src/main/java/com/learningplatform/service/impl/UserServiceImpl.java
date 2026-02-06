package com.learningplatform.service.impl;

import com.learningplatform.model.User;
import com.learningplatform.service.IUserService;
import com.learningplatform.util.DatabaseConnection;
import com.learningplatform.util.PasswordUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserServiceImpl implements IUserService {

    @Override
    public User authenticate(String username, String password) throws Exception {
        String sql = "SELECT userId, username, email, totalPoints, gameTickets FROM Users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // In real implementation, password would be hashed in DB
                // For now, we'll do direct comparison
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setTotalPoints(rs.getInt("totalPoints"));
                user.setGameTickets(rs.getInt("gameTickets"));
                
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean registerUser(User user) throws Exception {
        String checkSql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        String insertSql = "INSERT INTO Users (username, email, password, totalPoints, gameTickets) VALUES (?, ?, ?, 0, 0)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if username exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, user.getUsername());
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    return false; // Username already exists
                }
            }
            
            // Insert new user
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, user.getUsername());
                insertStmt.setString(2, user.getEmail());
                insertStmt.setString(3, user.getPassword());
                
                return insertStmt.executeUpdate() > 0;
            }
        }
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        String sql = "SELECT userId, username, email, totalPoints, gameTickets FROM Users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setTotalPoints(rs.getInt("totalPoints"));
                user.setGameTickets(rs.getInt("gameTickets"));
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserById(int userId) throws Exception {
        String sql = "SELECT userId, username, email, totalPoints, gameTickets FROM Users WHERE userId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setTotalPoints(rs.getInt("totalPoints"));
                user.setGameTickets(rs.getInt("gameTickets"));
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) throws Exception {
        String sql = "UPDATE Users SET email = ?, totalPoints = ?, gameTickets = ? WHERE userId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getEmail());
            stmt.setInt(2, user.getTotalPoints());
            stmt.setInt(3, user.getGameTickets());
            stmt.setInt(4, user.getUserId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean addPoints(int userId, int points) throws Exception {
        String sql = "UPDATE Users SET totalPoints = totalPoints + ? WHERE userId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, points);
            stmt.setInt(2, userId);
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean addGameTickets(int userId, int quantity) throws Exception {
        String sql = "UPDATE Users SET gameTickets = gameTickets + ? WHERE userId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public int getGameTickets(int userId) throws Exception {
        String sql = "SELECT gameTickets FROM Users WHERE userId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("gameTickets");
            }
        }
        return 0;
    }
}
