package com.studg.dao;

import com.studg.util.DBUtil;

import java.sql.Connection;

public class DAOFactory {
    public static UserDAO getUserDAO() {
        try (Connection c = DBUtil.getConnection()) {
            if (c!=null) return new MSSQLUserDAO();
        } catch (Exception e) {
            // fallback
        }
        return new InMemoryUserDAO();
    }
}
