package com.studg.service;

import com.studg.model.User;

public interface AuthService {
    User authenticate(String username, String password);
}
