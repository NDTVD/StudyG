package com.studg.service;

import com.studg.dao.UserDAO;
import com.studg.model.User;

public class SimpleAuthService implements AuthService {
    private final UserDAO userDao;

    public SimpleAuthService(UserDAO userDao) { this.userDao = userDao; }

    @Override
    public User authenticate(String username, String password) {
        User u = userDao.findByUsername(username);
        if (u!=null && u.getPassword()!=null && u.getPassword().equals(password)) return u;
        return null;
    }
}
