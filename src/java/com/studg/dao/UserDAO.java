package com.studg.dao;

import com.studg.model.User;
import java.util.Map;

public interface UserDAO {
    User findByUsername(String username);
    void save(User user);
    Map<String, User> getAll();
}
