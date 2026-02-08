package com.studg.dao;

import com.studg.model.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDAO implements UserDAO {
    private static final Map<String, User> users = new HashMap<>();

    static {
        User u = new User("alice", "pass"); u.setPoints(150); u.setPlays(1);
        users.put(u.getUsername(), u);
        users.put("bob", new User("bob","pass"));
    }

    @Override
    public User findByUsername(String username) {
        return users.get(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public Map<String, User> getAll() { return Collections.unmodifiableMap(users); }
}
