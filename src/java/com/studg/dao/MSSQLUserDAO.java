package com.studg.dao;

import com.studg.model.User;
import com.studg.util.DBUtil;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MSSQLUserDAO implements UserDAO {

    @Override
    public User findByUsername(String username) {
        String sqlUser = "SELECT username, password, points, plays FROM users WHERE username = ?";
        String sqlSubjects = "SELECT subject FROM learned_subjects WHERE username = ?";
        String sqlItems = "SELECT item_key FROM profile_items WHERE username = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sqlUser)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                User u = new User();
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setPoints(rs.getInt("points"));
                u.setPlays(rs.getInt("plays"));
                // get learned subjects
                try (PreparedStatement ps2 = c.prepareStatement(sqlSubjects)) {
                    ps2.setString(1, username);
                    try (ResultSet rs2 = ps2.executeQuery()) {
                        Set<String> subs = new HashSet<>();
                        while (rs2.next()) subs.add(rs2.getString("subject"));
                        for (String s: subs) u.addSubject(s);
                    }
                }
                // get profile items
                try (PreparedStatement psi = c.prepareStatement(sqlItems)) {
                    psi.setString(1, username);
                    try (ResultSet ri = psi.executeQuery()) {
                        Set<String> items = new HashSet<>();
                        while (ri.next()) items.add(ri.getString("item_key"));
                        for (String it: items) u.addItem(it);
                    }
                }
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(User user) {
        String upsert = "MERGE INTO users WITH (HOLDLOCK) AS T USING (SELECT ? AS username) AS S ON T.username = S.username "
                + "WHEN MATCHED THEN UPDATE SET password = ?, points = ?, plays = ? "
                + "WHEN NOT MATCHED THEN INSERT (username, password, points, plays) VALUES (?, ?, ?, ?);";
        String deleteSubjects = "DELETE FROM learned_subjects WHERE username = ?";
        String insertSubject = "INSERT INTO learned_subjects(username, subject) VALUES(?,?)";
        String deleteItems = "DELETE FROM profile_items WHERE username = ?";
        String insertItem = "INSERT INTO profile_items(username, item_key) VALUES(?,?)";
        try (Connection c = DBUtil.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(upsert)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword()); ps.setInt(3, user.getPoints()); ps.setInt(4, user.getPlays());
                ps.setString(5, user.getUsername()); ps.setString(6, user.getPassword()); ps.setInt(7, user.getPoints()); ps.setInt(8, user.getPlays());
                ps.executeUpdate();
            }
            try (PreparedStatement psd = c.prepareStatement(deleteSubjects)) {
                psd.setString(1, user.getUsername()); psd.executeUpdate();
            }
            try (PreparedStatement psi = c.prepareStatement(insertSubject)) {
                for (String s: user.getLearnedSubjects()) {
                    psi.setString(1, user.getUsername()); psi.setString(2, s); psi.addBatch();
                }
                psi.executeBatch();
            }
            try (PreparedStatement pd = c.prepareStatement(deleteItems)) {
                pd.setString(1, user.getUsername()); pd.executeUpdate();
            }
            try (PreparedStatement pi = c.prepareStatement(insertItem)) {
                for (String it: user.getProfileItems()) {
                    pi.setString(1, user.getUsername()); pi.setString(2, it); pi.addBatch();
                }
                pi.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, User> getAll() {
        Map<String, User> map = new HashMap<>();
        String sql = "SELECT username FROM users";
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                String uname = rs.getString("username");
                User u = findByUsername(uname);
                if (u!=null) map.put(uname, u);
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return map;
    }
}
