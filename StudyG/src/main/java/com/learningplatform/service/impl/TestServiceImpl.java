package com.learningplatform.service.impl;

import com.learningplatform.model.Test;
import com.learningplatform.service.ITestService;
import com.learningplatform.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestServiceImpl implements ITestService {

    @Override
    public boolean recordTest(Test test) throws Exception {
        String sql = "INSERT INTO Tests (userId, subjectId, section, score, passed, testDate) " +
                     "VALUES (?, ?, ?, ?, ?, GETDATE())";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, test.getUserId());
            stmt.setInt(2, test.getSubjectId());
            stmt.setInt(3, test.getSection());
            stmt.setInt(4, test.getScore());
            stmt.setBoolean(5, test.isPassed());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Test getTestById(int testId) throws Exception {
        String sql = "SELECT testId, userId, subjectId, section, score, passed, testDate FROM Tests WHERE testId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, testId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Test test = new Test();
                test.setTestId(rs.getInt("testId"));
                test.setUserId(rs.getInt("userId"));
                test.setSubjectId(rs.getInt("subjectId"));
                test.setSection(rs.getInt("section"));
                test.setScore(rs.getInt("score"));
                test.setPassed(rs.getBoolean("passed"));
                test.setTestDate(rs.getString("testDate"));
                return test;
            }
        }
        return null;
    }

    @Override
    public boolean hasUserPassedSection(int userId, int subjectId, int section) throws Exception {
        String sql = "SELECT COUNT(*) FROM Tests WHERE userId = ? AND subjectId = ? AND section = ? AND passed = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, subjectId);
            stmt.setInt(3, section);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            return rs.getInt(1) > 0;
        }
    }

    @Override
    public int getTotalPointsFromTests(int userId) throws Exception {
        String sql = "SELECT COALESCE(SUM(s.points), 0) as totalPoints FROM Tests t " +
                     "INNER JOIN Subjects s ON t.subjectId = s.subjectId " +
                     "WHERE t.userId = ? AND t.passed = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            return rs.getInt("totalPoints");
        }
    }

    @Override
    public int getTicketsFromPoints(int totalPoints) throws Exception {
        // 100 points = 1 game ticket
        return totalPoints / 100;
    }
}
