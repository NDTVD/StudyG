package com.learningplatform.service.impl;

import com.learningplatform.model.Subject;
import com.learningplatform.service.ISubjectService;
import com.learningplatform.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SubjectServiceImpl implements ISubjectService {

    @Override
    public List<Subject> getAllSubjects() throws Exception {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT subjectId, subjectName, description, points FROM Subjects";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subjectId"));
                subject.setSubjectName(rs.getString("subjectName"));
                subject.setDescription(rs.getString("description"));
                subject.setPoints(rs.getInt("points"));
                subjects.add(subject);
            }
        }
        return subjects;
    }

    @Override
    public Subject getSubjectById(int subjectId) throws Exception {
        String sql = "SELECT subjectId, subjectName, description, points FROM Subjects WHERE subjectId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, subjectId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subjectId"));
                subject.setSubjectName(rs.getString("subjectName"));
                subject.setDescription(rs.getString("description"));
                subject.setPoints(rs.getInt("points"));
                return subject;
            }
        }
        return null;
    }

    @Override
    public List<Subject> getCompletedSubjects(int userId) throws Exception {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT DISTINCT s.subjectId, s.subjectName, s.description, s.points " +
                     "FROM Subjects s INNER JOIN UserSubjectProgress usp ON s.subjectId = usp.subjectId " +
                     "WHERE usp.userId = ? AND usp.completed = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subjectId"));
                subject.setSubjectName(rs.getString("subjectName"));
                subject.setDescription(rs.getString("description"));
                subject.setPoints(rs.getInt("points"));
                subjects.add(subject);
            }
        }
        return subjects;
    }

    @Override
    public List<Subject> getAvailableSubjects(int userId) throws Exception {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.subjectId, s.subjectName, s.description, s.points " +
                     "FROM Subjects s WHERE s.subjectId NOT IN " +
                     "(SELECT subjectId FROM UserSubjectProgress WHERE userId = ? AND completed = 1)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subjectId"));
                subject.setSubjectName(rs.getString("subjectName"));
                subject.setDescription(rs.getString("description"));
                subject.setPoints(rs.getInt("points"));
                subjects.add(subject);
            }
        }
        return subjects;
    }

    @Override
    public boolean markSubjectComplete(int userId, int subjectId) throws Exception {
        String checkSql = "SELECT COUNT(*) FROM UserSubjectProgress WHERE userId = ? AND subjectId = ?";
        String insertSql = "INSERT INTO UserSubjectProgress (userId, subjectId, completed) VALUES (?, ?, 1)";
        String updateSql = "UPDATE UserSubjectProgress SET completed = 1 WHERE userId = ? AND subjectId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if progress exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, subjectId);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                
                if (rs.getInt(1) > 0) {
                    // Update existing record
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, userId);
                        updateStmt.setInt(2, subjectId);
                        return updateStmt.executeUpdate() > 0;
                    }
                } else {
                    // Insert new record
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, userId);
                        insertStmt.setInt(2, subjectId);
                        return insertStmt.executeUpdate() > 0;
                    }
                }
            }
        }
    }

    @Override
    public boolean isSubjectCompleted(int userId, int subjectId) throws Exception {
        String sql = "SELECT COUNT(*) FROM UserSubjectProgress WHERE userId = ? AND subjectId = ? AND completed = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, subjectId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            return rs.getInt(1) > 0;
        }
    }

    @Override
    public Subject getRandomLearnedSubject(int userId) throws Exception {
        String sql = "SELECT s.subjectId, s.subjectName, s.description, s.points " +
                     "FROM Subjects s INNER JOIN UserSubjectProgress usp ON s.subjectId = usp.subjectId " +
                     "WHERE usp.userId = ? AND usp.completed = 1 ORDER BY NEWID()";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subjectId"));
                subject.setSubjectName(rs.getString("subjectName"));
                subject.setDescription(rs.getString("description"));
                subject.setPoints(rs.getInt("points"));
                return subject;
            }
        }
        return null;
    }
}
